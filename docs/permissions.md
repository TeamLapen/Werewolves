---
sidebar_position: 7
title: Permissions
---

Werewolves utilizes Forge's permissions system for a few actions. Thereby it should be compatible with Forge based permission mods as well as SpongeForge's permission system.

## Available permissions
- `werewolves.check` Should be given to all players. Used to check if permissions system works
- `werewolves.form` Allow player to transfer be in werewolf form
- `werewolves.form.transform` Allow player to transfer into werewolf form
- `werewolves.bite` Bite Entities
- `werewolves.bite.player` Bite Player (just used if `werewolves.bite` permission is given)
- `werewolves.infect` Infect other Player with Lupus Sanguinem
- `vampirism.action.*` Allow a player to use the specific action (e.b. `vampirism.action.vampirism.bat`) (inherited from vampirism)
