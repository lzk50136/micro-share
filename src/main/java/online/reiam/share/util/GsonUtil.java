package online.reiam.share.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    private GsonUtil() {
    }

    /**
     * 对象转json字符串
     *
     * @param object 对象
     * @return json字符串
     */
    public static String beanToJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param cls  对象类型
     * @param <T>  泛型
     * @return 对象
     */
    public static <T> T jsonToBean(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    /**
     * json字符串转map
     *
     * @param json json字符串
     * @param <T>  泛型
     * @return map对象
     */
    public static <T> Map<String, T> jsonToMap(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
    }

    /**
     * map对象转json字符串
     *
     * @param map map对象
     * @param <T> 泛型
     * @return json字符串
     */
    public static <T> String mapToJson(Map<String, T> map) {
        return gson.toJson(map);
    }

    /**
     * json字符串转list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param json json字符串
     * @param <T>  泛型
     * @return list对象
     */
    public static <T> List<T> jsonToList(String json) {
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * json字符串转list
     * 解决泛型问题
     *
     * @param json json字符串
     * @param <T>  泛型
     * @return list对象
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * json字符串转成list中有map的
     * 泛型在编译期类型被擦除导致报错
     *
     * @param json json字符串
     * @param <T>  泛型
     * @return list对象
     */
    public static <T> List<Map<String, T>> jsonToListMap(String json) {
        return gson.fromJson(json, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * 判断一个字符串是否json格式
     *
     * @param json 字符串
     * @return 结果
     */
    public static boolean isJson(String json) {
        try {
            new JsonParser().parse(json);
        } catch (JsonParseException e) {
            return false;
        }
        return true;
    }

}