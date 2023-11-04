package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceAService {
    public synchronized void methodA(ResourceBService resourceBService) {
        log.info("ResourceA : Obtention du verrou sur ResourceA...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ResourceA : Attente pour acquérir le verrou de ResourceB...");
        resourceBService.methodB(this);
        log.info("ResourceA : Libération du verrou sur ResourceA.");
    }
}
