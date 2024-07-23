package org.example.case4.data;

import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class NotificationRepositoryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Order(1)
    @Test
    void testDb() {
        assertThat(mongoTemplate.getDb()).isNotNull();
    }

    @Test
    public void testSaveEvent() {
        NotificationEntity notification = new NotificationEntity();
        notification.setType(NotificationType.COMMENT);
        notification.setText("TestEvent");
        notification.setUserId("123");
        notification.setPostId("123");
        notification.setTimestamp(System.currentTimeMillis());

        Mono<NotificationEntity> savedNotificationMono = notificationRepository.save(notification);

        StepVerifier.create(savedNotificationMono)
                .assertNext(savedNotification -> {
                    assertThat(savedNotification.getId()).isNotNull();
                    assertThat(savedNotification.getType()).isEqualTo(NotificationType.COMMENT);
                    assertThat(savedNotification.getText()).isEqualTo("TestEvent");
                    assertThat(savedNotification.getUserId()).isEqualTo("123");
                    assertThat(savedNotification.getPostId()).isEqualTo("123");
                })
                .verifyComplete();
    }
}
