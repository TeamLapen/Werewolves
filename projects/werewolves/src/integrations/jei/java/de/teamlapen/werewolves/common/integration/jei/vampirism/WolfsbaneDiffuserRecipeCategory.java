package de.teamlapen.werewolves.common.integration.jei.vampirism;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class WolfsbaneDiffuserRecipeCategory implements IRecipeCategory<WolfsbaneDiffuserRecipe> {

    public static final int width = 150;
    public static final int height = 18;

    private final IDrawable background;
    private final IDrawable slot;
    private final IDrawable icon;
    private final Component localizedName;

    public WolfsbaneDiffuserRecipeCategory(IGuiHelper guiHelper, Block iconBlock) {
        this.background = guiHelper.createBlankDrawable(width, height);
        this.slot = guiHelper.getSlotDrawable();
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(iconBlock));
        this.localizedName = Component.translatable("block.werewolves.wolfsbane_diffuser_normal");
    }

    @Override
    public IRecipeType<WolfsbaneDiffuserRecipe> getRecipeType() {
        return WerewolvesVampirismJEIPlugin.WOLFSBANE_DIFFUSER;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WolfsbaneDiffuserRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .add(recipe.input());
    }

    @Override
    public void draw(WolfsbaneDiffuserRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphicsExtractor, double mouseX, double mouseY) {
        this.background.draw(guiGraphicsExtractor);
        guiGraphicsExtractor.pose().pushMatrix();
        this.slot.draw(guiGraphicsExtractor);

        // the burn duration keys are provided by Vampirism, which is always present when this category is registered
        int burnDuration = recipe.burnTime() / 20;
        Component text;
        if (burnDuration < 60) {
            text = Component.translatable("gui.vampirism.jei.category.diffuser.burn_duration_seconds", burnDuration);
        } else if (burnDuration < 3600) {
            text = Component.translatable("gui.vampirism.jei.category.diffuser.burn_duration_minutes", burnDuration / 60, (burnDuration % 60));
        } else {
            text = Component.translatable("gui.vampirism.jei.category.diffuser.burn_duration_hours", burnDuration / 3600, (burnDuration % 3600) / 60, (burnDuration % 3600 % 60));
        }

        guiGraphicsExtractor.text(Minecraft.getInstance().font, text, 24, 5, 0xFF808080, false);
        guiGraphicsExtractor.pose().popMatrix();
    }
}
