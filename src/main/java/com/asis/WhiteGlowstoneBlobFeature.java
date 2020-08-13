package com.asis;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class WhiteGlowstoneBlobFeature extends Feature<DefaultFeatureConfig> {
    public static final WhiteGlowstoneBlobFeature INSTANCE = new WhiteGlowstoneBlobFeature();
/*    public static final ConfiguredFeature<?, ?> WHITE_GLOWSTONE_BLOB_FEATURE = INSTANCE.configure(FeatureConfig.DEFAULT)
            .createDecoratedFeature(
                    ConfiguredFeatures.Decorators.COUNT_RANGE.configure(new RangeDecoratorConfig(
                            10,
                            15,
                            0,
                            128
                    ))));*/


    public WhiteGlowstoneBlobFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig featureConfig) {
        if (!world.isAir(blockPos)) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(blockPos.up());
            if (!blockState.isOf(Blocks.NETHERRACK) && !blockState.isOf(Blocks.BASALT) && !blockState.isOf(Blocks.BLACKSTONE) && !blockState.isOf(Blocks.SOUL_SAND) && !blockState.isOf(Blocks.SOUL_SOIL)) {
                return false;
            } else {
                world.setBlockState(blockPos, WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK.getDefaultState(), 2);

                for(int i = 0; i < 1500; ++i) {
                    BlockPos blockPos2 = blockPos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                    if (world.getBlockState(blockPos2).isAir()) {
                        int j = 0;
                        Direction[] var11 = Direction.values();

                        for (Direction direction : var11) {
                            if (world.getBlockState(blockPos2.offset(direction)).isOf(WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK)) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            world.setBlockState(blockPos2, WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK.getDefaultState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
