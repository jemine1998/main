package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SUBJECT;

import seedu.address.logic.commands.DeleteSubjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.SubjectTag;

/**
 * Parses input arguments and creates a new DeleteSubjectCommand object
 */
public class DeleteSubjectCommandParser implements Parser<DeleteSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSubjectCommand
     * and returns an DeleteSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSubjectCommand parse(String args) throws ParseException {
        try {
            SubjectTag subjectTag = ParserUtil.parseSubject(args);
            String trimmedArgs = args.trim();
            String[] nameKeywords = trimmedArgs.split("\\s+");
            return new DeleteSubjectCommand(subjectTag, nameKeywords);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_SUBJECT, DeleteSubjectCommand.MESSAGE_USAGE), pe);
        }
    }

}
