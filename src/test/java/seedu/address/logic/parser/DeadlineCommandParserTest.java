package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.model.flashcard.Deadline;

public class DeadlineCommandParserTest {
    private DeadlineCommandParser parser = new DeadlineCommandParser();
    private final Deadline nonEmptyDeadline = new Deadline("31 December 2099");

    @Test
    public void parse_indexSpecified_success() {
        //have deadline
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DEADLINE + nonEmptyDeadline;
        DeadlineCommand expectedCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD, nonEmptyDeadline);
        assertParseSuccess(parser, userInput, expectedCommand);

        //no deadline
        userInput = targetIndex.getOneBased() + " " + PREFIX_DEADLINE;
        expectedCommand = new DeadlineCommand(INDEX_FIRST_FLASHCARD, new Deadline(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE);

        //no parameters
        assertParseFailure(parser, DeadlineCommand.COMMAND_WORD, expectedMessage);

        //no index
        assertParseFailure(parser, DeadlineCommand.COMMAND_WORD + " " + nonEmptyDeadline, expectedMessage);
    }
}
