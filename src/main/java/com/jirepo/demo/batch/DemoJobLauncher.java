package com.jirepo.demo.batch;

import java.time.LocalDateTime;

// import org.joda.time.LocalDate;
// import org.joda.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

// @EnableScheduling
// @Component 
@Slf4j 
public class DemoJobLauncher {

  @Autowired
	private JobLauncher jobLauncher;

  // @Autowired
  // private Job demoJob;

  // @Autowired
  // private Job job1;

  
  @Autowired
  private Job flowJob;

  // // 초 분 시간 일 월 요일 년 
  // //@Scheduled(cron="0/5/10/15/20/30/40/50 * * * * * *")
  // @Scheduled(fixedDelay = 10 * 1000L)
  // public void jobDemo() throws Exception  {
  //   log.debug("Before jobDemo starts." + LocalDate.now().toString());
  //   JobExecution jobExecution =jobLauncher.run(demoJob, 
  //                  new JobParametersBuilder()
  //                      //.addString("datetime" , LocalDateTime.now().toString())
  //                      .addString("idss" , "aaa"+ LocalDateTime.now().toString())
  //                      .toJobParameters());
  //   // while (jobExecution.isRunning()) {
  //   //     log.info("...");
  //   // }                       
  //   // log.info("Job Execution: " + jobExecution.getStatus());
	// 	// log.info("Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
	// 	// log.info("Job getJobId: " + jobExecution.getJobId());
	// 	// log.info("Job getExitStatus: " + jobExecution.getExitStatus());
	// 	// log.info("Job getJobInstance: " + jobExecution.getJobInstance());
	// 	// log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
	// 	// log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
	// 	// log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());    
  // }


  // // 초 분 시간 일 월 요일 년 
  // @Scheduled(fixedDelay = 10 * 1000L)
  // public void multiStepsDemo() throws Exception  {
  //   log.debug("batch job started.....");
  //   jobLauncher.run(job1, new JobParametersBuilder()
  //                    .addString("datetime" , LocalDateTime.now().toString())
  //                    .toJobParameters());
  // }
  
  // 초 분 시간 일 월 요일 년 
  @Scheduled(fixedDelay = 10 * 1000L)
  public void jobCondition() throws Exception  {
    log.debug("batch job started.....");
    jobLauncher.run(flowJob, new JobParametersBuilder()
                     .addString("datetime" , LocalDateTime.now().toString())
                     .toJobParameters());
  }

  
  
}///~
