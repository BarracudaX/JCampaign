package com.barracuda.workflow.task;

/**
 * An abstraction of executable step. A step should be either IO or CPU bound.
 */
public sealed interface Task permits AbstractTask {

}
