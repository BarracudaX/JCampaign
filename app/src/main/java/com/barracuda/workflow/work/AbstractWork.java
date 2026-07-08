package com.barracuda.workflow.work;

import com.barracuda.workflow.domain.Workflow;
import lombok.ToString;
import org.slf4j.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

abstract class AbstractWork implements Work {

    @ToString.Include
    protected final String name;
    @ToString.Include
    protected final long id;

    AbstractWork(String name, long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public final WorkResult execute(Workflow workflow) {
        MDC.put("work", " - ["+name+"-"+id+"]");
        try{
            return executeWork(workflow);
        } finally {
            MDC.remove("work");
        }
    }

    protected abstract WorkResult executeWork(Workflow workflow);
}
