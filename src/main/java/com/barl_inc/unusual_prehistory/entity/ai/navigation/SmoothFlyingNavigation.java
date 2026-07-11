package com.barl_inc.unusual_prehistory.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SmoothFlyingNavigation extends FlyingPathNavigation implements ExtendedNavigator {

	public SmoothFlyingNavigation(Mob mob, Level level) {
		super(mob, level);
	}

    @Override
    public Mob getMob() {
        return mob;
    }

    @Nullable
    @Override
    public Path getPath() {
        return super.getPath();
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return createSmoothPathFinder(nodeEvaluator, maxVisitedNodes);
    }
}