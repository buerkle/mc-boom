package com.thebuerkle.mcboom;

/*import com.thebuerkle.mcclient.model.Slot;*/

import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;

public enum DataType {
    mc_bool(1),
    mc_byte(1),
    mc_unsigned_byte(1),
    mc_bytearray_2(-1) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             if (in.writerIndex() - position < 2) {
                 return -1;
             }
             return 2 + in.getShort(position);
         }
    },
    mc_bytearray_4(-1) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             if (in.writerIndex() - position < 4) {
                 return -1;
             }
             return 4 + in.getInt(position);
         }
    },
    mc_short(2),
    mc_int(4),
    mc_intarray_1(4) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             if (in.writerIndex() - position < 1) {
                 return -1;
             }
             return 1 + (in.getByte(position) * 4);
         }
    },
    mc_long(8),
    mc_fixed_point(4),
    mc_float(4),
    mc_double(8),
    mc_metadata(-1) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             int newpos = position;

             while (in.writerIndex() - newpos > 0) {
                 byte id = in.getByte(newpos);
                 newpos++;

                 if (id == 0x7F) {
                     return newpos - position;
                 }

                 int type = (id & 0xE0) >> 5;

                 switch (type) {
                 case 0: newpos += 1; break; // byte
                 case 1: newpos += 2; break; // short
                 case 2: newpos += 4; break; // int
                 case 3: newpos += 4; break; // float
                 case 4: {
                     int size = DataType.mc_string.size(in, newpos);
                     if (size == -1) {
                         return -1;
                     }
                     newpos += size;
                     break; // string
                 }
                 case 5: {
                     int size = DataType.mc_slot.size(in, newpos);
                     if (size == -1) {
                         return -1;
                     }
                     newpos +=  size;
                     break; // short, byte, short
                 }
                 case 6: newpos += 12; break; // int, int, int
                 }
             }
             return -1;
         }
    },
    mc_bytecoordarray(-1) {
        @Override()
        public int size(ChannelBuffer in, int position) {
            if (in.writerIndex() - position < 4) {
                return -1;
            }
            return 4 + (in.getInt(position) * 3);
        }
    },
    mc_object(-1) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             int remaining = in.writerIndex() - position;

             if (remaining < 4) {
                 return -1;
             }

             int eid = in.getInt(position);
             if (eid > 0) {
                 if (remaining < 10) {
                     return -1;
                 }
                 return 10; // eid, x, y, z
             }
             return 4;
         }
    },
    mc_properties(-1) {
         @Override()
         public int size(ChannelBuffer in, int position) {
             int remaining = in.writerIndex() - position;

             // properties count
             if (remaining < 4) {
                 return -1;
             }

             int count = in.getInt(position);
             int newpos = position+4;
             for (int i = 0; i < count; i++) {
                 int size = DataType.mc_string.size(in, newpos);
                 if (size == -1) {
                     return -1;
                 }
                 newpos += size;

                 // value(8), modifier count(2)
                 if (remaining - newpos < 10) {
                     return -1;
                 }
                 newpos += 10;

                 int modifierCount = in.getShort(newpos-2);

                 newpos += modifierCount * (16 + 8 + 1);

                 if (remaining - newpos < 0) {
                     return -1;
                 }
             }
             return newpos - position;
         }
    },
    mc_slot(-1) {
        @Override()
        public int size(ChannelBuffer in, int position) {
            int remaining = in.writerIndex() - position;

            if (remaining < 2) {
                return -1; // no room for id
            }

            // block ID
            int id = in.getShort(position);
            if (id == -1) {
                return 2;
            }

            if (remaining < 7) {
                return -1; // ID, count, damage, len
            }

            int len = in.getShort(position+5);
            if (len == -1) {
                return 7;
            }

            if (remaining < 7 + len) {
                return -1;
            }
            return 7 + len;
        }
    },
    mc_slotarray(-1) {
        @Override()
        public int size(ChannelBuffer in, int position) {
            if (in.writerIndex() - position < 2) {
                return -1;
            }
            int len = in.getShort(position);
            int newpos = position + 2;
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    int size = DataType.mc_slot.size(in, newpos);

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
         public int size(ChannelBuffer in, int position) {
             if (in.writerIndex() - position < 4) {
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
         public int size(ChannelBuffer in, int position) {
             if (in.writerIndex() - position < 2) {
                 return -1;
             }
             return 2 + (in.getShort(position) * 2);
         }
    },
    mc_chunk_bulk(-1) {
        @Override()
        public int size(ChannelBuffer in, int position) {
            int remaining = in.writerIndex() - position;

            // chunk count, data length, sky light sent
            if (remaining < 7) {
                return -1; // no room for chunk count and data length
            }

            int count = in.getShort(position);
            int len = in.getInt(position+2);

            if (remaining < 7 + len) {
                return -1; // no room for compressed data chunk
            }

            if (remaining < 7 + len + 12 * count) {
                return -1; // no room for chunk metadata
            }
            return 7 + len + 12 * count;
        }
    };

    private final int _size;

    private DataType(int size) {
        _size = size;
    }

    /**
     * Returns true if the ChannelBuffer has enough remaining to read this data type.
     */
    public int size(ChannelBuffer in, int position) {
        if (in.writerIndex() - position > _size) {
            return _size;
        }
        return -1;
    }
}
