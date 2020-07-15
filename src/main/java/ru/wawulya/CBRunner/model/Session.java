package ru.wawulya.CBRunner.model;

import java.util.UUID;

public class Session {
    public UUID uuid;

    public UUID getUuid() {

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }
}
