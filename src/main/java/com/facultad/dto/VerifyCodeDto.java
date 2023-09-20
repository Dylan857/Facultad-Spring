package com.facultad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VerifyCodeDto {

	@NotNull
	private Integer code;
}
