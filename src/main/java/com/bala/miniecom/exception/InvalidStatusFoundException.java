package com.bala.miniecom.exception;

public class InvalidStatusFoundException extends RuntimeException {

    public InvalidStatusFoundException(
            Object oldStatus,
            Object newStatus) {

        super("Invalid status transition from " +
              oldStatus + " to " + newStatus);
    }
}
