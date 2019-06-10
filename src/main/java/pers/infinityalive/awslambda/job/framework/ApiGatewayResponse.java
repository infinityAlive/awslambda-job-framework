package pers.infinityalive.awslambda.job.framework;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ApiGatewayResponse {

  private final int httpStatus;
  private final String payload;
  private final Map<String, String> headers;

  private ApiGatewayResponse(int status, String payload, Map<String, String> headers) {
    this.httpStatus = status;
    this.payload = payload;
    this.headers = headers;
  }

  static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private int httpStatus = 200;
    private Map<String, String> headers = Collections.emptyMap();
    private Object payload;

    Builder setHttpStatus(int httpStatus) {
      this.httpStatus = httpStatus;
      return this;
    }


    Builder setPayload(Object payload) {
      this.payload = payload;
      return this;
    }


    ApiGatewayResponse build() {
      String jsonPayload = "";
      if (ofNullable(payload).isPresent()) {
        try {
          jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException error) {
          log.error("Failed to serialize payload", error);
          throw new RuntimeException(error);
        }
      }
      return new ApiGatewayResponse(httpStatus, jsonPayload, headers);
    }
  }
}