package dev.quozul.mixin;

import dev.quozul.IResettableStats;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;


@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin implements IResettableStats {
    @Shadow
    @Final
    private Map<AdvancementEntry, AdvancementProgress> progress;

    @Shadow
    private boolean dirty;

    @Shadow
    @Final
    private Set<AdvancementEntry> visibleAdvancements;

    @Shadow
    public abstract void clearCriteria();

    @Shadow
    @Final
    private Set<PlacedAdvancement> updatedRoots;

    @Shadow
    @Final
    private Set<AdvancementEntry> progressUpdates;

    @Shadow
    private @Nullable AdvancementEntry currentDisplayTab;

    @Override
    public void megahardcore_template_1_21_4$resetAllStatistics() {
        // FIXME: This requires a logout of the player
        this.clearCriteria();
        this.progress.clear();
        this.visibleAdvancements.clear();
        this.updatedRoots.clear();
        this.progressUpdates.clear();
        this.dirty = true;
        this.currentDisplayTab = null;
    }
}
