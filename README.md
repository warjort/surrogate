# Surrogate
[Curseforge](https://www.curseforge.com/minecraft/mc-mods/surrogate)

[Changelog](CHANGELOG.md)

## Table of Contents
* [Overview](#about)
* [What](#what)
* [Technical](#technical)
* [Limitations](#limitations)
* [Incomplete](#incomplete)
* [Forge](#forge)
* [License](LICENSE)
* [Minecraft EULA](https://www.minecraft.net/en-us/eula/)
* [Fabric License](https://github.com/FabricMC/fabric-loader/blob/master/LICENSE)
* [Contributors](CONTRIBUTORS)

## About

Surrogate. Missing block and item handling for Fabric.

IMPORTANT: This is not a replacement for regular backups. It is intended for use by mod and modpack developers.

# What

Normally, Minecraft will respond to missing blocks/items by replacing them with "air",
meaning your test world is corrupted if you accidently removed modded blocks or items.

This mod detects missing blocks (including block/tile entities) or items in your save file and replaces them in game with a surrogate holding all the original data.

When these surrogate blocks/items are saved, the data is written back into the save file exactly as it was read.
So you can fix the problem with the missing/broken mods and recover the data by reloading.

You can right click a surrogate to get the orignal block id that is missing.
Items show the original id as an extra tooltip at the bottom.

# Technical

The surrogate blocks are created with the same properties as bedrock. So you will need creative mode to remove them manually.

The surrogate item stacks are created in such a way that you can't accidently stack them together and potentially lose data.

# Limitations

There are many things it does not handle, which is why good backups are still preferable.

You can at least use the characteristic black/purple blocks and items as an indication that you probably need to restore from a backup.

Partial list of things not handled when missing:
* Advancements
* Entities
* Statistics
* World Gen: dimensions, biomes, features, etc.

It also can't handle intermod processing or custom mod information. 
If for example you accidently remove Tech Reborn, there is no way to provide it's energy system to other mods.
So data about the stored energy in other mods' blocks maybe lost or corrupted.

# Incomplete

While this works fine on dedicated servers, the missing blocks will appear as "ghost blocks" on the client.
This is because the additional block states created to represent the missing blocks are not currently transmitted to the clients.
Items work fine.

The gui for right clicking surrogate blocks needs some work.

This mod is currently zero configuration. If you actually do want to remove some modded blocks, you will need to uninstall this mod.
It is planned to add some config settings so you can say what mods/ids should not be surrogated.

# Forge

This mod is for fabric.

Forge chooses a different mechanism to handle this scenario.
It makes a copy of your registries in the save data, then when you load a world it checks for missing registry entries.
It will then warn you before you load the world. If you continue it will make a backup, but the data will gone from the current world.

Well actually, it does have a way to ressurect blocks (but not items) provided you haven't placed another block in its place.

## Translations
To make your own translation, add a resource pack with an assets/surrogate/lang/xx_yy.json
<br>Please feel free to contribute back any translations you make.

[English](src/main/resources/assets/surrogate/lang/en_us.json)