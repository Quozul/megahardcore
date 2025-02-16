package dev.quozul.mixin;

import dev.quozul.IResettableStats;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.quozul.MegaHardCore.LOGGER;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void died(DamageSource damageSource, CallbackInfo info) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) (Object) this;

        // Reset spawn point
        serverPlayer.setSpawnPoint(World.OVERWORLD, null, 0.0F, false, false);

        // Clear ender chest
        serverPlayer.getEnderChestInventory().clear();

        // Reset statistics
        ServerStatHandler statHandler = serverPlayer.getStatHandler();
        if (statHandler instanceof IResettableStats resettable) {
            resettable.megahardcore_template_1_21_4$resetAllStatistics();
        } else {
            LOGGER.error("[MegaHardCore] Failed to reset stats for {}", serverPlayer.getName());
        }

        // Reset advancements
        PlayerAdvancementTracker advancementTracker = serverPlayer.getAdvancementTracker();
        if (advancementTracker instanceof IResettableStats resettable) {
            resettable.megahardcore_template_1_21_4$resetAllStatistics();
            advancementTracker.sendUpdate(serverPlayer);
        } else {
            LOGGER.error("[MegaHardCore] Failed to reset advancements for {}", serverPlayer.getName());
        }
    }
}
