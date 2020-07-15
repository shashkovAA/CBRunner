package ru.wawulya.CBRunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attempt {

    private Long id;
    private String ucid;
    private String callId;
    private Timestamp attemptStart;
    private Timestamp attemptStop;
    private String phantomNumber;
    private String operatorNumber;
    private CompletionCode completionCode;


}
