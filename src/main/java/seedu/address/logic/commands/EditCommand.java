package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUBJECTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.subject.*;
import seedu.address.model.subject.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing subject in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the subject identified "
            + "by the index number used in the displayed subject list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LEVEL + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LEVEL + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_SUBJECT_SUCCESS = "Edited Subject: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SUBJECT = "This subject already exists in the address book.";

    private final Index index;
    private final EditSubjectDescriptor editSubjectDescriptor;

    /**
     * @param index of the subject in the filtered subject list to edit
     * @param editSubjectDescriptor details to edit the subject with
     */
    public EditCommand(Index index, EditSubjectDescriptor editSubjectDescriptor) {
        requireNonNull(index);
        requireNonNull(editSubjectDescriptor);

        this.index = index;
        this.editSubjectDescriptor = new EditSubjectDescriptor(editSubjectDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Subject> lastShownList = model.getFilteredSubjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);
        }

        Subject subjectToEdit = lastShownList.get(index.getZeroBased());
        Subject editedSubject = createEditedSubject(subjectToEdit, editSubjectDescriptor);

        if (!subjectToEdit.isSameSubject(editedSubject) && model.hasSubject(editedSubject)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUBJECT);
        }

        model.setSubject(subjectToEdit, editedSubject);
        model.updateFilteredSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_SUBJECT_SUCCESS, editedSubject));
    }

    /**
     * Creates and returns a {@code Subject} with the details of {@code subjectToEdit}
     * edited with {@code editSubjectDescriptor}.
     */
    private static Subject createEditedSubject(Subject subjectToEdit, EditSubjectDescriptor editSubjectDescriptor) {
        assert subjectToEdit != null;

        Name updatedName = editSubjectDescriptor.getName().orElse(subjectToEdit.getName());
        Phone updatedPhone = editSubjectDescriptor.getPhone().orElse(subjectToEdit.getPhone());
        Email updatedEmail = editSubjectDescriptor.getEmail().orElse(subjectToEdit.getEmail());
        Address updatedAddress = editSubjectDescriptor.getAddress().orElse(subjectToEdit.getAddress());
        Deadline updatedDeadline = subjectToEdit.getDeadline(); //edit command does not allow editing deadlines
        Set<Tag> updatedTags = editSubjectDescriptor.getTags().orElse(subjectToEdit.getTags());

        return new Subject(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedDeadline, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editSubjectDescriptor.equals(e.editSubjectDescriptor);
    }

    /**
     * Stores the details to edit the subject with. Each non-empty field value will replace the
     * corresponding field value of the subject.
     */
    public static class EditSubjectDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditSubjectDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSubjectDescriptor(EditSubjectDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSubjectDescriptor)) {
                return false;
            }

            // state check
            EditSubjectDescriptor e = (EditSubjectDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
