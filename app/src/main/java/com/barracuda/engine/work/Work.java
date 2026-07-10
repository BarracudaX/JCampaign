package com.barracuda.engine.work;


import com.barracuda.engine.listener.WorkflowComponent;

public interface Work extends WorkflowComponent {

    WorkResult execute();
}
