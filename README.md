Werewolves for Minecraft 1.16 - Latest branch [![Java CI](https://github.com/TeamLapen/Werewolves/workflows/Java%20CI/badge.svg?branch=1.16)](https://github.com/TeamLapen/Werewolves/actions) [![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
============================================ 

## Mod Description

Werewolves are fast, strong and hungry entities, which do not like to be seen, but don't fear to fight.
With this mod you cannot only become a Vampire or Hunter, but a Werewolf to fight you own way through.

This mod allows you to become a werewolf with all its benefits and drawbacks.

## People
- [Cheaterpaul](https://github.com/Cheaterpaul) _Everything_

## Special Thanks to
- [maxanier](https://maxanier.de) _Vampirism_
- [PixelEyeStudios](https://github.com/PixelEyeStudios) _Werewolf Model & Textures_

#### Setup Gradle build script

#### !!Using Werewolves in another project is not yet possible!!

You should be able to include it with the following in your `build.gradle`:
```gradle
repositories {
    //Maven repo for Werewolves
    maven {
        url = "https://maven.paube.de"
    }
    //Maven repo for Vampirism
    maven {
        url = "https://maxanier.de/maven2"
    }
}
dependencies {
    compile "de.teamlapen.werewolves:Vampirism:${mc_version}-${vampirism_version}"
    compile "de.teamlapen.werewolves:Werewolves:${mc_version}-${werewolves_version}"
}
```

#### Choose a version
`${mc_version}` gets replaced by the current Minecraft version. (i.e. `1.14.4`)
`${vampirism_version}` gets replaced by the version of Vampirism you want to use (i.e `1.6.3`)
`${werewolves_version}` gets replaced by the version of Werewolves you want to use (i.e `1.0.0`)

These properties can be set in a file named `gradle.properties`, placed in the same directory as your `build.gradle` file.
Example `gradle.properties`:
```
mc_version=1.14.4
vampirism_version=1.6.3
werewolves_version=1.0.0
```

#### Rerun Gradle setup commands
Please run the commands that you used to setup your development environment again.
E.g. `gradlew` or `gradlew --refresh-dependencies`
Refresh/Restart your IDE afterwards.

## Setting up the development environment
If you would like to compile your own versions or even contribute to Werewolves's development you need to setup a dev environment.
The following example instructions will setup IntelliJ (Free community edition or Non-Free Ultimate edition). If you already have a setup or want to use another IDE, jump [here](#setting-up-werewolves-in-another-environment).

#### IntelliJ
1. Make sure you have the Java **JDK** (minimum Java 8) as well as the IntelliJ IDE installed.
2. If you want to contribute to the development (via pull requests), fork Vampirism on Github.
3. (Optionally) Install Git, so you can clone the repository and push changes.
4. Clone (`git clone https://github.com/TeamLapen/Werewolves`) or [download](https://github.com/TeamLapen/Werewolves/archive/14) Werewolves to a new "Werewolves" folder.
5. In IntelliJ use `New...` -> `New from Version Control` -> Fill out repo, directory and name
6. After cloning is done IntelliJ offers you to import a unlinked Gradle Project. Click this.
7. Run `genIntellijRuns` and edit the run config to use the correct module


That's it.

#### Eclipse or other IDEs
If you would like to setup Vampirism in another way or another IDE, you should pay regard to the following points.  
1. Make sure `src/main/java` and `src/api/java` are marked as source folders and `src/main/resources` is marked as resource folders.  
2. Werewolves might have a few dependencies (e.g. Vampirism), which are specified in the gradle files and should be automatically downloaded and added when you run `ideaModule` or `eclipse`.  
3. Werewolves requires at least Java 8 

## Licence
This mod is licenced under [LGPLv3](https://raw.githubusercontent.com/TeamLapen/Werewolves/1.14/LICENSE)
