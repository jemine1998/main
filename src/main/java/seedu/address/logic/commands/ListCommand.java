package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all flashcards in the FlashCards to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";

    public String getStatus() {
        return "default";
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        SelectSubjectCommand.listCommandCalled();
        model.setSelectedSubject(null);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
