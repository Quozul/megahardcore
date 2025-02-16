package dev.quozul.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Changes the default state of campfires so that they are unlit by default.
 */
@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends Block {

    public CampfireBlockMixin(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(boolean emitsParticles, int fireDamage, Settings settings, CallbackInfo ci) {
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(CampfireBlock.LIT, Boolean.valueOf(false))
                        .with(CampfireBlock.SIGNAL_FIRE, Boolean.valueOf(false))
                        .with(CampfireBlock.WATERLOGGED, Boolean.valueOf(false))
                        .with(CampfireBlock.FACING, Direction.NORTH)
        );
    }

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void overridePlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        cir.setReturnValue(state.with(CampfireBlock.LIT, false));
    }
}
