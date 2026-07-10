package com.barracuda.engine.workflow;

import com.barracuda.engine.listener.WorkflowComponent;
import com.barracuda.engine.listener.WorkflowEvent.*;
import com.barracuda.engine.listener.WorkflowEventPublisher;
import com.barracuda.engine.work.Work;

import java.util.List;

public class SubWorkflow extends AbstractWorkflow implements WorkflowComponent {

    private final List<Work> works;
    private volatile WorkflowEventPublisher workflowEventPublisher;

    public SubWorkflow(String name, long id, List<Work> works) {
        super(name, id);
        this.works = List.copyOf(works);
    }


    @Override
    protected void workflowStarting() {
        if(currentlyRunningTaskIndex.get() == 0){
            workflowEventPublisher.publishEvent(new SubWorkflowStartedEvent());
        }else {
            workflowEventPublisher.publishEvent(new SubWorkflowResumedEvent());
        }
    }

    @Override
    protected void workflowCompleted() {
        workflowEventPublisher.publishEvent(new SubWorkflowCompletedEvent());
    }

    @Override
    protected void workFailed(Exception e, Work work) {
        workflowEventPublisher.publishEvent(new SubWorkflowFailedEvent());
    }

    @Override
    protected void workPaused(Work work) {
        workflowEventPublisher.publishEvent(new SubWorkflowPausedEvent());
    }

    @Override
    protected List<Work> works() {
        return works;
    }

    @Override
    public void configure(WorkflowContext context) {
        this.workflowEventPublisher = context.getEventPublisher();
        for(Work work : works){
            work.configure(context);
        }
    }
}
