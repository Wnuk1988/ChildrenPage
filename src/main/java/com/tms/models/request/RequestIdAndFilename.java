package com.tms.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Schema(description = "To delete a user's favorites")
@Component
@Data
@NoArgsConstructor
public class RequestIdAndFilename {
    private Integer fileId;
    private String fileName;
}
