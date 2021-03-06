package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Flashcard;

/**
 * Unmodifiable view of an flash book
 */
public interface ReadOnlyFlashBook extends Observable {

    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     */
    ObservableList<Flashcard> getFlashcardList();

}
