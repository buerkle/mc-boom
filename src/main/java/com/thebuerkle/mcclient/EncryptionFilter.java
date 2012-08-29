package com.thebuerkle.mcclient;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.util.WriteRequestFilter;

public class EncryptionFilter extends WriteRequestFilter {

    private final Cipher _encrypt;
    private final Cipher _decrypt;

    public EncryptionFilter(SecretKey secret) {
        try {
            _encrypt = Cipher.getInstance("AES/CFB8/NoPadding");
            _encrypt.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(secret.getEncoded()));

            _decrypt = Cipher.getInstance("AES/CFB8/NoPadding");
            _decrypt.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(secret.getEncoded()));
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override()
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        if (message instanceof IoBuffer) {
/*          byte[] enc = new byte[((IoBuffer) message).remaining()];*/
/*          ((IoBuffer) message).get(enc);                          */
/*          ((IoBuffer) message).flip();                            */
/*          System.err.println("ENCRYPTED: " + toHex(enc));*/
/*          IoBuffer b = transform(_decrypt, (IoBuffer) message);*/

/*          System.err.println("DECRYPTED: " + b);*/
/*          byte[] bytes = new byte[b.remaining()];*/
/*          b.get(bytes);                          */

/*          System.err.println("DECRYPTED: " + toHex(bytes));*/
/*          b.flip();                              */
/*          nextFilter.messageReceived(session, b);*/
            nextFilter.messageReceived(session, transform(_decrypt, (IoBuffer) message));
        }
        nextFilter.messageReceived(session, message);
    }

    private String toHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(hex(b[i])).append(' ');
        }
        return sb.toString();
    }

    private static String hex(byte b) {
        String h = "0123456789ABCDEF";

        StringBuilder sb = new StringBuilder();
        sb.append(h.charAt((b >> 4) & 0x0F));
        sb.append(h.charAt(b & 0x0F));

        return  sb.toString();
    }

    @Override()
    protected Object doFilterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        IoBuffer buf = (IoBuffer) writeRequest.getMessage();

        if (buf.hasRemaining()) {
            return transform(_encrypt, buf);
        }
        return null; // ignore empty buffers
    }

    private IoBuffer transform(Cipher cipher, IoBuffer in) throws Exception { //GeneralSecurityException {
        int size = cipher.getOutputSize(in.remaining()+2);
        int r = in.remaining();
        IoBuffer result = IoBuffer.allocate(size);
        try {
            cipher.update(in.buf(), result.buf());
            result.flip();
            return result;
        }
        catch (Exception e) {
            System.err.println("---------- Input: " + size + ": " + in.position() + ": " + in.limit());
            System.err.println("---------- Output: " + result.position() + ": " + result.limit());
            throw e;
        }
    }
}
