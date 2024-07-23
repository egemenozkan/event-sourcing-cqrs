package org.example.case4.rest.admin;

import org.example.case4.service.notification.dto.response.AnalyticsResponse;
import org.example.case4.service.notification.dto.response.NotificationListResponse;
import org.example.case4.service.notification.query.AnalyticsQuery;
import org.example.case4.service.notification.query.IQueryHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/admin")
public class AdminRestController {
    private final IQueryHandler queryHandler;

    public AdminRestController(IQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @GetMapping("/analytics")
    public Mono<AnalyticsResponse> getAnalytics(@RequestParam long seconds) {
        return queryHandler.getAnalytics(new AnalyticsQuery(seconds));
    }
}
