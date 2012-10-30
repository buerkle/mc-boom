package com.thebuerkle.mcclient.request;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Difficulty;
import com.thebuerkle.mcclient.model.ViewDistance;

import org.apache.mina.core.buffer.IoBuffer;

public class ClientInfoRequest extends Request {

    public static final int ID = 0xCC;

    private final String _locale;
    private final ViewDistance _distance;
    private final int _chat;
    private final Difficulty _difficulty;
    private final boolean _showCape;

    public ClientInfoRequest(String locale, ViewDistance distance,
                             int chat, Difficulty difficulty, boolean showCape) {
        super(0xCC);

        _locale = locale;
        _distance = distance;
        _chat = chat;
        _difficulty = difficulty;
        _showCape = showCape;
    }

    @Override()
    public int getSize() {
        return(2 + _locale.length()*2) + 4;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_string(out, _locale);
        mc_byte(out, _distance.getValue());
        mc_byte(out, _chat);
        mc_byte(out, _difficulty.getValue());
        mc_bool(out, _showCape);
    }

    @Override()
    public int getId() {
        return ID;
    }
}
