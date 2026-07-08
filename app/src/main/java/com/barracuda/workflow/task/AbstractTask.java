package com.barracuda.workflow.task;

import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

public abstract sealed class AbstractTask implements Task permits IOTask,CpuTask{

    protected final String name;
    protected final long id;

    protected AbstractTask(String name, long id) {
        this.name = name;
        this.id = id;
    }

}
