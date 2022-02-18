package server;

import connection.*;
import network.Request;
import network.RequestOpsState;
import network.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static java.nio.channels.SelectionKey.*;

public class Server implements Runnable{
    private final ExecutorService readRequests = Executors.newFixedThreadPool(10);
    private final ExecutorService handleRequests = new ForkJoinPool(10);
    private final ExecutorService sendResponses = Executors.newCachedThreadPool();
    private final ServerConnectionManagerImpl2 connectionListener;
    private final RequestReader requestReader;
    private final RequestHandler requestHandler;
    private final ResponseSender responseSender;
    private final Selector selector;
    private final Map<SocketChannel, Response> responseMap = new HashMap<>();
    private final List<RequestOpsState> changeRequests = new ArrayList<>();


    public Server(ServerConnectionManagerImpl2 listener, RequestReader reader, RequestHandler handler, ResponseSender sender) throws IOException {
        connectionListener = listener;
        requestReader = reader;
        requestHandler = handler;
        responseSender = sender;
        selector = connectionListener.openConnection();
    }

    @Override
    public void run() {
        while(true) {
            try {
                synchronized (changeRequests) {
                    for (RequestOpsState requestOpsState : changeRequests) {
                        if (requestOpsState.getType() == RequestOpsState.CHANGEOPS) {
                            try {
                                SelectionKey key = requestOpsState.getSocketChannel().keyFor(selector);
                                key.interestOps(requestOpsState.getOps());
                            } catch (Exception e){
                                log.Logback.getLogger().error("error with selection keys");
                                log.Logback.getLogger().error(e.getMessage());
                            }
                        } else if (requestOpsState.getType() == RequestOpsState.DEREGISTER) {
                            SelectionKey key = requestOpsState.getSocketChannel().keyFor(selector);
                            key.cancel();
                            requestOpsState.getSocketChannel().close();
                        }
                    }
                    changeRequests.clear();
                }


                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        connectionListener.accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        sendResponse(key);
                        key.interestOps(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void read(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        Request request = requestReader.readRequest(selectionKey);
        Runnable readingRequestRunnable = () -> {
                handleRequest(request);
        };
        readRequests.submit(readingRequestRunnable);
    }


    private void handleRequest(Request request) {
        Runnable handlingRequestRunnable = () -> {
            Response response = requestHandler.handleRequest(request);
            response.setSocketChannel(request.getSocketChannel());
            prepareToSend(response);
            this.selector.wakeup();
        };
        handleRequests.submit(handlingRequestRunnable);
    }

    private void prepareToSend(Response response) {
        synchronized (changeRequests) {
            changeRequests.add(new RequestOpsState(response.getSocketChannel(), RequestOpsState.CHANGEOPS, OP_WRITE));
            synchronized (responseMap) {
                responseMap.put(response.getSocketChannel(), response);
            }
        }
        this.selector.wakeup();
    }


    private void sendResponse(SelectionKey selectionKey) {
        Runnable sendingResponseRunnable = () -> {
            try {
                synchronized (responseMap) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    responseSender.sendResponse(channel, responseMap.get(selectionKey.channel()));
                }
                selector.wakeup();
            } catch (IOException e) {
                log.Logback.getLogger().error(e.getMessage());
                e.printStackTrace();
            }
        };
        sendResponses.submit(sendingResponseRunnable);
    }
    public void shutdownExecutorServices() {
        readRequests.shutdownNow();
        handleRequests.shutdown();
        sendResponses.shutdown();
    }

}