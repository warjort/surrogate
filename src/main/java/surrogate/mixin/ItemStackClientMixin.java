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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import surrogate.SurrogateMain;

@Mixin(ItemStack.class)
public abstract class ItemStackClientMixin {

    @Shadow
    private CompoundTag tag;

    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable=true)
    private void surrogate_getTooltipLines(final Player player, final TooltipFlag context, final CallbackInfoReturnable<List<Component>> callbackInfo) {
        final List<Component> result = callbackInfo.getReturnValue();

        final CompoundTag original = SurrogateMain.getSurrogateOriginal(this.tag);
        if (original != null) {
            result.add(new TextComponent(original.getString("id")));
        }
        callbackInfo.setReturnValue(result);
    }
}
