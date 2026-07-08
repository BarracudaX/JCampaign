package com.barracuda.workflow.work;

import com.barracuda.workflow.domain.Workflow;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ParallelWork  {
//
//    private final List<Workflow> workflows;
//    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
//
//    ParallelWork(List<Workflow> workflows) {
//        this.workflows = List.copyOf(workflows);
//    }
//
//
//    @Override
//    public void start() {
//
//        try {
//            List<Callable<Void>> callableWorkflows = workflows
//                    .stream()
//                    .map(workflow -> (Callable<Void>) () -> { workflow.execute(); return null;})
//                    .toList();
//            executor.invokeAll(callableWorkflows);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @Override
//    public void resume() {
//        for (Workflow workflow : workflows) {
//            workflow.resume();
//        }
//    }
//
//    @Override
//    public void pause() {
//        for(Workflow workflow : workflows){
//            workflow.pause();
//        }
//    }
}
