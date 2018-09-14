package com.yx.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Json工具类
 *
 * Created by join on 2018/9/14.
 */
public class JsonUtils {
    protected final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final JsonUtils instance = new JsonUtils();

    private ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JsonUtils getInstance() {
        return instance;
    }

    public <T> T deserialize(final String json, final Class<T> valueType) {
        return string2Object(json, valueType);
    }

    public <T> T deserializeBytes(final byte[] val, final Class<T> valueType) {
        T obj = null;
        try {
            obj = mapper.readValue(val, valueType);
        } catch (Exception ex) {
            String tmp = "";
            try {
                tmp = new String(val, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                logger.error("deserializeBytes-UnsupportedEncodingException error:{}", e1);
            }
            this.logger.error("deserializeBytes json:{} classType:{},ex:{}", tmp, valueType.getName(),ex);

        }
        return obj;
    }

    public <T> T string2Object(final String json, final Class<T> valueType) {

        T obj = null;
        try {
            obj = mapper.readValue(json, valueType);
        } catch (IOException ex) {
            this.logger.error("string2Object json:{} classType:{},ex:{}", json, valueType.getName(), ex);

        }
        return obj;
    }

    public <T> T deserialize(final String json, final TypeReference<T> typeReference) {
        T obj = null;
        try {

            obj = mapper.readValue(json, typeReference);
        } catch (IOException ex) {
            this.logger.error("deserialize json:{} typeReference:{}", json,
                            typeReference.getType().getTypeName(),ex);

        }
        return obj;
    }

    public String serialize(final Object value) {
        return object2String(value);
    }

    /**
     * json字符串转换为对象
     * @param json
     * @param valueType
     * @param allowUnquotedControlChars
     *            是否忽略控制符，如tab等
     * @return
     */
    public <T> T string2Object(final String json, final Class<T> valueType, final Boolean allowUnquotedControlChars) {

        T obj = null;
        try {
            if (allowUnquotedControlChars) {
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
                mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
            }
            obj = mapper.readValue(json, valueType);
        } catch (IOException ex) {
            this.logger.error("string2Object json:{} classType:{},ex:{}", json, valueType.getName(), ex);
        }

        return obj;
    }

    /**
     * 对象转换Json字符串
     * @param value
     * @return
     */
    public String object2String(final Object value) {
        String result = null;
        try {
            result = mapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            this.logger.error("object2String class:{},ex:{}", value.getClass().getName(),ex);
        }

        return result;
    }

    /**
     * Map转换为对象
     * @param map
     * @param valueType
     * @param <T>
     * @return
     */
    public <T> T convertObject(final Object map, final Class<T> valueType) {
        return mapper.convertValue(map, valueType);
    }
}
