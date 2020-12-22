package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;

public class MovePlayerToAction extends MoveToAction {
    @Override
    protected void initialise(World world, ScriptedSection section, Body body) {
        super.initialise(world, section, body);
        this.body = world.player;
    }
}
