package com.barracuda.workflow.builder;

import com.barracuda.workflow.definition.WorkflowDefinition;

/**
 * This class is for internal workflow definitions.
 */
public class WorkflowDefinitionBuilder extends AbstractWorkflowDefinitionBuilder<WorkflowDefinitionBuilder> {


    WorkflowDefinition build(){
        return new WorkflowDefinition(name, description, workDefinitions);
    }

    @Override
    public WorkflowDefinitionBuilder self() {
        return this;
    }
}
