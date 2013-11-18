package com.thebuerkle.mcboom;

import com.thebuerkle.mcboom.packet.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class ProtocolDecoder extends FrameDecoder  {

    private static final Constructor[] _packets = new Constructor[256];
    private static final DataType[][] _encodings = new DataType[256][];

    static {
        register(AnimationPacket.class);
        register(AttachEntityPacket.class);
        register(BlockActionPacket.class);
        register(BlockBreakAnimationPacket.class);
        register(BlockChangePacket.class);
        register(ChangeGameStatePacket.class);
        register(ChatMessagePacket.class);
        register(CollectItemPacket.class);
        register(DestroyEntityPacket.class);
        register(EffectPacket.class);
        register(EncryptionKeyRequestPacket.class);
        register(EntityEffectPacket.class);
        register(EntityEquipmentPacket.class);
        register(EntityHeadLookPacket.class);
        register(EntityLookAndMovePacket.class);
        register(EntityLookPacket.class);
        register(EntityMetadataPacket.class);
        register(EntityPropertiesPacket.class);
        register(EntityMovePacket.class);
        register(EntityStatusPacket.class);
        register(EntityTeleportPacket.class);
        register(EntityVelocityPacket.class);
        register(ExplosionPacket.class);
        register(HeldItemChangePacket.class);
        register(ItemDataPacket.class);
        register(KeepAlivePacket.class);
        register(KickPacket.class);
        register(LoginRequestPacket.class);
        register(MapChunkBulkPacket.class);
        register(MultiBlockChangePacket.class);
        register(NamedSoundEffectPacket.class);
        register(PlayerPacket.class);
        register(PlayerAbilitiesPacket.class);
        register(PlayerListItemPacket.class);
        register(PlayerPositionAndLookPacket.class);
        register(PluginMessagePacket.class);
        register(RemoveEntityEffectPacket.class);
        register(SetExperiencePacket.class);
        register(SetSlotPacket.class);
        register(SetWindowItemsPacket.class);
        register(SpawnExperienceOrbPacket.class);
        register(SpawnGlobalEntityPacket.class);
        register(SpawnMobPacket.class);
        register(SpawnNamedEntityPacket.class);
        register(SpawnObjectPacket.class);
        register(SpawnPaintingPacket.class);
        register(SpawnPositionPacket.class);
        register(TimeUpdatePacket.class);
        register(UpdateHealthPacket.class);
        register(UpdateSignPacket.class);
        register(UpdateTileEntityPacket.class);
        register(UseBedPacket.class);
    }

    private static void register(Class<? extends Packet> packet) {
        try {
            int id = packet.getField("ID").getInt(null);

            _packets[id] = packet.getConstructor(ChannelBuffer.class);
            _encodings[id] = (DataType[]) packet.getField("ENCODING").get(null);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    private static Packet createPacket(int id, ChannelBuffer in) {
        try {
            Constructor<? extends Packet> packet = _packets[id];
            if (packet == null) {
                throw new NullPointerException("Unable to find packet class for ID: " + toString(id));
            }
            return (Packet) packet.newInstance(in);
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to find packet for ID: " + toString(id), e);
        }
    }

    private static String toString(int id) {
        String hex = Integer.toHexString(id);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        return "0x" + hex + " (" + id + ")";
    }

    // current packet we are decoding
    private int _id = -1;

    @Override()
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        Packet result = null;

        if (buffer.readable()) {
            if (_id == -1) {
                _id = buffer.readUnsignedByte();
            }

            DataType[] encoding = _encodings[_id];

            if (encoding == null) {
                throw new NullPointerException("Unable to find response class for ID: " + toString(_id));
            }

            int position = buffer.readerIndex();
            for (int i = 0; i < encoding.length; i++) {
                DataType dt = encoding[i];
                int size = dt.size(buffer, position);
                if (size == -1) {
                    return null;
                }
                position += size;
            }

            // ensure buffer has enough data for packet
            if (position - buffer.readerIndex() > buffer.readableBytes()) {
                return null;
            }

            result = createPacket(_id, buffer);
            _id = -1;
        }
        return result;
    }
}
