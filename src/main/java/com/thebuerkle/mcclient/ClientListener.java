package com.thebuerkle.mcclient;

public interface ClientListener {
    void onConnect(Client client);

    void onDisconnect(Client client);
}
