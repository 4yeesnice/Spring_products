package org.example.products_spring.exception;

public class CommandNotFound extends Exception{
    public CommandNotFound(){
        super("Input command not found, please try again!");
    }
}
