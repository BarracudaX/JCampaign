package com.barracuda.engine.work;

import com.barracuda.engine.listener.WorkflowComponent;
import com.barracuda.engine.workflow.SubWorkflow;
import com.barracuda.engine.workflow.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelWork extends AbstractWork implements WorkflowComponent {

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
    protected WorkResult executeWork() {
        logger.info("Executing parallel work {}",this);
        Map<String, String> logContext = MDC.getCopyOfContextMap();
        List<Callable<Void>> callables = workflows.stream().map( workflow -> (Callable<Void>)() -> {
            if(logContext != null){
                MDC.setContextMap(logContext);
            }
            workflow.execute();
            return null;
        }).toList();

        List<Future<Void>> results = callables.stream().map(executor::submit).toList();
        try {
            logger.info("Invoking all parallel works.");

            for(var result : results){
                result.get();
            }

        } catch (InterruptedException e) {
            logger.info("Parallel Work Interrupted. Pausing requested.");
            Thread.currentThread().interrupt();
            results.forEach(future -> future.cancel(true));
            return WorkResult.PAUSED;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if(nextWork != null) {
            logger.info("Parallel sub workflows finished. Executing the final work {}",nextWork);
            return nextWork.execute();
        }else{
            logger.info("Parallel sub workflows finished. No next work. Completed.");
            return WorkResult.COMPLETED;
        }
    }

    @Override
    public void configure(WorkflowContext context) {
        for(SubWorkflow subWorkflow : workflows){
            subWorkflow.configure(context);
        }
    }
}
