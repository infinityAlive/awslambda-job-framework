package pers.infinityalive.awslambda.job;

import pers.infinityalive.awslambda.job.framework.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
@Configuration
public class MainWorkEntry {

  public static void main(String[] args) {
    String beanConfigPath = args[0];
    String jobName = args[1];

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(beanConfigPath);
    SimpleJob simpleJob = applicationContext.getBean(jobName, SimpleJob.class);

    try {
      simpleJob.exec();
    } catch (Exception error) {
      log.error("Happening error for run job, ", error);
    }
  }
}
