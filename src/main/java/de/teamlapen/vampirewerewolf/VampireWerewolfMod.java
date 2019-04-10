package de.teamlapen.vampirewerewolf;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.lib.lib.util.IInitListener.Step;
import de.teamlapen.lib.lib.util.Logger;
import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.vampirewerewolf.config.Balance;
import de.teamlapen.vampirewerewolf.config.RawMeatLoader;
import de.teamlapen.vampirewerewolf.core.RegistryManager;
import de.teamlapen.vampirewerewolf.player.ModPlayerEventHandler;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.proxy.IProxy;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirewerewolf.util.ScoreboardUtil;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VampirismAPI;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.Color;
import java.io.File;

@Mod.EventBusSubscriber
@Mod(modid = REFERENCE.MODID, name = REFERENCE.NAME, version = REFERENCE.VERSION, acceptedMinecraftVersions = "[1.12.2,)", dependencies = "required-after:forge@[" + REFERENCE.FORGE_VERSION_MIN + ",);after:guideapi" + ";after:vampirism" + ";after:teamlapen-lib")
public class VampireWerewolfMod {
    /**
     * Werewolve creatures are of this creature type. Use the instance in
     * {@link VReference} instead of this one. This is only here to init it as early
     * as possible
     */
    private static final EnumCreatureType WEREWOLF_CREATURE_TYPE = EnumHelper.addCreatureType("VAMPIREWEREWOLF_WEREWOLF", IWerewolfMob.class, 30, Material.AIR, false, false);

    private final RegistryManager registryManager;
    @SidedProxy(clientSide = "de.teamlapen.vampirewerewolf.proxy.ClientProxy", serverSide = "de.teamlapen.vampirewerewolf.proxy.CommonProxy")
    public static IProxy proxy;

    @Mod.Instance
    public static VampireWerewolfMod instance;

    public final static Logger log = new Logger(REFERENCE.MODID, "de.teamlapen.vampirewerewolf");
    public static ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("tool_silver", 2, 250, 6.0F, 2.0F, 14);
    public static DamageSource AQUA = new DamageSource("aquaphobia").setDamageBypassesArmor().setMagicDamage();;
    public static final CreativeTabs creativeTab = new CreativeTabs(REFERENCE.MODID) {

        @Override
        public ItemStack getTabIconItem() {
            //TODO modify
            return new ItemStack(Items.APPLE);
        }
    };

    public VampireWerewolfMod() {
        registryManager = new RegistryManager();
        MinecraftForge.EVENT_BUS.register(registryManager);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        WerewolfPlayer.registerCapability();
        setupAPI2();
        Balance.init(new File(event.getModConfigurationDirectory(), REFERENCE.MODID), VampirismMod.inDev);
        RawMeatLoader.init(new File(event.getModConfigurationDirectory(), REFERENCE.MODID));

        registryManager.onInitStep(Step.PRE_INIT, event);
        proxy.onInitStep(IInitListener.Step.PRE_INIT, event);
        ScoreboardUtil.init();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        HelperRegistry.registerPlayerEventReceivingCapability(WerewolfPlayer.CAP, WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability(WerewolfPlayer.CAP, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.class);

        MinecraftForge.EVENT_BUS.register(new ModPlayerEventHandler());

        registryManager.onInitStep(Step.INIT, event);
        proxy.onInitStep(IInitListener.Step.INIT, event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        registryManager.onInitStep(Step.POST_INIT, event);
        proxy.onInitStep(IInitListener.Step.POST_INIT, event);
    }

    private void setupAPI2() {
        VReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry().registerPlayableFaction("Werewolf", IWerewolfPlayer.class, Color.DARK_GRAY.getRGB(), REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.CAP, REFERENCE.HIGHEST_WEREWOLF_LEVEL);
        VReference.WEREWOLF_FACTION.setChatColor(TextFormatting.DARK_GRAY).setUnlocalizedName("text.vampirewerewolf.werewolf", "text.vampirewerewolf.werewolf");
        VReference.WEREWOLF_CREATURE_TYPE = WEREWOLF_CREATURE_TYPE;
    }
}
