package de.teamlapen.werewolves.config;

import de.teamlapen.werewolves.util.FormHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WerewolvesConfig {
    public static final ClientConfig CLIENT;

    public static final ServerConfig SERVER;

    public static final CommonConfig COMMON;

    public static final BalanceConfig BALANCE;

    private static final ModConfigSpec clientSpec;
    private static final ModConfigSpec serverSpec;
    private static final ModConfigSpec commonSpec;
    private static final ModConfigSpec balanceSpec;

    static {
        final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    static {
        final Pair<ServerConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    static {
        final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    static {
        final Pair<BalanceConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(BalanceConfig::new);
        balanceSpec = specPair.getRight();
        BALANCE = specPair.getLeft();
    }

    public static void registerConfigs(IEventBus bus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        modContainer.registerConfig(ModConfig.Type.COMMON, commonSpec);
        modContainer.registerConfig(ModConfig.Type.SERVER, serverSpec);
        modContainer.registerConfig(ModConfig.Type.SERVER, balanceSpec, "werewolves-balance.toml");
        bus.register(WerewolvesConfig.class);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            FormHelper.reload();
        }
    }

    @SubscribeEvent
    public static void onReLoad(ModConfigEvent.Reloading event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            FormHelper.reload();
        }
    }

}
