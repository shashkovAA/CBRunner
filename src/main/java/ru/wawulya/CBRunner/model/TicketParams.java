package ru.wawulya.CBRunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketParams {

    private String cbUrl;
    private String ucidOld;
    private String cbType;
    private String cbSource;
    private String cbOriginator;
    private int cbMaxAttempts;
    private int cbAttemptsTimeout;


}
