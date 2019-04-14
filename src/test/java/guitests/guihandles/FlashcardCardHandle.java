package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

/**
 * Provides a handle to a flashcard card in the flashcard list panel.
 */
public class FlashcardCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TOPIC_FIELD_ID = "#topic";
    //private static final String CONTENT_FIELD_ID = "#content";
    private static final String DIFFICULTY_FIELD_ID = "#difficulty";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String TAGS_FIELD_ID = "#subject";

    private final Label idLabel;
    private final Label topicLabel;
    //private final Label contentLabel;
    private final Label difficultyLabel;
    private final Label deadlineLabel;
    private final List<Label> subjectLabel;

    public FlashcardCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        topicLabel = getChildNode(TOPIC_FIELD_ID);
        //contentLabel = getChildNode(CONTENT_FIELD_ID);
        difficultyLabel = getChildNode(DIFFICULTY_FIELD_ID);
        deadlineLabel = getChildNode(DEADLINE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        subjectLabel = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTopic() {
        return topicLabel.getText();
    }

    //public String getContent() {
    //return contentLabel.getText();
    //}

    public String getDifficulty() {
        return difficultyLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public List<String> getSubject() {
        return subjectLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code flashcard}.
     */
    public boolean equals(Flashcard flashcard) {
        return getTopic().equals(flashcard.getTopic().fullTopic)
                //&& getContent().equals(flashcard.getContent().value)
                && getDifficulty().equals(flashcard.getDifficulty().value)
                && ImmutableMultiset.copyOf(getSubject()).equals(ImmutableMultiset.copyOf(flashcard.getTags().stream()
                        .map(tag -> tag.subjectName)
                        .collect(Collectors.toList())));
    }
}
