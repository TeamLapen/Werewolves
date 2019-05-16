package de.teamlapen.werewolves.potion;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WerewolvesPotion extends Potion {

    private static final ResourceLocation ICONS = new ResourceLocation("vampirism", "textures/gui/potions.png");
    @SideOnly(Side.CLIENT)
    private static final int ICON_TEXTURE_WIDTH = 144;
    @SideOnly(Side.CLIENT)
    private static final int ICON_TEXTURE_HEIGHT = 36;
    private boolean statusIcon;

    public WerewolvesPotion(String name, boolean isBadEffectIn, int liquidColorIn, boolean statusIcon) {
        super(isBadEffectIn, liquidColorIn);
        this.setRegistryName(REFERENCE.MODID, name);
        this.setPotionName("effect.werewolves." + name);
        this.statusIcon = statusIcon;
    }

    @Override
    public boolean hasStatusIcon() {
        return this.statusIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int k, int l, PotionEffect effect, Minecraft mc, float alpha) {
        int index = this.getStatusIconIndex();
        if (index >= 0) {
            mc.getTextureManager().bindTexture(ICONS);
            UtilLib.drawTexturedModalRect(0, k + 3, l + 3, index % 8 * 18, index / 8 * 18, 18, 18, ICON_TEXTURE_WIDTH, ICON_TEXTURE_HEIGHT);

        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        int index = this.getStatusIconIndex();
        if (index >= 0) {
            mc.getTextureManager().bindTexture(ICONS);
            UtilLib.drawTexturedModalRect(0, x + 6, y + 7, index % 8 * 18, index / 8 * 18, 18, 18, ICON_TEXTURE_WIDTH, ICON_TEXTURE_HEIGHT);
        }
    }

    @Override
    public WerewolvesPotion setIconIndex(int p_76399_1_, int p_76399_2_) {
        super.setIconIndex(p_76399_1_, p_76399_2_);
        return this;
    }

}
