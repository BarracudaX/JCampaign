package com.barracuda.workflow.work;


import com.barracuda.workflow.domain.Workflow;

public interface Work {

    WorkResult execute(Workflow workflow);
}
