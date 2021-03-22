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

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import surrogate.SurrogateMain;

public class SurrogateBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private CompoundTag tag;

    public SurrogateBlockEntity() {
        super(SurrogateMain.SURROGATE_BLOCK_ENTITY_TYPE);
    }

    @Override
    public void fromTag(final BlockState state, final CompoundTag tag) {
        super.fromTag(state, tag);
        this.tag = tag.copy();
    }

    @Override
    public CompoundTag toTag(final CompoundTag tag) {
        if (this.tag != null) {
            return this.tag.copy();
        }
        return tag;
    }

    @Override
    public ScreenHandler createMenu(final int syncId, final PlayerInventory inv, final PlayerEntity player) {
        return new SurrogateScreenHandler(syncId, inv);
    }

    @Override
    public Text getDisplayName() {
        final CompoundTag stateTag = this.getCachedState().getTag();
        if (stateTag != null) {
            return new LiteralText(stateTag.getString("Name"));
        } else {
            return new TranslatableText(getCachedState().getBlock().getTranslationKey());
        }
    }

    @Override
    public SurrogateBlockState getCachedState() {
        return (SurrogateBlockState) super.getCachedState();
    }
}
