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
/*      register(ChunkDataPacket.class);                */
        register(CollectItemPacket.class);
        register(DestroyEntityPacket.class);
/*      register(DisconnectPacket.class);               */
        register(EffectPacket.class);
        register(EncryptionKeyRequestPacket.class);
/*      register(EncryptionKeyPacketPacket.class);      */
        register(EntityEffectPacket.class);
        register(EntityEquipmentPacket.class);
/*      register(EntityPacket.class);                   */
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
/*      register(IncrementStatisticPacket.class);*/
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
/*      register(SpawnDroppedItemPacket.class);         */
        register(SpawnExperienceOrbPacket.class);
        register(SpawnGlobalEntityPacket.class);
        register(SpawnMobPacket.class);
        register(SpawnNamedEntityPacket.class);
        register(SpawnObjectPacket.class);
        register(SpawnPaintingPacket.class);
        register(SpawnPositionPacket.class);
/*      register(ThunderboltPacket.class);              */
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
                throw new NullPointerException("Unable to find packet class for ID: " + Integer.toHexString(id));
            }
            return (Packet) packet.newInstance(in);
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to find packet for ID: " + Integer.toHexString(id), e);
        }
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

/*          System.err.println();                                                                */
/*          System.err.println("Packet to decode: " + _id + " -> 0x" + Integer.toHexString(_id));*/

            DataType[] encoding = _encodings[_id];

            if (encoding == null) {
                throw new NullPointerException("Unable to find response class for ID: 0x" + Integer.toHexString(_id));
            }

            int position = buffer.readerIndex();
/*          if (_id == Packet.ENTITY_METADATA) {                                              */
/*              System.err.println("Position: " + position + " -> " + buffer.readableBytes());*/
/*          }                                                                                 */
            for (int i = 0; i < encoding.length; i++) {
                DataType dt = encoding[i];
                int size = dt.size(buffer, position);

/*              if (_id == Packet.ENTITY_METADATA) {                  */
/*                  System.err.println("size: " + dt + " -> " + size);*/
/*              }                                                     */
                if (size == -1) {
                    return null;
                }
                position += size;
            }

/*          if (_id == Packet.ENTITY_METADATA) {*/
/*                                              */
/*          System.err.println("Readable: reader index = " + buffer.readerIndex()*/
/*              + "; position = " + position                                     */
/*              + "; readable = " + buffer.readableBytes()                       */
/*              + "; packet size = " + (position - buffer.readerIndex()));       */
/*          }                                                                    */
            if (position - buffer.readerIndex() > buffer.readableBytes()) {
                System.err.println("Bad position");
                return null;
            }

/*          System.err.println("------ Size required: id: " + Integer.toHexString(state.id)*/
/*              + " position:" + in.position()                                             */
/*              + " new position:" + position                                              */
/*              + " limit:" + in.limit()                                                   */
/*              + " size: " + (position - in.position()));                                 */


            try {
            result = createPacket(_id, buffer);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            if (_id == Packet.TEAMS) {
                System.err.println("Packet: " + result);
            }
            _id = -1;
        }
        return result;
    }
}
