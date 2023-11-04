package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceBService {

    public synchronized void methodB(ResourceAService resourceAService) {
        log.info("ResourceB : Obtention du verrou sur ResourceB...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("ResourceB : Attente pour acquérir le verrou de ResourceA...");
        resourceAService.methodA(this);
        log.info("ResourceB : Libération du verrou sur ResourceB.");
    }
}
