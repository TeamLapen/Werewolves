package de.teamlapen.vampirewerewolf;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.util.IInitListener.Step;
import de.teamlapen.lib.lib.util.Logger;
import de.teamlapen.lib.proxy.IProxy;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.vampirewerewolf.config.Balance;
import de.teamlapen.vampirewerewolf.core.RegistryManager;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirewerewolf.util.ScoreboardUtil;
import de.teamlapen.vampirewerewolf.util.VReference;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VampirismAPI;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.File;

@Mod.EventBusSubscriber
@Mod(modid = REFERENCE.MODID, name = REFERENCE.NAME, version = REFERENCE.VERSION, acceptedMinecraftVersions = "[1.12.2,)", dependencies = "required-after:forge@[" + REFERENCE.FORGE_VERSION_MIN + ",);after:guideapi" + ";after:vampirism" + ";after:teamlapen-lib")
public class VampireWerewolfMod {
    /**
     * Werewolve creatures are of this creature type. Use the instance in
     * {@link VReference} instead of this one. This is only here to init it as early
     * as possible
     */
    private static final EnumCreatureType WEREWOLF_CREATURE_TYPE = EnumHelper.addCreatureType("VAMPIREWEREWOLFE_WEREWOLF", IWerewolfMob.class, 30, Material.AIR, false, false);

    private final RegistryManager registryManager;
    @SidedProxy(clientSide = "de.teamlapen.lib.proxy.ClientProxy", serverSide = "de.teamlapen.lib.proxy.CommonProxy")
    public static IProxy proxy;

    @Mod.Instance
    public static VampireWerewolfMod instance;

    public final static Logger log = new Logger(REFERENCE.MODID, "de.teamlapen.vampirewerewolves");
    private static int modEntityId = 0;

    public VampireWerewolfMod() {
        registryManager = new RegistryManager();
        MinecraftForge.EVENT_BUS.register(registryManager);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        WerewolfPlayer.registerCapability();
        setupAPI2();
        Balance.init(new File(event.getModConfigurationDirectory(), REFERENCE.MODID), VampirismMod.inDev);

        registryManager.onInitStep(Step.PRE_INIT, event);
        ScoreboardUtil.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        HelperRegistry.registerPlayerEventReceivingCapability(WerewolfPlayer.CAP, WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability(WerewolfPlayer.CAP, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.class);

        registryManager.onInitStep(Step.INIT, event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        registryManager.onInitStep(Step.POST_INIT, event);
    }

    private void setupAPI2() {
        VReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry().registerPlayableFaction("Werewolve", IWerewolfPlayer.class, 0XFF646464, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.CAP, REFERENCE.HIGHEST_WEREWOLF_LEVEL);
        VReference.WEREWOLF_FACTION.setChatColor(TextFormatting.GRAY).setUnlocalizedName("text.vampirewerewolf.werewolf", "text.vampirewerewolf.werewolf");
        VReference.WEREWOLF_CREATURE_TYPE = WEREWOLF_CREATURE_TYPE;
    }
}
