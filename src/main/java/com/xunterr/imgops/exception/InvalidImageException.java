package com.xunterr.imgops.exception;

import java.io.IOException;

public class InvalidImageException extends IOException {
    public InvalidImageException(String message) {
        super(message);
    }
}
