package dev.quozul.mixin;

import dev.quozul.MegaHardCore;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

import static dev.quozul.MegaHardCore.MOD_ID;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin {
    @Shadow
    private PreparedRecipes preparedRecipes;

    @Inject(method = "apply(Lnet/minecraft/recipe/PreparedRecipes;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("RETURN"))
    private void onApply(PreparedRecipes preparedRecipes, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        List<Identifier> recipesToRemove = List.of(
                Identifier.of("minecraft", "netherite_upgrade_smithing_template"),
                Identifier.of("minecraft", "iron_ingot_from_smelting_iron_ore"),
                Identifier.of("minecraft", "iron_ingot_from_smelting_raw_iron"),
                Identifier.of("minecraft", "iron_ingot_from_smelting_deepslate_iron_ore"),
                Identifier.of("minecraft", "gold_ingot_from_smelting_iron_ore"),
                Identifier.of("minecraft", "gold_ingot_from_smelting_raw_gold"),
                Identifier.of("minecraft", "gold_ingot_from_smelting_deepslate_gold_ore")
        );

        // A list of all mod's recipes paths to remove duplicated recipes
        List<String> replacedRecipes = this.preparedRecipes.recipes().stream().filter(entry -> {
            Identifier identifier = entry.id().getValue();
            return identifier.getNamespace().equals(MOD_ID);
        }).map(entry -> entry.id().getValue().getPath()).toList();

        List<RecipeEntry<?>> filtered = this.preparedRecipes.recipes().stream()
                .filter(entry -> {
                    boolean removeRecipe = shouldRemoveRecipe(entry, recipesToRemove, replacedRecipes);
                    if (removeRecipe) {
                        MegaHardCore.LOGGER.info("Removing recipe {}", entry.id().getValue().toString());
                    }
                    return !removeRecipe;
                })
                .collect(Collectors.toList());

        this.preparedRecipes = PreparedRecipes.of(filtered);
    }

    @Unique
    private static boolean shouldRemoveCraftingRecipe(RecipeEntry<?> entry, List<Identifier> recipesToRemove, List<String> replacedRecipes) {
        Identifier identifier = entry.id().getValue();
        String namespace = identifier.getNamespace();
        String path = identifier.getPath();
        boolean isModRecipe = namespace.equals(MOD_ID);
        boolean isRecipeToRemove = recipesToRemove.contains(identifier);
        boolean isDuplicateRecipe = !isModRecipe && replacedRecipes.contains(path);
        return isDuplicateRecipe || isRecipeToRemove;
    }

    @Unique
    private static boolean shouldRemoveSmeltingRecipe(SmeltingRecipe smeltingRecipe) {
        boolean isOreGroup = smeltingRecipe.getGroup().equals("copper_ingot") || smeltingRecipe.getGroup().equals("gold_ingot") || smeltingRecipe.getGroup().equals("iron_ingot");
        boolean isFoodCategory = smeltingRecipe.getRecipeBookCategory().equals(RecipeBookCategories.FURNACE_FOOD);
        return isOreGroup || isFoodCategory;
    }

    @Unique
    private static boolean shouldRemoveRecipe(RecipeEntry<?> entry, List<Identifier> recipesToRemove, List<String> replacedRecipes) {
        Recipe<?> recipe = entry.value();
        if (recipe.getType() == RecipeType.SMELTING) {
            SmeltingRecipe smeltingRecipe = (SmeltingRecipe) recipe;
            if (shouldRemoveSmeltingRecipe(smeltingRecipe)) {
                return true;
            }
        }
        return shouldRemoveCraftingRecipe(entry, recipesToRemove, replacedRecipes);
    }
}
