package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.flashcard.Flashcard;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withFlashcard("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withFlashcard(Flashcard flashcard) {
        addressBook.addFlashcard(flashcard);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
