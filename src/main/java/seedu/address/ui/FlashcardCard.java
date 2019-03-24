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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Flashcard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public FlashcardCard(Flashcard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        name.setText(flashcard.getName().fullName);
        phone.setText(flashcard.getPhone().value);
        address.setText(flashcard.getAddress().value);
        email.setText(flashcard.getEmail().value);
        deadline.setText(flashcard.getDeadline().value);
        tagger(flashcard);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColourIndex(String tagName) {
        return TAG_COLOURS[Math.floorMod(tagName.hashCode(), TAG_COLOURS.length)];
    }

    /**
     * Creates the tag labels for {@code flashcard}.
     */
    private void tagger(Flashcard p) {
        p.getTags().forEach(tagT -> {
            Label tagLabel = new Label(tagT.tagName);
            tagLabel.getStyleClass().add(getTagColourIndex(tagT.tagName));
            tags.getChildren().add(tagLabel);
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
