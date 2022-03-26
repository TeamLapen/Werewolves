package de.teamlapen.werewolves.command.arguments.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WerewolfFormArgumentSerializer implements ArgumentSerializer<WerewolfFormArgument> {
    @Override
    public void serializeToNetwork(@Nonnull WerewolfFormArgument argument, @Nonnull FriendlyByteBuf buffer) {
        Collection<WerewolfForm> forms = argument.getAllowedForms();
        buffer.writeVarInt(forms.size());
        forms.forEach(form -> buffer.writeUtf(form.getName()));
    }

    @Nonnull
    @Override
    public WerewolfFormArgument deserializeFromNetwork(@Nonnull FriendlyByteBuf buffer) {
        List<WerewolfForm> forms = new ArrayList<>();
        int amount = buffer.readVarInt();
        for (int i = 0; i < amount; i++) {
            forms.add(WerewolfForm.getForm(buffer.readUtf(32767)));
        }
        return new WerewolfFormArgument(forms);
    }

    @Override
    public void serializeToJson(@Nonnull WerewolfFormArgument argument, @Nonnull JsonObject object) {
        JsonArray forms = new JsonArray();
        argument.getAllowedForms().forEach(form -> forms.add(form.getName()));
        object.add("forms", forms);
    }
}
