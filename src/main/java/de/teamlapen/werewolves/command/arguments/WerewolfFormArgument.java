package de.teamlapen.werewolves.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class WerewolfFormArgument implements ArgumentType<WerewolfForm> {

    private static final DynamicCommandExceptionType FORM_NOT_FOUND = new DynamicCommandExceptionType((id) -> {
        return new TranslationTextComponent("command.werewolves.argument.form.notfound", id);
    });
    private static final DynamicCommandExceptionType FORM_NOT_SUPPORTED = new DynamicCommandExceptionType((id) -> {
        return new TranslationTextComponent("command.werewolves.argument.form.not_supported", id);
    });

    public static WerewolfForm getForm(CommandContext<CommandSource> context, String id) {
        return context.getArgument(id, WerewolfForm.class);
    }

    public static WerewolfFormArgument formArgument() {
        return new WerewolfFormArgument();
    }

    @Override
    public WerewolfForm parse(StringReader reader) throws CommandSyntaxException {
        String id = reader.readString();
        WerewolfForm form = WerewolfForm.getForm(id);
        if (form == null) {
            throw FORM_NOT_FOUND.create(id);
        } else if (form == WerewolfForm.NONE) {
            throw FORM_NOT_SUPPORTED.create(id);
        }
        return form;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Collection<WerewolfForm> forms = WerewolfForm.getAllForms();
        forms.remove(WerewolfForm.NONE);
        return ISuggestionProvider.suggest(forms.stream().map(WerewolfForm::getName), builder);
    }

    @Override
    public Collection<String> getExamples() {
        Collection<WerewolfForm> forms = WerewolfForm.getAllForms();
        forms.remove(WerewolfForm.NONE);
        return forms.stream().map(WerewolfForm::getName).collect(Collectors.toList());
    }
}
