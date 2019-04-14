package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;
    Predicate<SubjectTag> PREDICATE_SHOW_ALL_SUBJECTS = unused -> true;


    // ================= SUBJECT BOOK ================= //
    /** Returns the SubjectBook */
    ReadOnlySubjectBook getSubjectBook();

    /** Returns an unmodifiable view of the filtered subject list */
    ObservableList<SubjectTag> getFilteredSubjectList();

    /**
     * Selected subject in the filtered subject list.
     * null if no subject is selected.
     */
    ReadOnlyProperty<SubjectTag> selectedSubjectProperty();

    /**
     * Adds the given subject
     * {@code flashcard} must not already exist in the flash book.
     */
    void addSubject(SubjectTag subject);

    /**
     * Deletes the given subject.
     */
    void deleteSubject(SubjectTag subject);

    /**
     * Sets the selected subject in the filtered subject list.
     */
    void setSelectedSubject(SubjectTag subject);

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSubjectList(Predicate<SubjectTag> predicate);

    /**
     * Replaces flash book data with the data in {@code flashBook}.
     */
    void setSubjectBook(SubjectBook subjectBook);

    // ================ GENERIC ================= //
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ================= FLASH BOOK ================= //
    /**
     * Returns the user prefs' flash book file path.
     */
    Path getFlashBookFilePath();

    /**
     * Sets the user prefs' flash book file path.
     */
    void setFlashBookFilePath(Path flashBookFilePath);

    SubjectTag getSelectedSubject();

    /**
     * Replaces flash book data with the data in {@code flashBook}.
     */
    void setFlashBook(ReadOnlyFlashBook flashBook);

    /** Returns the FlashBook */
    ReadOnlyFlashBook getFlashBook();

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the flash book.
     */
    boolean hasFlashcard(Flashcard flashcard);

    /**
     * Deletes the given flashcard.
     * The flashcard must exist in the flash book.
     */
    void deleteFlashcard(Flashcard target);

    /**
     * Adds the given flashcard.
     * {@code flashcard} must not already exist in the flash book.
     */
    void addFlashcard(Flashcard flashcard);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashcard}.
     * {@code target} must exist in the flash book.
     * The flashcard identity of {@code editedFlashcard} must not
     * be the same as another existing flashcard in the flash book.
     */
    void setFlashcard(Flashcard target, Flashcard editedFlashcard);

    /** Returns an unmodifiable view of the filtered flashcard list */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /** Returns an unmodifiable view of the filtered flashcard list */
    ObservableList<Flashcard> getUpdatedFlashcardList();

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);

    /**
     * Returns true if the model has previous flash book states to restore.
     */
    boolean canUndoFlashBook();

    /**
     * Returns true if the model has undone flash book states to restore.
     */
    boolean canRedoFlashBook();

    /**
     * Restores the model's flash book to its previous state.
     */
    void undoFlashBook();

    /**
     * Restores the model's flash book to its previously undone state.
     */
    void redoFlashBook();

    /**
     * Saves the current flash book state for undo/redo.
     */
    void commitFlashBook();

    /**
     * Selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    ReadOnlyProperty<Flashcard> selectedFlashcardProperty();

    /**
     * Returns the selected flashcard in the filtered flashcard list.
     * null if no flashcard is selected.
     */
    Flashcard getSelectedFlashcard();

    /**
     * Sets the selected flashcard in the filtered flashcard list.
     */
    void setSelectedFlashcard(Flashcard flashcard);
}
