package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.model.flashcard.Flashcard;


/**
 * Panel containing the topic and content of flashcards.
 */

public class RightPanel extends UiPart<Region> {
    //public static final URL DEFAULT_PAGE =
    //requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    //public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";
    //public static final String SEARCH_PAGE_URL = "https://cs2113-ay1819s2-m11-3.github.io/TopicContentPage.html";
    //public static final String DEFAULT_PAGE = "default.html";
    //public static final String SEARCH_PAGE_URL = "https://dannong.github.io/dannflashcards/TopicContentPage.html";
    //private static final String FXML = "BrowserPanel.fxml";

    private static final String FXML = "RightPanel.fxml";

    private boolean topicIsShowing = true;

    @FXML
    private WebView browser;

    @FXML
    private SplitPane rightMostPane;

    @FXML
    private AnchorPane buttonAnchorPane;

    @FXML
    private StackPane contentAnchorPane;

    //@FXML
    //private AnchorPane contentAnchorPane;

    @FXML
    private StackPane topicAnchorPane;

    @FXML
    private Label selectedFlashcardTopic;

    @FXML
    private Label selectedFlashcardContent;

    @FXML
    private Label welcomeMessage;

    public RightPanel(ObservableValue<Flashcard> selectedFlashcard) {
        super(FXML);

        // Load flashcard page when selected flashcard changes.
        selectedFlashcard.addListener((observable, oldValue, newValue) -> {
            //if (newValue == null) {
            //    loadDefaultPage();
            //    return;
            //}
            loadFlashcardPage(newValue);
            topicIsShowing = true;
        });
        loadDefaultPage();
    }

    /**
     * Handles the Button Topic and displays Topic if it is initially displaying Content
     * @param event
     */

    @FXML
    private void handleTopicEvent(ActionEvent event) {
        topicIsShowing = true;
        selectedFlashcardTopic.setVisible(true);
        selectedFlashcardContent.setVisible(false);
        welcomeMessage.setVisible(false);
    }

    /**
     * Handles the Button Content and displays Content if it is initially displaying Topic
     * @param event
     */

    @FXML
    private void handleContentEvent(ActionEvent event) {
        topicIsShowing = false;
        selectedFlashcardTopic.setVisible(false);
        selectedFlashcardContent.setVisible(true);
        welcomeMessage.setVisible(false);
    }

    /**
     * Listen and update Topic and Content when a new flashcard is selected.
     * @param flashcard
     */

    private void loadFlashcardPage(Flashcard flashcard) {
        //loadPage(SEARCH_PAGE_URL + flashcard.getTopic().fullTopic);
        selectedFlashcardTopic.setText(flashcard.getTopic().toString());
        selectedFlashcardContent.setText(flashcard.getContent().toString());
        selectedFlashcardTopic.setVisible(true);
        selectedFlashcardContent.setVisible(false);
        welcomeMessage.setVisible(false);
    }

    //public void loadPage(String url) { Platform.runLater(() -> browser.getEngine().load(url));

    private void loadDefaultPage() {
        //loadPage(DEFAULT_PAGE.toExternalForm());
        welcomeMessage.setVisible(true);

    }
}
