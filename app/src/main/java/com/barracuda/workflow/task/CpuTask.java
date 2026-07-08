package com.barracuda.workflow.task;

import com.barracuda.workflow.domain.TaskResult;
import com.barracuda.workflow.domain.TimeSlot;
import com.barracuda.workflow.domain.Workflow;
import lombok.ToString;
import org.slf4j.MDC;

@ToString
public non-sealed abstract class CpuTask extends AbstractTask {

    protected CpuTask(String name,long id) {
        super(name,id);
    }

    public final TaskResult execute(TimeSlot timeSlot, Workflow workflow){

        MDC.put("task", " - ["+name+"-"+id+"]");
        MDC.put("taskType", " - [CPU]");
        try{
            return executeTask(timeSlot,workflow);
        }finally {
            MDC.remove("task");
            MDC.remove("taskType");
        }

    }

    protected abstract TaskResult executeTask(TimeSlot timeSlot, Workflow workflow);
}
