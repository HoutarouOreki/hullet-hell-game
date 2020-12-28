package com.houtarouoreki.hullethell.scripts.exceptions;

public class ScriptException extends Exception {
    public ScriptException() {
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrorScreenMessage() {
        String msg = getMessage();
        if (getCause() instanceof ScriptException)
            msg += "\nCause: " + increasedIndent('\n' + ((ScriptException) getCause()).getErrorScreenMessage());
        else
            msg += "\nCause: " + increasedIndent('\n' + getCause().getMessage());
        return msg;
    }

    private String increasedIndent(String s) {
        return s.replace("\n", "\n    ");
    }
}
