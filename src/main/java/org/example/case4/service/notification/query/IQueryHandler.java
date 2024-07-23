package org.example.case4.service.notification.query;

import org.example.case4.service.notification.dto.response.AnalyticsResponse;
import org.example.case4.service.notification.dto.response.NotificationListResponse;
import reactor.core.publisher.Mono;

public interface IQueryHandler {
   Mono<NotificationListResponse> getNotificationsByUserId(String userId);

   Mono<AnalyticsResponse> getAnalytics(AnalyticsQuery query);
}
