package com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions;

import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionArgBase;

public class RequiredArgumentNotFoundException extends Exception {
    public RequiredArgumentNotFoundException(ActionArgBase arg) {
        super("Required argument \"" + arg.name + "\" not found.");
    }
}
