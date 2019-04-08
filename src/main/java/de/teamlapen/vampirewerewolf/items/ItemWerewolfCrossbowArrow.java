package de.teamlapen.vampirewerewolf.items;

public class ItemWerewolfCrossbowArrow extends ItemWerewolfBase {

    public ItemWerewolfCrossbowArrow() {
        super("crossbowA");
    }

    public enum EnumArrowType {
        SILVER("silver", 0.8, 0xFFFFFF);
        public final int color;
        final String name;
        final double baseDamage;

        private EnumArrowType(String name, double baseDamage, int color) {
            this.color = color;
            this.name = name;
            this.baseDamage = baseDamage;
        }
    }

}
