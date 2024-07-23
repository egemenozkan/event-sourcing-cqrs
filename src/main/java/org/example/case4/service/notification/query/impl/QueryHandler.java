package org.example.case4.service.notification.query.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.example.case4.service.notification.dto.NotificationDto;
import org.example.case4.service.notification.dto.response.AnalyticsResponse;
import org.example.case4.service.notification.dto.response.NotificationListResponse;
import org.example.case4.service.notification.query.AnalyticsQuery;
import org.example.case4.service.notification.query.IQueryHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class QueryHandler implements IQueryHandler {
    private final NotificationRepository notificationRepository;

    public QueryHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Mono<NotificationListResponse> getNotificationsByUserId(String userId) {
        log.info("Getting notifications for user: {}", userId);
        return notificationRepository.findAllByUserId(userId)
                .flatMapIterable(entities -> entities)
                .map(NotificationDto::entityToDto)
                .collectList()
                .map(notifications -> NotificationListResponse.builder().notifications(notifications).build());
    }

    @Override
    public Mono<AnalyticsResponse> getAnalytics(AnalyticsQuery query) {
        long currentTime = System.currentTimeMillis();
        long secondsAgo = currentTime - query.getSeconds() * 1000;

        Mono<Long> commentCountMono = notificationRepository.countByTypeAndTimestampAfter(NotificationType.COMMENT.name(), secondsAgo);
        Mono<Long> likeCountMono = notificationRepository.countByTypeAndTimestampAfter(NotificationType.LIKE.name(), secondsAgo);
        Mono<Long> postCountMono = notificationRepository.countByTypeAndTimestampAfter(NotificationType.POST.name(), secondsAgo);

        return Mono.zip(commentCountMono, likeCountMono, postCountMono)
                .map(tuple -> {
                    AnalyticsResponse response = new AnalyticsResponse();
                    response.setCommentCount(tuple.getT1());
                    response.setLikeCount(tuple.getT2());
                    response.setPostCount(tuple.getT3());
                    return response;
                });
    }
}
