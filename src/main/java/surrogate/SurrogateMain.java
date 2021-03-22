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

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
    public static final ScreenHandlerType<SurrogateScreenHandler> SURROGATE_SCREEN_HANDLER_TYPE;

    public static Identifier ID(final String path) {
        return new Identifier(MOD_ID, path);
    }

    public static ModContainer getModContainer() {
        return FabricLoader.getInstance().getModContainer(MOD_ID)
                .orElseThrow(() -> new IllegalStateException("Unable to get ModContainer: " + MOD_ID));
    }

    public static SurrogateBlockState getOrCreateBlockState(final CompoundTag tag) {
        return SURROGATE_BLOCK.getStateManager().getOrCreateBlockState(tag);
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
    }

    static {
        SURROGATE_BLOCK = registerBlock("surrogate_block", new SurrogateBlock());
        SURROGATE_BLOCK_ENTITY_TYPE = registerBlockEntityType(SurrogateBlockEntity::new, SURROGATE_BLOCK);
        SURROGATE_SCREEN_HANDLER_TYPE = registerScreenHandlerType(SURROGATE_BLOCK_ENTITY_TYPE, SurrogateScreenHandler::new);
        SURROGATE_ITEM = registerItem("surrogate_item", new SurrogateItem());
    }

    private static <T extends Block> T registerBlock(final String path, final T block) {
        return Registry.register(Registry.BLOCK, ID(path), block);
    }

    private static <T extends Block & BlockEntityProvider, U extends BlockEntity> BlockEntityType<U> registerBlockEntityType(final Supplier<? extends U> supplier, final T block) {
        final BlockEntityType.Builder<U> builder = BlockEntityType.Builder.create(supplier, block);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getId(block), builder.build(null));
    }

    private static <T extends Item> T registerItem(final String path, final T item) {
        return Registry.register(Registry.ITEM, ID(path), item);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandlerType(final BlockEntityType<?> blockEntityType, final ScreenHandlerType.Factory<T> factory) {
        final ScreenHandlerType<T> type = new ScreenHandlerType<>(factory);
        return Registry.register(Registry.SCREEN_HANDLER, Registry.BLOCK_ENTITY_TYPE.getId(blockEntityType), type);
    }
}
