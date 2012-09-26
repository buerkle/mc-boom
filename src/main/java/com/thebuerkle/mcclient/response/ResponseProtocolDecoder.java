package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ResponseProtocolDecoder extends CumulativeProtocolDecoder {

    private static final String STATE_KEY = ResponseProtocolDecoder.class.getName() + ".STATE";

    private static final Constructor[] RESPONSES = new Constructor[256];
    private static final DataType[][] ENCODINGS = new DataType[256][];

    static {
        register(AnimationResponse.class);
        register(AttachEntityResponse.class);
        register(BlockBreakAnimationResponse.class);
        register(BlockActionResponse.class);
        register(BlockChangeResponse.class);
        register(ChangeGameStateResponse.class);
        register(ChatMessageResponse.class);
        register(ChunkDataResponse.class);
        register(CollectItemResponse.class);
        register(DestroyEntityResponse.class);
        register(DisconnectResponse.class);
        register(EncryptionKeyRequestResponse.class);
        register(EncryptionKeyResponseResponse.class);
        register(EntityEffectResponse.class);
        register(EntityEquipmentResponse.class);
        register(EntityResponse.class);
        register(EntityHeadLookResponse.class);
        register(EntityLookAndRelativeMoveResponse.class);
        register(EntityLookResponse.class);
        register(EntityMetadataResponse.class);
        register(EntityRelativeMoveResponse.class);
        register(EntityStatusResponse.class);
        register(EntityTeleportResponse.class);
        register(EntityVelocityResponse.class);
        register(ExplosionResponse.class);
        register(IncrementStatisticResponse.class);
        register(KeepAliveResponse.class);
        register(LoginRequestResponse.class);
        register(MultiBlockChangeResponse.class);
        register(NamedSoundEffectResponse.class);
        register(ParticleEffectResponse.class);
        register(PlayerAbilitiesResponse.class);
        register(PlayerListItemResponse.class);
        register(PlayerPositionAndLookResponse.class);
        register(RemoveEntityEffectResponse.class);
        register(SetExperienceResponse.class);
        register(SetSlotResponse.class);
        register(SetWindowItemsResponse.class);
        register(SpawnDroppedItemResponse.class);
        register(SpawnExperienceOrb.class);
        register(SpawnMobResponse.class);
        register(SpawnNamedEntityResponse.class);
        register(SpawnObjectResponse.class);
        register(SpawnPaintingResponse.class);
        register(SpawnPositionResponse.class);
        register(ThunderboltResponse.class);
        register(TimeUpdateResponse.class);
        register(UpdateHealthResponse.class);
        register(UpdateSignResponse.class);
        register(UpdateTileEntityResponse.class);
    }

    private static void register(Class<? extends Response> response) {
        try {
            int id = response.getField("ID").getInt(null);

            RESPONSES[id] = response.getConstructor(IoBuffer.class);
            ENCODINGS[id] = (DataType[]) response.getField("ENCODING").get(null);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    private static Response createResponse(int id, IoBuffer in) {
        try {
            Constructor<? extends Response> response = RESPONSES[id];
            if (response == null) {
                throw new NullPointerException("Unable to find response class for ID: " + Integer.toHexString(id));
            }
            return (Response) response.newInstance(in);
        }
        catch (Exception e) {
            throw new Error("Unable to find response for ID: " + Integer.toHexString(id), e);
        }
    }

    private static class State {
        // response ID
        int id = -1;
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
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        State state = (State) session.getAttribute(STATE_KEY);
        if (state == null) {
            state = new State();
            session.setAttribute(STATE_KEY, state);
        }

/*      System.err.println("Message length: " + in.remaining());*/

/*      byte[] bytes = new byte[in.remaining()];*/
/*      int p = in.position();                  */
/*      in.get(bytes);                          */
/*      in.position(p);                         */
/*                                              */
/*      System.err.println("Message: " + toHex(bytes));*/

/*      System.err.println(in);*/

        while (in.hasRemaining()) {
            if (state.id == -1) {
                state.id = in.getUnsigned();
            }

            DataType[] encoding = ENCODINGS[state.id];

            if (encoding == null) {
                throw new NullPointerException("Unable to find response class for ID: " + Integer.toHexString(state.id));
            }

            int position = in.position();
            for (int i = 0; i < encoding.length; i++) {
                DataType dt = encoding[i];
                int size = dt.size(in, position);

                if (size == -1) {
                    return false;
                }
                position += size;
            }

            if (position > in.limit()) {
                return false;
            }

/*          System.err.println("------ Size required: id: " + Integer.toHexString(state.id)*/
/*              + " position:" + in.position()                                             */
/*              + " new position:" + position                                              */
/*              + " limit:" + in.limit()                                                   */
/*              + " size: " + (position - in.position()));                                 */

            out.write(createResponse(state.id, in));
            state.id = -1;
        }

/*      System.err.println("POSITION: " + pos + " -> " + in.position());*/
        return true;
    }

    @Override()
    public void dispose(IoSession session) throws Exception {
        super.dispose(session);
        session.removeAttribute(STATE_KEY);
    }
}

