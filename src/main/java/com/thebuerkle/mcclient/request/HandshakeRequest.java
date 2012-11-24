package com.thebuerkle.mcclient.request;
import org.apache.mina.core.buffer.IoBuffer;

public class HandshakeRequest extends Request {

    public final String username;
    public final String host;
    public final int port;

    public HandshakeRequest(String username, String host, int port) {
        super(0x02);

        this.username = username;
        this.host = host;
        this.port = port;
    }

    @Override()
    public int getSize() {
        return 1 + (2 + this.username.length()*2) + (2+ this.host.length()*2) + 4;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_byte(out, 49); // 1.4.4 protocol version
        mc_string(out, username);
        mc_string(out, host);
        mc_int(out, port);
    }
}
