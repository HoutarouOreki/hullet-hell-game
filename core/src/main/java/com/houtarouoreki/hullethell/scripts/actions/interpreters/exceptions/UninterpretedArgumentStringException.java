package com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions;

public class UninterpretedArgumentStringException extends Exception {
    public UninterpretedArgumentStringException(String argString) {
        super("Could not interpret argument string \"" + argString + "\".");
    }
}
