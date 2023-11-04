package com.example.demo.controller;

import com.example.demo.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private ApiController apiController;

    private ExecutorService executor;

    @BeforeEach
    public void setUp() {
        executor = Executors.newFixedThreadPool(5);
    }

    @Test
    public void testAddNumbers() throws InterruptedException {
        // Mocking calculatorService
        when(calculatorService.add(2, 3)).thenReturn(5);

        // Appel de la méthode du contrôleur
        ResponseEntity<Integer> response = apiController.addNumbers(2, 3);

        // Vérification du résultat
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, response.getBody());
    }

    @Test
    public void testAddNumbersWithDelay() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            // Mocking calculatorService
            when(calculatorService.add(2, 3)).thenReturn(5);

            // Appel de la méthode du contrôleur
            ResponseEntity<Integer> response = apiController.addNumbers(2, 3);

            // Vérification du résultat
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(5, response.getBody());

        }
    }

    @Test
    public void testAddNumbersWithParallelExecution() throws Exception {
        Future<?>[] futures = new Future[5];

        for (int i = 0; i < 5; i++) {
            // Mocking calculatorService
            when(calculatorService.add(2, 3)).thenReturn(5);
            futures[i] = executor.submit(() -> {
                // Appel de la méthode du contrôleur
                ResponseEntity<Integer> response = null;
                try {
                    response = apiController.addNumbers(2, 3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Vérification du résultat
                assertEquals(200, response.getStatusCodeValue());
                assertEquals(5, response.getBody());

            });
        }

        // Attend que toutes les tâches soient terminées
        for (Future<?> future : futures) {
            future.get();
        }

        // Arrêtez le pool de threads
        executor.shutdown();

    }

}