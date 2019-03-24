package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;



/**
 * Displays previous flashcard in the FlashCards to the user.
 */
public class PreviousCommand extends Command {

    public static final String COMMAND_WORD = "previous";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_SUCCESS = "Viewing flashcard %1$s";
    public static final String MESSAGE_FIRST_FLASHCARD = "This is the first flash card!";

    private static int previousInteger;

    private static Index previousIndex;
    private static int flashCardBegin = 0;

    public PreviousCommand() {
        flashCardBegin++;
        if (flashCardBegin > 1) {
            previousInteger--;
            previousIndex = Index.fromOneBased(previousInteger);
        }
        if (previousInteger >= 1) {
            NextCommand.setNextInteger(previousInteger, flashCardBegin);
        }
    }

    public static void getPreviousIndex (Index index, int begin) {
        previousIndex = index;
        flashCardBegin = begin;
    }

    public static void getPreviousInteger(Integer indexIntegerValue) {
        previousInteger = indexIntegerValue;
    }

    public static void setPreviousInteger(Integer newIndexInteger, int start) {
        previousInteger = newIndexInteger;
        flashCardBegin = start;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Flashcard> filteredFlashcardList = model.getFilteredFlashcardList();

        if (previousInteger <= 0) {
            throw new CommandException(MESSAGE_FIRST_FLASHCARD);
        }
        model.setSelectedFlashcard(filteredFlashcardList.get(previousIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, previousIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreviousCommand // instanceof handles nulls
                && previousIndex.equals(((PreviousCommand) other).previousIndex)); // state check
    }

}


