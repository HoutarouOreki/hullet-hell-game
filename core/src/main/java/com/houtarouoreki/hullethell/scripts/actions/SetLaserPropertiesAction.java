package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Laser;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionFloatArg;

import java.util.regex.Pattern;

public class SetLaserPropertiesAction extends ScriptedAction {
    private float width;
    private float length;
    private float rotationDuration;
    private float damage;

    private static final Pattern width_pattern = Pattern.compile("(\\d+(?:\\.\\d+)?) ?m(?:eters?)? wide");
    private static final Pattern length_pattern = Pattern.compile("(\\d+(?:\\.\\d+)?) ?m(?:eters)? long");
    private static final Pattern rotation_duration_pattern = Pattern.compile("rotation(?: duration| spanning|) (\\d+(?:\\.\\d+)?) ?s(?:econds?)?");
    private static final Pattern damage_pattern = Pattern.compile("(?:deals )?(\\d+(?:\\.\\d+)?) (damage|dmg)");

    @Override
    protected void performAction() {
        ((Laser) body).setWidth(width);
        ((Laser) body).setLength(length);
        ((Laser) body).setRotationDuration(1 / rotationDuration);
        ((Laser) body).damage = damage;
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.floatArgs.add(new ActionFloatArg(
                "Width",
                "How thick the laser is.",
                "0.5m wide",
                width_pattern,
                this::setWidth,
                false
        ));
        parser.floatArgs.add(new ActionFloatArg(
                "Length",
                null,
                "3 meters long",
                length_pattern,
                this::setLength,
                false
        ));
        parser.floatArgs.add(new ActionFloatArg(
                "Rotation duration",
                "In how many seconds the laser rotates a full circle.",
                "rotation duration 5 s",
                rotation_duration_pattern,
                this::setRotationDuration,
                true
        ));
        parser.floatArgs.add(new ActionFloatArg(
                "Damage",
                null,
                "deals 4 dmg",
                damage_pattern,
                this::setDamage,
                true
        ));
    }

    private void setLength(Float length) {
        this.length = length;
    }

    private void setWidth(Float width) {
        this.width = width;
    }

    private void setRotationDuration(float rotationDuration) {
        this.rotationDuration = rotationDuration;
    }

    private void setDamage(float damage) {
        this.damage = damage;
    }
}
