package com.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FairyTales {
    private Integer id;
    private String fairyTaleName;
    private String author;
    private String shortDescription;
    private String pathToFile;
    private Categories categories;
}
