package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModPotions;
import de.teamlapen.werewolves.potion.DrowsyPotion;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPelt extends ItemArmor {
    public static final String regName = "wolfs_pelt";

    public ItemPelt() {
        super(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.LEGS);
        this.setCreativeTab(WerewolvesMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return String.format(REFERENCE.MODID + ":textures/models/armor/" + regName + ".png");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isDaytime() && world.getWorldTime() % 240 == 0) {
            if (!(player.isPotionActive(ModPotions.drowsy) || Helper.isWerewolf(player) || player.isPlayerSleeping())) {
                DrowsyPotion.addDrowsyPotion(player);
            }
        }
    }

}
