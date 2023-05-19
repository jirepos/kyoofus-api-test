package com.jirepo.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Configuration
// @EnableBatchProcessing
public class DemoBatchConfiguration {
  
  // @Autowired
  // public JobBuilderFactory jobBuilderFactory;

  // @Autowired
  // public StepBuilderFactory stepBuilderFactory;

  // //@Autowired 
  // //private DemoTasklet demoTasklet; 
  
  // @Bean
  // @JobScope 
  // public Tasklet sampleTasklet(@Value("#{jobParameters[idss]}") String id) {
  //    //public Tasklet sampleTasklet() {
  //   //log.debug(">>>> id:" + id);
  //   return (contribution, context) -> { 
  //     log.debug("################# tasklet created.");
  //     return RepeatStatus.FINISHED;
  //   };
  // }//:


  // @Bean 
  // public Step demoStep(@Autowired Tasklet sampleTasklet) {
  //   log.debug(">>>> Step has created");
  //   return stepBuilderFactory.get("demoStep")
  //             .tasklet(sampleTasklet)
  //             // .tasklet(new DemoTasklet())
  //             //.tasklet(demoTasklet)
  //              .build();
  // }//:


  // @Bean 
  // public Job demoJob(@Autowired Step demoStep) {
  //   return jobBuilderFactory.get("demoJob")
  //           .start(demoStep)
  //           .build();
  // }



  // @Bean 
  // public Step step1() {
  //   return stepBuilderFactory.get("step1")
  //             .tasklet((contribution, context) -> {
  //               log.debug("task1 was executed.");
  //               return RepeatStatus.FINISHED;
  //               //return RepeatStatus.CONTINUABLE;
  //             })
  //             .build();
  // }//:
  
  // @Bean 
  // public Step step2() {
  //   return stepBuilderFactory.get("step2")
  //             .tasklet((contribution, context) -> {
  //               log.debug("task2 was executed.");
  //               return RepeatStatus.FINISHED;
  //               //return RepeatStatus.CONTINUABLE;
  //             })
  //             .build();
  // }//:

  
  // @Bean 
  // //public Job job1(@Autowired Step step1, @Autowired Step step2) {
  // // 아래와 같이 작성하면 step이 실행되지 않는다. 
  // public Job job2() {
  //   return jobBuilderFactory.get("job1")
  //           .start(step1())  // 이런식으로 하면 step이 실행이 되지 않음 
  //           .next(step2())
  //           .build();
  // }

  
  // // https://jojoldu.tistory.com/328
  // @Bean 
  // public Job job1(@Autowired Step step1, @Autowired Step step2) {
  //   return jobBuilderFactory.get("job1")
  //           .start(step1)  
  //           .next(step2)
  //           .build();
  // }


  
}///~
