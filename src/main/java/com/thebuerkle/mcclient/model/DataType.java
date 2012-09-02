package com.thebuerkle.mcclient.model;

import com.thebuerkle.mcclient.model.Slot;

import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;

public enum DataType {
    mc_bool(1),
    mc_byte(1),
    mc_unsigned_byte(1),
    mc_bytearray_2(-1) {
         @Override()
         public int size(IoBuffer in, int position) {
             if (in.limit() - position < 2) {
                 return -1;
             }
             return 2 + in.getShort(position);
         }
    },
    mc_bytearray_4(-1) {
         @Override()
         public int size(IoBuffer in, int position) {
             if (in.limit() - position < 4) {
                 return -1;
             }
             return 4 + in.getInt(position);
         }
    },
    mc_short(2),
    mc_int(4),
    mc_intarray_1(4) {
         @Override()
         public int size(IoBuffer in, int position) {
             if (in.limit() - position < 1) {
                 return -1;
             }
             return 1 + (in.get(position) * 4);
         }
    },
    mc_long(8),
    mc_float(4),
    mc_double(8),
    mc_metadata(-1) {
         @Override()
         public int size(IoBuffer in, int position) {
             return EntityMetadata.size(in, position);
         }
    },
    mc_bytecoordarray(-1) {
        @Override()
        public int size(IoBuffer in, int position) {
            if (in.limit() - position < 4) {
                return -1;
            }
            return 4 + (in.getInt(position) * 3);
        }
    },
    mc_slot(-1) {
        @Override()
        public int size(IoBuffer in, int position) {
            return Slot.size(in, position);
        }
    },
    mc_slotarray(-1) {
        @Override()
        public int size(IoBuffer in, int position) {
            if (in.limit() - position < 2) {
                return -1;
            }
            int len = in.getShort(position);
            int newpos = position + 2;
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    int size = Slot.size(in, newpos);

                    if (size == -1) {
                        return -1;
                    }
                    newpos += size;
                }
            }
            return newpos - position;
        }
    },
    // used by 0x17 (Spawn Object)
    mc_speed(-1) {
         @Override()
         public int size(IoBuffer in, int position) {
             if (in.limit() - position < 4) {
                 return -1;
             }
             int data = in.getInt(position);
             if (data > 0) {
                 return 10;
             }
             return 4;
         }
    },
    mc_string(-1) {
         @Override()
         public int size(IoBuffer in, int position) {
             if (in.limit() - position < 2) {
                 return -1;
             }
             return 2 + (in.getShort(position) * 2);
         }
    };

    private final int _size;

    private DataType(int size) {
        _size = size;
    }

    /**
     * Returns true if the IoBuffer has enough remaining to read this data type.
     */
    public int size(IoBuffer in, int position) {
        return _size;
    }
}
