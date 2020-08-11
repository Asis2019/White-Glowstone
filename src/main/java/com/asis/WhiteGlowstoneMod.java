package com.asis;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

public class WhiteGlowstoneMod implements ModInitializer {

    public static final String MOD_ID = "whiteglowstone";

    //Blocks
    public static final Block WHITE_GLOWSTONE_BLOCK = new WhiteGlowstoneBlock();

    //Mod group
    public static final ItemGroup MOD_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(WHITE_GLOWSTONE_BLOCK));


    //Items
    public static final Item WHITE_GLOWSTONE_DUST = new Item(new Item.Settings().group(MOD_ITEM_GROUP));

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, WhiteGlowstoneBlock.ID, WHITE_GLOWSTONE_BLOCK);
        Registry.register(Registry.ITEM, WhiteGlowstoneBlock.ID, new BlockItem(WHITE_GLOWSTONE_BLOCK, new Item.Settings().group(MOD_ITEM_GROUP)));

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "white_glowstone_dust"), WHITE_GLOWSTONE_DUST);

        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "white_glowstone_blob"), WhiteGlowstoneBlobFeature.INSTANCE);

        //Biome loop
        Registry.BIOME.forEach(biome -> {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addFeature(
                        GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                        WhiteGlowstoneBlobFeature.INSTANCE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(
                                        Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
                                                10,
                                                15,
                                                0,
                                                128
                                        ))));
            }
        });
    }
}
