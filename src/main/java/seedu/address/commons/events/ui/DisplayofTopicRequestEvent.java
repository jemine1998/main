package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to focus on the Topic display.
 * Does not deselect what the user has selected on screen.
 */

public class DisplayofTopicRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
