package de.teamlapen.werewolves.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.village.PointOfInterestType;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class MixinAccessors {

    @Mixin(IngameGui.class)
    public interface IngameGuiAccessor {
        @Accessor("scaledWidth")
        int getScaledWidth();

        @Accessor("scaledHeight")
        int getScaledHeight();
    }

    @Mixin(Blocks.class)
    public interface BlocksInvoker {

        @Invoker("createLogBlock")
        static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
            throw new NotImplementedException("Mixin inject failed");
        }
    }

    @Mixin(FireBlock.class)
    public interface FireBlockInvoker {

        @Invoker("setFireInfo")
        void invokeSetFireInfo(Block blockIn, int encouragement, int flammability);
    }

    @Mixin(PointOfInterestType.class)
    public interface PointOfInterestTypeInvoker {
        @Invoker("registerBlockStates")
        static PointOfInterestType registerBlockStates(PointOfInterestType poit) {
            throw new NotImplementedException("Mixin inject failed");
        }
    }

    @Mixin(Screen.class)
    public interface ScreenMixin {
        @Accessor("font")
        FontRenderer getFont();

        @Invoker("addButton")
        <T extends Widget> T invokeAddButton(T button);
    }

}
