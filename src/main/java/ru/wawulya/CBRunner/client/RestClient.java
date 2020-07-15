package ru.wawulya.CBRunner.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.wawulya.CBRunner.config.AppProperties;
import ru.wawulya.CBRunner.model.CompletionCode;
import ru.wawulya.CBRunner.model.Property;
import ru.wawulya.CBRunner.model.Ticket;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class RestClient {


    @Autowired
    private AppProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    private HttpEntity<String> httpEntity;
    private HttpHeaders headers;

    public RestClient(AppProperties properties) {
        this.properties = properties;

        String strAuth = properties.getApi().get("username") + ":" + properties.getApi().get("password");
        String authBase64 = new String(Base64.getEncoder().encode(strAuth.getBytes()));

        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authBase64); //here is some login and pass like this login:pass

        httpEntity = new HttpEntity<String>(headers);
    }

    public Ticket getTicketById(Long id) {

        String url = properties.getBaseUrl() + "ticket/" + id;
        log.info("GET " + url);

        Ticket ticket = restTemplate.getForObject(url,Ticket.class);
        log.debug(ticket.toString());
        return ticket;
    }

    public List<Ticket> getTicketsForJob(UUID sessionId, int count) {
        String url =  properties.getBaseUrl() + "ticket/job?count=" + count;
        long start = System.currentTimeMillis();
        List<Ticket> ticketList = new ArrayList<>();
        ResponseEntity<Ticket[]> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Ticket[].class);

            if (response.getBody().length != 0)
                ticketList = Arrays.asList(response.getBody());

            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + response.getStatusCode() + " | " + duration);

        } catch (HttpClientErrorException exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getStatusCode() + " | " + duration);
        } catch (Exception exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getMessage() + " | " + duration);
        }

        return ticketList;
    }

    public void updateTicket(UUID sessionId, Ticket ticket) {
        String url = properties.getBaseUrl() + "ticket/update";
        long start = System.currentTimeMillis();
        ResponseEntity<String> response = null;

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Ticket> httpEntity = new HttpEntity<>(ticket,headers);

        try {
            response = restTemplate.postForEntity(url, httpEntity, String.class);
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | POST " + url + " | " + response.getStatusCode() + " | " + duration);

        } catch (HttpClientErrorException exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | POST " + url + " | " + exception.getStatusCode() + " | " + duration);

        } catch (Exception exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | POST " + url + " | " + exception.getMessage() + " | " + duration);

        }
    }

    public CompletionCode getCompletionCodeBySysname(UUID sessionId, String sysname) {
        String url =  properties.getBaseUrl() + "compcode/fetch?sysname=" + sysname;
        long start = System.currentTimeMillis();
        ResponseEntity<CompletionCode> response = null;
        CompletionCode completionCodeCode = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, CompletionCode.class);
            completionCodeCode = response.getBody();
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + response.getStatusCode() + " | " + duration);

        } catch (HttpClientErrorException exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getStatusCode() + " | " + duration);

        } catch (Exception exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getMessage() + " | " + duration);

        }

        return completionCodeCode;
    }

    @Async
    public CompletableFuture<CompletionCode> getCompletionCodeBySysname2(String sysname) throws InterruptedException {

        String url =  "http://localhost:8080/api/compcode/fetch?sysname=" + sysname;
        log.info("GET " + url);

        RestTemplate restTemplate = new RestTemplate();

        CompletionCode comCode = restTemplate.getForObject(url, CompletionCode.class);

        return CompletableFuture.completedFuture(comCode);
    }

    public Property getPropertyByName(UUID sessionId, String name) {
        String url = properties.getBaseUrl() + "properties/find?name=" + name;
        long start = System.currentTimeMillis();
        ResponseEntity<Property> response = null;
        Property property = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Property.class);
            property = response.getBody();
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + response.getStatusCode() + " | " + duration);

        } catch (HttpClientErrorException exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getStatusCode() + " | " + duration);

        } catch (Exception exception) {
            String duration = (System.currentTimeMillis() - start) + "ms";
            log.info(sessionId + " | GET " + url + " | " + exception.getMessage() + " | " + duration);

        }

        return property;
    }





}
