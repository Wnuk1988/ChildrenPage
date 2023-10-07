package com.tms.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "To delete a user's favorites")
@Data
public class RequestIdAndFilename {
    private Integer fileId;
    private String fileName;
}
