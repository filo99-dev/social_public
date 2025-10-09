package org.elis.social.dto.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationErrorDTO {
    private String message;
    private LocalDateTime timestamp=LocalDateTime.now();
    private Map<String,String> fieldErrors;
    private String path;
    private String status;
}
