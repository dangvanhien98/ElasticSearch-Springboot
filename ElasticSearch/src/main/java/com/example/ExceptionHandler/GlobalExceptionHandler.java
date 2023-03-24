package com.example.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<ErrorModel>> handleBindException(BindException bindException) {
//		String mess = "request hop le";
		if (bindException.getBindingResult().hasErrors()) {
//			Map<String, String> errors = new HashMap<String, String>();
			List<ErrorModel> lstErr = new ArrayList<ErrorModel>();
//			bindException.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
			bindException.getFieldErrors().forEach(error -> lstErr.add(new ErrorModel(error.getField(), error.getDefaultMessage())));
//			String err = "";
//			for (String key : errors.keySet()) {
//				err +=key + " : " + errors.get(key) + "\n";
//			}
			return ResponseEntity.status(400).body(lstErr);
		}
		return ResponseEntity.status(200).build();
	}
}
