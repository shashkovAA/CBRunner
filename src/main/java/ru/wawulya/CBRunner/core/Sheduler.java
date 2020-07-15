package ru.wawulya.CBRunner.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wawulya.CBRunner.client.RestClient;
import ru.wawulya.CBRunner.config.AppProperties;
import ru.wawulya.CBRunner.enums.PropertyNameEnum;
import ru.wawulya.CBRunner.model.Property;
import ru.wawulya.CBRunner.model.Session;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class Sheduler {

    @Autowired
    private RestClient restClient;

    @Autowired
    private AppProperties appProperties;

    private volatile boolean startFlag = false;

    private long delay = 10;
    private long period = 60;
    private TaskManager taskManager;

    private ScheduledExecutorService executorService;

    public void initialize() {

        log.warn("Initializing Sheduler ...");
        UUID sessionId = new Session().getUuid();
        Property property = restClient.getPropertyByName(sessionId, PropertyNameEnum.AVAILABILITY);

        if (property != null) {
            startFlag = true;
            executorService = Executors.newScheduledThreadPool(1);
            taskManager = new TaskManager(restClient);
            log.warn("Initialization successfully !");
        } else {
            log.error("Initialization failed !");
            log.error("No access to Ticket API or database, check log files and try again");
            System.exit(0);
        }

    }

    public void start() {

        if (startFlag) {
            log.info("Starting Sheduler ...");
            executorService.scheduleAtFixedRate(taskManager, delay, period, TimeUnit.SECONDS);
            log.info("Sheduler started!");
        }
    }

}
