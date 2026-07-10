package com.barracuda.engine.listener;

public sealed interface WorkflowEvent {

    record WorkflowStartedEvent() implements WorkflowEvent{
    }

    record WorkflowCompletedEvent() implements WorkflowEvent{}

    record WorkflowFailedEvent() implements WorkflowEvent{}

    record WorkflowPausedEvent() implements WorkflowEvent{}

    record WorkflowResumedEvent() implements WorkflowEvent{}

    record WorkStartedEvent() implements WorkflowEvent{}

    record WorkCompletedEvent() implements WorkflowEvent{}

    record WorkFailedEvent() implements WorkflowEvent{}

    record WorkPausedEvent() implements WorkflowEvent{}

    record WorkResumedEvent() implements WorkflowEvent{}

    record TaskStartedEvent() implements WorkflowEvent{}

    record TaskCompletedEvent() implements WorkflowEvent{}

    record TaskFailedEvent() implements WorkflowEvent{}

    record TaskPausedEvent() implements WorkflowEvent{}

    record TaskResumedEvent() implements WorkflowEvent{}

    record SubWorkflowStartedEvent() implements WorkflowEvent{}

    record SubWorkflowCompletedEvent() implements WorkflowEvent{}

    record SubWorkflowFailedEvent() implements WorkflowEvent{}

    record SubWorkflowPausedEvent() implements WorkflowEvent{}

    record SubWorkflowResumedEvent() implements WorkflowEvent{}
}


