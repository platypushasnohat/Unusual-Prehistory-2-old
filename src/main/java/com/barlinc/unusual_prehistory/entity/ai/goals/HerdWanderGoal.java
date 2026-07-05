package com.barlinc.unusual_prehistory.entity.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class HerdWanderGoal extends PrehistoricWanderGoal {

	protected final Predicate<PathfinderMob> herdPredicate;
	protected final double speedModifierFar;
	protected final int preferredHerdSize;
	protected boolean isHerdFar;

	public HerdWanderGoal(PathfinderMob mob, double speedModifier, double speedModifierFar, int preferredHerdSize) {
		this(mob, speedModifier, speedModifierFar, 120, 18, 7, preferredHerdSize, true, entity -> entity.getClass().isAssignableFrom(mob.getClass()) || mob.getClass().isAssignableFrom(entity.getClass()));
	}

	public HerdWanderGoal(PathfinderMob mob, double speedModifier, double speedModifierFar, int preferredHerdSize, boolean shouldAvoidWater) {
		this(mob, speedModifier, speedModifierFar, 120, 18, 7, preferredHerdSize, shouldAvoidWater, entity -> entity.getClass().isAssignableFrom(mob.getClass()) || mob.getClass().isAssignableFrom(entity.getClass()));
	}

	public HerdWanderGoal(PathfinderMob mob, double speedModifier, double speedModifierFar, int interval, int preferredHerdSize, boolean shouldAvoidWater) {
		this(mob, speedModifier, speedModifierFar, interval, 18, 7, preferredHerdSize, shouldAvoidWater, entity -> entity.getClass().isAssignableFrom(mob.getClass()) || mob.getClass().isAssignableFrom(entity.getClass()));
	}

	public HerdWanderGoal(PathfinderMob mob, double speedModifier, double speedModifierFar, int radius, int height, int interval, int preferredHerdSize, boolean shouldAvoidWater, Predicate<PathfinderMob> herdPredicate) {
		super(mob, speedModifier, interval, radius, height, shouldAvoidWater);
		this.speedModifierFar = speedModifierFar;
		this.preferredHerdSize = preferredHerdSize;
        this.herdPredicate = herdPredicate;
    }

	@Override
	public void start() {
		this.mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, isHerdFar ? speedModifierFar : speedModifier);
	}

	@Nullable
	@Override
	protected Vec3 getPosition() {
		List<PathfinderMob> herd = mob.level().getEntitiesOfClass(PathfinderMob.class, mob.getBoundingBox().inflate(32.0D, 8.0D, 32.0D), herdPredicate.and(entity -> entity != mob));
		List<PathfinderMob> closeHerd = herd.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(mob))).limit(preferredHerdSize - 1).toList();

		if (!closeHerd.isEmpty()) {
			double friendDistance = mob.distanceToSqr(closeHerd.getFirst());
			double herdX = 0.0D;
			double herdY = 0.0D;
			double herdZ = 0.0D;
			int herdSize = 0;

			for (PathfinderMob entity : closeHerd) {
				if (mob.distanceToSqr(entity) > 144.0D && herdSize >= preferredHerdSize - 2) {
					break;
				}
				herdX += entity.getX();
				herdY += entity.getY();
				herdZ += entity.getZ();
				herdSize++;
			}

			Vec3 herdCenter = new Vec3(herdX / herdSize, herdY / herdSize, herdZ / herdSize);
			if (herdSize > 0) {
				double scale = Math.max(1.0D, mob.getBbWidth() * 1.25D);
				double scaleSquared = scale * scale;
				double herdDistance = mob.distanceToSqr(herdCenter);
				if (friendDistance > 9.0D * scaleSquared || herdDistance > 64.0D * scaleSquared) {
					int maxDistance = Math.clamp(Mth.floor(Math.sqrt(herdDistance) * scale), Math.max(3, Mth.ceil(scale * 2.0D)), Math.max(16, Mth.ceil(scale * 8.0D)));
					int minDistance = Math.max(Mth.ceil(scale), maxDistance - Mth.ceil(scale * 2.0D));
					for (int i = 0; i < 8; i++) {
						Vec3 pos = DefaultRandomPos.getPosTowards(mob, maxDistance, minDistance, herdCenter, Math.PI / 2.0D);
						if (pos != null) {
							this.isHerdFar = herdDistance > 196.0D * scaleSquared;
							return pos;
						}
					}
				}
			}
		}
		return super.getPosition();
	}
}