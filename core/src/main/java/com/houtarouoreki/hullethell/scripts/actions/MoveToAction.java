package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionFloatArg;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionInterpolationArg;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionVector2Arg;

import java.util.regex.Pattern;

public class MoveToAction extends ScriptedAction {
	private static final Pattern target_position_pattern
			= Pattern.compile("\\((\\d+(?:\\.\\d+)?, \\d+(?:\\.\\d+)?)\\)");
	private static final Pattern duration_pattern
			= Pattern.compile("over ([0-9]+(?:[.][0-9]+)?) s(?:econds?)?");
	private Vector2 startingPosition;
	private Vector2 targetPosition;
	private float duration;
	private Interpolation interpolation;

	@Override
	protected void performAction() {
		if (duration == 0) {
			body.setPosition(targetPosition);
			setFinished();
			return;
		}
		if (getTicks() == 0) {
			startingPosition = body.getPosition();
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

	@Override
	public int bodiesAmount() {
		return 0;
	}

	@Override
	protected void addArgumentsInfo() {
		parser.vector2Args.add(new ActionVector2Arg(
				"Target position",
				null,
				"(0.3, 0.9)",
				target_position_pattern,
				this::setTargetPosition,
				false
		));
		parser.floatArgs.add(new ActionFloatArg(
				"Duration",
				"How long it takes to get there from the position at the start of the action",
				"over 4.2 seconds",
				duration_pattern,
				this::setDuration,
				true
		));
		parser.interpolationArgs.add(new ActionInterpolationArg(
				"Interpolation",
				null,
				"sineIn",
				Pattern.compile("(\\w+) interpolation"),
				this::setInterpolation,
				true
		));
	}

	private void setTargetPosition(Vector2 targetPosition) {
		this.targetPosition = targetPosition;
	}

	private void setDuration(float duration) {
		this.duration = duration;
	}

	private void setInterpolation(Interpolation interpolation) {
		this.interpolation = interpolation;
	}
}
