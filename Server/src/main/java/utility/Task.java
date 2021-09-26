package utility;

import java.util.concurrent.RecursiveTask;

/**
 * Class that implement task for ForkJoinPool
 */
public class Task extends RecursiveTask<Response> {

    private final Invoker invoker;
    private final Request request;

    public Task(Invoker anInvoker, Request aRequest) {
        invoker = anInvoker;
        request = aRequest;
    }

    @Override
    protected Response compute() {
        return invoker.execute(request);
    }
}