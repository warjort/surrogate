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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import surrogate.mixin.SurrogateStateAccessor;

public class SurrogateStateManager extends StateManager<Block, BlockState> {

    private final SurrogateBlockState defaultState;

    private final MapCodec<BlockState> codec;

    private final List<SurrogateBlockState> states = Lists.newCopyOnWriteArrayList();

    @SuppressWarnings("unchecked")
    public SurrogateStateManager(final SurrogateBlock block) {
        super(Block::getDefaultState, block, SurrogateBlockState::new, Collections.emptyMap());
        this.defaultState = (SurrogateBlockState) getDefaultState();
        this.codec = ((SurrogateStateAccessor<BlockState>) this.defaultState).getCodec();
        states.add(defaultState);
    }

    @Override
    public ImmutableList<BlockState> getStates() {
        return ImmutableList.copyOf(this.states);
    }

    public SurrogateBlockState getOrCreateBlockState(final CompoundTag tag) {
        for (SurrogateBlockState state : states) {
            if (Objects.equals(tag, state.getTag())) {
                return state;
            }
        }

        final SurrogateBlockState result = new SurrogateBlockState(getOwner(), this.defaultState.getEntries(), this.codec);
        result.setTag(tag);
        this.states.add(result);
        Block.STATE_IDS.add(result);
        return result;
    }
}
