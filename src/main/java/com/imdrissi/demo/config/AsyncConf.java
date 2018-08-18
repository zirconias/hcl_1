package com.imdrissi.demo.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConf implements AsyncConfigurer {

  @Value("${async.pool-size}")
  private int poolSize;

  @Value("${async.max-pool-size}")
  private int maxPoolSize;

  private static final String TASK_EXECUTOR_DEFAULT = "taskExecutor";
  private static final String TASK_EXECUTOR_NAME_PREFIX_DEFAULT = "taskExecutor-";
  private static final String TASK_EXECUTOR_NAME_PREFIX_SERVICE = "serviceTaskExecutor-";

  public static final String TASK_EXECUTOR_SERVICE = "serviceTaskExecutor";

  @Override
  @Bean(name = TASK_EXECUTOR_DEFAULT)
  public Executor getAsyncExecutor() {
    return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_DEFAULT);
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new SimpleAsyncUncaughtExceptionHandler();
  }

  @Bean(name = TASK_EXECUTOR_SERVICE)
  public Executor getServiceAsyncExecutor() {
    return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_SERVICE);
  }

  private Executor newTaskExecutor(String taskExecutorNamePrefixDefault) {

    final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    executor.setCorePoolSize(poolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setThreadNamePrefix(taskExecutorNamePrefixDefault);

    return executor;
  }
}
