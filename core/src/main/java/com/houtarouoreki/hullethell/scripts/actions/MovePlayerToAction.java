package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedActionInitializationException;

public class MovePlayerToAction extends MoveToAction {
    @Override
    protected void initialise(World world, ScriptedSection section, Body body) throws ScriptedActionInitializationException {
        super.initialise(world, section, body);
        this.body = world.player;
    }
}
