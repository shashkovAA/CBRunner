package ru.wawulya.CBRunner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wawulya.CBRunner.client.RestClient;
import ru.wawulya.CBRunner.model.Ticket;


@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private RestClient restclient;

    @GetMapping(value = "/testGetOneTicket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ticket testGetOneTicket(@PathVariable String id) {
        log.info("REST GET /api/testGetOneTicket/" + id);
        Ticket ticket = restclient.getTicketById(Long.valueOf(id));
        return ticket ;
    }

}
