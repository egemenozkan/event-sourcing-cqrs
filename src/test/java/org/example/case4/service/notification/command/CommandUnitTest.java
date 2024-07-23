package org.example.case4.service.notification.command;

import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.example.case4.service.notification.command.impl.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandUnitTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private Command command;

    @Test
    void testHandleCreateNotificationCommand() {
        // Arrange
        CreateNotificationCommand createCommand = CreateNotificationCommand.builder()
                .userId("123")
                .text("Test Notification")
                .type("COMMENT")
                .postId("123")
                .build();

        NotificationEntity notificationEntity = createCommand.toEntity();
        notificationEntity.setId("1");

        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(Mono.just(notificationEntity));

        // Act
        command.handleCreateNotificationCommand(createCommand);

        // Assert
        verify(notificationRepository).save(any(NotificationEntity.class));
    }
}
