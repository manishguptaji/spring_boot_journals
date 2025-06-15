package net.engineeringdigest.journalApp.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean error;
    private String message;
    private T data;
}