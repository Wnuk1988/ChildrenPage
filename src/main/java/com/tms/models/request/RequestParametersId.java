package com.tms.models.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class RequestParametersId {
    private Integer userId;
    private Integer fileId;
}
