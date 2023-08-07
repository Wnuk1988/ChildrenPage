package com.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioFairyTales {
    private Integer id;
    private String audioName;
    private String author;
    private String pathToFile;
    private Categories categories;
}
