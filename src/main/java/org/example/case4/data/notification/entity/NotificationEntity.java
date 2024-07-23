package org.example.case4.data.notification.entity;

import lombok.Data;
import org.example.case4.data.notification.model.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
public class NotificationEntity {
    @Id
    private String id;
    private String postId;
    private String userId;
    private String text;
    private NotificationType type;
    private long timestamp;

}
