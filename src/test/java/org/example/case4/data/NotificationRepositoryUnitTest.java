import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryUnitTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationRepositoryUnitTest notificationRepositoryUnitTest;

    @Test
    void testDb() {
        when(mongoTemplate.getDb()).thenReturn(null); // Mock the return value as per your setup
        assertThat(mongoTemplate.getDb()).isNull();
    }

    @Test
    public void testSaveEvent() {
        NotificationEntity notification = new NotificationEntity();
        notification.setType(NotificationType.COMMENT);
        notification.setText("TestEvent");
        notification.setUserId("123");
        notification.setPostId("123");

        NotificationEntity savedNotification = new NotificationEntity();
        savedNotification.setId("1");
        savedNotification.setType(NotificationType.COMMENT);
        savedNotification.setText("TestEvent");
        savedNotification.setUserId("123");
        savedNotification.setPostId("123");

        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(Mono.just(savedNotification));

        Mono<NotificationEntity> result = notificationRepository.save(notification);
        StepVerifier.create(result)
                .assertNext(res -> {
                    assertThat(res.getId()).isNotNull();
                    assertThat(res.getId()).isEqualTo("1");
                })
                .verifyComplete();
    }

    @Test
    public void testCountByTypeAndTimestampAfter() {
        String type = NotificationType.COMMENT.name();
        long timestamp = System.currentTimeMillis() - 3600 * 1000; // 1 hour ago
        when(notificationRepository.countByTypeAndTimestampAfter(type, timestamp)).thenReturn(Mono.just(10L));

        Mono<Long> count = notificationRepository.countByTypeAndTimestampAfter(type, timestamp);
        StepVerifier.create(count)
                .assertNext(cnt -> assertThat(cnt).isEqualTo(10L))
                .verifyComplete();
    }
}
