package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the buttons in RightPanel has been toggled
 */

public class RightPanelChangedEvent extends BaseEvent {

    public RightPanelChangedEvent() {}

    @Override
    public String toString() {
        return "RightPanel Toggled.";
    }
}
