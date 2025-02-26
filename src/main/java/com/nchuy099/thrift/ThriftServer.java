package com.nchuy099.thrift;

import com.nchuy099.service.ProductService;
import com.nchuy099.thrift.genJava.OrderRequest;
import com.nchuy099.thrift.genJava.OrderResponse;
import com.nchuy099.thrift.genJava.OrderService;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    public static void main(String[] args) throws TTransportException {
        OrderServiceHandler handler = new OrderServiceHandler();
        OrderService.Processor<OrderServiceHandler> processor = new OrderService.Processor<>(handler);
        TServerTransport serverTransport = new TServerSocket(9090);
        TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
        System.out.println("Thrift Server is running...");
        server.serve();
    }
}

class OrderServiceHandler implements OrderService.Iface {
    @Override
    public OrderResponse calculateTotal(OrderRequest request) throws TException {
        double price = ProductService.getPrice(request.productId);
        double total = price * request.quantity;

        OrderResponse response = new OrderResponse();
        response.setResult(total);
        return response;
    }
}
