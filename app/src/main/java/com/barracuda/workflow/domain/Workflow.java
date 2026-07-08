package com.barracuda.workflow.domain;

import com.barracuda.workflow.work.Work;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Note workflow pausing should be done through the pause() method. In addition, in responsiveness is needed, users can interrupt the virtual thread in which the workflow is running but this should be done after calling the pause method.
 */
@ToString(onlyExplicitlyIncluded = true)
public class Workflow {

    private final AtomicReference<WorkflowStatus> workflowStatus = new AtomicReference<>(WorkflowStatus.CREATED);
    private final AtomicInteger currentlyRunningTaskIndex = new AtomicInteger(0);
    private final Logger logger = LoggerFactory.getLogger(Workflow.class);
    @ToString.Include
    private final String name;
    private final long id;
    @Getter
    private final ExecutorService cpuExecutorService;
    private final List<Work> works;
    
    public Workflow(String name, long id, ExecutorService cpuExecutorService, List<Work> works) {
        this.name = name;
        this.id = id;
        this.cpuExecutorService = cpuExecutorService;
        this.works = List.copyOf(works);
    }

    /**
     * Starts the workflow or continues from where it stopped.
     */
    public void execute(){
        MDC.put("workflow", " - ["+name+"-"+id+"]");

        if (!Thread.currentThread().isVirtual()) {
            logger.error("Workflow {} has not been started in the virtual thread",this);
            throw new IllegalStateException("Workflow should be executed in a virtual thread.");
        }


        logger.info("Executing workflow {} starting at work {}\n", this,currentlyRunningTaskIndex.get());
        if(!workflowStatus.compareAndSet(WorkflowStatus.CREATED, WorkflowStatus.RUNNING) && !workflowStatus.compareAndSet(WorkflowStatus.PAUSED, WorkflowStatus.RUNNING)) {
            throw new IllegalStateException("Workflow cannot be executed due to its current state being "+ workflowStatus.get().name());
        }

        int startIndex = currentlyRunningTaskIndex.get();

        for(int i = startIndex; i < works.size(); i++){
            Work work = works.get(i);
            logger.info("Executing work {}\n", work);

            try{
                switch (work.execute(this)){
                    case COMPLETED -> {
                        logger.info("Work {} completed\n", work);
                        currentlyRunningTaskIndex.incrementAndGet();
                    }
                    case PAUSED -> {
                        if(!workflowStatus.compareAndSet(WorkflowStatus.REQUEST_PAUSING,WorkflowStatus.PAUSED)){
                            logger.error("Work {} has been paused without pausing being requested", work);
                            throw new IllegalStateException("Work returned due to requested pausing but no request pausing was detected. Current workflow state is "+workflowStatus.get().name());
                        }
                        logger.info("Workflow {} paused\n", this);
                        return;
                    }
                }
            }catch (Exception e){
                logger.error("Error executing work {}\n", work, e);
                workflowStatus.set(WorkflowStatus.FAILED);
                throw e;
            }finally {
                MDC.remove("workflow");
            }

        }

        logger.info("Workflow {} completed\n", this);
        MDC.remove("workflow");
        workflowStatus.set(WorkflowStatus.COMPLETED);
    }

    /**
     * Asynchronously requesting the workflow to pause.
     */
    public void pause(){
        logger.info("Requesting workflow {} to be paused\n", this);
        if (!workflowStatus.compareAndSet(WorkflowStatus.RUNNING, WorkflowStatus.REQUEST_PAUSING)) {
            logger.error("Workflow {} cannot be paused because of its current state being {}", this, workflowStatus.get().name());
            throw new IllegalStateException("Cannot pause a workflow due to its current state being "+workflowStatus.get().name());
        }
    }

    public WorkflowStatus getWorkflowStatus(){ return workflowStatus.get(); }
    
}
