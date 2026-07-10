package com.barracuda.engine.task;

import com.barracuda.engine.listener.WorkflowComponent;

/**
 * An abstraction of executable step. A step should be either IO or CPU bound.
 */
public sealed interface Task extends WorkflowComponent permits AbstractTask {

}
