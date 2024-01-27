package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.player.VampirismPlayerAttributes;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.util.ArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SilverArmorItem extends ArmorItem implements ISilverItem {

    public static final ArmorMaterial SILVER = new ArmorMaterial("werewolves:silver", 15, ArmorMaterial.createReduction(2, 6,5, 2), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(ModTags.Items.SILVER_INGOT));

    public SilverArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(SILVER, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, @NotNull Player player) {
        if (player.tickCount % 16 == 8) {
            IFaction<?> f = VampirismPlayerAttributes.get(player).faction;
            if (WReference.WEREWOLF_FACTION.equals(f)) {
                player.addEffect(SilverEffect.createSilverEffect(player, 20, 1));
            }
        }
    }
}