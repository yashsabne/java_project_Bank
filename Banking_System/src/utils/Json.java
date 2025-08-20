package utils;

import java.util.*;
import java.util.stream.Collectors;

public final class Json {
    public static String obj(Map<String, Object> map) {
        return "{" + map.entrySet().stream()
                .map(e -> q(e.getKey()) + ":" + val(e.getValue()))
                .collect(Collectors.joining(",")) + "}";
    }
    public static String arr(List<?> list) {
        return "[" + list.stream().map(Json::val).collect(Collectors.joining(",")) + "]";
    }
    @SuppressWarnings("unchecked")
    private static String val(Object v) {
        if (v == null) return "null";
        if (v instanceof Number || v instanceof Boolean) return String.valueOf(v);
        if (v instanceof Map) return obj((Map<String, Object>) v);
        if (v instanceof List) return arr((List<?>) v);
        return q(String.valueOf(v));
    }
    private static String q(String s) { return "\"" + s.replace("\"", "\\\"") + "\""; }
    private Json() {}
}
