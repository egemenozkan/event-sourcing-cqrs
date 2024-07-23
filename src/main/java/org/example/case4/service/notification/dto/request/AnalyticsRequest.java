package org.example.case4.service.notification.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AnalyticsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long seconds;

}
