package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUBJECT;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjects;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSubject;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.SubjectCardHandle;
import guitests.guihandles.SubjectListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.subject.*;
import seedu.address.model.subject.Phone;

public class SubjectListPanelTest extends GuiUnitTest {
    private static final ObservableList<Subject> TYPICAL_SUBJECTS =
            FXCollections.observableList(getTypicalSubjects());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<>();
    private SubjectListPanelHandle subjectListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_SUBJECTS);

        for (int i = 0; i < TYPICAL_SUBJECTS.size(); i++) {
            subjectListPanelHandle.navigateToCard(TYPICAL_SUBJECTS.get(i));
            Subject expectedSubject = TYPICAL_SUBJECTS.get(i);
            SubjectCardHandle actualCard = subjectListPanelHandle.getSubjectCardHandle(i);

            assertCardDisplaysSubject(expectedSubject, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedSubjectChanged_selectionChanges() {
        initUi(TYPICAL_SUBJECTS);
        Subject secondSubject = TYPICAL_SUBJECTS.get(INDEX_SECOND_SUBJECT.getZeroBased());
        guiRobot.interact(() -> selectedSubject.set(secondSubject));
        guiRobot.pauseForHuman();

        SubjectCardHandle expectedSubject = subjectListPanelHandle
                .getSubjectCardHandle(INDEX_SECOND_SUBJECT.getZeroBased());
        SubjectCardHandle selectedSubject = subjectListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedSubject, selectedSubject);
    }

    /**
     * Verifies that creating and deleting large number of subjects in {@code SubjectListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Subject> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of subject cards exceeded time limit");
    }

    /**
     * Returns a list of subjects containing {@code subjectCount} subjects that is used to populate the
     * {@code SubjectListPanel}.
     */
    private ObservableList<Subject> createBackingList(int subjectCount) {
        ObservableList<Subject> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < subjectCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            Deadline deadline = new Deadline("a");
            Subject subject = new Subject(name, phone, email, address, deadline, Collections.emptySet());
            backingList.add(subject);
        }
        return backingList;
    }

    /**
     * Initializes {@code subjectListPanelHandle} with a {@code SubjectListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code SubjectListPanel}.
     */
    private void initUi(ObservableList<Subject> backingList) {
        SubjectListPanel subjectListPanel =
                new SubjectListPanel(backingList, selectedSubject, selectedSubject::set);
        uiPartRule.setUiPart(subjectListPanel);

        subjectListPanelHandle = new SubjectListPanelHandle(getChildNode(subjectListPanel.getRoot(),
                SubjectListPanelHandle.SUBJECT_LIST_VIEW_ID));
    }
}
