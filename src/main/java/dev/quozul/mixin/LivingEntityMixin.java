package dev.quozul.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyArg(
            method = "dropLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootWorldContext;JLjava/util/function/Consumer;)V"
            ),
            index = 2
    )
    private Consumer<ItemStack> modifyDropStackArg(Consumer<ItemStack> lootConsumer) {
        Entity entity = (Entity) (Object) this;
        return (stack) -> {
            if (entity instanceof SheepEntity && stack.isIn(ItemTags.WOOL)) {
                lootConsumer.accept(new ItemStack(Items.STRING, stack.getCount()));
            } else {
                lootConsumer.accept(stack);
            }
        };
    }
}
