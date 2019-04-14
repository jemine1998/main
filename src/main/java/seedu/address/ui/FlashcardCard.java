package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

/**
 * An UI component that displays information of a {@code Flashcard}.
 */
public class FlashcardCard extends UiPart<Region> {

    private static final String FXML = "FlashcardListCard.fxml";
    private static final String[] TAG_COLOURS =
        {"white", "black", "orange", "maroon", "pink", "green", "blue", "purple", "navy", "beige"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FlashBook level 4</a>
     */

    public final Flashcard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label topic;
    @FXML
    private Label id;
    @FXML
    private Label difficulty;
    //@FXML
    //private Label content;
    @FXML
    private Label deadline;
    @FXML
    private Label index;
    @FXML
    private FlowPane subject;

    public FlashcardCard(Flashcard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        topic.setText(flashcard.getTopic().fullTopic);
        difficulty.setText(flashcard.getDifficulty().value);
        //content.setText(flashcard.getContent().value);
        deadline.setText(flashcard.getDeadline().value);
        tagger(flashcard);
    }

    /**
     * Returns the color style for {@code subjectName}'s label.
     */
    private String getTagColourIndex(String tagName) {
        return TAG_COLOURS[Math.floorMod(tagName.hashCode(), TAG_COLOURS.length)];
    }

    /**
     * Creates the tag labels for {@code flashcard}.
     */
    private void tagger(Flashcard f) {
        f.getTags().forEach(tagT -> {
            Label tagLabel = new Label(tagT.subjectName);
            tagLabel.getStyleClass().add(getTagColourIndex(tagT.subjectName));
            subject.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashcardCard)) {
            return false;
        }

        // state check
        FlashcardCard card = (FlashcardCard) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
