package org.example.case4.consumer.notification;

import lombok.extern.slf4j.Slf4j;
import org.example.case4.service.notification.command.CreateNotificationCommand;
import org.example.case4.service.notification.command.ICommand;
import org.example.case4.service.notification.model.UserActivity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserActivityConsumerService {
    private final ICommand command;

    public UserActivityConsumerService(ICommand command) {
        this.command = command;
    }

    @KafkaListener(topics = "user-activity", groupId = "sm-group")
    public void consume(UserActivity userActivity) {
        log.info("Consumed message: " + userActivity);

        command.handleCreateNotificationCommand(CreateNotificationCommand.builder()
                .userId(userActivity.getUserId())
                .text(userActivity.getText())
                .type(userActivity.getType())
                .postId(userActivity.getPostId())
                .build());
    }
}