package com.example.todo.dto;

import java.time.Instant;
import java.util.Map;

/**
 *  les champs sont final par défaut donc pas de setter —
 *  un record est immuable. Et Jackson (le JSON de Spring) sérialise les records parfaitement sans config supplémentaire.
 * @param status
 * @param message
 * @param timeStamp
 * @param fieldErrors
 */
public record  ApiErrorResponse (
        int status,
        String message,
        Instant timeStamp,
        Map<String,String> fieldErrors
){ }
