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
package surrogate.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import surrogate.SurrogateMain;

public class SurrogateBlockEntity extends BlockEntity implements MenuProvider {

    private CompoundTag tag;

    public SurrogateBlockEntity() {
        super(SurrogateMain.SURROGATE_BLOCK_ENTITY_TYPE);
    }

    @Override
    public void load(final BlockState state, final CompoundTag tag) {
        super.load(state, tag);
        this.tag = tag.copy();
    }

    @Override
    public CompoundTag save(final CompoundTag tag) {
        if (this.tag != null) {
            return this.tag.copy();
        }
        return tag;
    }

    @Override
    public AbstractContainerMenu createMenu(final int syncId, final Inventory inv, final Player player) {
        return new SurrogateScreenHandler(syncId, inv);
    }

    @Override
    public Component getDisplayName() {
        final CompoundTag stateTag = this.getBlockState().getTag();
        if (stateTag != null) {
            return new TextComponent(stateTag.getString("Name"));
        }
        return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public SurrogateBlockState getBlockState() {
        return (SurrogateBlockState) super.getBlockState();
    }
}
