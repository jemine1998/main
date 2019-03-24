package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidLevel(null));

        // invalid phone numbers
        assertFalse(Phone.isValidLevel("")); // empty string
        assertFalse(Phone.isValidLevel(" ")); // spaces only
        assertFalse(Phone.isValidLevel("91")); // less than 3 numbers
        assertFalse(Phone.isValidLevel("phone")); // non-numeric
        assertFalse(Phone.isValidLevel("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidLevel("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidLevel("1")); // exactly 3 numbers
        assertTrue(Phone.isValidLevel("2"));
        assertTrue(Phone.isValidLevel("3")); // long phone numbers
    }
}
