package pers.infinityalive.awslambda.job.framework;

import java.util.Map;
import lombok.Getter;

@Getter
class Response {

  private final String message;
  private final Map<String, Object> input;

  Response(String message, Map<String, Object> input) {
    this.message = message;
    this.input = input;
  }
}
