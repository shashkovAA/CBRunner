package ru.wawulya.CBRunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ticket {

    private Long id;
    private String cbNumber;
    private Timestamp cbDate;
    private int attemptCount;
    private boolean finished;
    private TicketParams ticketParams;
    private List<Attempt> attempts;
    private CompletionCode lastCompletionCode;

}
