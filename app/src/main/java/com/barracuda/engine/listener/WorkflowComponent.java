package com.barracuda.engine.listener;

import com.barracuda.engine.workflow.WorkflowContext;

/**
 * This method is for internal usage by the workflow components to be notified of workflow initialization.
 */
public interface WorkflowComponent {

    void configure(WorkflowContext context);

}
