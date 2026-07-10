package com.barracuda.engine.workflow;

import com.barracuda.engine.domain.WorkflowStatus;
import com.barracuda.engine.listener.WorkflowEventPublisher;
import com.barracuda.engine.listener.WorkflowExecutionListener;
import com.barracuda.engine.work.Work;

public interface RootWorkflow extends Workflow, WorkflowEventPublisher {

    /**
     * Registered this listener for workflow events
     * @param listener to add to the listeners of this workflow
     */
    void registerListener(WorkflowExecutionListener listener);

    WorkflowStatus status();

    void addWork(Work work);

    /**
     * Initializes the workflow. Must be called before executing this workflow.
     */
    void initialize();
}
