package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TopicTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Topic(null));
    }

    @Test
    public void constructor_invalidTopic_throwsIllegalArgumentException() {
        String invalidTopic = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Topic(invalidTopic));
    }

    @Test
    public void isValidTopic() {
        // null topic
        Assert.assertThrows(NullPointerException.class, () -> Topic.isValidTopic(null));

        // invalid topic
        assertFalse(Topic.isValidTopic("")); // empty string
        assertFalse(Topic.isValidTopic(" ")); // spaces only
        assertFalse(Topic.isValidTopic("^")); // only non-alphanumeric characters
        assertFalse(Topic.isValidTopic("chinese*")); // contains non-alphanumeric characters

        // valid topic
        assertTrue(Topic.isValidTopic("Chinese language")); // alphabets only
        assertTrue(Topic.isValidTopic("22")); // numbers only
        assertTrue(Topic.isValidTopic("chapter 2")); // alphanumeric characters
        assertTrue(Topic.isValidTopic("MATH")); // with capital letters
        assertTrue(Topic.isValidTopic("Mathematics chapter 3 about area calculation")); // long topics
    }
}
