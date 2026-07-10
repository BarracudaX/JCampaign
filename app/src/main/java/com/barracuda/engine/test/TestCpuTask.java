package com.barracuda.engine.test;

import com.barracuda.engine.domain.*;
import com.barracuda.engine.task.CpuTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TestCpuTask extends CpuTask {

    private long remainingNanos;
    private final Logger logger = LoggerFactory.getLogger(TestCpuTask.class);

    public TestCpuTask(String name,long id,Duration duration) {
        super(name,id);
        this.remainingNanos = duration.toNanos();
    }

    @Override
    public TaskResult executeTask(TimeSlot timeSlot) {

        logger.info("Executing task {}",this);
        double dummyValue = 0.12345;

        long loopStartTime = System.nanoTime();

        while (remainingNanos > 0 && !Thread.currentThread().isInterrupted()) {

            if(timeSlot.hasExpired()){
                logger.info("TimeSlot has expired. Task {} needs more time",this);
                remainingNanos -= (System.nanoTime() - loopStartTime);
                return TaskResult.NEED_MORE_TIME;
            }

            dummyValue = Math.sin(Math.cos(Math.tan(dummyValue))) + Math.log(dummyValue + 1.0);
            long now = System.nanoTime();
            remainingNanos -= (now - loopStartTime);
            loopStartTime = now;
        }

        if(Thread.currentThread().isInterrupted()){
            logger.info("Task {} has been requested to pause",this);
            return TaskResult.PAUSED;
        }

        if (dummyValue == 999.999) {
            System.out.println("If this prints, you broke mathematics: " + dummyValue);
        }

        logger.info("Finished executing task {}",this);

        return TaskResult.COMPLETED;
    }

}
