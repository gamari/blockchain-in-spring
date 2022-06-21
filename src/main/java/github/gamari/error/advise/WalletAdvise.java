package github.gamari.error.advise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import github.gamari.error.ExceptionResponse;
import github.gamari.error.exception.ValidationException;

@RestControllerAdvice
public class WalletAdvise extends ResponseEntityExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public ExceptionResponse validationException(ValidationException e, WebRequest request) {
		return new ExceptionResponse(e.getMessage());
	}
}
