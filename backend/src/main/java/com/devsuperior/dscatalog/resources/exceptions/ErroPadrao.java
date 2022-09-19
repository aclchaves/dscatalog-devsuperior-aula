package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

public class ErroPadrao {
	
	private Instant timpestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	public ErroPadrao() {		
	}

	public Instant getTimpestamp() {
		return timpestamp;
	}

	public void setTimpestamp(Instant timpestamp) {
		this.timpestamp = timpestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
