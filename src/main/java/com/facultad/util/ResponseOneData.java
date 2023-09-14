package com.facultad.util;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseOneData {

	private int statusCode;
	private String message;
	private Map<String, Object> data;
}
