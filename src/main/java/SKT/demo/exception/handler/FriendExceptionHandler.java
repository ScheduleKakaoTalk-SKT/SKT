package SKT.demo.exception.handler;

import SKT.demo.exception.error.FriendException;
import SKT.demo.exception.response.FriendErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FriendExceptionHandler {
    @ExceptionHandler(FriendException.class)
    protected ResponseEntity<FriendErrorResponse> handleFriendException(FriendException e) {
        return FriendErrorResponse.toResponseEntity(e.getFriendErrorMessage());
    }
}
