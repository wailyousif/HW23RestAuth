package com.ironyard.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wailm.yousif on 2/16/17.
 */

public class HttpInfo
{
    private Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void addHttpHeader(String header, String value)
    {
        map.put(header, value);
    }
}
