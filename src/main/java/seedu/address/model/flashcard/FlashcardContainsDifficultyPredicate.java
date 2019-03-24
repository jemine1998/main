package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Flashcard}'s {@code Name} matches any of the keywords given.
 */
public class FlashcardContainsDifficultyPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public FlashcardContainsDifficultyPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(flashcard.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardContainsDifficultyPredicate // instanceof handles nulls
                && keywords.equals(((FlashcardContainsDifficultyPredicate) other).keywords)); // state check
    }

}
