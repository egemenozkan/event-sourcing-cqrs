package org.example.case4.service.notification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.case4.service.notification.model.UserActivity;

import java.util.Map;

public class UserActivityDeserializer implements Deserializer<UserActivity> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public UserActivity deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, UserActivity.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing UserActivity", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
