package com.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColoringPages {
    private Integer id;
    private String coloringPageName;
    private String pathToFile;
    private Categories category;
}
