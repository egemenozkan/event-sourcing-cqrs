package org.example.case4.service.notification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.example.case4.service.notification.model.UserActivity;

import java.util.Map;

public class UserActivitySerializer implements Serializer<UserActivity> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public byte[] serialize(String topic, UserActivity data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing UserActivity", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}