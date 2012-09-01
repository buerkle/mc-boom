package com.thebuerkle.mcclient;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.util.WriteRequestFilter;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptionFilter extends WriteRequestFilter {

    private final BufferedBlockCipher _encrypt;
    private final BufferedBlockCipher _decrypt;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public EncryptionFilter(Key secret) {
        try {
            _encrypt = cipher(true, secret);
            _decrypt = cipher(false, secret);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    private BufferedBlockCipher cipher(boolean encrypt, Key key) {
        BufferedBlockCipher cipher = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
        cipher.init(encrypt, new ParametersWithIV(new KeyParameter(key.getEncoded()), key.getEncoded(), 0, 16));
        return cipher;
    }

    @Override()
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (message instanceof IoBuffer) {
            IoBuffer in = (IoBuffer) message;
            if (in.remaining() > 0) {
                nextFilter.messageReceived(session, transform(_decrypt, in));
            }
            else {
                System.err.println("--------------- ZERO");
            }
        }
        else {
            nextFilter.messageReceived(session, message);
        }
    }

    @Override()
    protected Object doFilterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        IoBuffer buf = (IoBuffer) writeRequest.getMessage();

        if (buf.hasRemaining()) {
            return transform(_encrypt, buf);
        }
        return null; // ignore empty buffers
    }

    private IoBuffer transform(BufferedBlockCipher cipher, IoBuffer in) throws Exception { //GeneralSecurityException {
        int size = cipher.getOutputSize(in.remaining());
        byte[] result = new byte[size];
        byte[] bytes = in.array();

        cipher.processBytes(bytes, in.position(), in.remaining(), result, 0);
        return IoBuffer.wrap(result);
    }

    public void exceptionCaught(NextFilter nextFilter, IoSession session,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        nextFilter.exceptionCaught(session, cause);
    }
}
