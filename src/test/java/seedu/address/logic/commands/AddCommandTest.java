package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FlashBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.FlashcardBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashcardAdded modelStub = new ModelStubAcceptingFlashcardAdded();
        Flashcard validFlashcard = new FlashcardBuilder().build();

        CommandResult commandResult = new AddCommand(validFlashcard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFlashcard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashcard), modelStub.flashcardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() throws Exception {
        Flashcard validFlashcard = new FlashcardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithFlashcard(validFlashcard);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Flashcard alice = new FlashcardBuilder().withTopic("Alice").build();
        Flashcard bob = new FlashcardBuilder().withTopic("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        /**
         * Returns the SubjectBook
         */
        @Override
        public ReadOnlySubjectBook getSubjectBook() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the filtered subject list
         */
        @Override
        public ObservableList<SubjectTag> getFilteredSubjectList() {
            throw new AssertionError("This method should not be called.");
        }


        /**
         * Selected subject in the filtered subject list.
         * null if no subject is selected.
         */
        @Override
        public ReadOnlyProperty<SubjectTag> selectedSubjectProperty() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Adds the given flashcard.
         * {@code flashcard} must not already exist in the flash book.
         *
         * @param subject
         */

        @Override
        public void addSubject(SubjectTag subject) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Deletes the given subject.
         *
         * @param subject
         */
        @Override
        public void deleteSubject(SubjectTag subject) {

        }

        /**
         * Sets the selected subject in the filtered subject list.
         *
         * @param subject
         */

        @Override
        public void setSelectedSubject(SubjectTag subject) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
         *
         * @param predicate
         * @throws NullPointerException if {@code predicate} is null.
         */

        @Override
        public void updateFilteredSubjectList(Predicate<SubjectTag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Replaces flash book data with the data in {@code flashBook}.
         *
         * @param subjectBook
         */
        @Override
        public void setSubjectBook(SubjectBook subjectBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFlashBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashBookFilePath(Path flashBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SubjectTag getSelectedSubject() {
            return null;
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashBook(ReadOnlyFlashBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFlashBook getFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the filtered flashcard list
         */
        @Override
        public ObservableList<Flashcard> getUpdatedFlashcardList() {
            return null;
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFlashBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Flashcard> selectedFlashcardProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Flashcard getSelectedFlashcard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }
    }


    /**
     * A Model stub that contains a single flashcard.
     */
    private class ModelStubWithFlashcard extends ModelStub {
        private final Flashcard flashcard;

        ModelStubWithFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return this.flashcard.isSameFlashcard(flashcard);
        }
    }


    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingFlashcardAdded extends ModelStub {
        final ArrayList<Flashcard> flashcardsAdded = new ArrayList<>();

        @Override
        public void addSubject(SubjectTag subject) {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void setSelectedSubject(SubjectTag subject) {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void setSelectedFlashcard(Flashcard flashcard) {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return flashcardsAdded.stream().anyMatch(flashcard::isSameFlashcard);
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public void commitFlashBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyFlashBook getFlashBook() {
            return new FlashBook();
        }
    }

}

