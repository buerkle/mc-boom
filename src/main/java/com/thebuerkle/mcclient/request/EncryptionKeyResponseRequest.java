package com.thebuerkle.mcclient.request;

import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.mina.core.buffer.IoBuffer;

public class EncryptionKeyResponseRequest extends Request {

    private final byte[] _secret;
    private final byte[] _token;

    public EncryptionKeyResponseRequest(PublicKey key, SecretKey secret, byte[] token) {
        super(0xFC);

        _secret = encrypt(key, secret.getEncoded());
        _token = encrypt(key, token);
    }

    private static byte[] encrypt(PublicKey key, byte[] toEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(toEncrypt);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override()
    public void write(IoBuffer out) {
        mc_bytearray_2(out, _secret);
        mc_bytearray_2(out, _token);
    }
}
