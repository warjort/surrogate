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

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import surrogate.SurrogateMain;

public class SurrogateScreenHandler extends AbstractContainerMenu {

    public SurrogateScreenHandler(final int syncId, final Inventory inventory) {
        super(SurrogateMain.SURROGATE_SCREEN_HANDLER_TYPE, syncId);
    }

    @Override
    public boolean stillValid(final Player player) {
        return true;
    }
}
