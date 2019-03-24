package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.ReadOnlyAddressBook;



/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String TOTAL_FLASHCARDS_STATUS = "%d flashcard(s) total";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */

    private static final String FXML = "StatusBarFooter.fxml";

    private static Clock clock = Clock.systemDefaultZone();

    private Logic logic;

    @FXML
    private Label syncStatus;
    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label totalFlashcardsStatus;



    public StatusBarFooter(Path saveLocation, ReadOnlyAddressBook addressBook, int totalFlashcards) {
        super(FXML);
        addressBook.addListener(observable -> updateSyncStatus());
        syncStatus.setText(SYNC_STATUS_INITIAL);
        setTotalFlashcards(totalFlashcards);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        registerAsAnEventHandler(this);
    }

    public StatusBarFooter(Path stubSaveLocation, int initialTotalFlashcards) {
        super(stubSaveLocation, initialTotalFlashcards);
    }

    private void setTotalFlashcards(int totalFlashcards) {
        Platform.runLater(() -> totalFlashcardsStatus.setText(String.format(TOTAL_FLASHCARDS_STATUS, totalFlashcards)));
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    /**
     * Updates "last updated" status to the current time.
     */
    private void updateSyncStatus() {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        syncStatus.setText(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        updateSyncStatus();
        setTotalFlashcards(abce.data.getFlashcardList().size());
    }

}
