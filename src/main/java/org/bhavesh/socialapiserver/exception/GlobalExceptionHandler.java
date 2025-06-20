package org.bhavesh.socialapiserver.exception;


import org.bhavesh.socialapiserver.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(UserException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>("Internal server error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ApiResponse<?>> handlePostException(PostException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}