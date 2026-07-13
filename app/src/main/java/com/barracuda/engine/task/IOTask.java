package com.barracuda.engine.task;

import com.barracuda.engine.domain.TaskResult;
import lombok.ToString;
import org.slf4j.MDC;

@ToString
public non-sealed abstract class IOTask extends AbstractTask {

    protected IOTask(String name,long id) {
        super(name,id);
    }

    public final void execute(){

        MDC.put("task", " - ["+name+"-"+id+"]");
        MDC.put("taskType", " - [IO]");
        try{
            executeTask();
        }finally {
            MDC.remove("task");
            MDC.remove("taskType");
        }
    }

    protected abstract void executeTask();
}
