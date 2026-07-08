package com.barracuda.workflow.task;

import com.barracuda.workflow.domain.TaskResult;
import com.barracuda.workflow.domain.Workflow;
import lombok.ToString;
import org.slf4j.MDC;

@ToString
public non-sealed abstract class IOTask extends AbstractTask {

    protected IOTask(String name,long id) {
        super(name,id);
    }

    public final TaskResult execute(Workflow workflow){

        MDC.put("task", " - ["+name+"-"+id+"]");
        MDC.put("taskType", " - [IO]");
        try{
            return executeTask(workflow);
        }finally {
            MDC.remove("task");
            MDC.remove("taskType");
        }
    }

    protected abstract TaskResult executeTask(Workflow workflow);
}
