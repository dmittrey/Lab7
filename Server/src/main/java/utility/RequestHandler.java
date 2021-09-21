package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class RequestHandler {

    private final Invoker invoker;
    private final AutoGenFieldsSetter fieldsSetter;
    private final Executor deliverManager;

    public RequestHandler(Invoker anInvoker, AutoGenFieldsSetter aFieldsSetter, Executor aDeliverManager) {
        invoker = anInvoker;
        fieldsSetter = aFieldsSetter;
        deliverManager = aDeliverManager;
    }

    public void process(Request request, DatagramSocket datagramSocket, SocketAddress socketAddress) {
        ForkJoinPool requestHandler = new ForkJoinPool(Runtime.getRuntime().availableProcessors()/3);
        Task task = new Task(invoker, fieldsSetter.setFields(request));
        Response response = requestHandler.invoke(task);

        Deliver deliver = new Deliver(datagramSocket, response, socketAddress);
        deliverManager.execute(deliver);
    }
}
