package com.asis;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.function.Supplier;

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
        ConfiguredFeature<?, ?> WHITE_GLOWSTONE_BLOB_FEATURE =registerConfiguredFeature("white_glowstone_blob", new WhiteGlowstoneBlobFeature().configure(FeatureConfig.DEFAULT).decorate(
                Decorator.RANGE.configure(
                        new RangeDecoratorConfig(0, 0, 64)).spreadHorizontally()).repeat(30)
        );

        BuiltinRegistries.BIOME.forEach(biome -> {
            if (biome.getCategory() == Biome.Category.NETHER) {
                addFeatureToBiome(biome, GenerationStep.Feature.TOP_LAYER_MODIFICATION, WHITE_GLOWSTONE_BLOB_FEATURE);
            }
        });

        //Biome loop
        /*Registry.BIOME.forEach(biome -> {

        });*/

        //Registry.register(Registry.CONFIGURED_FEATURE_WORLDGEN, )

        //Registry.register(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(MOD_ID, "asd"), WhiteGlowstoneBlobFeature.INSTANCE.configure(FeatureConfig.DEFAULT));
    }

    /*private void handleBiome(Biome biome) {
        if (biome.getCategory() == Biome.Category.NETHER) {
            biome.
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
    }*/

    private static ConfiguredFeature<?, ?> registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_ID, registryName), configuredFeature);
        return configuredFeature;
    }

    private static void addFeatureToBiome(Biome biome, GenerationStep.Feature feature, ConfiguredFeature<?, ?> configuredFeature) {
        GenerationSettings.INSTANCE.getFeatures().add(Lists.newArrayList(() -> configuredFeature));
        //biome.getGenerationSettings().getFeatures().add(Lists.newArrayList(() -> configuredFeature));

        convertImmutableFeatures(biome);
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = biome.getGenerationSettings().getFeatures();
        while (biomeFeatures.size() <= feature.ordinal()) biomeFeatures.add(Lists.newArrayList());

        biomeFeatures.get(feature.ordinal()).add(() -> configuredFeature);
    }

    //Swap the list to mutable in order for us to add our features with ease.
    private static void convertImmutableFeatures(Biome biome) {
        if (biome.getGenerationSettings().getFeatures() instanceof ImmutableList) {
           // biome.getGenerationSettings().features = biome.getGenerationSettings().getFeatures().stream().map(Lists::newArrayList).collect(Collectors.toList());
        }
    }
}
