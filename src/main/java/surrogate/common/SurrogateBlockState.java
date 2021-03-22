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

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Property;

public class SurrogateBlockState extends BlockState {

    private CompoundTag tag;

    public SurrogateBlockState(final Block block, final ImmutableMap<Property<?>, Comparable<?>> immutableMap, final MapCodec<BlockState> mapCodec) {
        super(block, immutableMap, mapCodec);
    }

    public CompoundTag getTag() {
        return this.tag == null ? null : this.tag.copy();
    }

    void setTag(final CompoundTag tag) {
        this.tag = tag.copy();
    }
}
