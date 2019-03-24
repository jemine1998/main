package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashcards;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysFlashcard;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.FlashcardCardHandle;
import guitests.guihandles.FlashcardListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Address;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Email;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Name;
import seedu.address.model.flashcard.Phone;


public class SubjectListPanelTest extends GuiUnitTest {
    private static final ObservableList<Flashcard> TYPICAL_SUBJECTS =
            FXCollections.observableList(getTypicalFlashcards());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Flashcard> selectedSubject = new SimpleObjectProperty<>();
    private FlashcardListPanelHandle subjectListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_SUBJECTS);

        for (int i = 0; i < TYPICAL_SUBJECTS.size(); i++) {
            subjectListPanelHandle.navigateToCard(TYPICAL_SUBJECTS.get(i));
            Flashcard expectedSubject = TYPICAL_SUBJECTS.get(i);
            FlashcardCardHandle actualCard = subjectListPanelHandle.getFlashcardCardHandle(i);

            assertCardDisplaysFlashcard(expectedSubject, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedSubjectChanged_selectionChanges() {
        initUi(TYPICAL_SUBJECTS);
        Flashcard secondSubject = TYPICAL_SUBJECTS.get(INDEX_SECOND_FLASHCARD.getZeroBased());
        guiRobot.interact(() -> selectedSubject.set(secondSubject));
        guiRobot.pauseForHuman();

        FlashcardCardHandle expectedSubject = subjectListPanelHandle
                .getFlashcardCardHandle(INDEX_SECOND_FLASHCARD.getZeroBased());
        FlashcardCardHandle selectedSubject = subjectListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedSubject, selectedSubject);
    }

    /**
     * Verifies that creating and deleting large number of subjects in {@code SubjectListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Flashcard> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of subject cards exceeded time limit");
    }

    /**
     * Returns a list of subjects containing {@code subjectCount} subjects that is used to populate the
     * {@code SubjectListPanel}.
     */
    private ObservableList<Flashcard> createBackingList(int subjectCount) {
        ObservableList<Flashcard> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < subjectCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("2");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            Deadline deadline = new Deadline("a");
            Flashcard subject = new Flashcard(name, phone, email, address, deadline, Collections.emptySet());
            backingList.add(subject);
        }
        return backingList;
    }

    /**
     * Initializes {@code subjectListPanelHandle} with a {@code SubjectListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code SubjectListPanel}.
     */
    private void initUi(ObservableList<Flashcard> backingList) {
        FlashcardListPanel subjectListPanel =
                new FlashcardListPanel(backingList, selectedSubject, selectedSubject::set);
        uiPartRule.setUiPart(subjectListPanel);

        subjectListPanelHandle = new FlashcardListPanelHandle(getChildNode(subjectListPanel.getRoot(),
                FlashcardListPanelHandle.FLASHCARD_LIST_VIEW_ID));
    }
}
