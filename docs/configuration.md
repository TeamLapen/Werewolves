---
sidebar_position: 4
title: Configuration
---

Werewolves is highly configurably, like Vampirism.

## Configuration Files

Werewolves's configuration is split across several files:

- werewolves-client.toml (located in `.minecraft/config/`) for client specific options
- werewolves-common.toml (located in `.minecraft/config/`) for common options
- werewolves-server.toml (located in `<world-dir>/serverconfig/`) for gameplay options per world
- werewolves-balance.toml (located in `<world-dir>/serverconfig/`) for fine-tuning of the balancing per world

If you want to change the world specific options before creating a world, either to change world-generation or to change it for all created worlds, you can use the defaultconfigs folder. It is located in your .minecraft or server directory. Copy a server or balance config from an existing world to this folder and change the values as desired. It will be used for new worlds.

## Noticeable configuration options
### Client sided
#### Disable screen overlays rendering
- `disableScreenFurRendering` to disable the fur border when in werewolf form
- `disableFangCrosshairRendering` to disable the crosshair replacement of the werewolf fangs

### Common sided - World Gen
- `disableWerewolfHeaven` disable the werewolf heaven biome
- `disableOreGen` remove the generation of silver ore
- `werewolfHeavenWeight` modify the biome weight of the werewolf heaven

### Server sided - General configuration
- `disableToothInfection` disable the option to infect yourself with a werewolf tooth
- `customRawMeatItems` add other food items to the list of items that should be considered raw meat
- `customMeatItems` add other food items to the list of items that should be considered meat
- `werewolfFormFreeFormBiomes` add additional item to the list of biomes werewolves can stay in werewolf form unlimited

### Server sided - Balance configuration
In the Balance config ther are several config options to fine tune every skill, action, refinement, entities and player settings.

## Permission
Using appropriate server mods/plugins certain actions can also be controlled with permissions.
See [Permissions](https://github.com/TeamLapen/Werewolves/wiki/Permissions)
