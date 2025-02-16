package dev.quozul.mixin;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin makes leader zombies bigger.
 */
@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @Shadow
    @Final
    private static Identifier LEADER_ZOMBIE_BONUS_MODIFIER_ID;

    @Inject(
            method = "applyAttributeModifiers",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/ZombieEntity;setCanBreakDoors(Z)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onApplyAttributeModifiers(float chanceMultiplier, CallbackInfo ci) {
        ZombieEntity zombie = (ZombieEntity) (Object) this;
        EntityAttributeInstance scaleAttr = zombie.getAttributeInstance(EntityAttributes.SCALE);
        EntityAttributeInstance attackDamageAttribute = zombie.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);

        if (scaleAttr != null && attackDamageAttribute != null) {
            scaleAttr.setBaseValue(1.2D);
            attackDamageAttribute.addPersistentModifier(new EntityAttributeModifier(LEADER_ZOMBIE_BONUS_MODIFIER_ID, zombie.getRandom().nextDouble() * 2.0 + 0.5, EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }
}
