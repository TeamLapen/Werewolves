package de.teamlapen.werewolves;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModCommands;
import de.teamlapen.werewolves.core.RegistryManager;
import de.teamlapen.werewolves.data.BlockStateGenerator;
import de.teamlapen.werewolves.data.ItemModelGenerator;
import de.teamlapen.werewolves.data.ModTagsProvider;
import de.teamlapen.werewolves.data.RecipeGenerator;
import de.teamlapen.werewolves.entities.ModEntityEventHandler;
import de.teamlapen.werewolves.network.ModPacketDispatcher;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.proxy.ClientProxy;
import de.teamlapen.werewolves.proxy.Proxy;
import de.teamlapen.werewolves.proxy.ServerProxy;
import de.teamlapen.werewolves.util.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.Optional;

@SuppressWarnings("InstantiationOfUtilityClass")
@Mod(REFERENCE.MODID)
public class WerewolvesMod {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final AbstractPacketDispatcher dispatcher = new ModPacketDispatcher();
    public static final Proxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final EntityClassification WEREWOLF_CREATUE_TYPE = EntityClassification.create("werewolves_werewolf", "werewolves_werewolf", 20, false, false);
    private static final CreatureAttribute WEREWOLF_CREATURE_ATTRIBUTES = new CreatureAttribute();
    public static WerewolvesMod instance;
    public static boolean inDev = false;
    public final RegistryManager registryManager = new RegistryManager();

    public WerewolvesMod() {
        WerewolvesMod.instance = this;
        checkDevEnv();
        WUtils.init();

        Optional<? extends ModContainer> opt = ModList.get().getModContainerById(de.teamlapen.vampirism.util.REFERENCE.MODID);
        if (opt.isPresent()) {
            REFERENCE.VERSION = opt.get().getModInfo().getVersion();
        } else {
            LOGGER.warn("Cannot get version from mod info");
        }

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::loadComplete);
        bus.addListener(this::processIMC);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::gatherData);
        bus.addListener(this::setUpClient);
        bus.register(registryManager);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(registryManager);

        WerewolvesConfig.registerConfigs();
        Permissions.init();
    }

    private void checkDevEnv() {
        String launchTarget = System.getenv().get("target");
        if (launchTarget != null && launchTarget.contains("dev")) {
            WerewolvesMod.inDev = true;
        }
    }

    private void setupAPI() {
        WReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry().registerPlayableFaction(REFERENCE.WEREWOLF_PLAYER_KEY, IWerewolfPlayer.class, Color.orange, true, () -> WerewolfPlayer.CAP, REFERENCE.HIGHEST_WEREWOLF_LEVEL, 0, LordTitles::getWerewolfTitle, new WerewolfVillageData());
        WReference.WEREWOLF_FACTION.setChatColor(TextFormatting.GOLD).setTranslationKeys("text.werewolves.werewolf", "text.vampirism.werewolves");

        WReference.WEREWOLF_CREATUE_TYPE = WerewolvesMod.WEREWOLF_CREATUE_TYPE;
        WReference.WEREWOLF_CREATURE_ATTRIBUTES = WerewolvesMod.WEREWOLF_CREATURE_ATTRIBUTES;
    }


    private void setup(final FMLCommonSetupEvent event) {
        setupAPI();
        WerewolfPlayer.registerCapability();

        dispatcher.registerPackets();
        registryManager.onInitStep(IInitListener.Step.COMMON_SETUP, event);
        proxy.onInitStep(IInitListener.Step.COMMON_SETUP, event);

        MinecraftForge.EVENT_BUS.register(new ModEntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new ModPlayerEventHandler());
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        registryManager.onInitStep(IInitListener.Step.LOAD_COMPLETE, event);
        proxy.onInitStep(IInitListener.Step.LOAD_COMPLETE, event);
    }

    private void processIMC(final InterModProcessEvent event) {
        registryManager.onInitStep(IInitListener.Step.PROCESS_IMC, event);
        proxy.onInitStep(IInitListener.Step.PROCESS_IMC, event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        HelperRegistry.registerPlayerEventReceivingCapability(WerewolfPlayer.CAP, WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability(WerewolfPlayer.CAP, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.class);
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            ModTagsProvider.addProvider(generator);
            generator.addProvider(new RecipeGenerator(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new ItemModelGenerator(generator, event.getExistingFileHelper()));
            generator.addProvider(new BlockStateGenerator(generator, event.getExistingFileHelper()));
        }
    }

    private void setUpClient(final FMLClientSetupEvent event) {
        registryManager.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
        proxy.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        ModCommands.registerCommands(event.getCommandDispatcher());
    }
}
