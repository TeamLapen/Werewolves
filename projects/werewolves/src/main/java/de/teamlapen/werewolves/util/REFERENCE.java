package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.WResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class REFERENCE {
    public static final String MODID = "werewolves";
    public static final String VMODID = "vampirism";
    public static final String NAME = "Werewolves";
    public static final int HIGHEST_WEREWOLF_LEVEL = 14;
    public static final int HIGHEST_WEREWOLF_LORD_LEVEL = 5;
    public static final ResourceLocation WEREWOLF_PLAYER_KEY = WResourceLocation.mod("werewolf");
    public static final ResourceLocation EXTENDED_WEREWOLF_KEY = WResourceLocation.mod("iextendedwerewolf");
    public static final int EYE_TYPE_COUNT = 9;
    public static ArtifactVersion VERSION = new DefaultArtifactVersion("0.0.0");

}
