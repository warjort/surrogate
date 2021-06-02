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
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import surrogate.SurrogateMain;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    private CompoundTag tag;

    @Inject(method = "of", at = @At("HEAD"), cancellable=true)
    private static void surrogate_of(final CompoundTag tag, final CallbackInfoReturnable<ItemStack> callbackInfo) {
        final ResourceLocation identifier = new ResourceLocation(tag.getString("id"));
        if (identifier.getPath().isEmpty()) {
            return;
        }
        final Optional<Item> entry = Registry.ITEM.getOptional(identifier);
        if (entry.isPresent()) {
            return;
        }

        final CompoundTag originalTag = copyTag(tag, new CompoundTag());

        final ItemStack result = new ItemStack(SurrogateMain.SURROGATE_ITEM, 1);
        
        final CompoundTag myTag = result.getOrCreateTagElement(SurrogateMain.MOD_ID);
        myTag.putUUID("discriminator", UUID.randomUUID()); // Should stop most attempts to stack/merge?
        myTag.put("original", originalTag);
        callbackInfo.setReturnValue(result);
    }

    @Inject(method = "save", at = @At("HEAD"), cancellable=true)
    private void surrogate_save(final CompoundTag saveTag, final CallbackInfoReturnable<CompoundTag> callbackInfo) {
        final CompoundTag original = SurrogateMain.getSurrogateOriginal(this.tag);
        if (original == null) {
            return;
        }
        final CompoundTag result = copyTag(original, saveTag);
        callbackInfo.setReturnValue(result);
    }

    private static CompoundTag copyTag(final CompoundTag from, final CompoundTag to) {
        to.putString("id", from.getString("id"));
        to.putByte("Count", from.getByte("Count"));
        if (from.contains("tag", 10)) {
            to.put("tag", from.get("tag").copy());
        } else {
            to.remove("tag");
        }
        return to;
    }
}
