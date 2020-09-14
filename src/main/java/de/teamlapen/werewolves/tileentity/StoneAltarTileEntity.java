package de.teamlapen.werewolves.tileentity;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;

public class StoneAltarTileEntity extends TileEntity implements ITickableTileEntity {

    private int activated;
    private int[] items = new int[2];

    public StoneAltarTileEntity() {
        super(ModTiles.stone_altar);
    }

    @Override
    public void tick() {

    }

    public ActionResultType onInteraction(PlayerEntity player) {
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            WerewolfLevelConf.LevelRequirement req = WerewolfLevelConf.getInstance().getRequirement(werewolf.getLevel() + 1);
            if (req instanceof WerewolfLevelConf.StoneAltarRequirement) {
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.isEmpty()) {
                    if (/*werewolf.getLevelHandler().canLevelUp() &&*/ isAltarFilled(((WerewolfLevelConf.StoneAltarRequirement) req))) {
                        VampirismAPI.getFactionPlayerHandler(player).ifPresent(p -> {
                            this.removeRequirements(((WerewolfLevelConf.StoneAltarRequirement) req), werewolf);
                            p.setFactionLevel(WReference.WEREWOLF_FACTION, p.getCurrentLevel() + 1);
                        });
                    } else {
                        if (werewolf.getMaxLevel() == werewolf.getLevel()) {
                            player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.maxlvl"), true);
                        } else {
                            player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.no_xp"), true);
                        }
                    }
                } else {
                    if (stack.getItem() == ModItems.liver) {
                        items[0] += 1;
                        stack.grow(-1);
                    } else if (stack.getItem() == ModItems.bones) {
                        items[1] += 1;
                        stack.grow(-1);
                    }
                }
            } else {
                player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.wrong_level"), true);
            }
        }
        return ActionResultType.SUCCESS;
    }

    private boolean isAltarFilled(WerewolfLevelConf.StoneAltarRequirement requirement) {
        return items[0] >= requirement.liverAmount && items[1] >= requirement.bonesAmount;
    }

    private void removeRequirements(WerewolfLevelConf.StoneAltarRequirement requirement, WerewolfPlayer werewolf) {
        this.items[0] -= requirement.liverAmount;
        this.items[1] -= requirement.bonesAmount;
        werewolf.getLevelHandler().clear();
    }
}
