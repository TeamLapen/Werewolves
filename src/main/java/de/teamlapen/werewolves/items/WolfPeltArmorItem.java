package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.api.items.IItemWithTier;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.client.extensions.ItemExtensions;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class WolfPeltArmorItem extends ArmorItem implements IFactionExclusiveItem, IWerewolfArmor, IItemWithTier {

    private final @NotNull TIER tier;

    public WolfPeltArmorItem(Holder<ArmorMaterial> material, Type pType, TIER tier) {
        super(material, pType, new Properties());
        this.tier = tier;
    }

    @Override
    public @Nullable IFaction<?> getExclusiveFaction(@NotNull ItemStack stack) {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ItemExtensions.WOLF_PELT);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return slot == EquipmentSlot.HEAD ? WResourceLocation.mod("textures/models/armor/" + RegUtil.id(this).getPath() + ".png") : super.getArmorTexture(stack, entity, slot, layer, innerModel);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Item.TooltipContext pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        addTierInformation(pTooltipComponents);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public TIER getVampirismTier() {
        return this.tier;
    }

    @Override
    public boolean canEquip(@NotNull ItemStack stack, @NotNull EquipmentSlot armorType, @NotNull LivingEntity entity) {
        return super.canEquip(stack, armorType, entity) && Helper.isWerewolf(entity) && (!(entity instanceof Player player) || WerewolfPlayer.get(player).canWearArmor(stack));
    }

    @Override
    public boolean canWear(IWerewolfPlayer player, WerewolfForm form) {
        return true;
    }
}
