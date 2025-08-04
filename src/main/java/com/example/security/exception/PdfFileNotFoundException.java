package com.example.security.exception;

public class PdfFileNotFoundException extends  RuntimeException{
    public PdfFileNotFoundException(String error){
        super(error);
    }
}
