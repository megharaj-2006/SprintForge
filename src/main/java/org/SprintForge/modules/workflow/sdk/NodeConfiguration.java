package org.SprintForge.modules.workflow.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NodeConfiguration {
    private final Map<String, Object> configData;
    private static final ObjectMapper mapper = new ObjectMapper();

    public NodeConfiguration(Map<String, Object> configData) {
        this.configData = configData != null ? configData : new HashMap<>();
    }

    public Object get(String key) {
        return configData.get(key);
    }

    public String getString(String key, String defaultValue) {
        Object val = configData.get(key);
        return val != null ? val.toString() : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        Object val = configData.get(key);
        if (val instanceof Number n) return n.intValue();
        if (val != null) {
            try { return Integer.parseInt(val.toString()); } catch (NumberFormatException ignored) {}
        }
        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object val = configData.get(key);
        if (val instanceof Boolean b) return b;
        if (val != null) return Boolean.parseBoolean(val.toString());
        return defaultValue;
    }

    public <T> T bind(Class<T> targetClass) {
        return mapper.convertValue(configData, targetClass);
    }
}
