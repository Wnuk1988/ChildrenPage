package com.tms.models.request;

import com.tms.models.PaginationSort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Schema(description = "Для вывода описания файлов пагинацией")
@Component
@Data
@NoArgsConstructor
public class RequestPagination {
    @Min(0)
    private Integer offset;
    @Min(1)
    @Max(50)
    private Integer limit;
    @Enumerated(EnumType.STRING)
    private PaginationSort sort;
}
