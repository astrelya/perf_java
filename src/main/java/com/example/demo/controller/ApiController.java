package com.example.demo.controller;

import com.example.demo.service.CalculatorService;
import com.example.demo.service.ResourceAService;
import com.example.demo.service.ResourceBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ResourceAService resourceAService;

    @Autowired
    private ResourceBService resourceBService;

    @GetMapping("/add")
    public ResponseEntity<Integer> addNumbers(@RequestParam int num1, @RequestParam int num2) throws InterruptedException {

        Thread.sleep(10000);
        int add = calculatorService.add(num1, num2);

        return ResponseEntity.ok(add);
    }

    @GetMapping("/deadlock")
    public ResponseEntity<String> simulateDeadlock() {
        Thread thread1 = new Thread(() -> {
            resourceAService.methodA(resourceBService);
        });

        Thread thread2 = new Thread(() -> {
            resourceBService.methodB(resourceAService);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }

        return ResponseEntity.ok("Simulation de deadlock terminée.");
    }
}
