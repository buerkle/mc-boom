package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.jboss.netty.buffer.ChannelBuffer;

public class EncryptionKeyRequestPacket extends Packet {

    public static final int ID = ENCRYPTION_KEY_REQUEST;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
            DataType.mc_bytearray_2,
            DataType.mc_bytearray_2
    };

    public final String serverId;
    public final PublicKey key;
    public final byte[] token;

    public EncryptionKeyRequestPacket(ChannelBuffer in) {
        super(ID);

        this.serverId = Buffers.mc_string(in);

        byte[] key = Buffers.mc_bytearray_2(in);

        this.token = Buffers.mc_bytearray_2(in);

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
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("server id", serverId)
            .toString();
    }
}
