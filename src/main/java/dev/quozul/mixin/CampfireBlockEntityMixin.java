package dev.quozul.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {

    @Inject(method = "litServerTick", at = @At("TAIL"))
    private static void addCampfireRegenerationEffect(ServerWorld world, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, ServerRecipeManager.MatchGetter<?, ?> matchGetter, CallbackInfo ci) {
        double radius = 5.0D;
        Box effectArea = new Box(pos).expand(radius);
        List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, effectArea);

        for (PlayerEntity player : players) {
            StatusEffectInstance currentEffect = player.getStatusEffect(StatusEffects.REGENERATION);
            if (currentEffect != null && currentEffect.getAmplifier() > 0) {
                continue;
            }

            if (currentEffect == null || currentEffect.getDuration() < 20) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0, true, true));
            }
        }
    }
}
