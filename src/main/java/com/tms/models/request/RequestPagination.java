package com.tms.models.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class RequestPagination {
    @Min(0)
    private Integer offset;
    @Min(1)
    @Max(50)
    private Integer limit;
}
