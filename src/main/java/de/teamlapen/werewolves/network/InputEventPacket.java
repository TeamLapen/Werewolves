package de.teamlapen.werewolves.network;

import de.teamlapen.lib.HelperLib;
import de.teamlapen.lib.lib.network.ISyncable;
import de.teamlapen.lib.network.IMessage;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.hunter.IHunterPlayer;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.inventory.container.BloodPotionTableContainer;
import de.teamlapen.vampirism.inventory.container.HunterBasicContainer;
import de.teamlapen.vampirism.inventory.container.HunterTrainerContainer;
import de.teamlapen.vampirism.items.VampirismVampireSword;
import de.teamlapen.vampirism.player.hunter.HunterPlayer;
import de.teamlapen.vampirism.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.player.skills.SkillHandler;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class InputEventPacket implements IMessage {

    public static final String BITE = "bt";

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SPLIT = "&";


    static void encode(InputEventPacket msg, PacketBuffer buf) {
        buf.writeString(msg.action + SPLIT + msg.param);
    }

    static InputEventPacket decode(PacketBuffer buf) {
        String[] s = buf.readString(50).split(SPLIT);
        InputEventPacket msg = new InputEventPacket();
        msg.action = s[0];
        if (s.length > 1) {
            msg.param = s[1];
        } else {
            msg.param = "";
        }
        return msg;
    }

    public static void handle(final InputEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        Validate.notNull(msg.action);
        ServerPlayerEntity player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(() -> {
            switch (msg.action) {
                case BITE:
                    try {
                        int id = Integer.parseInt(msg.param);
                        if (id != 0) {
                            WerewolfPlayer.getOpt(player).ifPresent(werewolfPlayer -> werewolfPlayer.biteEntity(id));
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.error("Receiving invalid param {} for {}", msg.param, msg.action);
                    }
                    break;
                default:
            }
            ctx.setPacketHandled(true);
        });
    }
    private String param;
    private String action;

    public InputEventPacket(String action, String param) {
        this.action = action;
        this.param = param;
    }

    private InputEventPacket() {

    }
}
