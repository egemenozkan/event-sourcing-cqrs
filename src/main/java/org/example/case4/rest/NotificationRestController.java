package org.example.case4.rest;

import org.example.case4.service.notification.dto.response.NotificationListResponse;
import org.example.case4.service.notification.query.IQueryHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationRestController {
    private final IQueryHandler queryHandler;

    public NotificationRestController(IQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @GetMapping("/notifications")
    public NotificationListResponse getNotificationsByUserId(@RequestParam String userId) {
        return queryHandler.getNotificationsByUserId(userId).block();
    }
}
