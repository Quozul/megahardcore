package dev.quozul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes monster stronger during new moon and in the depths.
 */
@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Unique
    private static final Identifier NEW_MOON_BONUS_MODIFIER_ID = Identifier.ofVanilla("new_moon_bonus");

    @Unique
    private static final Identifier DEPTH_BONUS_MODIFIER_ID = Identifier.ofVanilla("depth_bonus");

    @Inject(
            method = "spawnEntity",
            at = @At("HEAD")
    )
    private void spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof HostileEntity zombie) {
            EntityAttributeInstance attackDamageAttribute = zombie.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
            EntityAttributeInstance healthAttribute = zombie.getAttributeInstance(EntityAttributes.MAX_HEALTH);

            if (attackDamageAttribute != null && healthAttribute != null) {
                if (isNightAndNewMoon()) {
                    attackDamageAttribute.addPersistentModifier(new EntityAttributeModifier(NEW_MOON_BONUS_MODIFIER_ID, zombie.getRandom().nextDouble() * 2.0 + 0.5, EntityAttributeModifier.Operation.ADD_VALUE));
                    healthAttribute.addPersistentModifier(new EntityAttributeModifier(NEW_MOON_BONUS_MODIFIER_ID, zombie.getRandom().nextDouble() * 3.0 + 1.0, EntityAttributeModifier.Operation.ADD_VALUE));
                }
                if (entity.getY() < 0) {
                    attackDamageAttribute.addPersistentModifier(new EntityAttributeModifier(DEPTH_BONUS_MODIFIER_ID, zombie.getRandom().nextDouble() * 2.0 + 0.5, EntityAttributeModifier.Operation.ADD_VALUE));
                    healthAttribute.addPersistentModifier(new EntityAttributeModifier(DEPTH_BONUS_MODIFIER_ID, zombie.getRandom().nextDouble() * 3.0 + 1.0, EntityAttributeModifier.Operation.ADD_VALUE));
                }

                zombie.setHealth(zombie.getMaxHealth());
            }
        }
    }

    @Unique
    private boolean isNightAndNewMoon() {
        ServerWorld world = (ServerWorld) (Object) this;
        long timeOfDay = world.getTimeOfDay() % 24000;
        boolean isNight = timeOfDay >= 13000 && timeOfDay <= 23000;
        boolean isNewMoon = world.getMoonPhase() == 4;
        return isNight && isNewMoon;
    }
}
