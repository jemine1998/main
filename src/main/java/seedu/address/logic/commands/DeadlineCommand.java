package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;

/**
 * Adds/Changes the deadline or exam of an existing flashcard in the address book/flashcards.
 */

public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    public static final String COMMAND_ALIAS = "exam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deadline/exam of a flashcard identified "
            + "by the index number used in the last flashcard listing."
            + "Existing deadline will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "[DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "04 April 2028";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to Flashcard: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline from Flashcard: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This deadline clashes with another in the address book.";

    private final Index index;
    private final Deadline deadline;

    /**
     * @param index of the flashcard in the filtered flashcard list to edit the deadline
     * @param deadline of the flashcard to be updated to
     */

    public DeadlineCommand(Index index, Deadline deadline) {
        //requireAllNonNull(index, deadline);
        requireNonNull(index);
        requireNonNull(deadline);

        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcardToEdit = lastShownList.get(index.getZeroBased());
        Flashcard editedFlashcard = new Flashcard(flashcardToEdit.getName(), flashcardToEdit.getPhone(),
                flashcardToEdit.getEmail(),
                flashcardToEdit.getAddress(), deadline, flashcardToEdit.getTags());

        try {
            model.setFlashcard(flashcardToEdit, editedFlashcard);
        } catch (DuplicateFlashcardException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        } catch (FlashcardNotFoundException pnfe) {
            throw new AssertionError("The target flashcard cannot be missing");
        }

        //model.updateFlashcard(flashcardToEdit, editedFlashcard);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedFlashcard));
    }

    /**
     * Generates a command execution success message based on whether the deadline is added to or removed from
     * {@code flashcardToEdit}.
     */

    private String generateSuccessMessage(Flashcard flashcardToEdit) {
        String message = !deadline.value.isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, flashcardToEdit);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DeadlineCommand)) {
            return false;
        }

        //state check
        DeadlineCommand e = (DeadlineCommand) other;
        return index.equals(e.index)
                && deadline.equals(e.deadline);
    }
}
