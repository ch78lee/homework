package com.cardwork;

import java.util.ArrayList;
import java.util.HashMap;

import com.cardwork.entity.DataVo;

public class PacketWork {
    private ArrayList<DataVo> datavo = new ArrayList<DataVo>();
    private HashMap<String, DataVo> nameAccess = new HashMap<String, DataVo>();

    public void addItem(DataVo datavo) {
        this.datavo.add(datavo);
        if(nameAccess.containsKey(datavo.getId())) {
            throw new RuntimeException(
                    "Duplicated item name:["+datavo.getId()+"]");
        }
        nameAccess.put(datavo.getId(), datavo);
    }

    public DataVo getItem(int index) {
        return this.datavo.get(index);
    }

    public DataVo getItem(String name) {
        return nameAccess.get(name);
    }

    public String raw() {
        StringBuffer result = new StringBuffer();
        for(DataVo datavo: datavo) {
            result.append(datavo.raw());
        }
        return result.toString();
    }

    public void parse(String data) {
        byte[] bdata = data.getBytes();
        int pos = 0;
        byte[] temp = null;
        for(DataVo datavo: datavo) {
            temp = new byte[datavo.getLength()];
            System.arraycopy(bdata, pos, temp, 0, datavo.getLength());
            pos += datavo.getLength();
            datavo.setValue(new String(temp));
        }
    }
}
