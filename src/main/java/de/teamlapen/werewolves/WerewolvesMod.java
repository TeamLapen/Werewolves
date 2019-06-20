package de.teamlapen.werewolves;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.lib.lib.util.IInitListener.Step;
import de.teamlapen.lib.lib.util.Logger;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.core.RegistryManager;
import de.teamlapen.werewolves.network.ModPacketDispatcher;
import de.teamlapen.werewolves.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.proxy.IProxy;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.ScoreboardUtil;
import de.teamlapen.werewolves.world.ModVillageEventHandler;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.*;
import java.io.File;

@Mod.EventBusSubscriber
@Mod(modid = REFERENCE.MODID, name = REFERENCE.NAME, version = REFERENCE.VERSION, acceptedMinecraftVersions = "[1.12.2,)", dependencies = "required-after:forge@[" + REFERENCE.FORGE_VERSION_MIN + ",);after:guideapi" + ";required-after:vampirism@[" + REFERENCE.VAMPIRISM_VERSION_MIN + ",);after:teamlapen-lib")
public class WerewolvesMod {
    /**
     * Werewolve creatures are of this creature type. Use the instance in
     * {@link WReference} instead of this one. This is only here to init it as early
     * as possible
     */
    private static final EnumCreatureType WEREWOLF_CREATURE_TYPE = EnumHelper.addCreatureType("WEREWOLVES_WEREWOLF", IWerewolfMob.class, 30, Material.AIR, false, false);

    private final RegistryManager registryManager;
    @SidedProxy(clientSide = "de.teamlapen.werewolves.proxy.ClientProxy", serverSide = "de.teamlapen.werewolves.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.Instance(value = REFERENCE.MODID)
    public static WerewolvesMod instance;

    public final static Logger log = new Logger(REFERENCE.MODID, "de.teamlapen.werewolves");
    public static final AbstractPacketDispatcher dispatcher = new ModPacketDispatcher();
    public static ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("tool_silver", 2, 250, 6.0F, 2.0F, 14);
    public static final CreativeTabs creativeTab = new CreativeTabs(REFERENCE.MODID) {

        @Override
        public ItemStack getTabIconItem() {
            // TODO modify
            return new ItemStack(Items.APPLE);
        }
    };

    public WerewolvesMod() {
        this.registryManager = new RegistryManager();
        MinecraftForge.EVENT_BUS.register(this.registryManager);
        MinecraftForge.EVENT_BUS.register(new ModVillageEventHandler());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        WerewolfPlayer.registerCapability();
        this.setupAPI2();
        Balance.init(new File(event.getModConfigurationDirectory(), REFERENCE.MODID), VampirismMod.inDev);

        dispatcher.registerPackets();
        this.registryManager.onInitStep(Step.PRE_INIT, event);
        proxy.onInitStep(IInitListener.Step.PRE_INIT, event);
        ScoreboardUtil.init();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        HelperRegistry.registerPlayerEventReceivingCapability(WerewolfPlayer.CAP, WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability(WerewolfPlayer.CAP, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.class);

        MinecraftForge.EVENT_BUS.register(new ModPlayerEventHandler());

        this.registryManager.onInitStep(Step.INIT, event);
        proxy.onInitStep(IInitListener.Step.INIT, event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        this.registryManager.onInitStep(Step.POST_INIT, event);
        proxy.onInitStep(IInitListener.Step.POST_INIT, event);
    }

    private void setupAPI2() {
        WReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry().registerPlayableFaction("Werewolf", IWerewolfPlayer.class, Color.DARK_GRAY.getRGB(), REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.CAP, REFERENCE.HIGHEST_WEREWOLF_LEVEL);
        WReference.WEREWOLF_FACTION.setChatColor(TextFormatting.DARK_GRAY).setUnlocalizedName("text.werewolves.werewolf", "text.werewolves.werewolf");
        WReference.WEREWOLF_CREATURE_TYPE = WEREWOLF_CREATURE_TYPE;
    }
}
