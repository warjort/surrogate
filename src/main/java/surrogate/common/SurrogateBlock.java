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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SurrogateBlock extends BlockWithEntity implements BlockEntityProvider {

    private SurrogateStateManager surrogateStateManager;

    public SurrogateBlock() {
        super(AbstractBlock.Settings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false));
        this.surrogateStateManager = new SurrogateStateManager(this);
        this.setDefaultState((BlockState) this.surrogateStateManager.getDefaultState());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public SurrogateBlockEntity createBlockEntity(final BlockView world) {
        return new SurrogateBlockEntity();
    }

    @Override
    public SurrogateStateManager getStateManager() {
        return this.surrogateStateManager;
    }

    @Override
    public ActionResult onUse(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final Hand hand,
            final BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        final NamedScreenHandlerFactory namedScreenHandlerFactory = state.createScreenHandlerFactory(world, pos);
        if (namedScreenHandlerFactory != null) {
            player.openHandledScreen(namedScreenHandlerFactory);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canPlaceAt(final BlockState state, final WorldView world, final BlockPos pos) {
        return false;
    }

    @Override
    public ItemStack getPickStack(final BlockView world, final BlockPos pos, final BlockState state) {
        return ItemStack.EMPTY;
    }
}
