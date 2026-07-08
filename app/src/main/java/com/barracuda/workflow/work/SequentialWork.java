package com.barracuda.workflow.work;

import com.barracuda.workflow.domain.TaskResult;
import com.barracuda.workflow.domain.TimeSlot;
import com.barracuda.workflow.domain.Workflow;
import com.barracuda.workflow.task.CpuTask;
import com.barracuda.workflow.task.IOTask;
import com.barracuda.workflow.task.Task;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An abstraction of unit of work that consists of steps.
 */
@ToString(onlyExplicitlyIncluded = true)
public class SequentialWork extends AbstractWork {

    private final AtomicInteger currentlyRunningTaskIndex = new AtomicInteger(0);
    private final List<Task> tasks;
    private final Logger logger = LoggerFactory.getLogger(SequentialWork.class);

    public SequentialWork(String name, long id, List<Task> tasks) {
        super(name, id);
        this.tasks = List.copyOf(tasks);
    }

    @Override
    protected final WorkResult executeWork(Workflow workflow) {
        int startIndex = currentlyRunningTaskIndex.get();

        logger.info("Executing work {} starting at task {}\n", this,currentlyRunningTaskIndex.get());
        for (int i = startIndex; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            logger.info("Executing task {}\n", task);
            switch(runTask(task,workflow)){
                case COMPLETED -> {
                    logger.info("Completed task {}\n", task);
                    currentlyRunningTaskIndex.incrementAndGet();
                }
                case NEED_MORE_TIME -> throw new IllegalStateException("Task requesting more time should be handled internally");
                case PAUSED -> {
                    logger.info("Task {} paused\n", task);
                    return WorkResult.PAUSED;
                }
            }
        }

        logger.info("Executing work {} finished\n", this);
        return WorkResult.COMPLETED;
    }


    private TaskResult runTask(Task task, Workflow workflow) {
        return switch (task){
            case CpuTask cpuTask -> runCPUTask(cpuTask,workflow);
            case IOTask ioTask -> runIOTask(ioTask,workflow);
        };
    }

    private TaskResult runCPUTask(CpuTask cpuTask,Workflow workflow) {

        while(true) {

            TaskResult taskResult;
            Future<TaskResult> cpuTaskFuture = null;
            Map<String, String> logContext = MDC.getCopyOfContextMap();
            try {
                cpuTaskFuture = workflow.getCpuExecutorService().submit(() -> {
                    if(logContext != null){ MDC.setContextMap(logContext); }
                    return cpuTask.execute(new TimeSlot(Duration.ofSeconds(2)), workflow);
                });
                taskResult = cpuTaskFuture.get();
            } catch (InterruptedException e) {
                cpuTaskFuture.cancel(true);
                Thread.currentThread().interrupt();
                return TaskResult.PAUSED;
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            switch (taskResult) {
                case COMPLETED, PAUSED -> {
                    return taskResult;
                }
                case NEED_MORE_TIME -> { /* continue */ }
            }
        }

    }

    private TaskResult runIOTask(IOTask ioTask,Workflow workflow) {
        return ioTask.execute(workflow);
    }


}
