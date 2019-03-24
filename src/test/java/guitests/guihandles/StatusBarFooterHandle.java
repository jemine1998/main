package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";
    private static final String TOTAL_FLASHCARDS_STATUS_ID = "#totalFlashcardsStatus";

    private final Labeled syncStatusNode;
    private final Labeled totalFlashcardsStatusNode;
    private final Labeled saveLocationNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedSaveLocation;
    private String lastRememberedTotalFlashcardsStatus;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncStatusNode = getChildNode(SYNC_STATUS_ID);
        saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
        totalFlashcardsStatusNode = getChildNode(TOTAL_FLASHCARDS_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Returns the text of the 'total flashcards' portion of the status bar.
     */
    public String getTotalFlashcardsStatus() {
        return totalFlashcardsStatusNode.getText();
    }


    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }

    /**
     * Remembers the content of the 'total flashcards' portion of the status bar.
     */
    public void rememberTotalFlashcardsStatus() {
        lastRememberedTotalFlashcardsStatus = getTotalFlashcardsStatus();
    }

    /**
     * Returns true if the current content of the 'total flashcards' is different from the value remembered by the most
     * recent {@code rememberTotalFlashcardsStatus()} call.
     */
    public boolean isTotalFlashcardsStatusChanged() {
        return !lastRememberedTotalFlashcardsStatus.equals(getTotalFlashcardsStatus());
    }

}
