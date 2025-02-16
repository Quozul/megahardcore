package dev.quozul;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            offerPlanksRecipe(Items.OAK_PLANKS, ItemTags.OAK_LOGS, 3);
            offerPlanksRecipe(Items.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS, 3);
            offerPlanksRecipe(Items.BIRCH_PLANKS, ItemTags.BIRCH_LOGS, 3);
            offerPlanksRecipe(Items.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS, 3);
            offerPlanksRecipe(Items.ACACIA_PLANKS, ItemTags.ACACIA_LOGS, 3);
            offerPlanksRecipe(Items.CHERRY_PLANKS, ItemTags.CHERRY_LOGS, 3);
            offerPlanksRecipe(Items.PALE_OAK_PLANKS, ItemTags.PALE_OAK_LOGS, 3);
            offerPlanksRecipe(Items.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS, 3);
            offerPlanksRecipe(Items.MANGROVE_PLANKS, ItemTags.MANGROVE_LOGS, 3);

            // Nether logs
            offerPlanksRecipe(Items.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS, 3);
            offerPlanksRecipe(Items.WARPED_PLANKS, ItemTags.WARPED_STEMS, 3);
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
            finishToolBuilder(createShaped(RecipeCategory.COMBAT, Items.STONE_AXE)
                    .pattern("ff")
                    .pattern("sf")
                    .pattern("s "));

            // Hoe
            finishToolBuilder(createShaped(RecipeCategory.COMBAT, Items.STONE_HOE)
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
            offerSmelting(List.of(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.7f, 200, "iron_nugget");
            offerSmelting(List.of(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.7f, 200, "iron_nugget");
            offerSmelting(List.of(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.7f, 200, "iron_nugget");

            offerSmelting(List.of(Items.DEEPSLATE_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_NUGGET, 1f, 200, "gold_nugget");
            offerSmelting(List.of(Items.GOLD_ORE), RecipeCategory.MISC, Items.GOLD_NUGGET, 1f, 200, "gold_nugget");
            offerSmelting(List.of(Items.NETHER_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_NUGGET, 1f, 200, "gold_nugget");
            offerSmelting(List.of(Items.RAW_GOLD), RecipeCategory.MISC, Items.GOLD_NUGGET, 1f, 200, "gold_nugget");

            // Those are the vanilla blasting recipes, the only difference is the cooking time which has been increased
            // from 100 to 200 which is the same as all those recipes in a regular furnace
            offerBlasting(List.of(Items.COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1f, 200, "coal");
            offerBlasting(List.of(Items.DEEPSLATE_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1f, 200, "coal");
            offerBlasting(List.of(Items.COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200, "copper_ingot");
            offerBlasting(List.of(Items.DEEPSLATE_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200, "copper_ingot");
            offerBlasting(List.of(Items.RAW_COPPER), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200, "copper_ingot");
            offerBlasting(List.of(Items.DEEPSLATE_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1f, 200, "diamond");
            offerBlasting(List.of(Items.DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1f, 200, "diamond");
            offerBlasting(List.of(Items.DEEPSLATE_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1f, 200, "emerald");
            offerBlasting(List.of(Items.EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1f, 200, "emerald");
            offerBlasting(List.of(Items.DEEPSLATE_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1f, 200, "gold_ingot");
            offerBlasting(List.of(Items.GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1f, 200, "gold_ingot");
            offerBlasting(List.of(Items.NETHER_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1f, 200, "gold_ingot");
            offerBlasting(List.of(Items.RAW_GOLD), RecipeCategory.MISC, Items.GOLD_INGOT, 1f, 200, "gold_ingot");
            offerBlasting(List.of(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1f, 200, "gold_nugget");
            offerBlasting(List.of(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200, "iron_ingot");
            offerBlasting(List.of(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200, "iron_ingot");
            offerBlasting(List.of(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200, "iron_ingot");
            offerBlasting(List.of(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1f, 200, "iron_nugget");
            offerBlasting(List.of(Items.DEEPSLATE_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2f, 200, "lapis_lazuli");
            offerBlasting(List.of(Items.LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2f, 200, "lapis_lazuli");
            offerBlasting(List.of(Items.ANCIENT_DEBRIS), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 2f, 200, "netherite_scrap");
            offerBlasting(List.of(Items.NETHER_QUARTZ_ORE), RecipeCategory.MISC, Items.QUARTZ, 0.2f, 200, "quartz");
            offerBlasting(List.of(Items.DEEPSLATE_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7f, 200, "redstone");
            offerBlasting(List.of(Items.REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.7f, 200, "redstone");
        }

        private void food() {
            offerSmoking(Items.POTATO, RecipeCategory.MISC, Items.BAKED_POTATO, 0.35f, 200, "baked_potato");
            offerSmoking(Items.BEEF, RecipeCategory.MISC, Items.COOKED_BEEF, 0.35f, 200, "cooked_beef");
            offerSmoking(Items.CHICKEN, RecipeCategory.MISC, Items.COOKED_CHICKEN, 0.35f, 200, "cooked_chicken");
            offerSmoking(Items.COD, RecipeCategory.MISC, Items.COOKED_COD, 0.35f, 200, "cooked_cod");
            offerSmoking(Items.MUTTON, RecipeCategory.MISC, Items.COOKED_MUTTON, 0.35f, 200, "cooked_mutton");
            offerSmoking(Items.PORKCHOP, RecipeCategory.MISC, Items.COOKED_PORKCHOP, 0.35f, 200, "cooked_porkchop");
            offerSmoking(Items.RABBIT, RecipeCategory.MISC, Items.COOKED_RABBIT, 0.35f, 200, "cooked_rabbit");
            offerSmoking(Items.SALMON, RecipeCategory.MISC, Items.COOKED_SALMON, 0.35f, 200, "cooked_salmon");
            offerSmoking(Items.KELP, RecipeCategory.MISC, Items.DRIED_KELP, 0.1f, 200, "dried_kelp");
        }

        private void offerSmoking(ItemConvertible itemConvertible, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItem(itemConvertible), category, output, experience, cookingTime, RecipeSerializer.SMOKING, SmokingRecipe::new)
                    .group(group)
                    .criterion(hasItem(itemConvertible), this.conditionsFromItem(itemConvertible))
                    .offerTo(this.exporter, getItemPath(output) + "_from_smoking");
        }

        private void finishToolBuilder(ShapedRecipeJsonBuilder builder) {
            builder.input('s', Items.STICK)
                    .input('f', Items.FLINT)
                    .group("multi_bench")
                    .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                    .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT))
                    .offerTo(exporter);
        }
    }
}
