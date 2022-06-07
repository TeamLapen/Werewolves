---
sidebar_position: 4
title: Troubleshooting
---

:::info

This page will be extended

:::

## Know issues

### Epic Fight Mod - Play Werewolf Model is not shown
Epic Fight Mod cancels any rendering of something else than the original player model

### Parcool Mod - Crash on 3rd person animations
When rendering the werewolf model in 3rd person the game crashes

### java.lang.IncompatibleClassChangeError:
This is not an actual bug in Werewolves, it is mere a bug in forge that shows this instead of the real bug.  
To find out what is wrong first make sure all Werewolves requirements are met:
- use the latest version of Forge
- use the latest version of Vampirism

If your issue is not resolved there are two ways to continue:  
1. Remove Werewolves and restart the game and fix all issues the game shows you and re-add Werewolves once the game starts into the main menu. Then the issue should be fixed.  If the issue is not fixed or the game does not show you any issues in the first place go to 2.
2. For this way you need to take a look at the `debug.log`, which will tell you what exactly is wrong. 
You can find it in the [logs folder of your minecraft instance](https://help.ggservers.com/en-us/article/where-to-find-client-side-logs-7upje9/)  
(If there is no `debug.log` or it only contains a few lines, and you use the Curseforge launcher, make sure to enable the logging the Minecraft Settings and crash the game again)  
Once you have the `debug.log` file, then you can search in it for `net.minecraftforge.fml.ModLoadingException`. Take a look at the text the line shows and fix your minecraft installation accordingly.  
If you do not know what to do with the `debug.log` you can send it to us (__DO NOT__ post some lines of the log file, put the entire log file)

## Crash-report - If your game crashes we do NEED a crash-report
If the issue is not know we can not help you when we do not know that happened. We __NEED__ the crash-report to determine what crashed. If you do not provide one we __WILL__ ask for it.


If you want to report your issue you have three choices to notify us of this issue:
- create an issue on [GitHub](https://github.com/TeamLapen/Werewolves/issues/new/choose)
- go to the [#w-bugreport](https://discord.gg/xvWKdBxK3N) channel on Discord
- comment on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/werewolves-become-a-beast)

If you do not know what a crashreport is or were to find it check out [this post](https://hypixel.net/threads/guide-how-to-post-a-crash-report.577718/) 