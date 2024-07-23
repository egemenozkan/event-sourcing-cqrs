package org.example.case4.consumer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.example.case4.service.notification.model.UserActivity;
import org.example.case4.service.notification.utils.UserActivitySerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { "user-activity" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ExtendWith(SpringExtension.class)
class UserActivityConsumerServiceIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setup() {
        notificationRepository.deleteAll().block();
    }

    @Test
    void testConsume() throws InterruptedException {
        UserActivity userActivity = new UserActivity();
        userActivity.setPostId("123");
        userActivity.setUserId("123");
        userActivity.setText("Test Activity");
        userActivity.setTimestamp(System.currentTimeMillis());
        userActivity.setType("COMMENT");

        kafkaTemplate().send("user-activity", userActivity);

        // Give some time for the consumer to process the message
        TimeUnit.SECONDS.sleep(2);

        // Verify that the notification has been saved to the repository
        StepVerifier.create(notificationRepository.findAll())
                .expectNextMatches(notification -> {
                    assertThat(notification.getUserId()).isEqualTo("123");
                    assertThat(notification.getPostId()).isEqualTo("123");
                    assertThat(notification.getText()).isEqualTo("Test Activity");
                    assertThat(notification.getType()).isEqualTo(NotificationType.COMMENT);
                    return true;
                })
                .verifyComplete();
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> configs = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserActivitySerializer.class);
        return configs;
    }

    private ProducerFactory<String, UserActivity> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    private KafkaTemplate<String, UserActivity> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
