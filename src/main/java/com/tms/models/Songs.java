package com.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Songs {
    private Integer id;
    private String songName;
    private String author;
    private String pathToFile;
    private Categories categories;
}
