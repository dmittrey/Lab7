package utility;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Class that process requests
 */
public class RequestHandler {

    private final Invoker invoker;
    private final Executor deliverManager;
    private final ForkJoinPool forkJoinPool;

    public RequestHandler(Invoker anInvoker, Executor aDeliverManager, ForkJoinPool aForkJoinPool) {
        invoker = anInvoker;
        deliverManager = aDeliverManager;
        forkJoinPool = aForkJoinPool;
    }

    public void process(Request request, DatagramSocket datagramSocket, SocketAddress socketAddress) {
        Task task = new Task(invoker, request);
        Response response = forkJoinPool.invoke(task);

        Deliver deliver = new Deliver(datagramSocket, response, socketAddress);
        deliverManager.execute(deliver);
    }
}