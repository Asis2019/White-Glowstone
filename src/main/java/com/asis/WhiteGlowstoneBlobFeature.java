package com.asis;


import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class WhiteGlowstoneBlobFeature extends Feature<DefaultFeatureConfig> {
    public static final WhiteGlowstoneBlobFeature INSTANCE = new WhiteGlowstoneBlobFeature(DefaultFeatureConfig.CODEC);


    public WhiteGlowstoneBlobFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos blockPos, DefaultFeatureConfig config) {
        if (!serverWorldAccess.isAir(blockPos)) {
            return false;
        } else {
            BlockState blockState = serverWorldAccess.getBlockState(blockPos.up());
            if (!blockState.isOf(Blocks.NETHERRACK) && !blockState.isOf(Blocks.BASALT) && !blockState.isOf(Blocks.BLACKSTONE) && !blockState.isOf(Blocks.SOUL_SAND) && !blockState.isOf(Blocks.SOUL_SOIL)) {
                return false;
            } else {
                serverWorldAccess.setBlockState(blockPos, WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK.getDefaultState(), 2);

                for(int i = 0; i < 1500; ++i) {
                    BlockPos blockPos2 = blockPos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
                    if (serverWorldAccess.getBlockState(blockPos2).isAir()) {
                        int j = 0;
                        Direction[] var11 = Direction.values();

                        for (Direction direction : var11) {
                            if (serverWorldAccess.getBlockState(blockPos2.offset(direction)).isOf(WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK)) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            serverWorldAccess.setBlockState(blockPos2, WhiteGlowstoneMod.WHITE_GLOWSTONE_BLOCK.getDefaultState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
