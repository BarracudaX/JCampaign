package com.barracuda.engine.work;

import lombok.ToString;
import org.slf4j.MDC;

@ToString(onlyExplicitlyIncluded = true)
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
    public final WorkResult execute() {
        MDC.put("work", " - ["+name+"-"+id+"]");
        try{
            return executeWork();
        } finally {
            MDC.remove("work");
        }
    }

    protected abstract WorkResult executeWork();

}
