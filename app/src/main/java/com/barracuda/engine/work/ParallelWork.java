package com.barracuda.engine.work;

import com.barracuda.engine.listener.WorkflowComponent;
import com.barracuda.engine.workflow.SubWorkflow;
import com.barracuda.engine.workflow.Workflow;
import com.barracuda.engine.workflow.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.StructuredTaskScope.Joiner;

public class ParallelWork extends AbstractWork {

    private final Logger logger = LoggerFactory.getLogger(ParallelWork.class);
    private final List<SubWorkflow> workflows;
    private final Work nextWork;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public ParallelWork(String name, long id, List<SubWorkflow> workflows, Work nextWork) {
        super(name,id);
        this.workflows = List.copyOf(workflows);
        this.nextWork = nextWork;
    }

    @Override
    protected void executeWork() {
        logger.info("Executing parallel work {}",this);


        try(var scope = StructuredTaskScope.open(Joiner.awaitAllSuccessfulOrThrow())){
            logger.info("Invoking all parallel works.");

            for(var subworkflow : workflows){
                scope.fork(subworkflow::execute);
            }

            try {
                scope.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if(nextWork != null) {
            logger.info("Parallel sub workflows finished. Executing the final work {}",nextWork);
            nextWork.execute();
        }
        logger.info("Parallel sub workflows finished. No next work. Completed.");
    }

}
