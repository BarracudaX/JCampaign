package com.barracuda.engine.work;

import com.barracuda.engine.domain.TaskResult;
import com.barracuda.engine.domain.TimeSlot;
import com.barracuda.engine.task.CpuTask;
import com.barracuda.engine.task.IOTask;
import com.barracuda.engine.task.Task;
import com.barracuda.engine.workflow.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An abstraction of unit of work that consists of steps.
 */
public class SequentialWork extends AbstractWork {

    private final AtomicInteger currentlyRunningTaskIndex = new AtomicInteger(0);
    private final List<Task> tasks;
    private final Logger logger = LoggerFactory.getLogger(SequentialWork.class);
    private volatile Duration cpuTimeSlot;
    private volatile ExecutorService cpuExecutorService;

    public SequentialWork(String name, long id, List<Task> tasks) {
        super(name, id);
        this.tasks = List.copyOf(tasks);
    }

    @Override
    protected final WorkResult executeWork() {
        int startIndex = currentlyRunningTaskIndex.get();

        logger.info("Executing work {} starting at task {}\n", this, currentlyRunningTaskIndex.get());

        for (int i = startIndex; i < tasks.size(); i++) {

            Task task = tasks.get(i);

            logger.info("Executing task {}\n", task);

            switch(runTask(task)){
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


    private TaskResult runTask(Task task) {
        return switch (task){
            case CpuTask cpuTask -> runCPUTask(cpuTask);
            case IOTask ioTask -> runIOTask(ioTask);
        };
    }

    private TaskResult runCPUTask(CpuTask cpuTask) {

        while(true) {
            TaskResult taskResult;
            Future<TaskResult> cpuTaskFuture = null;
            Map<String, String> logContext = MDC.getCopyOfContextMap();
            try {
                cpuTaskFuture = cpuExecutorService.submit(() -> {
                    if(logContext != null){ MDC.setContextMap(logContext); }
                    return cpuTask.execute(new TimeSlot(cpuTimeSlot));
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

    private TaskResult runIOTask(IOTask ioTask) {
        return ioTask.execute();
    }


    @Override
    public void configure(WorkflowContext context) {
        this.cpuExecutorService = context.cpuExecutorService();
        this.cpuTimeSlot = context.cpuTimeSlot();
        for(Task task : tasks) {
            task.configure(context);
        }
    }
}
