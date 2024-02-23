package com.metamon.horok.config;

import com.metamon.horok.service.PaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayScheduler {

    private final PaysService paysService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleRemovePayList(){
            paysService.setPayExpired();
    }


}
