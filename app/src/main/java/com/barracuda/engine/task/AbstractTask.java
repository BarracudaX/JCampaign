package com.barracuda.engine.task;

import com.barracuda.engine.workflow.WorkflowContext;

public abstract sealed class AbstractTask implements Task permits IOTask,CpuTask{

    protected final String name;
    protected final long id;

    protected AbstractTask(String name, long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void configure(WorkflowContext context) {
    }
}
