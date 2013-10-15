package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;
import com.thebuerkle.mcboom.Property;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityPropertiesPacket extends Packet {

    public static final int ID = ENTITY_PROPERTIES;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_properties
    };

    public final int eid;
    public final List<Property> properties;

    public EntityPropertiesPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.properties = Buffers.mc_properties(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("Properties", properties)
            .toString();
    }
}
