package de.teamlapen.werewolves.common.config;

import de.teamlapen.faction.Services;
import de.teamlapen.faction.client.config.values.ColorConfigValue;
import de.teamlapen.faction.common.util.ConfigValueCodec;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.util.FormHelper;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class ModConfig extends Services {

    private final ModConfig.Config<ClientConfig> client;
    private final ModConfig.Config<CommonConfig> common;
    private final ModConfig.Config<ServerConfig> server;
    private final ModConfig.Config<BalanceConfig> balance;

    private final ModContainer container;

    public ModConfig(ModContainer container) {
        this.container = container;
        this.client = Config.create(WIdentifier.mod("client"), ClientConfig::new);
        this.common = Config.create(WIdentifier.mod("common"), CommonConfig::new);
        this.server = Config.create(WIdentifier.mod("server"), ServerConfig::new);
        this.balance = Config.create(WIdentifier.mod("balance"), BalanceConfig::new);
    }

    public static ClientConfig client() {
        return WerewolvesMod.config().clientConfig();
    }

    public static CommonConfig common() {
        return WerewolvesMod.config().commonConfig();
    }

    public static ServerConfig server() {
        return WerewolvesMod.config().serverConfig();
    }

    public static BalanceConfig balance() {
        return WerewolvesMod.config().balanceConfig();
    }

    public ClientConfig clientConfig() {
        return client.config();
    }

    public CommonConfig commonConfig() {
        return common.config();
    }

    public ServerConfig serverConfig() {
        return server.config();
    }

    public BalanceConfig balanceConfig() {
        return balance.config();
    }

    @Override
    protected void registerModBus(IEventBus bus) {
        bus.addListener(this::setup);
        bus.addListener(this::onLoad);
        bus.addListener(this::onReload);
    }

    private void setup(NewRegistryEvent event) {
        this.container.registerConfig(Type.COMMON, common.spec());
        this.container.registerConfig(Type.CLIENT, client.spec());
        this.container.registerConfig(Type.SERVER, server.spec());
        this.container.registerConfig(Type.SERVER, balance.spec(), "werewolves-balance.toml");
    }

    public void onLoad(final ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getType() == Type.SERVER) {
            FormHelper.reload();
        }
    }

    public void onReload(final ModConfigEvent.Reloading configEvent) {
        if (configEvent.getConfig().getType() == Type.SERVER) {
            FormHelper.reload();
        }
    }

    private record Config<T>(T config, ModConfigSpec spec) {

        public static <T> ModConfig.Config<T> create(Identifier id, Function<ModConfigSpec.Builder, T> consumer) {
            var builder = ColorConfigValue.configure(consumer);
            ConfigValueCodec.register(id, builder.getRight());
            return new ModConfig.Config<>(builder.getLeft(), builder.getRight());
        }

        public boolean isSpec(IConfigSpec spec) {
            return this.spec == spec;
        }
    }

}
