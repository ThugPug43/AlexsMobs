package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.registries.RegistryObject;

public class AMBlockItem extends BlockItem {

    private final RegistryObject<Block> blockSupplier;

    public AMBlockItem(RegistryObject<Block> blockSupplier, Item.Properties props) {
        super((Block)null, props);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public Block getBlock() {
        return blockSupplier.get();
    }

    public boolean canFitInsideCraftingRemainingItems() {
        return !(blockSupplier.get() instanceof ShulkerBoxBlock);
    }

    public void onDestroyed(ItemEntity p_150700_) {
        if (this.blockSupplier.get() instanceof ShulkerBoxBlock) {
            ItemStack itemstack = p_150700_.getItem();
            CompoundTag compoundtag = getBlockEntityData(itemstack);
            if (compoundtag != null && compoundtag.contains("Items", 9)) {
                ListTag listtag = compoundtag.getList("Items", 10);
                ItemUtils.onContainerDestroyed(p_150700_, listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of));
            }
        }
    }


    public boolean canBeHurtBy(DamageSource damage) {
        return super.canBeHurtBy(damage) && (this != AMBlockRegistry.TRANSMUTATION_TABLE.get().asItem() || !damage.isExplosion());
    }
}
