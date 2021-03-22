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

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import surrogate.SurrogateMain;
import surrogate.common.SurrogateBlockState;

@Mixin(NbtHelper.class)
public class NbtHelperMixin {

    @Inject(method = "toBlockState", at = @At("HEAD"), cancellable=true)
    private static void surrogate_toBlockState(final CompoundTag tag, final CallbackInfoReturnable<BlockState> callbackInfo) {
        if (tag.contains("Name", 8)) {
            final Identifier identifier = new Identifier(tag.getString("Name"));
            Optional<Block> block = Registry.BLOCK.getOrEmpty(identifier);
            if (block.isPresent())
                return;

            callbackInfo.setReturnValue(SurrogateMain.getOrCreateBlockState(tag));
        }
    }

    @Inject(method = "fromBlockState", at = @At("HEAD"), cancellable=true)
    private static void surrogate_fromBlockState(final BlockState state, final CallbackInfoReturnable<CompoundTag> callbackInfo) {
        if (state instanceof SurrogateBlockState == false)
            return;

        final SurrogateBlockState surrogateBlockState = (SurrogateBlockState) state;
        final CompoundTag tag = surrogateBlockState.getTag();
        if (tag == null) {
            return;
        }

        callbackInfo.setReturnValue(tag);
    }
}
