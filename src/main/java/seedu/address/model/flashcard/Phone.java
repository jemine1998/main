package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Subject's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevel(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Difficulty should be either 1 (easy), 2 (medium) or 3 (difficult)!";
    public static final String VALIDATION_REGEX = "[1-3]";
    public final String value;
    public final Integer integerValue;

    /**
     * Constructs a {@code Phone}.
     *
     * @param level A valid phone number.
     */
    public Phone(String level) {
        requireNonNull(level);
        checkArgument(isValidLevel(level), MESSAGE_CONSTRAINTS);
        value = level;
        integerValue = Integer.valueOf(level);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidLevel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
