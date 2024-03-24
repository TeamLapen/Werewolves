package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.api.items.IItemWithTier;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.client.extensions.ItemExtensions;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.ArmorMaterial;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class WolfPeltArmorItem extends ArmorItem implements IFactionExclusiveItem, IWerewolfArmor, IItemWithTier {

    public static final ArmorMaterial.Tiered PELT = new ArmorMaterial.Tiered("werewolves:pelt", IItemWithTier.TIER.NORMAL,15, ArmorMaterial.createReduction(1,1,1,1), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ModItems.PELT.get()));
    public static final ArmorMaterial.Tiered DARK_PELT = new ArmorMaterial.Tiered("werewolves:dark_pelt", IItemWithTier.TIER.ENHANCED,25, ArmorMaterial.createReduction(1,2,2,1), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ModItems.DARK_PELT.get()));
    public static final ArmorMaterial.Tiered WHITE_PELT = new ArmorMaterial.Tiered("werewolves:white_pelt", IItemWithTier.TIER.ULTIMATE,35, ArmorMaterial.createReduction(2,3,2,1), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);

    private final @NotNull TIER tier;
    public WolfPeltArmorItem(ArmorMaterial.Tiered material, Type pType, Properties pProperties) {
        super(material, pType, pProperties);
        this.tier = material.getTier();
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
    public @Nullable String getArmorTexture(@NotNull ItemStack stack, @NotNull Entity entity, @NotNull EquipmentSlot slot, @NotNull String type) {
        return slot == EquipmentSlot.HEAD ? REFERENCE.MODID + ":textures/models/armor/" + RegUtil.id(this).getPath() + ".png" : super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        addTierInformation(pTooltipComponents);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public TIER getVampirismTier() {
        return this.tier;
    }

    @Override
    public boolean canEquip(@NotNull ItemStack stack, @NotNull EquipmentSlot armorType, @NotNull Entity entity) {
        return super.canEquip(stack, armorType, entity) && Helper.isWerewolf(entity) && (!(entity instanceof Player player) || WerewolfPlayer.get(player).canWearArmor(stack));
    }

    @Override
    public boolean canWear(IWerewolfPlayer player, WerewolfForm form) {
        return true;
    }
}
