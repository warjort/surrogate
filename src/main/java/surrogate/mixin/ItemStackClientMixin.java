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

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import surrogate.SurrogateMain;

@Mixin(ItemStack.class)
public abstract class ItemStackClientMixin {

    @Shadow
    private CompoundTag tag;

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable=true)
    private void surrogate_getTooltip(final PlayerEntity player, final TooltipContext context, final CallbackInfoReturnable<List<Text>> callbackInfo) {
        final List<Text> result = callbackInfo.getReturnValue();

        final CompoundTag original = SurrogateMain.getSurrogateOriginal(this.tag);
        if (original != null) {
            result.add(new LiteralText(original.getString("id")));
        }
        callbackInfo.setReturnValue(result);
    }
}
