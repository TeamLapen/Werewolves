package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.player.VampirismPlayerAttributes;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.effects.WerewolfWeakeningEffect;
import de.teamlapen.werewolves.util.WerewolvesArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SilverArmorItem extends ArmorItem implements ISilverItem {

    public SilverArmorItem(EquipmentSlot slot, Item.Properties properties) {
        super(WerewolvesArmorMaterials.SILVER, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, @NotNull Player player) {
        if (player.tickCount % 16 == 8) {
            IFaction<?> f = VampirismPlayerAttributes.get(player).faction;
            if (WReference.WEREWOLF_FACTION.equals(f)) {
                player.addEffect(WerewolfWeakeningEffect.createSilverEffect(player, 20, 1));
            }
        }
    }
}