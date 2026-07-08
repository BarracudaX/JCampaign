package com.barracuda.workflow.task;

import com.barracuda.workflow.domain.TaskResult;
import com.barracuda.workflow.domain.Workflow;
import com.barracuda.workflow.domain.WorkflowStatus;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@ToString
public class TestIOTask extends IOTask{

    private final Duration duration;
    private final Logger logger = LoggerFactory.getLogger(TestIOTask.class);

    public TestIOTask(String name, long id, Duration duration) {
        super(name,id);
        this.duration = duration;
    }

    @Override
    public TaskResult executeTask(Workflow workflow) {
        return work(workflow);
    }


    private TaskResult work(Workflow workflow){

        logger.info("Executing task {}",this);

        long endTime = System.nanoTime() + duration.toNanos();

        while(System.nanoTime() < endTime && workflow.getWorkflowStatus() != WorkflowStatus.REQUEST_PAUSING){
            try {

                logger.info("Task going to block {}",this);
                Thread.sleep(500);
                logger.info("Task doing some work after unblocking {}",this);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        if(workflow.getWorkflowStatus() == WorkflowStatus.REQUEST_PAUSING || Thread.currentThread().isInterrupted()){
            logger.info("Task {} has been requested to pause",this);
            return TaskResult.PAUSED;
        }

        logger.info("Finished executing task {}",this);
        return TaskResult.COMPLETED;
    }
}
