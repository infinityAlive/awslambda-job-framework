package pers.infinityalive.awslambda.job.framework;

@FunctionalInterface
public interface SimpleJob {

  void exec() throws Exception;
}
