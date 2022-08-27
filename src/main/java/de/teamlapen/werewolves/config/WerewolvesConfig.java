package de.teamlapen.werewolves.config;

import de.teamlapen.werewolves.util.FormHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class WerewolvesConfig {
    public static final ClientConfig CLIENT;

    public static final ServerConfig SERVER;

    public static final CommonConfig COMMON;

    public static final BalanceConfig BALANCE;

    private static final ForgeConfigSpec clientSpec;
    private static final ForgeConfigSpec serverSpec;
    private static final ForgeConfigSpec commonSpec;
    private static final ForgeConfigSpec balanceSpec;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    static {
        final Pair<BalanceConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BalanceConfig::new);
        balanceSpec = specPair.getRight();
        BALANCE = specPair.getLeft();
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, balanceSpec, "werewolves-balance.toml");
        FMLJavaModLoadingContext.get().getModEventBus().register(WerewolvesConfig.class);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.@NotNull Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            FormHelper.reload();
        }
    }

    @SubscribeEvent
    public static void onReLoad(ModConfigEvent.@NotNull Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            FormHelper.reload();
        }
    }

}
