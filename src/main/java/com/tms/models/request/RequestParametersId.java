package com.tms.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "To add user favorites")
@Data
public class RequestParametersId {
    private Integer userId;
    private Integer fileId;
}
