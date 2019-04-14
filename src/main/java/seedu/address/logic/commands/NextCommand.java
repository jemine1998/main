package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Displays next flashcard in the FlashCards to the user.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";
    public static final String COMMAND_ALIAS = "n";

    public static final String MESSAGE_SUCCESS = "Viewing flashcard %1$s";
    public static final String MESSAGE_LAST_FLASHCARD = "This is the last flash card!";
    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX =
            "The next flash card index provided is invalid";

    private static Integer nextInteger;

    private static Index nextIndex;
    private static int flashCardBegin = 0;

    public NextCommand() {
        flashCardBegin++;
        if (flashCardBegin > 1) {
            nextInteger++;
            nextIndex = Index.fromOneBased(nextInteger);
        }
    }

    public static void getNextIndex (Index index, int begin) {
        nextIndex = index;
        flashCardBegin = begin;
    }

    public static void getNextInteger (Integer indexIntegerValue) {
        nextInteger = indexIntegerValue;
    }

    public static void setNextInteger(Integer newIndexInteger, int start) {
        nextInteger = newIndexInteger;
        flashCardBegin = start;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Flashcard> updatedFlashcardList = model.getUpdatedFlashcardList();

        if (nextInteger >= updatedFlashcardList.size() + 1) {

            throw new CommandException(MESSAGE_LAST_FLASHCARD);
        }

        Flashcard selectedFlashcard = updatedFlashcardList.get(nextIndex.getZeroBased());
        PreviousCommand.setPreviousInteger(nextInteger, flashCardBegin);
        model.setSelectedSubject(selectedFlashcard.getSubject());
        model.setSelectedFlashcard(selectedFlashcard);

        return new CommandResult(String.format(MESSAGE_SUCCESS, nextIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextCommand // instanceof handles nulls
                && nextIndex.equals(((NextCommand) other).nextIndex)); // state check
    }

}


