package org.example.case4.rest;

import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.service.notification.model.UserActivity;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setup() {
        notificationRepository.deleteAll();
        UserActivity userActivity = new UserActivity();
        userActivity.setPostId("123");
        userActivity.setUserId("testUser");
        userActivity.setText("Test Activity");
        userActivity.setTimestamp(System.currentTimeMillis());
        userActivity.setType("COMMENT");

        // Save a notification to the repository for testing
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId("1");
        notificationEntity.setUserId(userActivity.getUserId());
        notificationEntity.setPostId(userActivity.getPostId());
        notificationEntity.setText(userActivity.getText());
        notificationEntity.setType(NotificationType.COMMENT);
        notificationEntity.setTimestamp(userActivity.getTimestamp());

        notificationRepository.save(notificationEntity);
    }

    @Test
    void testGetNotificationsByUserId() throws Exception {
        mockMvc.perform(get("/notifications")
                        .param("userId", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].userId").value("testUser"))
                .andExpect(jsonPath("$.notifications[0].postId").value("123"))
                .andExpect(jsonPath("$.notifications[0].text").value("Test Activity"))
                .andExpect(jsonPath("$.notifications[0].type").value("COMMENT"));
    }
}
