package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.flashcard.Flashcard;

/**
 * Represents a selection change in the Flashcard List Panel
 */
public class FlashcardPanelSelectionChangedEvent extends BaseEvent {


    private final Flashcard newSelection;

    public FlashcardPanelSelectionChangedEvent(Flashcard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Flashcard getNewSelection() {
        return newSelection;
    }
}
