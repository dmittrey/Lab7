package utility;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class RequestHandler {

    private final Invoker invoker;
    private final Executor deliverManager;

    public RequestHandler(Invoker anInvoker, Executor aDeliverManager) {
        invoker = anInvoker;
        deliverManager = aDeliverManager;
    }

    public void process(Request request, DatagramSocket datagramSocket, SocketAddress socketAddress) {
        ForkJoinPool requestHandler = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 3);
        Task task = new Task(invoker, request);
        Response response = requestHandler.invoke(task);

        Deliver deliver = new Deliver(datagramSocket, response, socketAddress);
        deliverManager.execute(deliver);
    }
}
