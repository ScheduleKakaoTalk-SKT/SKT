package SKT.demo.exception.handler;

import SKT.demo.exception.error.UserException;
import SKT.demo.exception.response.UserErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)
    protected ResponseEntity<UserErrorResponse> handleUserException(UserException e) {
        return UserErrorResponse.toResponseEntity(e.getUserErrorMessage());
    }
}
