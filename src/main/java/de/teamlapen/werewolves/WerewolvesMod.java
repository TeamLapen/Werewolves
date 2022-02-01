package de.teamlapen.werewolves;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModCommands;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.core.RegistryManager;
import de.teamlapen.werewolves.data.*;
import de.teamlapen.werewolves.entities.ModEntityEventHandler;
import de.teamlapen.werewolves.entities.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.items.WerewolfRefinementItem;
import de.teamlapen.werewolves.modcompat.guide.WerewolvesGuideBook;
import de.teamlapen.werewolves.network.ModPacketDispatcher;
import de.teamlapen.werewolves.proxy.ClientProxy;
import de.teamlapen.werewolves.proxy.Proxy;
import de.teamlapen.werewolves.proxy.ServerProxy;
import de.teamlapen.werewolves.util.*;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
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
    public static final Proxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final EntityClassification WEREWOLF_CREATURE_TYPE = EntityClassification.create("werewolves_werewolf", "werewolves_werewolf", 8, false, false, 128);
    private static final CreatureAttribute WEREWOLF_CREATURE_ATTRIBUTES = new CreatureAttribute();
    public static WerewolvesMod instance;
    public static boolean inDev = false;
    public final RegistryManager registryManager = new RegistryManager();

    public WerewolvesMod() {
        WerewolvesMod.instance = this;
        checkDevEnv();
        WUtils.init();

        Optional<? extends ModContainer> opt = ModList.get().getModContainerById(REFERENCE.VMODID);
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
        bus.addGenericListener(Block.class, this::blockRegister); // First event after mod init. Faction can only be registered after VampirismMod's constructor
        bus.register(registryManager);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(registryManager);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ModBiomes::onBiomeLoadingEventAdditions);
        MinecraftForge.EVENT_BUS.addListener(ModLootTables::onLootLoad);

        if (ModList.get().isLoaded("guideapi-vp")) {
            MinecraftForge.EVENT_BUS.addListener(WerewolvesGuideBook::onVampirismGuideBookCategoriesEvent);
        }

        WerewolvesConfig.registerConfigs();
    }

    private void checkDevEnv() {
        String launchTarget = System.getenv().get("target");
        if (launchTarget != null && launchTarget.contains("dev")) {
            WerewolvesMod.inDev = true;
        }
    }

    private boolean setupAPI;

    private void setupAPI() {
        if (!setupAPI) {
            WReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry()
                    .createPlayableFaction(REFERENCE.WEREWOLF_PLAYER_KEY, IWerewolfPlayer.class, () -> WerewolfPlayer.CAP)
                    .color(Color.orange)
                    .hostileTowardsNeutral()
                    .highestLevel(REFERENCE.HIGHEST_WEREWOLF_LEVEL)
                    .lordLevel(REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL)
                    .lordTitle(LordTitles::getWerewolfTitle)
                    .village(WerewolfVillageData::werewolfVillage)
                    .chatColor(TextFormatting.GOLD)
                    .name("text.werewolves.werewolf")
                    .namePlural("text.vampirism.werewolves")
                    .refinementItems(WerewolfRefinementItem::getRefinementItem)
                    .register();
            WReference.WEREWOLF_CREATURE_ATTRIBUTES = WerewolvesMod.WEREWOLF_CREATURE_ATTRIBUTES;
            setupAPI = true;
        }
    }

    private void blockRegister(RegistryEvent.Register<Block> event) {
        setupAPI();
    }


    private void setup(final FMLCommonSetupEvent event) {
        setupAPI();
        WerewolfPlayer.registerCapability();

        dispatcher.registerPackets();
        registryManager.onInitStep(IInitListener.Step.COMMON_SETUP, event);
        proxy.onInitStep(IInitListener.Step.COMMON_SETUP, event);

        MinecraftForge.EVENT_BUS.register(new ModEntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new ModPlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());

        Permissions.init();
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
        setupAPI();
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            ModTagsProvider.addProvider(generator, event.getExistingFileHelper());
            generator.addProvider(new RecipeGenerator(generator));
            generator.addProvider(new LootTablesGenerator(generator));
            generator.addProvider(new GlobalLootTableGenerator(generator));
            generator.addProvider(new SkillNodeGenerator(generator));
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
    public void onCommandsRegister(RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }
}
