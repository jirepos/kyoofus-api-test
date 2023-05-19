package com.jirepo.demo.batch;

import java.time.LocalDateTime;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class DemoTasklet implements Tasklet  {

  public DemoTasklet() {
    log.debug("DemoTasklet created:" + LocalDateTime.now().toString());
  }

  @Override
  public RepeatStatus execute(StepContribution arg0, ChunkContext context) throws Exception {
    log.debug(("DemoTasklet executed"));
    // StepContext stepContext = context.getStepContext();
    // stepContext.getJobParameters();
    //IoUtils.writeStringToFile(new File("d:\\batch.log"), "Batch has finished.", "UTF-8");
    return RepeatStatus.FINISHED;
  }
  
}
