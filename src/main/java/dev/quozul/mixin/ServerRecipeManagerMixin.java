package dev.quozul.mixin;

import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin {

    @Shadow
    private PreparedRecipes preparedRecipes;

    /**
     * Disables copper ingot and food in regular furnaces.
     * Disables Netherite upgrade duplication craft.
     */
    @Inject(method = "apply(Lnet/minecraft/recipe/PreparedRecipes;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("RETURN"))
    private void onApply(PreparedRecipes preparedRecipes, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        List<Identifier> recipesToRemove = List.of(
                Identifier.of("minecraft", "netherite_upgrade_smithing_template"),
                Identifier.of("minecraft", "stone_axe"),
                Identifier.of("minecraft", "stone_hoe"),
                Identifier.of("minecraft", "stone_pickaxe"),
                Identifier.of("minecraft", "stone_pickaxe"),
                Identifier.of("minecraft", "stone_shovel"),
                Identifier.of("minecraft", "stone_sword")
        );

        List<RecipeEntry<?>> filtered = this.preparedRecipes.recipes().stream()
                .filter(entry -> {
                    Recipe<?> recipe = entry.value();
                    if (recipe.getType() == RecipeType.SMELTING) {
                        SmeltingRecipe smeltingRecipe = (SmeltingRecipe) recipe;
                        if (smeltingRecipe.getGroup().equals("copper_ingot") ||
                                smeltingRecipe.getRecipeBookCategory().equals(RecipeBookCategories.FURNACE_FOOD)
                        ) {
                            return false;
                        }
                    }
                    return !recipesToRemove.contains(entry.id().getValue());
                })
                .collect(Collectors.toList());

        this.preparedRecipes = PreparedRecipes.of(filtered);
    }
}
