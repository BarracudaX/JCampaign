package com.barracuda.engine.workflow;

import com.barracuda.engine.domain.WorkflowStatus;
import com.barracuda.engine.listener.WorkflowEvent;
import com.barracuda.engine.listener.WorkflowEvent.WorkflowResumedEvent;
import com.barracuda.engine.listener.WorkflowEvent.WorkflowStartedEvent;
import com.barracuda.engine.listener.WorkflowExecutionListener;
import com.barracuda.engine.work.Work;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class RootWorkflowImpl extends AbstractWorkflow implements RootWorkflow{

    private final CopyOnWriteArrayList<WorkflowExecutionListener> listeners = new CopyOnWriteArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(RootWorkflowImpl.class);
    private final WorkflowContext context;
    @ToString.Include
    private final AtomicReference<WorkflowStatus> workflowStatus = new AtomicReference<>(WorkflowStatus.NOT_INITIALIZED);
    private final List<Work> works = Collections.synchronizedList(new ArrayList<>());

    public RootWorkflowImpl(String name, long id, Duration cpuTimeSlot, ExecutorService cpuExecutorService) {
        super(name, id);
        this.context = new WorkflowContext(cpuExecutorService, cpuTimeSlot, this);
    }

    @Override
    protected void workflowStarting() {
        if(!workflowStatus.compareAndSet(WorkflowStatus.INITIALIZED, WorkflowStatus.RUNNING) && !workflowStatus.compareAndSet(WorkflowStatus.PAUSED, WorkflowStatus.RUNNING)) {
            throw new IllegalStateException("Workflow cannot be executed due to its current state being "+ workflowStatus.get().name()+". The workflow needs to be in either INITIALIZED or PAUSED state in order to be executed.");
        }
        publishStartEvent();
    }

    @Override
    protected void workflowCompleted() {
        logger.info("Workflow {} completed\n", this);

        MDC.remove("workflow");

        publishEvent(new WorkflowEvent.WorkflowCompletedEvent());

        workflowStatus.set(WorkflowStatus.COMPLETED);
    }

    @Override
    protected void workFailed(Exception e, Work work) {
        logger.error("Error executing work {}\n", work, e);

        publishEvent(new WorkflowEvent.WorkflowFailedEvent());

        workflowStatus.set(WorkflowStatus.FAILED);
    }

    @Override
    protected void workPaused(Work work) {
        if (!workflowStatus.compareAndSet(WorkflowStatus.RUNNING, WorkflowStatus.PAUSED)) {
            throw new IllegalStateException("Requested pausing while workflow isn't running.");
        }

        publishEvent(new WorkflowEvent.WorkflowPausedEvent());

        logger.info("Workflow {} paused\n", this);
    }

    private void publishStartEvent() {
        if (currentlyRunningTaskIndex.get() == 0) {
            publishEvent(new WorkflowStartedEvent());
        } else {
            publishEvent(new WorkflowResumedEvent());
        }
    }

    @Override
    protected List<Work> works() {
        return works;
    }

    @Override
    public void registerListener(WorkflowExecutionListener listener){
        listeners.add(listener);
    }

    @Override
    public WorkflowStatus status() {
        return workflowStatus.get();
    }

    @Override
    public void addWork(Work work) {
        if(workflowStatus.get() != WorkflowStatus.NOT_INITIALIZED) {
            throw new IllegalStateException("Cannot add work because workflow state is " + workflowStatus.get().name());
        }
        works.add(work);
    }

    @Override
    public void initialize() {
        if(workflowStatus.get() != WorkflowStatus.NOT_INITIALIZED){
            throw new IllegalStateException("Workflow has already been initialize and is in "+workflowStatus.get().name()+" state.");
        }

        for(Work work : works) {
            work.configure(context);
        }

        if(!workflowStatus.compareAndSet(WorkflowStatus.NOT_INITIALIZED, WorkflowStatus.INITIALIZED)) {
            throw new IllegalStateException("Workflow cannot be initialized due to its current state being "+workflowStatus.get().name());
        }

    }

    public void publishEvent(WorkflowEvent event){
        for(WorkflowExecutionListener listener : listeners){
            listener.event(event);
        }
    }
}
