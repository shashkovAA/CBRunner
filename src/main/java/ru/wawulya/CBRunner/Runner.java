package ru.wawulya.CBRunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wawulya.CBRunner.client.RestClient;
import ru.wawulya.CBRunner.core.Sheduler;
import ru.wawulya.CBRunner.model.Attempt;
import ru.wawulya.CBRunner.model.CompletionCode;
import ru.wawulya.CBRunner.model.Session;
import ru.wawulya.CBRunner.model.Ticket;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class Runner {

    @Autowired
    private RestClient restClient;

    private boolean  isExit = false;
    private String command, input;

    public Runner() {
    }

    public void start () {
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            System.out.println();
            System.out.println("Avaliable commands:");
            System.out.println();
            System.out.println("1. Get Ticket by Id");
            System.out.println("2. Action #2");
            System.out.println("3. Action #3");
            System.out.println();
            System.out.println("0. exit");
            System.out.println();
            System.out.print("Select command number: ");

            command = scanner.next();

            switch (command) {

                case "1": {
                    System.out.print("Enter id: ");
                    String id = scanner.next();

                    Ticket ticket = restClient.getTicketById(Long.valueOf(id));
                    System.out.println(ticket);
                    System.out.println();
                    break;
                }
                case "2": {
                    UUID sessionId = new Session().getUuid();
                    long start = System.currentTimeMillis();

                    String compCodeStr = "busy";

                    /*CompletableFuture<CompletionCode> compCode = null;

                    try {
                        compCode = restClient.getCompletionCodeBySysname2(compCodeStr);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    log.info("Request in process ...");
                    // Wait until they are all done
                    CompletableFuture.allOf(compCode).join();

                    log.info("Elapsed time: " + (System.currentTimeMillis() - start));*/

                    CompletionCode compCode = restClient.getCompletionCodeBySysname(sessionId, compCodeStr);
                    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
                    log.info(compCode.toString());

                    /*try {
                        log.info(compCode.get().toString());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }*/


                    break;}

                case "3":  {
                    System.out.print("You press '3'");
                    UUID sessionId = new Session().getUuid();
                    System.out.print("You start job");
                    List<Ticket> jobList = restClient.getTicketsForJob(sessionId,1);

                    if (jobList.size() == 0) {
                        System.out.println("jobList.size() = " + jobList.size());
                        break;
                    }
                    String compCodeStr = "busy";
                    CompletionCode compCode = restClient.getCompletionCodeBySysname(sessionId, compCodeStr);
                    System.out.print("Completion Code = " + compCode);

                    Ticket currTicket = jobList.get(0);

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currTicket.setLastCompletionCode(compCode);

                    Attempt attempt = currTicket.getAttempts().get(currTicket.getAttemptCount()-1);
                    attempt.setCallId("11230");
                    attempt.setUcid("110112" + String.valueOf(System.currentTimeMillis()));
                    attempt.setAttemptStop(new Timestamp(new Date().getTime()));
                    attempt.setCompletionCode(compCode);
                    attempt.setPhantomNumber("11203");
                    attempt.setOperatorNumber("70035");

                    restClient.updateTicket(sessionId, currTicket);


                    System.out.println("ha ha");
                    break;
                }


                case "0":
                    isExit = true;
                    System.out.println("Exit program.");
                    pressAnyKeyToContinue();
                    System.exit(0);
                    break;

                default:
                    System.out.print("Invalid input. Try again...");
                    System.out.println();

            }

            System.out.print("Enter 'y' for continue? :");
            input = scanner.next();

            if ((!input.equals("y") && !input.equals("Y"))) {
                isExit = true;
                System.out.println("Exit program.");
                pressAnyKeyToContinue();
            }
        }

        scanner.close();
        System.exit(0);
    }

    private void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to close  ...");
        try{
            System.in.read();
        }
        catch(Exception e){
        }
    }


}
