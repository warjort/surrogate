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

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class SurrogateBlock extends BaseEntityBlock {

    private SurrogateStateManager surrogateStateManager;

    public SurrogateBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noDrops().isValidSpawn((state, world, pos, type) -> false));
        this.surrogateStateManager = new SurrogateStateManager(this);
        registerDefaultState(this.surrogateStateManager.any());
    }

    @Override
    public RenderShape getRenderShape(final BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public SurrogateBlockEntity newBlockEntity(final BlockGetter world) {
        return new SurrogateBlockEntity();
    }

    @Override
    public SurrogateStateManager getStateDefinition() {
        return this.surrogateStateManager;
    }

    @Override
    public InteractionResult use(final BlockState state, final Level world, final BlockPos pos, final Player player, final InteractionHand hand,
            final BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        final MenuProvider namedScreenHandlerFactory = state.getMenuProvider(world, pos);
        if (namedScreenHandlerFactory != null) {
            player.openMenu(namedScreenHandlerFactory);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(final BlockState state, final LevelReader world, final BlockPos pos) {
        return false;
    }

    @Override
    public ItemStack getCloneItemStack(final BlockGetter world, final BlockPos pos, final BlockState state) {
        return ItemStack.EMPTY;
    }
}
