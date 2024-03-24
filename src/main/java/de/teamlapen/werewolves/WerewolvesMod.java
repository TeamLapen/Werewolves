package de.teamlapen.werewolves;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import de.teamlapen.lib.HelperRegistry;
import de.teamlapen.lib.lib.entity.IPlayerEventListener;
import de.teamlapen.lib.lib.storage.IAttachedSyncable;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.entity.minion.VampireMinionEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfMob;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.WerewolvesModClient;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.entities.ModEntityEventHandler;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.items.WerewolfRefinementItem;
import de.teamlapen.werewolves.modcompat.terrablender.TerraBlenderCompat;
import de.teamlapen.werewolves.network.ModPacketDispatcher;
import de.teamlapen.werewolves.proxy.Proxy;
import de.teamlapen.werewolves.proxy.ServerProxy;
import de.teamlapen.werewolves.util.*;
import de.teamlapen.werewolves.world.gen.OverworldModifications;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;

@SuppressWarnings("InstantiationOfUtilityClass")
@Mod(REFERENCE.MODID)
public class WerewolvesMod {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final Proxy proxy = FMLEnvironment.dist == Dist.CLIENT ? WerewolvesModClient.getProxy() : new ServerProxy();
    public static final MobCategory WEREWOLF_CREATURE_TYPE = MobCategory.create("werewolves_werewolf", "werewolves_werewolf", 8, false, false, 128);
    private static final MobType WEREWOLF_CREATURE_ATTRIBUTES = new MobType();
    public static WerewolvesMod instance;
    public final RegistryManager registryManager;

    public WerewolvesMod(IEventBus modEventBus) {
        WerewolvesMod.instance = this;
        registryManager = new RegistryManager(modEventBus);
        WUtils.init();

        Optional<? extends ModContainer> opt = ModList.get().getModContainerById(REFERENCE.VMODID);
        if (opt.isPresent()) {
            REFERENCE.VERSION = opt.get().getModInfo().getVersion();
        } else {
            LOGGER.warn("Cannot get version from mod info");
        }

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::setUpClient);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::blockRegister); // First event after mod init. Faction can only be registered after VampirismMod's constructor
        modEventBus.register(ModPacketDispatcher.class);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.register(new WerewolvesModClient(modEventBus, this.registryManager));
        }

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(ModLootTables::onLootLoad);
        NeoForge.EVENT_BUS.register(Permissions.class);

//        if (ModList.get().isLoaded("guideapi_vp")) {
//            NeoForge.EVENT_BUS.addListener(WerewolvesGuideBook::onVampirismGuideBookCategoriesEvent);
//        }

        RegistryManager.setupRegistries(modEventBus);
        WerewolvesBiomeFeatures.register(modEventBus);
        modEventBus.addListener(ModItems::registerOtherCreativeTabItems);

        WerewolvesConfig.registerConfigs(modEventBus);
    }

    private boolean setupAPI;

    private void setupAPI() {
        if (!setupAPI) {
            //noinspection unchecked
            WReference.WEREWOLF_FACTION = VampirismAPI.factionRegistry()
                    .createPlayableFaction(REFERENCE.WEREWOLF_PLAYER_KEY, IWerewolfPlayer.class, () -> (AttachmentType<IWerewolfPlayer>)(Object) ModAttachments.WEREWOLF_PLAYER.get())
                    .color(Color.orange.getRGB())
                    .hostileTowardsNeutral()
                    .highestLevel(REFERENCE.HIGHEST_WEREWOLF_LEVEL)
                    .lord()
                    .lordLevel(REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL)
                    .lordTitle(LordTitles::getWerewolfTitle)
                    .enableLordSkills()
                    .minion(WerewolfMinionEntity.WerewolfMinionData.ID, WerewolfMinionEntity.WerewolfMinionData::new)
                    .commandBuilder(ModEntities.WEREWOLF_MINION::get)
                    .with("name", "Werewolf", StringArgumentType.string(), MinionData::setName, StringArgumentType::getString)
                    .with("skin", -1, IntegerArgumentType.integer(-1), WerewolfMinionEntity.WerewolfMinionData::setSkinType, IntegerArgumentType::getInteger)
                    .with("eye", -1, IntegerArgumentType.integer(-1), WerewolfMinionEntity.WerewolfMinionData::setEyeType, IntegerArgumentType::getInteger)
                    .with("glowingEye", false, BoolArgumentType.bool(), WerewolfMinionEntity.WerewolfMinionData::setGlowingEyes, BoolArgumentType::getBool)
                    .with("form", WerewolfForm.BEAST, WerewolfFormArgument.nonHumanForms(),WerewolfMinionEntity.WerewolfMinionData::setForm, WerewolfFormArgument::getForm)
                    .build().build()
                    .build()
                    .village(WerewolfVillageData::werewolfVillage)
                    .chatColor(ChatFormatting.GOLD)
                    .name("text.werewolves.werewolf")
                    .namePlural("text.vampirism.werewolves")
                    .refinementItems(WerewolfRefinementItem::getRefinementItem)
                    .addTag(Registries.BIOME, ModTags.Biomes.IS_WEREWOLF_BIOME)
                    .addTag(Registries.POINT_OF_INTEREST_TYPE, ModTags.PoiTypes.IS_WEREWOLF)
                    .addTag(Registries.VILLAGER_PROFESSION, ModTags.Professions.IS_WEREWOLF)
                    .addTag(Registries.ENTITY_TYPE, ModTags.Entities.WEREWOLF)
                    .addTag(VampirismRegistries.Keys.TASK, ModTags.Tasks.IS_WEREWOLF)
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

        registryManager.onInitStep(IInitListener.Step.COMMON_SETUP, event);
        proxy.onInitStep(IInitListener.Step.COMMON_SETUP, event);

        NeoForge.EVENT_BUS.register(new ModEntityEventHandler());
        NeoForge.EVENT_BUS.register(new ModPlayerEventHandler());
        NeoForge.EVENT_BUS.register(new GeneralEventHandler());
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
        HelperRegistry.registerPlayerEventReceivingCapability((AttachmentType<IPlayerEventListener>) (Object) ModAttachments.WEREWOLF_PLAYER.get(), WerewolfPlayer.class);
        HelperRegistry.registerSyncablePlayerCapability((AttachmentType<IAttachedSyncable>) (Object) ModAttachments.WEREWOLF_PLAYER.get(), WerewolfPlayer.class);
    }

    private void setUpClient(final FMLClientSetupEvent event) {
        registryManager.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
        proxy.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
    }

    @SubscribeEvent
    public void onCommandsRegister(@NotNull RegisterCommandsEvent event) {
        ModCommands.registerCommands(event.getDispatcher());
    }

    private void registerCapabilities(@NotNull RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTiles.STONE_ALTAR.get(), (o, side) -> new InvWrapper(o));
    }
}
