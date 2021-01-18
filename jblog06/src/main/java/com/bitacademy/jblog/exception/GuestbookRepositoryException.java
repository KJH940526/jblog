package com.bitacademy.jblog.exception;

public class GuestbookRepositoryException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GuestbookRepositoryException() {
		super("Guestbook Repository Exception");
	}
	
	public GuestbookRepositoryException(String message) {
		super(message);
	}
}
