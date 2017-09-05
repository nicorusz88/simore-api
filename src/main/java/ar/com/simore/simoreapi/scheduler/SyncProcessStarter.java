package ar.com.simore.simoreapi.scheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This bean acts as a starter for the wearables data synchronization service
 */
@Component
public class SyncProcessStarter {
    private final Logger logger = Logger.getLogger(SyncProcessStarter.class.getName());

    @Scheduled(fixedRate = 65000000)
    public void init(){

    }
}
