package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.TopicContainsSubjectPredicate;
import seedu.address.model.tag.SubjectTag;

/**
 * Deletes a selected Subject from the FlashBook.
 */
public class DeleteSubjectCommand extends Command {

    public static final String COMMAND_WORD = "deleteSubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all the flashcards with the identified subject.\n"
            + "Parameters: Subject (must be already in subject list panel)\n"
            + "Example: " + COMMAND_WORD + " science";

    public static final String MESSAGE_DELETE_SUBJECT_SUCCESS = "Deleted Subject: %1$s";

    private static SubjectTag targetSubject;

    private static SubjectTag deleteSubject = null;

    private static TopicContainsSubjectPredicate predicate;

    private String[] keywords;



    public DeleteSubjectCommand(SubjectTag targetSubject, String[] keywords) {
        this.targetSubject = targetSubject;
        this.keywords = keywords;
        deleteSubject = targetSubject;
    }

    public static TopicContainsSubjectPredicate getPredicate() {
        return predicate;
    }

    public static void listCommandCalled() {
        deleteSubject = null;
    }

    public static SubjectTag getDeleteSubject() {
        return deleteSubject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<SubjectTag> filteredSubjectBook = model.getFilteredSubjectList();
        predicate = new TopicContainsSubjectPredicate(Arrays.asList(keywords));

        if (!filteredSubjectBook.contains(targetSubject)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT);
        }
        model.deleteSubject(targetSubject);


        return new CommandResult(String.format(MESSAGE_DELETE_SUBJECT_SUCCESS, targetSubject));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSubjectCommand // instanceof handles nulls
                && targetSubject.equals(((DeleteSubjectCommand) other).targetSubject)); // state check
    }
}

