package com.barracuda.engine.workflow;

import com.barracuda.engine.listener.WorkflowEventPublisher;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

public final class WorkflowContext {

    private final ExecutorService cpuExecutorService;
    private final Duration cpuTimeSlot;
    private final RootWorkflow rootWorkflow;


    public WorkflowContext(ExecutorService cpuExecutorService, Duration cpuTimeSlot, RootWorkflow rootWorkflow) {
        this.cpuExecutorService = cpuExecutorService;
        this.cpuTimeSlot = cpuTimeSlot;
        this.rootWorkflow = rootWorkflow;
    }

    public WorkflowEventPublisher getEventPublisher() { return rootWorkflow; }

    public ExecutorService cpuExecutorService() {
        return cpuExecutorService;
    }

    public Duration cpuTimeSlot() {
        return cpuTimeSlot;
    }

}
