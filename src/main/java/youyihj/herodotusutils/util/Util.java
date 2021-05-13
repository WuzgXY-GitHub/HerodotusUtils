package youyihj.herodotusutils.util;

import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public final class Util {
    private Util() {
    }

    public static IData createDataMap(String key, IData value) {
        Map<String, IData> temp = new HashMap<>();
        temp.put(key, value);
        return new DataMap(temp, true);
    }
}
