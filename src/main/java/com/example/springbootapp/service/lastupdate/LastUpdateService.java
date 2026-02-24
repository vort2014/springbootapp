package com.example.springbootapp.service.lastupdate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class LastUpdateService {

    private volatile Instant databaseTimestamp = Instant.now();

    public void updateDatabaseTimestamp(Instant timestamp) {
        log.trace("Updating database timestamp to {}", timestamp);
        databaseTimestamp = timestamp;
    }

    public LastUpdateModel getLastUpdate() {
        return new LastUpdateModel(databaseTimestamp);
    }
}
