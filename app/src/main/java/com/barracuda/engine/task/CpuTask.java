package com.barracuda.engine.task;

import com.barracuda.engine.domain.TaskResult;
import com.barracuda.engine.domain.TimeSlot;
import com.barracuda.engine.test.TaskNeedMoreTimeException;
import lombok.ToString;
import org.slf4j.MDC;

public non-sealed abstract class CpuTask extends AbstractTask {

    protected CpuTask(String name,long id) {
        super(name,id);
    }

    public final void execute(TimeSlot timeSlot) {

        MDC.put("task", " - ["+name+"-"+id+"]");
        MDC.put("taskType", " - [CPU]");
        try{
            executeTask(timeSlot);
        }finally {
            MDC.remove("task");
            MDC.remove("taskType");
        }

    }

    protected abstract void executeTask(TimeSlot timeSlot);
}
