package dev.quozul.mixin;

import dev.quozul.IResettableStats;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(StatHandler.class)
public class StatHandlerMixin implements IResettableStats {
    @Shadow
    @Final
    protected Object2IntMap<Stat<?>> statMap;

    @Override
    public void megahardcore_template_1_21_4$resetAllStatistics() {
        this.statMap.clear();
    }
}
