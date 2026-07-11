package com.barl_inc.unusual_prehistory.worldgen.feature.tree;

import com.barl_inc.unusual_prehistory.registry.UP2Trees;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TreeBranchDecorator extends TreeDecorator {

    public static final MapCodec<TreeBranchDecorator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> treeDecorator.probability),
            BlockStateProvider.CODEC.fieldOf("leaf_provider").forGetter((treeDecorator) -> treeDecorator.leafProvider)
    ).apply(instance, TreeBranchDecorator::new));

    private final float probability;
    private final BlockStateProvider leafProvider;

    public TreeBranchDecorator(float probability, BlockStateProvider leafProvider) {
        this.probability = probability;
        this.leafProvider = leafProvider;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return UP2Trees.TREE_BRANCH.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        List<BlockPos> leaves = context.leaves();
        logs.stream().filter((pos) -> pos.getY() < leaves.get(0).getY() - 2 && pos.getY() > logs.get(0).getY() + 2).forEach((pos) -> {
            if (random.nextFloat() <= this.probability) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                BlockPos.MutableBlockPos mutablePos = pos.offset(direction.getStepX(), 0, direction.getStepZ()).mutable();
                if (context.isAir(mutablePos)) {
                    context.setBlock(mutablePos, leafProvider.getState(random, mutablePos));
                }
                mutablePos.move(direction.getClockWise());
                if (context.isAir(mutablePos)) {
                    context.setBlock(mutablePos, leafProvider.getState(random, mutablePos));
                }
                mutablePos.move(direction.getClockWise().getOpposite(), 2);
                if (context.isAir(mutablePos)) {
                    context.setBlock(mutablePos, leafProvider.getState(random, mutablePos));
                }
                mutablePos.move(direction.getClockWise()).move(direction);
                if (context.isAir(mutablePos)) {
                    context.setBlock(mutablePos, leafProvider.getState(random, mutablePos));
                }
                mutablePos.move(direction.getOpposite()).move(Direction.UP);
                if (context.isAir(mutablePos)) {
                    context.setBlock(mutablePos, leafProvider.getState(random, mutablePos));
                }
            }
        });
    }
}
