package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.resources.Identifier;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class REFERENCE {
    public static final String MODID = "werewolves";
    public static final String VMODID = "vampirism";
    public static final String NAME = "Werewolves";
    public static final int HIGHEST_WEREWOLF_LEVEL = 14;
    public static final int HIGHEST_WEREWOLF_LORD_LEVEL = 5;
    public static final Identifier WEREWOLF_PLAYER_KEY = WIdentifier.mod("werewolf");
    public static final Identifier EXTENDED_WEREWOLF_KEY = WIdentifier.mod("iextendedwerewolf");
    public static final int EYE_TYPE_COUNT = 9;
    /**
     * Interval in ticks in which a werewolf player checks whether it stands in a wolfsbane-affected chunk
     */
    public static final int REFRESH_WOLFSBANE_TICKS = 40;
    public static ArtifactVersion VERSION = new DefaultArtifactVersion("0.0.0");

}
