package org.elis.social.errorhandling.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.elis.social.dto.error.ErrorResponseDTO;
import org.elis.social.dto.error.ValidationErrorDTO;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.errorhandling.exceptions.OwnershipException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.SignatureException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyErrorHandlers {
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorResponseDTO> handleNoMediaType(Exception e, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var dto = new ErrorResponseDTO();
        dto.setMessage("media type della request non supportato");
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        var dto = new ErrorResponseDTO();
        var status = HttpStatus.BAD_REQUEST;
        dto.setMessage("errore nel formato del body della request");
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleMalformedJWT(MalformedJwtException exception, WebRequest request) throws Throwable {
        var dto = new ErrorResponseDTO();
        var status = HttpStatus.FORBIDDEN;
        dto.setMessage("Il token non rispetta il pattern JWT");
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredJwtException exception, WebRequest request) throws Throwable {
        var dto = new ErrorResponseDTO();
        var status = HttpStatus.FORBIDDEN;
        dto.setMessage("Il token e' scaduto, effettua un nuovo login e prendi un token aggiornato");
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDTO> handleSignatureException(SignatureException exception, WebRequest request) throws Throwable {
        var dto = new ErrorResponseDTO();
        var status = HttpStatus.FORBIDDEN;
        dto.setMessage("Il token e' stato manomesso e la firma non e' valida");
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler({ DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponseDTO> handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException exception, WebRequest request) throws Throwable {
        var dto = new ErrorResponseDTO();
        var status = HttpStatus.BAD_REQUEST;
        dto.setMessage("operazione non supportata");
        dto.setPath(request.getDescription(false));
        dto.setStatus(status.name());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponseDTO>  notFoundException(Exception e, WebRequest request) throws Throwable {
        HttpStatus status = HttpStatus.NOT_FOUND;
        var dto = new ErrorResponseDTO();
        dto.setMessage(e.getMessage());
        dto.setStatus(status.name());
        dto.setPath(request.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> validation(MethodArgumentNotValidException e, WebRequest request) throws Throwable {
        var dto = new ValidationErrorDTO();
        dto.setMessage("Errore nei campi inviati");
        dto.setPath(request.getDescription(false));
        dto.setFieldErrors(e.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, t->{
            String message = t.getDefaultMessage();
            if(message==null)
            {
                message = "Messaggio non disponibile. Valore rifiutato: "+t.getRejectedValue();
            }
            return message;
        })));
        dto.setStatus(HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<ErrorResponseDTO> ownershipExceptionHandler(OwnershipException ex, WebRequest wr)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var dto = new ErrorResponseDTO();
        dto.setMessage(ex.getMessage());
        dto.setStatus(status.name());
        dto.setPath(wr.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> fallbackHandler(ResponseStatusException ex, WebRequest wr)
    {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        var dto = new ErrorResponseDTO();
        dto.setMessage(ex.getReason());
        dto.setStatus(status.name());
        dto.setPath(wr.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> fallbackHandler(Exception ex, WebRequest wr)
    {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var dto = new ErrorResponseDTO();
        dto.setMessage(ex.toString());
        dto.setStatus(status.name());
        dto.setPath(wr.getDescription(false));
        return ResponseEntity.status(status).body(dto);
    }
}
