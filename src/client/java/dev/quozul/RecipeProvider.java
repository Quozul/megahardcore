package dev.quozul;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static dev.quozul.MegaHardCore.MOD_ID;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new MyRecipeGenerator(registryLookup, exporter);
    }

    @Override
    public String getName() {
        return "FabricDocsReferenceRecipeProvider";
    }

    static class MyRecipeGenerator extends RecipeGenerator {
        protected MyRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
            super(registries, exporter);
        }

        @Override
        public void generate() {
            planks();
            stoneTools();
            ores();
            food();
        }

        private void planks() {
            // Overworld logs
            offerPlanksRecipe3(Items.OAK_PLANKS, ItemTags.OAK_LOGS);
            offerPlanksRecipe3(Items.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS);
            offerPlanksRecipe3(Items.BIRCH_PLANKS, ItemTags.BIRCH_LOGS);
            offerPlanksRecipe3(Items.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS);
            offerPlanksRecipe3(Items.ACACIA_PLANKS, ItemTags.ACACIA_LOGS);
            offerPlanksRecipe3(Items.CHERRY_PLANKS, ItemTags.CHERRY_LOGS);
            offerPlanksRecipe3(Items.PALE_OAK_PLANKS, ItemTags.PALE_OAK_LOGS);
            offerPlanksRecipe3(Items.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS);
            offerPlanksRecipe3(Items.MANGROVE_PLANKS, ItemTags.MANGROVE_LOGS);

            // Nether logs
            offerPlanksRecipe3(Items.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS);
            offerPlanksRecipe3(Items.WARPED_PLANKS, ItemTags.WARPED_STEMS);
        }

        private void stoneTools() {
            // Shovel
            finishToolBuilder(createShaped(RecipeCategory.TOOLS, Items.STONE_SHOVEL)
                    .pattern("f")
                    .pattern("s")
                    .pattern("s"));

            // Pickaxe
            finishToolBuilder(createShaped(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                    .pattern("fff")
                    .pattern(" s ")
                    .pattern(" s "));

            // Axe
            finishToolBuilder(createShaped(RecipeCategory.TOOLS, Items.STONE_AXE)
                    .pattern("ff")
                    .pattern("fs")
                    .pattern(" s"));

            // Hoe
            finishToolBuilder(createShaped(RecipeCategory.TOOLS, Items.STONE_HOE)
                    .pattern("ff")
                    .pattern(" s")
                    .pattern(" s"));

            // Sword
            finishToolBuilder(createShaped(RecipeCategory.COMBAT, Items.STONE_SWORD)
                    .pattern("f")
                    .pattern("f")
                    .pattern("s"));
        }

        private void ores() {
            // Iron and gold only gives nuggets in regular furnaces
            offerSmelting2(List.of(Items.DEEPSLATE_IRON_ORE), Items.IRON_NUGGET, 0.7f, "iron_nugget");
            offerSmelting2(List.of(Items.IRON_ORE), Items.IRON_NUGGET, 0.7f, "iron_nugget");
            offerSmelting2(List.of(Items.RAW_IRON), Items.IRON_NUGGET, 0.7f, "iron_nugget");
            offerSmelting2(List.of(Items.DEEPSLATE_GOLD_ORE), Items.GOLD_NUGGET, 1f, "gold_nugget");
            offerSmelting2(List.of(Items.GOLD_ORE), Items.GOLD_NUGGET, 1f, "gold_nugget");
            offerSmelting2(List.of(Items.NETHER_GOLD_ORE), Items.GOLD_NUGGET, 1f, "gold_nugget");
            offerSmelting2(List.of(Items.RAW_GOLD), Items.GOLD_NUGGET, 1f, "gold_nugget");

            // Those are the vanilla blasting recipes, the only difference is the cooking time which has been increased
            // from 100 to 200 which is the same as all those recipes in a regular furnace
            offerBlasting2(List.of(Items.COAL_ORE), Items.COAL, 0.1f, "coal");
            offerBlasting2(List.of(Items.DEEPSLATE_COAL_ORE), Items.COAL, 0.1f, "coal");
            offerBlasting2(List.of(Items.COPPER_ORE), Items.COPPER_INGOT, 0.7f, "copper_ingot");
            offerBlasting2(List.of(Items.DEEPSLATE_COPPER_ORE), Items.COPPER_INGOT, 0.7f, "copper_ingot");
            offerBlasting2(List.of(Items.RAW_COPPER), Items.COPPER_INGOT, 0.7f, "copper_ingot");
            offerBlasting2(List.of(Items.DEEPSLATE_DIAMOND_ORE), Items.DIAMOND, 1f, "diamond");
            offerBlasting2(List.of(Items.DIAMOND_ORE), Items.DIAMOND, 1f, "diamond");
            offerBlasting2(List.of(Items.DEEPSLATE_EMERALD_ORE), Items.EMERALD, 1f, "emerald");
            offerBlasting2(List.of(Items.EMERALD_ORE), Items.EMERALD, 1f, "emerald");
            offerBlasting2(List.of(Items.DEEPSLATE_GOLD_ORE), Items.GOLD_INGOT, 1f, "gold_ingot");
            offerBlasting2(List.of(Items.GOLD_ORE), Items.GOLD_INGOT, 1f, "gold_ingot");
            offerBlasting2(List.of(Items.NETHER_GOLD_ORE), Items.GOLD_INGOT, 1f, "gold_ingot");
            offerBlasting2(List.of(Items.RAW_GOLD), Items.GOLD_INGOT, 1f, "gold_ingot");
            offerBlasting2(List.of(Items.DEEPSLATE_IRON_ORE), Items.IRON_INGOT, 0.7f, "iron_ingot");
            offerBlasting2(List.of(Items.IRON_ORE), Items.IRON_INGOT, 0.7f, "iron_ingot");
            offerBlasting2(List.of(Items.RAW_IRON), Items.IRON_INGOT, 0.7f, "iron_ingot");
            offerBlasting2(List.of(Items.DEEPSLATE_LAPIS_ORE), Items.LAPIS_LAZULI, 0.2f, "lapis_lazuli");
            offerBlasting2(List.of(Items.LAPIS_ORE), Items.LAPIS_LAZULI, 0.2f, "lapis_lazuli");
            offerBlasting2(List.of(Items.ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 2f, "netherite_scrap");
            offerBlasting2(List.of(Items.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2f, "quartz");
            offerBlasting2(List.of(Items.DEEPSLATE_REDSTONE_ORE), Items.REDSTONE, 0.7f, "redstone");
            offerBlasting2(List.of(Items.REDSTONE_ORE), Items.REDSTONE, 0.7f, "redstone");
            offerBlasting2(List.of(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1f, "gold_nugget");
            offerBlasting2(List.of(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1f, "iron_nugget");
        }

        private void food() {
            offerSmoking(Items.POTATO, Items.BAKED_POTATO, 0.35f, "baked_potato");
            offerSmoking(Items.BEEF, Items.COOKED_BEEF, 0.35f, "cooked_beef");
            offerSmoking(Items.CHICKEN, Items.COOKED_CHICKEN, 0.35f, "cooked_chicken");
            offerSmoking(Items.COD, Items.COOKED_COD, 0.35f, "cooked_cod");
            offerSmoking(Items.MUTTON, Items.COOKED_MUTTON, 0.35f, "cooked_mutton");
            offerSmoking(Items.PORKCHOP, Items.COOKED_PORKCHOP, 0.35f, "cooked_porkchop");
            offerSmoking(Items.RABBIT, Items.COOKED_RABBIT, 0.35f, "cooked_rabbit");
            offerSmoking(Items.SALMON, Items.COOKED_SALMON, 0.35f, "cooked_salmon");
            offerSmoking(Items.KELP, Items.DRIED_KELP, 0.1f, "dried_kelp");
        }

        private void offerSmelting2(List<ItemConvertible> itemConvertibles, ItemConvertible output, float experience, String group) {
            for (ItemConvertible itemConvertible : itemConvertibles) {
                CookingRecipeJsonBuilder.create(Ingredient.ofItem(itemConvertible), RecipeCategory.MISC, output, experience, 200, RecipeSerializer.SMELTING, SmeltingRecipe::new)
                        .group(group)
                        .criterion(hasItem(itemConvertible), this.conditionsFromItem(itemConvertible))
                        .offerTo(this.exporter, getPath(output) + "_from_smelting_" + getItemPath(itemConvertible));
            }
        }

        private void offerBlasting2(List<ItemConvertible> itemConvertibles, ItemConvertible output, float experience, String group) {
            for (ItemConvertible itemConvertible : itemConvertibles) {
                CookingRecipeJsonBuilder.create(Ingredient.ofItem(itemConvertible), RecipeCategory.MISC, output, experience, 200, RecipeSerializer.BLASTING, BlastingRecipe::new)
                        .group(group)
                        .criterion(hasItem(itemConvertible), this.conditionsFromItem(itemConvertible))
                        .offerTo(this.exporter, getPath(output) + "_from_blasting_" + getItemPath(itemConvertible));
            }
        }

        private void offerSmoking(ItemConvertible itemConvertible, ItemConvertible output, float experience, String group) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItem(itemConvertible), RecipeCategory.FOOD, output, experience, 200, RecipeSerializer.SMOKING, SmokingRecipe::new)
                    .group(group)
                    .criterion(hasItem(itemConvertible), this.conditionsFromItem(itemConvertible))
                    .offerTo(this.exporter, getPath(output) + "_from_smoking");
        }

        private void finishToolBuilder(ShapedRecipeJsonBuilder builder) {
            builder.input('s', Items.STICK)
                    .input('f', Items.FLINT)
                    .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT))
                    .offerTo(exporter, getPath(builder.getOutputItem()));
        }

        private void offerPlanksRecipe3(ItemConvertible output, TagKey<Item> logTag) {
            Identifier itemId = CraftingRecipeJsonBuilder.getItemId(output);
            this.createShapeless(RecipeCategory.BUILDING_BLOCKS, output, 3)
                    .input(logTag)
                    .group("planks")
                    .criterion("has_logs", this.conditionsFromTag(logTag))
                    .offerTo(exporter, getPath(output));
        }

        private String getPath(ItemConvertible output) {
            Identifier itemId = CraftingRecipeJsonBuilder.getItemId(output);
            return Identifier.of(MOD_ID, itemId.getPath()).toString();
        }
    }
}
