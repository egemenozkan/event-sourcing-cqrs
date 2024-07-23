package org.example.case4.service.notification.command;

import lombok.Builder;
import lombok.Getter;
import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.model.NotificationType;

@Builder
@Getter
public class CreateNotificationCommand {
    private String postId;
    private String userId;
    private String text;
    private String type;

    public NotificationEntity toEntity() {
        NotificationEntity entity = new NotificationEntity();
        entity.setPostId(postId);
        entity.setUserId(userId);
        entity.setText(text);
        entity.setType(NotificationType.valueOf(type));

        return entity;
    }
}