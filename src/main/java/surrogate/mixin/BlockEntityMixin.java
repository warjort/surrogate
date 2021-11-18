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
package surrogate.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import surrogate.SurrogateMain;
import surrogate.common.SurrogateBlockEntity;
import surrogate.common.SurrogateBlockState;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {

    @Inject(method = "loadStatic", at = @At("HEAD"), cancellable=true)
    private static void surrogate_loadStatic(final BlockPos pos, final BlockState blockState, final CompoundTag tag, final CallbackInfoReturnable<BlockEntity> callbackInfo) {
        if (blockState instanceof SurrogateBlockState == false) {
            return;
        }
        final SurrogateBlockEntity result = SurrogateMain.SURROGATE_BLOCK_ENTITY_TYPE.create(pos, blockState);
        result.load(tag);
        callbackInfo.setReturnValue(result);
    }
}
