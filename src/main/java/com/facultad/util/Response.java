package com.facultad.util;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
	
	private int statusCode;
	private String message;
	private List<?> data;
}
