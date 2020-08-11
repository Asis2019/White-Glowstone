package com.asis;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class WhiteGlowstoneBlock extends Block {
    public static Identifier ID = new Identifier(WhiteGlowstoneMod.MOD_ID, "white_glowstone_block");

    public WhiteGlowstoneBlock() {
        super(FabricBlockSettings.copyOf(Blocks.GLOWSTONE));
    }

}
