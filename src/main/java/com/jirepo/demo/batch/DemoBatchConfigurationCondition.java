package com.jirepo.demo.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @EnableBatchProcessing
// @Configuration
public class DemoBatchConfigurationCondition {

    
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;


  // @Bean 
  public Step step10() {
    log.debug("step10");
    return stepBuilderFactory.get("step10")
              .tasklet((contribution, context) -> {
                log.debug("task10 was executed.");
                contribution.setExitStatus(ExitStatus.FAILED);  // ExitStatus를 FAILED로 지정하면 실패 
                return RepeatStatus.FINISHED;
              })
              .build();
  }//:
  // @Bean 
  public Step step10Alt() {
    log.debug("step10Alt");
    return stepBuilderFactory.get("step10Alt")
              .tasklet((contribution, context) -> {
                log.debug("task10Alt was executed.");
                return RepeatStatus.FINISHED;
              })
              .build();
  }//:
  // @Bean 
  public Step step20() {
    log.debug("step20");
    return stepBuilderFactory.get("step20")
              .tasklet((contribution, context) -> {
                log.debug("task20 was executed.");
                return RepeatStatus.FINISHED;
              })
              .build();
  }//:
  // @Bean 
  public Step step30() {
    log.debug("step20");
    return stepBuilderFactory.get("step30")
              .tasklet((contribution, context) -> {
                log.debug("task30 was executed.");
                return RepeatStatus.FINISHED;
              })
              .build();
  }//:




  // @Bean
  public Job flowJob() {

    return jobBuilderFactory.get("flowJob")
            // 실패 시나리오 :  step 10 -> step10Alt
            // 성공 시나리오 :  step 10 -> step 20 -> step 30
            .start(step10())
                .on("FAILED")       // catch 할 경우 ExitStatus 지정, * 일 경우 모든 ExitStatus가 지정
                .to(step10Alt())    // 다음으로 이동할 Step 지정 
                .on("*")     // step10Alt가 ExitStatus와 관계없이 모두 해당
                .end()       // setp10Alt로 이동하면 flow 종료, 아래 from에 해당하는 flow는 시작되지 않는다. 
            .from(step10()) // step 1으로 부터 
                .on("*")    // FAILED 이외의 모든 경우 
                .to(step20())  // step 2로 이동 
                .next(step30()) // step 2가 성공하면  step 3으로 이동
                .on("*")  // step 3 결과에 관계 없이 
                .end()    // Flow 종료 
            .end() // Job 종료 
            .build();
  }//:

  
}///~
