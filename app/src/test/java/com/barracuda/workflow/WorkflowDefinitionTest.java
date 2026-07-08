package com.barracuda.workflow;

import com.barracuda.workflow.domain.Workflow;
import com.barracuda.workflow.domain.WorkflowStatus;
import com.barracuda.workflow.task.TestCpuTask;
import com.barracuda.workflow.task.TestIOTask;
import com.barracuda.workflow.work.SequentialWork;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkflowDefinitionTest {

    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Test
    void test() throws InterruptedException, ExecutionException {

        Workflow workflow = new Workflow(
                "Test Workflow", 1,
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
                List.of(
                        new SequentialWork( "TestSeqWork",1L,
                                List.of(
                                        new TestIOTask("TestIoTask",1, Duration.ofSeconds(2)),
                                        new TestCpuTask("TestCpuTask",1,Duration.ofSeconds(1))
                                )),
                        new SequentialWork("TestSeqWork",2L,
                                List.of(
                                        new TestIOTask("TestIoTask",2, Duration.ofSeconds(2)),
                                        new TestCpuTask("TestCpuTask",2,Duration.ofSeconds(5))
                                ))
                ));


        virtualThreadExecutor.submit(workflow::execute);

        Thread.sleep(4000);

        workflow.pause();

        Awaitility.await().until( () -> workflow.getWorkflowStatus() == WorkflowStatus.PAUSED);

        virtualThreadExecutor.submit(workflow::execute);

        Awaitility.await().atMost(Duration.ofSeconds(20)).until(() -> workflow.getWorkflowStatus() == WorkflowStatus.COMPLETED);
    }

    @Test
    void shouldBeAbleToDefineWorkflowDefinitions() {
//        WorkflowDefinition workflowDefinition = RootWorkflowDefinitionBuilder
//                .builder()
//                .workflowConfig(flowConfig -> {
//                    flowConfig.name("Flow Name");
//                }).work(step -> {
//                    step.name("Send email to employee");
//                }).decisionStep(decisionStep -> {
//                    decisionStep.name("Deciding Payment Increase");
//
//                    decisionStep
//                            .decision(flow -> {})
//                            .decision(flow -> {});
//
//                })
//                .parallelStep(parallelStep -> {
//                    parallelStep.name("Parallel Step").description("Parallel Step Description");
//
//                    parallelStep
//                            .path(flow -> {})
//                            .path(flow -> {})
//                            .path(flow -> {});
//                }).build();

//        WorkflowDefinitionVerifier
//                .verify(workflowDefinition, workflow -> {
//                    workFlow.hasName("Flow Name");
//                })
//                .step( stepVerifier -> {
//                    stepVerifier.hasName("Send email to employee");
//                })
//                .desicionStep( decisionStepVerifier -> {
//                    decisionStepVerifier.hasDecision( flow -> {
//
//                    });
//
//                    decisionStepVerifier.hasDecision( flow -> {
//
//                    });
//                })
//                .parallelStep( parallelStepVerifier -> {
//                    parallelStepVerifier.hasPath( flow -> {
//
//                    });
//                    parallelStepVerifier.hasPath(flow -> {
//
//                    });
//                    parallelStepVerifier.hasPath( flow -> {
//
//                    });
//                }).verify();


    }
}
