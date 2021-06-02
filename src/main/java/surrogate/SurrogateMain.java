/*
 * This file is part of Surrogate.
 * Copyright (c) 2021, warjort and others, All rights reserved.
 *
 * Surrogate is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Surrogate is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Surrogate.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package surrogate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import surrogate.common.SurrogateBlock;
import surrogate.common.SurrogateBlockEntity;
import surrogate.common.SurrogateBlockState;
import surrogate.common.SurrogateItem;
import surrogate.common.SurrogateScreenHandler;

public class SurrogateMain implements ModInitializer {

    public static final Logger log = LogManager.getLogger();
    public static String MOD_ID = "surrogate";

    public static final SurrogateBlock SURROGATE_BLOCK;
    public static final BlockEntityType<SurrogateBlockEntity> SURROGATE_BLOCK_ENTITY_TYPE;
    public static final SurrogateItem SURROGATE_ITEM;
    public static final MenuType<SurrogateScreenHandler> SURROGATE_SCREEN_HANDLER_TYPE;

    public static ResourceLocation ID(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ModContainer getModContainer() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .orElseThrow(() -> new IllegalStateException("Unable to get ModContainer: " + MOD_ID));
    }

    public static SurrogateBlockState getOrCreateBlockState(final CompoundTag tag) {
        return SURROGATE_BLOCK.getStateDefinition().getOrCreateBlockState(tag);
    }

    public static CompoundTag getSurrogateOriginal(final CompoundTag tag) {
        if (tag != null && tag.contains(MOD_ID, 10)) {
            final CompoundTag myTag = tag.getCompound(MOD_ID);
            return myTag.getCompound("original");
        }
        return null;
    }

    @Override
    public void onInitialize() {
        // nothing
    }

    static {
        SURROGATE_BLOCK = registerBlock("surrogate_block", new SurrogateBlock());
        SURROGATE_BLOCK_ENTITY_TYPE = registerBlockEntityType(SurrogateBlockEntity::new, SURROGATE_BLOCK);
        SURROGATE_SCREEN_HANDLER_TYPE = registerMenuType(SURROGATE_BLOCK_ENTITY_TYPE, SurrogateScreenHandler::new);
        SURROGATE_ITEM = registerItem("surrogate_item", new SurrogateItem());
    }

    private static <T extends Block> T registerBlock(final String path, final T block) {
        return Registry.register(Registry.BLOCK, ID(path), block);
    }

    private static <T extends Block & EntityBlock, U extends BlockEntity> BlockEntityType<U> registerBlockEntityType(final BlockEntityType.BlockEntitySupplier<? extends U> supplier, final T block) {
        final BlockEntityType.Builder<U> builder = BlockEntityType.Builder.of(supplier, block);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getKey(block), builder.build(null));
    }

    private static <T extends Item> T registerItem(final String path, final T item) {
        return Registry.register(Registry.ITEM, ID(path), item);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> registerMenuType(final BlockEntityType<?> blockEntityType, final MenuType.MenuSupplier<T> factory) {
        final MenuType<T> type = new MenuType<>(factory);
        return Registry.register(Registry.MENU, Registry.BLOCK_ENTITY_TYPE.getKey(blockEntityType), type);
    }
}
