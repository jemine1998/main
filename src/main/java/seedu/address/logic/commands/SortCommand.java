package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.flashcard.TopicContainsDifficultyPredicate;
import seedu.address.model.flashcard.TopicContainsDoublePredicate;
import seedu.address.model.tag.SubjectTag;

/**
 * Finds and lists all flashcards in FlashCards whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all flashcards with selected difficulty "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final TopicContainsDifficultyPredicate predicate;

    private final String[] difficultyName;

    public SortCommand(TopicContainsDifficultyPredicate predicate, String[] difficultyName) {
        this.predicate = predicate;
        this.difficultyName = difficultyName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (model.getSelectedSubject() == null) {
            model.updateFilteredFlashcardList(predicate);
        } else {
            SubjectTag currentSubject = model.getSelectedSubject();
            model.setSelectedSubject(null);
            String[] subjectName = currentSubject.toString().split("\\s+");
            model.updateFilteredFlashcardList(new TopicContainsDoublePredicate(Arrays.asList(subjectName),
                    Arrays.asList(difficultyName)));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW, model.getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && predicate.equals(((SortCommand) other).predicate)); // state check
    }
}
