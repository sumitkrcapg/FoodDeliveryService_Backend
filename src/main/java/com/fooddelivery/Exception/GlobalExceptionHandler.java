package com.fooddelivery.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fooddelivery.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	@ExceptionHandler(InvalidRestaurantIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCustomerIDException(InvalidRestaurantIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(InvalidItemIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidItemIdException(InvalidItemIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(InvalidMenuItemException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidMenuItemException(InvalidMenuItemException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
		
	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(CustomerNotFoundException ex){
		ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("NOT_FOUND");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(DuplicateCustomerIDException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionResponse> handleDuplicateCustomerIDException(DuplicateCustomerIDException ex){
    	ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DuplicateRestaurantIDException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateRestaurantIDException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
    }
 	
 	@ExceptionHandler(NoSuchRestaurantIDException.class)
 	public ResponseEntity<ExceptionResponse> NoSuchRestaurantIDException(NoSuchRestaurantIDException e){
 		ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("NOT_FOUND");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
 	}
 	
 	@ExceptionHandler(OrdersNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrdersNotFoundException(OrdersNotFoundException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
 	
	@ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOrderIDException(InvalidOrderIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus. CONFLICT);
    }
	
	@ExceptionHandler(DuplicateOrderIdException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateOrderIDException(DuplicateOrderIdException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("Duplicate id");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DuplicateDriverIDException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateDriverIDException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(InvalidDriverIDException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantIDException(InvalidDriverIDException e){
        ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NoSuchDriverIDException.class)
	public ResponseEntity<ExceptionResponse> NoSuchRestaurantIDException(NoSuchDriverIDException e){
		ExceptionResponse response = new ExceptionResponse();
        
        response.setErrorCode("NOT_FOUND");
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String,String> errors = new HashMap<>();
		
		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
