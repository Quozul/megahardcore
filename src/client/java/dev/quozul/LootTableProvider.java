package dev.quozul;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.SheepPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class LootTableProvider extends SimpleFabricLootTableProvider {
    public LootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(ModLootTables.SHEEP_LOOT, sheep());
        lootTableBiConsumer.accept(ModLootTables.IRON_GOLEM_LOOT, ironGolem());
    }

    private LootTable.Builder sheep() {
        // Mutton
        LootPool muttonPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(Items.MUTTON)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                        .conditionally(AnyOfLootCondition.builder()
                                .or(EntityPropertiesLootCondition.builder(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.create()
                                                .flags(EntityFlagsPredicate.Builder.create().onFire(true)))
                                )
                                .or(EntityPropertiesLootCondition.builder(
                                        LootContext.EntityTarget.DIRECT_ATTACKER,
                                        EntityPredicate.Builder.create()
                                                .equipment(EntityEquipmentPredicate.Builder.create()
                                                        .mainhand(ItemPredicate.Builder.create().tag(Registries.createEntryLookup(Registries.ITEM), ItemTags.ACACIA_LOGS))))
                                )
                        )
                )
                .build();

        // String
        LootPool stringPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(sheepEntry(DyeColor.WHITE))
                .with(sheepEntry(DyeColor.ORANGE))
                .with(sheepEntry(DyeColor.MAGENTA))
                .with(sheepEntry(DyeColor.LIGHT_BLUE))
                .with(sheepEntry(DyeColor.YELLOW))
                .with(sheepEntry(DyeColor.LIME))
                .with(sheepEntry(DyeColor.PINK))
                .with(sheepEntry(DyeColor.GRAY))
                .with(sheepEntry(DyeColor.LIGHT_GRAY))
                .with(sheepEntry(DyeColor.CYAN))
                .with(sheepEntry(DyeColor.PURPLE))
                .with(sheepEntry(DyeColor.BLUE))
                .with(sheepEntry(DyeColor.BROWN))
                .with(sheepEntry(DyeColor.GREEN))
                .with(sheepEntry(DyeColor.RED))
                .with(sheepEntry(DyeColor.BLACK))
                .build();

        return LootTable.builder()
                .pool(muttonPool)
                .pool(stringPool);
    }

    private LeafEntry.Builder<?> sheepEntry(DyeColor color) {
        return ItemEntry.builder(Items.STRING)
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(SheepPredicate.unsheared(color))));
    }

    private LootTable.Builder ironGolem() {
        // Iron nuggets
        LootPool ironNuggetPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(Items.IRON_NUGGET)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 5.0f))))
                .build();

        // Poppy
        LootPool poppyPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(Items.POPPY)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
                .build();

        return LootTable.builder()
                .pool(ironNuggetPool)
                .pool(poppyPool);
    }

    static class ModLootTables {
        public static RegistryKey<LootTable> IRON_GOLEM_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "entities/iron_golem"));
        public static RegistryKey<LootTable> SHEEP_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "entities/sheep"));
    }
}
