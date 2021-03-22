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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import surrogate.client.SurrogateScreen;

public class SurrogateClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerScreen(SurrogateMain.SURROGATE_SCREEN_HANDLER_TYPE, SurrogateScreen::new);
    }

    private static <T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<T>> void registerScreen(final ScreenHandlerType<T> type, final HandledScreens.Provider<T, U> provider) {
        HandledScreens.register(type, provider);
    }
}
