package org.example.case4.service.notification.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserActivity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String postId;
    private String userId;
    private String type;
    private String text;
    private long timestamp;

    @Override
    public String toString() {
        return "UserActivity{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}