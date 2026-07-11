package com.barl_inc.unusual_prehistory.entity.utils;

import com.google.common.collect.AbstractIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.Nullable;

public class CustomBlockCollisions extends AbstractIterator<VoxelShape> {

    private final AABB box;
    private final CollisionContext context;
    private final Cursor3D cursor;
    private final BlockPos.MutableBlockPos pos;
    private final VoxelShape entityShape;
    private final CollisionGetter collisionGetter;
    private final boolean onlySuffocatingBlocks;
    @Nullable
    private BlockGetter cachedBlockGetter;
    private long cachedBlockGetterPos;

    public CustomBlockCollisions(CollisionGetter getter, @Nullable Entity entity, AABB aabb) {
        this(getter, entity, aabb, false);
    }

    public CustomBlockCollisions(CollisionGetter getter, @Nullable Entity entity, AABB aabb, boolean suffocates) {
        this.context = entity == null ? CollisionContext.empty() : CollisionContext.of(entity);
        this.pos = new BlockPos.MutableBlockPos();
        this.entityShape = Shapes.create(aabb);
        this.collisionGetter = getter;
        this.box = aabb;
        this.onlySuffocatingBlocks = suffocates;
        int i = Mth.floor(aabb.minX - 1.0E-7D) - 1;
        int j = Mth.floor(aabb.maxX + 1.0E-7D) + 1;
        int k = Mth.floor(aabb.minY - 1.0E-7D) - 1;
        int l = Mth.floor(aabb.maxY + 1.0E-7D) + 1;
        int i1 = Mth.floor(aabb.minZ - 1.0E-7D) - 1;
        int j1 = Mth.floor(aabb.maxZ + 1.0E-7D) + 1;
        this.cursor = new Cursor3D(i, k, i1, j, l, j1);
    }

    @Nullable
    private BlockGetter getChunk(int p_186412_, int p_186413_) {
        int i = SectionPos.blockToSectionCoord(p_186412_);
        int j = SectionPos.blockToSectionCoord(p_186413_);
        long k = ChunkPos.asLong(i, j);
        if (this.cachedBlockGetter != null && this.cachedBlockGetterPos == k) {
            return this.cachedBlockGetter;
        } else {
            BlockGetter blockgetter = this.collisionGetter.getChunkForCollisions(i, j);
            this.cachedBlockGetter = blockgetter;
            this.cachedBlockGetterPos = k;
            return blockgetter;
        }
    }

    @Override
    protected VoxelShape computeNext() {
        while (true) {
            if (this.cursor.advance()) {
                int i = this.cursor.nextX();
                int j = this.cursor.nextY();
                int k = this.cursor.nextZ();
                int l = this.cursor.getNextType();
                if (l == 3) {
                    continue;
                }

                BlockGetter blockgetter = this.getChunk(i, k);
                if (blockgetter == null) {
                    continue;
                }

                this.pos.set(i, j, k);
                BlockState blockstate = blockgetter.getBlockState(this.pos);

                if (this.onlySuffocatingBlocks && !blockstate.isSuffocating(blockgetter, this.pos) || l == 1 && !blockstate.hasLargeCollisionShape() || l == 2 && !blockstate.is(Blocks.MOVING_PISTON)) {
                    continue;
                }

                VoxelShape voxelshape = blockstate.getCollisionShape(collisionGetter, pos, context);
                if (context instanceof EntityCollisionContext) {
                    Entity entity = ((EntityCollisionContext)context).getEntity();
                    if (entity instanceof CustomCollisions) {
                        if (((CustomCollisions) entity).canPassThrough(pos, blockstate, voxelshape)){
                            continue;
                        }
                    }
                }
                if (voxelshape == Shapes.block()) {
                    if (!box.intersects(i, j, k, (double) i + 1.0D, (double) j + 1.0D, (double) k + 1.0D)) {
                        continue;
                    }
                    return voxelshape.move(i, j, k);
                }

                VoxelShape voxelshape1 = voxelshape.move(i, j, k);
                if (!Shapes.joinIsNotEmpty(voxelshape1, entityShape, BooleanOp.AND)) {
                    continue;
                }
                return voxelshape1;
            }

            return this.endOfData();
        }
    }
}