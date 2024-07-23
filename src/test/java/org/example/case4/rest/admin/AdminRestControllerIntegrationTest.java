package org.example.case4.rest.admin;

import org.example.case4.data.notification.entity.NotificationEntity;
import org.example.case4.data.notification.model.NotificationType;
import org.example.case4.data.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setup() {
        notificationRepository.deleteAll();

        long currentTime = System.currentTimeMillis();
        NotificationEntity commentNotification = new NotificationEntity();
        commentNotification.setUserId("testUser");
        commentNotification.setPostId("123");
        commentNotification.setText("Comment Activity");
        commentNotification.setType(NotificationType.COMMENT);
        commentNotification.setTimestamp(currentTime);

        NotificationEntity likeNotification = new NotificationEntity();
        likeNotification.setUserId("testUser");
        likeNotification.setPostId("123");
        likeNotification.setText("Like Activity");
        likeNotification.setType(NotificationType.LIKE);
        likeNotification.setTimestamp(currentTime - TimeUnit.HOURS.toMillis(1));

        NotificationEntity postNotification = new NotificationEntity();
        postNotification.setUserId("testUser");
        postNotification.setPostId("123");
        postNotification.setText("Post Activity");
        postNotification.setType(NotificationType.POST);
        postNotification.setTimestamp(currentTime - TimeUnit.HOURS.toMillis(2));

        notificationRepository.save(commentNotification);
        notificationRepository.save(likeNotification);
        notificationRepository.save(postNotification);
    }

    @Test
    void testGetAnalytics() throws Exception {
        mockMvc.perform(get("/admin/analytics")
                        .param("seconds", "7200")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentCount").value(1))
                .andExpect(jsonPath("$.likeCount").value(1))
                .andExpect(jsonPath("$.postCount").value(1));
    }
}
