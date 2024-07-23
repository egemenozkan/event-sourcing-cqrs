package org.example.case4.service.notification.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.case4.service.notification.dto.NotificationDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class NotificationListResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<NotificationDto> notifications;
}
