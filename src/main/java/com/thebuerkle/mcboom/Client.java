package com.thebuerkle.mcboom;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class Client extends SimpleChannelUpstreamHandler {

    private final String _host;
    private final int _port;
    private final String _user;

    private volatile Channel _channel;


    public Client(String host, int port, String user) {
        _host = host;
        _port = port;
        _user = user;
    }

    public void connect(ClientBootstrap bootstrap) {
        bootstrap.getPipeline().addLast("client", this);
    }

    public void send() {
    }
}
