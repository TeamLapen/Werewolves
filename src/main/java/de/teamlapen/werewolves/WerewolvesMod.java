package de.teamlapen.werewolves;

import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.entity.IPlayerEventListener;
import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.lib.lib.network.ISyncable;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.client.core.ClientRegistryHandler;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModCommands;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.core.RegistryManager;
import de.teamlapen.werewolves.data.*;
import de.teamlapen.werewolves.entities.ModEntityEventHandler;
import de.teamlapen.werewolves.entities.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.items.WerewolfRefinementItem;
import de.teamlapen.werewolves.modcompat.guide.WerewolvesGuideBook;
import de.teamlapen.werewolves.modcompat.terrablender.TerraBlenderCompat;
import de.teamlapen.werewolves.network.ModPacketDispatcher;
import de.teamlapen.werewolves.proxy.ClientProxy;
import de.teamlapen.werewolves.proxy.Proxy;
import de.teamlapen.werewolves.proxy.ServerProxy;
import de.teamlapen.werewolves.util.*;
import de.teamlapen.werewolves.world.gen.OverworldModifications;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;

@SuppressWarnings("InstantiationOfUtilityClass")
@Mod(REFERENCE.MODID)
public class WerewolvesMod {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final AbstractPacketDispatcher dispatcher = new ModPacketDispatcher();
    public static final Proxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final MobCategory WEREWOLF_CREATURE_TYPE = MobCategory.create("werewolves_werewolf", "werewolves_werewolf", 8, false, false, 128);
    private static final MobType WEREWOLF_CREATURE_ATTRIBUTES = new MobType();
    public static WerewolvesMod instance;
    public final RegistryManager registryManager = new RegistryManager();

    public WerewolvesMod() {
        WerewolvesMod.instance = this;
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
        bus.addListener(this::blockRegister); // First event after mod init. Faction can only be registered after VampirismMod's constructor
        bus.register(registryManager);
        bus.addListener(this::registerCapability);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientRegistryHandler::init);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(registryManager);
        MinecraftForge.EVENT_BUS.addListener(ModLootTables::onLootLoad);
        MinecraftForge.EVENT_BUS.register(Permissions.class);

        if (ModList.get().isLoaded("guideapi_vp")) {
            MinecraftForge.EVENT_BUS.addListener(WerewolvesGuideBook::onVampirismGuideBookCategoriesEvent);
        }

        RegistryManager.setupRegistries(FMLJavaModLoadingContext.get().getModEventBus());
        WerewolvesBiomeFeatures.register(FMLJavaModLoadingContext.get().getModEventBus());

        WerewolvesConfig.registerConfigs();
    }

    private boolean setupAPI;

    private void setupAPI() {
        if (!setupAPI) {
            WReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry()
                    .createPlayableFaction(REFERENCE.WEREWOLF_PLAYER_KEY, IWerewolfPlayer.class, () -> WerewolfPlayer.CAP)
                    .color(Color.orange.getRGB())
                    .hostileTowardsNeutral()
                    .highestLevel(REFERENCE.HIGHEST_WEREWOLF_LEVEL)
                    .lord().lordLevel(REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL).lordTitle(LordTitles::getWerewolfTitle).enableLordSkills().build()
                    .village(WerewolfVillageData::werewolfVillage)
                    .chatColor(ChatFormatting.GOLD)
                    .name("text.werewolves.werewolf")
                    .namePlural("text.vampirism.werewolves")
                    .refinementItems(WerewolfRefinementItem::getRefinementItem)
                    .register();
            WReference.WEREWOLF_CREATURE_ATTRIBUTES = WerewolvesMod.WEREWOLF_CREATURE_ATTRIBUTES;
            setupAPI = true;
        }
    }

    private void blockRegister(RegisterEvent event) {
        setupAPI();
    }


    private void setup(final @NotNull FMLCommonSetupEvent event) {
        setupAPI();

        dispatcher.registerPackets();
        registryManager.onInitStep(IInitListener.Step.COMMON_SETUP, event);
        proxy.onInitStep(IInitListener.Step.COMMON_SETUP, event);

        MinecraftForge.EVENT_BUS.register(new ModEntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new ModPlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        event.enqueueWork(TerraBlenderCompat::registerBiomeProviderIfPresentUnsafe);
    }

    private void loadComplete(final @NotNull FMLLoadCompleteEvent event) {
        registryManager.onInitStep(IInitListener.Step.LOAD_COMPLETE, event);
        proxy.onInitStep(IInitListener.Step.LOAD_COMPLETE, event);
        event.enqueueWork(OverworldModifications::addBiomesToOverworldUnsafe);
    }

    private void processIMC(final @NotNull InterModProcessEvent event) {
        registryManager.onInitStep(IInitListener.Step.PROCESS_IMC, event);
        proxy.onInitStep(IInitListener.Step.PROCESS_IMC, event);
    }

    @SuppressWarnings("unchecked")
    private void enqueueIMC(final InterModEnqueueEvent event) {
        HelperRegistry.registerPlayerEventReceivingCapability((Capability<IPlayerEventListener>) (Object) WerewolfPlayer.CAP, WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability((Capability<ISyncable.ISyncableEntityCapabilityInst>) (Object) WerewolfPlayer.CAP, REFERENCE.WEREWOLF_PLAYER_KEY, WerewolfPlayer.class);
    }

    private void registerCapability(@NotNull RegisterCapabilitiesEvent event) {
        event.register(IWerewolfPlayer.class);
    }

    private void gatherData(final @NotNull GatherDataEvent event) {
        ModBlockFamilies.init();
        setupAPI();
        DataGenerator generator = event.getGenerator();
        ModTagsProvider.addProvider(event);
        BiomeGenerator.addProvider(event);
        generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
        generator.addProvider(event.includeServer(), new LootTablesGenerator(generator));
        generator.addProvider(event.includeServer(), new GlobalLootTableGenerator(generator));
        generator.addProvider(event.includeServer(), new SkillNodeGenerator(generator));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new BlockStateGenerator(generator, event.getExistingFileHelper()));
    }

    private void setUpClient(final @NotNull FMLClientSetupEvent event) {
        registryManager.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
        proxy.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
    }

    @SubscribeEvent
    public void onCommandsRegister(@NotNull RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }
}
