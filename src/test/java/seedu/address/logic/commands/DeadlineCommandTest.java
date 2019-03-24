package seedu.address.logic.commands;

//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.testutil.TypicalFlashcards.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.testutil.FlashcardBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeadlineCommand.
 */

public class DeadlineCommandTest {

    private static final String DEADLINE_STUB = "31 December 2099";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addDeadlineUnfilteredList_success() {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard = new FlashcardBuilder(firstFlashcard).withDeadline(DEADLINE_STUB).build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD,
                new Deadline(editedFlashcard.getDeadline().value));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_ADD_DEADLINE_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashcard(firstFlashcard, editedFlashcard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_deleteDeadlineUnfilteredList_success() {
        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard = new FlashcardBuilder(firstFlashcard).withDeadline("").build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD,
                new Deadline(editedFlashcard.getDeadline().toString()));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_DELETE_DEADLINE_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashcard(firstFlashcard, editedFlashcard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredList_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Flashcard firstFlashcard = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard editedFlashcard = new FlashcardBuilder(model.getFilteredFlashcardList()
                .get(INDEX_FIRST_FLASHCARD.getZeroBased())).withDeadline(DEADLINE_STUB).build();

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD,
                new Deadline(editedFlashcard.getDeadline().value));

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_ADD_DEADLINE_SUCCESS, editedFlashcard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashcard(firstFlashcard, editedFlashcard);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidFlashcardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, VALID_DEADLINE_BOB);

        assertCommandFailure(deadlineCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */

    @Test
    public void execute_invalidFlashcardIndexFilteredList_failure() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        //ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFlashcardList().size());

        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, VALID_DEADLINE_BOB);

        assertCommandFailure(deadlineCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Flashcard flashcardToModify = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard modifiedFlashcard = new FlashcardBuilder(flashcardToModify).withDeadline(DEADLINE_STUB).build();
        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD, new Deadline(DEADLINE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setFlashcard(flashcardToModify, modifiedFlashcard);
        expectedModel.commitAddressBook();

        //deadline -> first flashcard deadline changed
        deadlineCommand.execute(model, commandHistory);

        //undo -> reverts address book back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first flashcard modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeadlineCommand deadlineCommand = new DeadlineCommand(outOfBoundIndex, new Deadline(""));

        //execution failed -> address book state not added into model
        assertCommandFailure(deadlineCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);

        //single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Flashcard#deadline} from a filtered list.
     * 2. Undo the modification.
     * The unfiltered lsit should be shown now. Verify that the index of the previously modified flashcard in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the flashcard object regardless of indexing.
     */

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameFlashcardDeleted() throws Exception {
        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD, new Deadline(DEADLINE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showFlashcardAtIndex(model, INDEX_SECOND_FLASHCARD);
        Flashcard flashcardToModify = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        Flashcard modifiedFlashcard = new FlashcardBuilder(flashcardToModify).withDeadline(DEADLINE_STUB).build();
        expectedModel.setFlashcard(flashcardToModify, modifiedFlashcard);
        expectedModel.commitAddressBook();

        //deadline -> modifies second flashcard in unfiltered flashcard list/first flashcard in filtered flashcard list
        deadlineCommand.execute(model, commandHistory);

        //undo -> reverts address book back to previous state and filtered flashcard list to show all subjects
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> modifies same second flashcard in unfiltered flashcard list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }

}
