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
package surrogate;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import surrogate.client.SurrogateScreen;

public class SurrogateClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerScreen(SurrogateMain.SURROGATE_SCREEN_HANDLER_TYPE, SurrogateScreen::new);
    }

    private static <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void registerScreen(final MenuType<T> type, final MenuScreens.ScreenConstructor<T, U> provider) {
        MenuScreens.register(type, provider);
    }
}
