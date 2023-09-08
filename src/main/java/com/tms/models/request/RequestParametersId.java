package com.tms.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Schema(description = "To add user favorites")
@Component
@Data
@NoArgsConstructor
public class RequestParametersId {
    private Integer userId;
    private Integer fileId;
}
