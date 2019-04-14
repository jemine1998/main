package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashBook;
import static seedu.address.testutil.TypicalSubjects.getTypicalSubjectBook;

import org.junit.Before;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.SubjectTag;
import seedu.address.testutil.FlashcardBuilder;

/**
 *Contains integration tests (interaction with the Model) for {@code AddCommand}.
*/
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSubjectBook(), getTypicalFlashBook(), new UserPrefs());
    }

    @Test
    public void execute_newFlashcard_success() {
        Flashcard validFlashcard = new FlashcardBuilder().build();

        Model expectedModel = new ModelManager(model.getSubjectBook(), model.getFlashBook(), new UserPrefs());
        expectedModel.addFlashcard(validFlashcard);
        expectedModel.addSubject(validFlashcard.getSubject());
        expectedModel.commitFlashBook();
        expectedModel.setSelectedSubject(new SubjectTag("English"));
        ObservableList<Flashcard> f = expectedModel.getUpdatedFlashcardList();
        model.setSelectedSubject(new SubjectTag("English"));
        f = model.getUpdatedFlashcardList();
        expectedModel.setSelectedSubject(validFlashcard.getSubject());
        expectedModel.setSelectedFlashcard(validFlashcard);

        assertCommandSuccess(new AddCommand(validFlashcard), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validFlashcard), expectedModel);
    }


    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard flashcardInList = model.getFlashBook().getFlashcardList().get(0);
        assertCommandFailure(new AddCommand(flashcardInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

}
