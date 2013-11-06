package com.thebuerkle.mcboom.packet;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.jboss.netty.buffer.ChannelBuffer;

import gnu.trove.map.hash.TIntObjectHashMap;

public abstract class Packet {

    public static final int KEEP_ALIVE = 0x00;
    public static final int LOGIN_REQUEST = 0x01;
    public static final int HANDSHAKE = 0x02;
    public static final int CHAT_MESSAGE = 0x03;
    public static final int TIME_UPDATE = 0x04;
    public static final int ENTITY_EQUIPMENT = 0x05;
    public static final int SPAWN_POSITION = 0x06;
    public static final int UPDATE_HEALTH = 0x08;
    public static final int PLAYER = 0x0A;
    public static final int PLAYER_POSITION = 0x0B;
    public static final int PLAYER_POSITION_AND_LOOK = 0x0D;
    public static final int HELD_ITEM_CHANGE = 0x10;
    public static final int USE_BED = 0x11;
    public static final int ANIMATION = 0x12;
    public static final int SPAWN_NAMED_ENTITY = 0x14;
    public static final int COLLECT_ITEM = 0x16;
    public static final int SPAWN_OBJECT = 0x17;
    public static final int SPAWN_MOB = 0x18;
    public static final int SPAWN_PAINTING = 0x19;
    public static final int SPAWN_EXPERIENCE_ORB = 0x1A;
    public static final int ENTITY_VELOCITY = 0x1C;
    public static final int DESTROY_ENTITY = 0x1D;
    public static final int ENTITY_MOVE = 0x1F;
    public static final int ENTITY_LOOK = 0x20;
    public static final int ENTITY_LOOK_AND_MOVE = 0x21;
    public static final int ENTITY_TELEPORT = 0x22;
    public static final int ENTITY_HEAD_LOOK = 0x23;
    public static final int ENTITY_STATUS = 0x26;
    public static final int ATTACH_ENTITY = 0x27;
    public static final int ENTITY_METADATA = 0x28;
    public static final int ENTITY_EFFECT = 0x29;
    public static final int REMOVE_ENTITY_EFFECT = 0x2A;
    public static final int SET_EXPERIENCE = 0x2B;
    public static final int ENTITY_PROPERTIES = 0x2C;
    public static final int CHUNK_DATA = 0x33;
    public static final int MULTI_BLOCK_CHANGE = 0x34;
    public static final int BLOCK_CHANGE = 0x35;
    public static final int BLOCK_ACTION = 0x36;
    public static final int BLOCK_BREAK_ANIMATION = 0x37;
    public static final int MAP_CHUNK_BULK = 0x38;
    public static final int EXPLOSION = 0x3C;
    public static final int EFFECT = 0x3D;
    public static final int NAMED_SOUND_EFFECT = 0x3E;
    public static final int CHANGE_GAME_STATE = 0x46;
    public static final int SPAWN_GLOBAL_ENTITY = 0x47;
    public static final int SET_SLOT = 0x67;
    public static final int SET_WINDOW_ITEMS = 0x68;
    public static final int UPDATE_SIGN = 0x82;
    public static final int ITEM_DATA = 0x83;
    public static final int UPDATE_TILE_ENTITY = 0x84;
    public static final int PLAYER_LIST_ITEM = 0xC9;
    public static final int PLAYER_ABILITIES = 0xCA;
    public static final int CLIENT_STATUS = 0xCD;
    public static final int TEAMS = 0xD1;
    public static final int PLUGIN_MESSAGE = 0xFA;
    public static final int SERVER_LIST_PING = 0xFE;
    public static final int ENCRYPTION_KEY_REQUEST = 0xFD;
    public static final int KICK = 0xFF;

    private static final TIntObjectHashMap<String> _names = new TIntObjectHashMap<>();

    static {
        try {
            for (Field field : Packet.class.getDeclaredFields()) {
                String name = field.getName();

                if (!name.equals("_names") && Modifier.isStatic(field.getModifiers())) {
                    int id = field.getInt(null);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < name.length(); i++) {
                        char ch = name.charAt(i);

                        if (ch == '_') {
                            sb.append('-');
                        }
                        else {
                            sb.append(Character.toLowerCase(ch));
                        }
                    }
                    if (_names.contains(id)) {
                        throw new IllegalArgumentException("Packet already registered: 0x" + Integer.toHexString(id));
                    }
                    _names.put(id, sb.toString());
                }
            }
        }
        catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static String name(int id) {
        return _names.get(id);
    }

    public final int id;

    public Packet(int id) {
        this.id = id;
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public void write(ChannelBuffer out) {
        throw new UnsupportedOperationException();
    }

    protected int mc_string_length(String v) {
        return 2 + 2 * v.length();
    }
}
