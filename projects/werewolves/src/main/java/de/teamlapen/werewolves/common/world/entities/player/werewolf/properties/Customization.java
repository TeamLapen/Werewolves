package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;

import com.mojang.serialization.Codec;
import de.teamlapen.faction.common.util.collections.CollectionUtil;
import de.teamlapen.sync.PropertyParentSync;
import de.teamlapen.sync.PropertySync;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;

import java.util.HashMap;
import java.util.Map;

public class Customization extends PropertyParentSync {
    private final Map<WerewolfForm, Integer> eyeTypes = new HashMap<>();
    private final Map<WerewolfForm, Integer> skinTypes = new HashMap<>();
    private final Map<WerewolfForm, Boolean> glowingEyes = new HashMap<>();

    private static final Codec<Map<WerewolfForm, Integer>> INT_TYPE_CODEC = Codec.simpleMap(WerewolfForm.CODEC, Codec.INT, WerewolfForm.FORM_KEYS).codec();
    private static final Codec<Map<WerewolfForm, Boolean>> BOOL_TYPE_CODEC = Codec.simpleMap(WerewolfForm.CODEC, Codec.BOOL, WerewolfForm.FORM_KEYS).codec();

    public Customization(PropertySync parent) {
        super(parent);
    }

    @Override
    protected void registerProperties() {
        registerProperty(WIdentifier.mod("eye_types")).map(INT_TYPE_CODEC).provider(() -> this.eyeTypes).commonLoader(x -> CollectionUtil.updateCollection(this.eyeTypes, x)).register();
        registerProperty(WIdentifier.mod("skin_type")).map(INT_TYPE_CODEC).provider(() -> this.skinTypes).commonLoader(x -> CollectionUtil.updateCollection(this.skinTypes, x)).register();
        registerProperty(WIdentifier.mod("glowing_eyes")).map(BOOL_TYPE_CODEC).provider(() -> this.glowingEyes).commonLoader(x -> CollectionUtil.updateCollection(this.glowingEyes, x)).register();
    }

    public int getEyeType(WerewolfForm form) {
        return Math.max(0, this.eyeTypes.getOrDefault(form, 0));
    }

    public int getSkinType(WerewolfForm form) {
        return Math.max(0, this.skinTypes.getOrDefault(form, 0));
    }

    public boolean hasGlowingEyes(WerewolfForm form) {
        return this.glowingEyes.getOrDefault(form, false);
    }

    public void setEyeType(WerewolfForm form, int eyeType){
        this.eyeTypes.put(form, eyeType);
    }

    public void setSkinType(WerewolfForm form, int skinType){
        this.skinTypes.put(form, skinType);
    }

    public void setGlowingEyes(WerewolfForm form, boolean glowingEyes){
        this.glowingEyes.put(form, glowingEyes);
    }
}
