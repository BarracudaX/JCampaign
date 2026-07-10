package com.barracuda.engine.test;

import com.barracuda.engine.domain.TaskResult;
import com.barracuda.engine.task.IOTask;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@ToString
public class TestIOTask extends IOTask {

    private final Duration duration;
    private final Duration sleep;
    private final Logger logger = LoggerFactory.getLogger(TestIOTask.class);

    public TestIOTask(Duration sleep,String name, long id, Duration duration) {
        super(name,id);
        this.duration = duration;
        this.sleep = sleep;
    }

    @Override
    public TaskResult executeTask() {
        return work();
    }


    private TaskResult work(){

        logger.info("Executing task {}",this);

        long endTime = System.nanoTime() + duration.toNanos();

        while(System.nanoTime() < endTime && !Thread.currentThread().isInterrupted()){
            logger.info("Task doing some work... {}",this);
            try {
                logger.info("About to block for {}",sleep);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return TaskResult.PAUSED;
            }
        }

        if(System.nanoTime() >= endTime){
            logger.info("Finished executing task {}",this);
            return TaskResult.COMPLETED;
        }

        if(Thread.currentThread().isInterrupted()){
            logger.info("Task {} has been requested to pause",this);
            return TaskResult.PAUSED;
        }

        throw new IllegalStateException("Should never reach this point");

    }
}
