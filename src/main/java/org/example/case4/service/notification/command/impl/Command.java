package org.example.case4.service.notification.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.example.case4.service.notification.command.CreateNotificationCommand;
import org.example.case4.service.notification.command.ICommand;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class Command implements ICommand {
    private final NotificationRepository notificationRepository;

    public Command(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void handleCreateNotificationCommand(CreateNotificationCommand command) {
        Mono<NotificationEntity> savedNotification = notificationRepository.save(command.toEntity());

        // TODO: call send notification service method
        log.info("Notification sent to followers of user: {}, notificationId: {}", command.getUserId(), Objects.requireNonNull(savedNotification.block()).getId());
    }
}
