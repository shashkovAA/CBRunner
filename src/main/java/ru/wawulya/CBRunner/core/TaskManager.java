package ru.wawulya.CBRunner.core;

import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Component;
import ru.wawulya.CBRunner.client.RestClient;

import ru.wawulya.CBRunner.model.Attempt;
import ru.wawulya.CBRunner.model.CompletionCode;
import ru.wawulya.CBRunner.model.Session;
import ru.wawulya.CBRunner.model.Ticket;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Slf4j
@Component
public class TaskManager implements Runnable{

    private RestClient restClient;

    final Random random = new Random();

    public TaskManager(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void run() {

        UUID sessionId = new Session().getUuid();

        log.debug(sessionId + " | Start task...");

        String [] compCodes = {"busy", "network_busy", "noanswer", "connection.disconnect.transfer", "canceled", "dropped", "undefined"};

        //long start = System.currentTimeMillis();

        //String compCodeStr = "busy";

        /*CompletableFuture<CompletionCode> compCode = null;

        try {
            compCode = restClient.getCompletionCodeBySysname2(compCodeStr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Request in process ...");
        // Wait until they are all done
        //CompletableFuture.allOf(compCode).join();*/

        /*CompletionCode compCode = restClient.getCompletionCodeBySysname(compCodeStr);

        log.info("Elapsed time: " + (System.currentTimeMillis() - start));

        log.info(compCode.toString());*/

        /*try {
            log.info(compCode.get().toString());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/


        List<Ticket> jobList = restClient.getTicketsForJob(sessionId, 1);

        if (jobList.size() == 0) {
            log.debug(sessionId + " | Don't get active tickets from DB ...");


        } else {
            log.debug(sessionId + " | Get list of active tickets from DB: ");
            jobList.forEach(t->log.debug(sessionId + " | " + t.toString()));

            String compCodeStr = compCodes[random.nextInt(7)];
            log.info(sessionId + " | completion code = " + compCodeStr);

            CompletionCode compCode = restClient.getCompletionCodeBySysname(sessionId, compCodeStr);
            log.info(sessionId + " | completion code from DB = " + compCode.toString());


            Ticket currTicket = jobList.get(0);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currTicket.setLastCompletionCode(compCode);

            Attempt attempt = currTicket.getAttempts().get(currTicket.getAttemptCount()-1);
            attempt.setCallId("11230");
            attempt.setUcid("110112" + System.currentTimeMillis());
            attempt.setAttemptStop(new Timestamp(new Date().getTime()));
            attempt.setCompletionCode(compCode);
            attempt.setPhantomNumber("11203");
            attempt.setOperatorNumber("70035");

            restClient.updateTicket(sessionId, currTicket);

        }

        log.debug(sessionId + " | Stop task...");

    }
}
