package pers.infinityalive.awslambda.job.framework;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> requestBody, Context context) {
    return ApiGatewayResponse.builder()
      .build();
  }

  protected ApiGatewayResponse driveBySpringConfig(Map<String, Object> requestBody, String jobName) {
    ApplicationContext applicationContext =
      new ClassPathXmlApplicationContext(System.getenv("jobConfig"));

    SimpleJob simpleJob = applicationContext.getBean(jobName, SimpleJob.class);

    final int OK_200 = 200;
    final int INTERNAL_SERVER_ERROR_500 = 500;
    int httpStatus;
    String responseMessage;
    try {
      simpleJob.exec();
      httpStatus = OK_200;
      responseMessage = "Successfully！";
    } catch (Exception error) {
      log.error("Happening error for run job, ", error);
      httpStatus = INTERNAL_SERVER_ERROR_500;
      responseMessage = "Failure！";
    }

    Response responseBody = new Response(responseMessage, requestBody);
    return ApiGatewayResponse.builder()
      .setHttpStatus(httpStatus)
      .setPayload(responseBody)
      .build();
  }
}
