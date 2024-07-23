package org.example.case4.service.notification.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AnalyticsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long commentCount;
    private long likeCount;
    private long postCount;


}
