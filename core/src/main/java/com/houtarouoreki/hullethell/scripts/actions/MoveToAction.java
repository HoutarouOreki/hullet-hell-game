package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class MoveToAction extends ScriptedAction {
    private Vector2 startingPosition;
    private Vector2 targetPosition;
    private double duration;
    private Interpolation interpolation;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        targetPosition = ParsingHelpers.vector2fromStrings(arguments.get(0), arguments.get(1)).scl(world.viewArea);
        if (arguments.size() < 3) {
            duration = 0;
        } else {
            duration = Double.parseDouble(arguments.get(2));
        }
        if (arguments.size() < 4) {
            interpolation = Interpolation.linear;
        } else {
            interpolation = getInterpolationFromString(arguments.get((3)));
        }
    }

    @Override
    protected void performAction() {
        if (duration == 0) {
            body.setPosition(targetPosition.cpy());
            setFinished();
            return;
        }
        if (getTicks() == 0) {
            startingPosition = new Vector2(body.getPosition());
        }
        float progress = (float) ((section.getTimePassed() - getScriptedTime()) / duration);
        if (progress > 1) {
            progress = 1;
            setFinished();
        }
        float x = interpolation.apply(startingPosition.x, targetPosition.x, progress);
        float y = interpolation.apply(startingPosition.y, targetPosition.y, progress);
        body.setPosition(new Vector2(x, y));
    }

    private Interpolation getInterpolationFromString(String s) {
        if (s.equals("linear")) {
            return Interpolation.linear;
        }
        if (s.equals("smooth")) {
            return Interpolation.smooth;
        }
        if (s.equals("smooth2")) {
            return Interpolation.smooth2;
        }
        if (s.equals("smoother")) {
            return Interpolation.smoother;
        }
        if (s.equals("fade")) {
            return Interpolation.fade;
        }
        if (s.equals("pow2")) {
            return Interpolation.pow2;
        }
        if (s.equals("pow2In")) {
            return Interpolation.pow2In;
        }
        if (s.equals("pow2Out")) {
            return Interpolation.pow2Out;
        }
        if (s.equals("pow2InInverse")) {
            return Interpolation.pow2InInverse;
        }
        if (s.equals("pow2OutInverse")) {
            return Interpolation.pow2OutInverse;
        }
        if (s.equals("pow3")) {
            return Interpolation.pow3;
        }
        if (s.equals("pow3In")) {
            return Interpolation.pow3In;
        }
        if (s.equals("pow3Out")) {
            return Interpolation.pow3Out;
        }
        if (s.equals("pow3InInverse")) {
            return Interpolation.pow3InInverse;
        }
        if (s.equals("pow3OutInverse")) {
            return Interpolation.pow3OutInverse;
        }
        if (s.equals("pow4")) {
            return Interpolation.pow4;
        }
        if (s.equals("pow4In")) {
            return Interpolation.pow4In;
        }
        if (s.equals("pow4Out")) {
            return Interpolation.pow4Out;
        }
        if (s.equals("pow5")) {
            return Interpolation.pow5;
        }
        if (s.equals("pow5In")) {
            return Interpolation.pow5In;
        }
        if (s.equals("pow5Out")) {
            return Interpolation.pow5Out;
        }
        if (s.equals("sine")) {
            return Interpolation.sine;
        }
        if (s.equals("sineIn")) {
            return Interpolation.sineIn;
        }
        if (s.equals("sineOut")) {
            return Interpolation.sineOut;
        }
        if (s.equals("exp10")) {
            return Interpolation.exp10;
        }
        if (s.equals("exp10In")) {
            return Interpolation.exp10In;
        }
        if (s.equals("exp10Out")) {
            return Interpolation.exp10Out;
        }
        if (s.equals("exp5")) {
            return Interpolation.exp5;
        }
        if (s.equals("exp5In")) {
            return Interpolation.exp5In;
        }
        if (s.equals("exp5Out")) {
            return Interpolation.exp5Out;
        }
        if (s.equals("circle")) {
            return Interpolation.circle;
        }
        if (s.equals("circleIn")) {
            return Interpolation.circleIn;
        }
        if (s.equals("circleOut")) {
            return Interpolation.circleOut;
        }
        if (s.equals("elastic")) {
            return Interpolation.elastic;
        }
        if (s.equals("elasticIn")) {
            return Interpolation.elasticIn;
        }
        if (s.equals("elasticOut")) {
            return Interpolation.elasticOut;
        }
        if (s.equals("swing")) {
            return Interpolation.swing;
        }
        if (s.equals("swingIn")) {
            return Interpolation.swingIn;
        }
        if (s.equals("swingOut")) {
            return Interpolation.swingOut;
        }
        if (s.equals("bounce")) {
            return Interpolation.bounce;
        }
        if (s.equals("bounceIn")) {
            return Interpolation.bounceIn;
        }
        if (s.equals("bounceOut")) {
            return Interpolation.bounceOut;
        }
        throw new Error("Could not find interpolation \"" + s + "\"");
    }
}
