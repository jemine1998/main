package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FlashBookChangedEvent;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.model.subject.ReadOnlySubjectBook;
import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * Represents the in-memory model of the flash book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFlashBook versionedFlashBook;
    private final SubjectBook subjectBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final SimpleObjectProperty<Flashcard> selectedFlashcard = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<SubjectTag> selectedSubject = new SimpleObjectProperty<>();
    private final FilteredList<SubjectTag> filteredSubjects;
    private ObservableList<Flashcard> updatedFlashcardList = FXCollections.observableArrayList();

    /**
     * Initializes a ModelManager with the given flashBook and userPrefs.
     */
    public ModelManager(ReadOnlySubjectBook subjectBook, ReadOnlyFlashBook flashBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(subjectBook, flashBook, userPrefs);

        logger.fine("Initializing with flash book: " + flashBook + " and user prefs " + userPrefs);

        versionedFlashBook = new VersionedFlashBook(flashBook);
        this.subjectBook = new SubjectBook(subjectBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(versionedFlashBook.getFlashcardList());
        filteredFlashcards.addListener(this::ensureSelectedFlashcardIsValid);
        filteredSubjects = new FilteredList<>(this.subjectBook.getSubjectList());
        filteredSubjects.addListener(this::ensureSelectedSubjectIsValid);
    }

    public ModelManager() {
        this(new SubjectBook(), new FlashBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFlashBookFilePath() {
        return userPrefs.getFlashBookFilePath();
    }

    @Override
    public void setFlashBookFilePath(Path flashBookFilePath) {
        requireNonNull(flashBookFilePath);
        userPrefs.setFlashBookFilePath(flashBookFilePath);
    }

    //=========== Subject Book ==================================================================================

    /**
     * Returns the SubjectBook
     */
    @Override
    public ReadOnlySubjectBook getSubjectBook() {
        return subjectBook;
    }

    /**
     * Returns an unmodifiable view of the filtered subject list
     */
    @Override
    public ObservableList<SubjectTag> getFilteredSubjectList() {
        return filteredSubjects;
    }

    @Override
    public void addSubject(SubjectTag subject) {
        subjectBook.addSubject(subject);
        updateFilteredSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
    }

    @Override
    public void deleteSubject(SubjectTag subject) {
        subjectBook.deleteSubject(subject);
    }

    /**
     * Selected subject in the filtered subject list.
     * null if no subject is selected.
     */
    @Override
    public ReadOnlyProperty<SubjectTag> selectedSubjectProperty() {
        return selectedSubject;
    }

    /**
     * Sets the selected subject in the filtered subject list.
     *
     * @param subject
     */
    @Override
    public void setSelectedSubject(SubjectTag subject) {
        selectedSubject.setValue(subject);
    }

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredSubjectList(Predicate<SubjectTag> predicate) {
        requireNonNull(predicate);
        filteredSubjects.setPredicate(predicate);
    }

    /**
     * Replaces flash book data with the data in {@code flashBook}.
     *
     * @param subjects
     */
    @Override
    public void setSubjectBook(SubjectBook subjects) {
        subjectBook.resetData(subjects);
    }


    /**
     * Ensures {@code selectedFlashcard} is a valid flashcard in {@code filteredFlashcards}.
     */
    private void ensureSelectedSubjectIsValid(ListChangeListener.Change<? extends SubjectTag> change) {
        while (change.next()) {
            if (selectedSubject.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedSubjectReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedSubject.getValue());
            if (wasSelectedSubjectReplaced) {
                // Update selectedFlashcard to its new value.
                int index = change.getRemoved().indexOf(selectedSubject.getValue());
                selectedSubject.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedSubjectRemoved = change.getRemoved().stream()
                    .anyMatch(removedSubject -> selectedSubject.getValue().isSameSubject(removedSubject));
            if (wasSelectedSubjectRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedSubject.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public SubjectTag getSelectedSubject() {
        return selectedSubject.get();
    }


    //=========== FlashBook ================================================================================

    @Override
    public void setFlashBook(ReadOnlyFlashBook flashBook) {
        versionedFlashBook.resetData(flashBook);
    }

    @Override
    public ReadOnlyFlashBook getFlashBook() {
        return versionedFlashBook;
    }

    private void indicateFlashBookChanged() {
        raise(new FlashBookChangedEvent(versionedFlashBook));
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return versionedFlashBook.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        versionedFlashBook.removeFlashcard(target);
        indicateFlashBookChanged();
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        versionedFlashBook.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        indicateFlashBookChanged();
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        versionedFlashBook.setFlashcard(target, editedFlashcard);
    }


    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedFlashBook}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedFlashBook}
     */
    @Override
    public ObservableList<Flashcard> getUpdatedFlashcardList() {

        updatedFlashcardList.clear();

        for (Flashcard f : filteredFlashcards) {
            if (selectedSubject.getValue().equals(f.getSubject())) {
                updatedFlashcardList.add(f);
            }
        }

        for (SubjectTag s : filteredSubjects) {
            if (!s.equals(selectedSubject.get())) {
                setSelectedSubject(s);
                break;
            }
        }

        return updatedFlashcardList;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFlashBook() {
        return versionedFlashBook.canUndo();
    }

    @Override
    public boolean canRedoFlashBook() {
        return versionedFlashBook.canRedo();
    }

    @Override
    public void undoFlashBook() {
        versionedFlashBook.undo();
        indicateFlashBookChanged();
    }

    @Override
    public void redoFlashBook() {
        versionedFlashBook.redo();
        indicateFlashBookChanged();
    }

    @Override
    public void commitFlashBook() {
        versionedFlashBook.commit();
    }

    //=========== Selected flashcard ===========================================================================

    @Override
    public ReadOnlyProperty<Flashcard> selectedFlashcardProperty() {
        return selectedFlashcard;
    }

    @Override
    public Flashcard getSelectedFlashcard() {
        return selectedFlashcard.getValue();
    }

    @Override
    public void setSelectedFlashcard(Flashcard flashcard) {
        if (flashcard != null && !filteredFlashcards.contains(flashcard)) {
            throw new FlashcardNotFoundException();
        }
        selectedFlashcard.setValue(flashcard);
    }

    /**
     * Ensures {@code selectedFlashcard} is a valid flashcard in {@code filteredFlashcards}.
     */
    private void ensureSelectedFlashcardIsValid(ListChangeListener.Change<? extends Flashcard> change) {
        while (change.next()) {
            if (selectedFlashcard.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedFlashcardReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedFlashcard.getValue());
            if (wasSelectedFlashcardReplaced) {
                // Update selectedFlashcard to its new value.
                int index = change.getRemoved().indexOf(selectedFlashcard.getValue());
                selectedFlashcard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedFlashcardRemoved = change.getRemoved().stream()
                    .anyMatch(removedFlashcard -> selectedFlashcard.getValue().isSameFlashcard(removedFlashcard));
            if (wasSelectedFlashcardRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedFlashcard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedFlashBook.equals(other.versionedFlashBook)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashcards.equals(other.filteredFlashcards)
                && Objects.equals(selectedFlashcard.get(), other.selectedFlashcard.get());
    }

}
