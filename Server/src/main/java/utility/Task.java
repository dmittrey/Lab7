package utility;

import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<Response> {

    private final Invoker invoker;
    private final Request request;

    public Task(Invoker anInvoker, Request aRequest) {
        invoker = anInvoker;
        request = aRequest;
    }

    @Override
    protected Response compute() {
        System.out.println(request);
        System.out.println(invoker);
        return invoker.execute(request);
    }
}
