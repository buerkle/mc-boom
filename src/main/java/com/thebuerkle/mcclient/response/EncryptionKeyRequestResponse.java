package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.mina.core.buffer.IoBuffer;

public class EncryptionKeyRequestResponse extends Response {

    public static final int ID = 0xFD;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
            DataType.mc_bytearray_2,
            DataType.mc_bytearray_2
    };

    public final String serverId;
    public final PublicKey key;
    public final byte[] token;

    public EncryptionKeyRequestResponse(IoBuffer in) {
        this.serverId = mc_string(in);

        byte[] key = mc_bytearray_2(in);

        this.token = mc_bytearray_2(in);

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
            this.key = factory.generatePublic(spec);
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
        catch (InvalidKeySpecException e) {
            throw new Error(e);
        }
    }

    @Override()
    public int getId() {
        return ID;
    }
}
