package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.faction.common.components.FactionRestriction;
import de.teamlapen.library.api.world.items.IItemWithTier;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.common.tags.ModFactionsTags;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Consumer;

public class WolfPeltArmorItem extends Item implements IWerewolfArmor, IItemWithTier {

    private final Tier tier;

    public WolfPeltArmorItem(ArmorMaterial material, Properties props, ArmorType pType, Tier tier) {
        super(props.humanoidArmor(material, pType).durability(pType.getDurability(switch (tier) {
            case NORMAL -> 15;
            case ENHANCED -> 25;
            case ULTIMATE -> 35;
        })).factions$restrictFaction(ModFactionsTags.IS_WEREWOLF));
        this.tier = tier;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        addTierInformation(builder);
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    @Override
    public Tier getTier() {
        return this.tier;
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return super.canEquip(stack, armorType, entity) && FactionRestriction.canUse(entity, stack, true) && (!(entity instanceof Player player) || WerewolfPlayer.get(player).canWearArmor(stack));
    }

    @Override
    public boolean canWear(IWerewolfPlayer player, WerewolfForm form) {
        return true;
    }

}
