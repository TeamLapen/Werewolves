package de.teamlapen.werewolves.common.world.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfTransformable;
import de.teamlapen.werewolves.common.core.ModAttachments;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.villager.Villager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Werewolf related data of villagers.
 * <p>
 * Replaces the synched entity data and fields that were previously injected into {@link Villager} via mixin, which conflicted with other mods.
 * The data is only needed on the server, so it is not synchronized.
 */
public class WerewolfVillagerAttachment {

    public static final MapCodec<WerewolfVillagerAttachment> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.BOOL.optionalFieldOf("werewolf", false).forGetter(d -> d.werewolf),
            WerewolfForm.CODEC.optionalFieldOf("form", WerewolfForm.BEAST).forGetter(d -> d.form),
            Codec.INT.optionalFieldOf("type", -1).forGetter(d -> d.skinType)
    ).apply(inst, WerewolfVillagerAttachment::new));

    private boolean werewolf;
    private WerewolfForm form;
    private int skinType;
    /**
     * Not persisted, the rage decays over time anyway
     */
    private int rage;

    public WerewolfVillagerAttachment() {
        this(false, WerewolfForm.BEAST, -1);
    }

    private WerewolfVillagerAttachment(boolean werewolf, WerewolfForm form, int skinType) {
        this.werewolf = werewolf;
        this.form = form;
        this.skinType = skinType >= 0 && skinType < WerewolfTransformable.TYPES ? skinType : -1;
    }

    public static WerewolfVillagerAttachment get(@NotNull Villager villager) {
        return villager.getData(ModAttachments.WEREWOLF_VILLAGER);
    }

    public static Optional<WerewolfVillagerAttachment> getOpt(@NotNull Villager villager) {
        return villager.getExistingData(ModAttachments.WEREWOLF_VILLAGER);
    }

    /**
     * @return whether the data differs from the default and has to be saved
     */
    public boolean shouldSerialize() {
        return this.werewolf || this.skinType >= 0;
    }

    public boolean isWerewolf() {
        return this.werewolf;
    }

    public void setWerewolf(boolean werewolf, RandomSource random) {
        this.werewolf = werewolf;
        if (werewolf) {
            this.form = random.nextBoolean() ? WerewolfForm.SURVIVALIST : WerewolfForm.BEAST;
        }
    }

    public WerewolfForm getForm() {
        return this.form;
    }

    /**
     * @return the skin type. Creates a random one if none has been chosen yet
     */
    public int getOrCreateSkinType(RandomSource random) {
        if (this.skinType < 0) {
            this.skinType = random.nextInt(WerewolfTransformable.TYPES);
        }
        return this.skinType;
    }

    public void setSkinType(int skinType) {
        this.skinType = skinType;
    }

    public int getRage() {
        return this.rage;
    }

    public void addRage(int amount) {
        this.rage = Math.max(0, this.rage + amount);
    }

    public void resetRage() {
        this.rage = 0;
    }
}
