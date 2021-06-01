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
package surrogate.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import surrogate.common.SurrogateScreenHandler;

//TODO better gui
@Environment(EnvType.CLIENT)
public class SurrogateScreen extends AbstractContainerScreen<SurrogateScreenHandler> {

    public SurrogateScreen(final SurrogateScreenHandler handler, final Inventory inventory, final Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(final PoseStack matrices, final float delta, final int mouseX, final int mouseY) {
        // unused
    }

    @Override
    public void render(final PoseStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(BookViewScreen.BOOK_LOCATION);
        final int i = (this.width - 192) / 2;
        this.blit(matrices, i, 2, 0, 0, 192, 192);

        final int k = this.font.width(this.title);
        this.font.draw(matrices, this.title, i - k + 192 - 44, 18.0F, 0);
    }
}
