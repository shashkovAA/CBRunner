package ru.wawulya.CBRunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompletionCode {
    private Long id;
    private String name;
    private String sysname;
    private String description;
    private boolean recall;

}



