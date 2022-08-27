package de.teamlapen.werewolves.command.arguments;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class WerewolfFormArgument implements ArgumentType<WerewolfForm> {

    private static final DynamicCommandExceptionType FORM_NOT_FOUND = new DynamicCommandExceptionType((id) -> {
        return Component.translatable("command.werewolves.argument.form.notfound", id);
    });
    private static final DynamicCommandExceptionType FORM_NOT_SUPPORTED = new DynamicCommandExceptionType((id) -> {
        return Component.translatable("command.werewolves.argument.form.not_supported", id);
    });

    public static WerewolfForm getForm(@NotNull CommandContext<CommandSourceStack> context, String id) {
        return context.getArgument(id, WerewolfForm.class);
    }

    public static @NotNull WerewolfFormArgument allForms() {
        return new WerewolfFormArgument(WerewolfForm.getAllForms());
    }

    public static @NotNull WerewolfFormArgument transformedForms() {
        return new WerewolfFormArgument(WerewolfForm.getAllForms().stream().filter(WerewolfForm::isTransformed).collect(Collectors.toList()));
    }

    public static @NotNull WerewolfFormArgument nonHumanForms() {
        return new WerewolfFormArgument(WerewolfForm.getAllForms().stream().filter(w -> !w.isHumanLike()).collect(Collectors.toList()));
    }

    public static @NotNull WerewolfFormArgument formArgument(WerewolfForm... allowedForms) {
        return new WerewolfFormArgument(Arrays.asList(allowedForms));
    }

    @NotNull
    private final Collection<WerewolfForm> allowedForms;

    public WerewolfFormArgument(@NotNull Collection<WerewolfForm> allowedForms) {
        this.allowedForms = allowedForms;
    }

    @NotNull
    public Collection<WerewolfForm> getAllowedForms() {
        return this.allowedForms;
    }

    @Override
    public @NotNull WerewolfForm parse(@NotNull StringReader reader) throws CommandSyntaxException {
        String id = reader.readString();
        WerewolfForm form = WerewolfForm.getForm(id);
        if (form == null) {
            throw FORM_NOT_FOUND.create(id);
        } else if (!this.allowedForms.contains(form)) {
            throw FORM_NOT_SUPPORTED.create(id);
        }
        return form;
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        Collection<WerewolfForm> forms = WerewolfForm.getAllForms();
        forms.remove(WerewolfForm.NONE);
        return SharedSuggestionProvider.suggest(this.allowedForms.stream().map(WerewolfForm::getName), builder);
    }

    @Override
    public @NotNull Collection<String> getExamples() {
        Collection<WerewolfForm> forms = WerewolfForm.getAllForms();
        forms.remove(WerewolfForm.NONE);
        return this.allowedForms.stream().map(WerewolfForm::getName).collect(Collectors.toList());
    }

    public static class Info implements ArgumentTypeInfo<WerewolfFormArgument, Info.Template> {
        @Override
        public void serializeToNetwork(@NotNull Template template, @NotNull FriendlyByteBuf buffer) {
            Collection<WerewolfForm> forms = template.allowedForms;
            buffer.writeVarInt(forms.size());
            forms.forEach(form -> buffer.writeUtf(form.getName()));
        }

        @NotNull
        @Override
        public Template deserializeFromNetwork(@NotNull FriendlyByteBuf buffer) {
            List<WerewolfForm> forms = new ArrayList<>();
            int amount = buffer.readVarInt();
            for (int i = 0; i < amount; i++) {
                forms.add(WerewolfForm.getForm(buffer.readUtf(32767)));
            }
            return new Template(forms);
        }

        @Override
        public void serializeToJson(@NotNull Template template, @NotNull JsonObject json) {
            JsonArray forms = new JsonArray();
            template.allowedForms.forEach(form -> forms.add(form.getName()));
            json.add("allowed_forms", forms);
        }

        @NotNull
        @Override
        public Template unpack(@NotNull WerewolfFormArgument argument) {
            return new Template(argument.getAllowedForms());
        }

        public class Template implements ArgumentTypeInfo.Template<WerewolfFormArgument> {

            final Collection<WerewolfForm> allowedForms;

            public Template(Collection<WerewolfForm> allowedForms) {
                this.allowedForms = allowedForms;
            }

            @NotNull
            @Override
            public WerewolfFormArgument instantiate(@NotNull CommandBuildContext context) {
                return new WerewolfFormArgument(allowedForms);
            }

            @NotNull
            @Override
            public ArgumentTypeInfo<WerewolfFormArgument, ?> type() {
                return Info.this;
            }
        }
    }
}
