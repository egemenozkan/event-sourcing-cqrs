package org.example.case4.service.notification.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.case4.data.notification.entity.NotificationEntity;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
public class NotificationDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String postId;
    private String text;
    private String type;
    private long timestamp;

    public static NotificationDto entityToDto(NotificationEntity notificationEntity) {
        return NotificationDto.builder()
                .id(notificationEntity.getId())
                .userId(notificationEntity.getUserId())
                .text(notificationEntity.getText())
                .type(notificationEntity.getType().name())
                .postId(notificationEntity.getPostId())
                .timestamp(notificationEntity.getTimestamp())
                .build();
    }

}
