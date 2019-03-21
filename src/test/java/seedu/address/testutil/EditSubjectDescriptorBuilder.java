package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditSubjectDescriptor;
import seedu.address.model.subject.*;
import seedu.address.model.subject.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditSubjectDescriptor objects.
 */
public class EditSubjectDescriptorBuilder {

    private EditSubjectDescriptor descriptor;

    public EditSubjectDescriptorBuilder() {
        descriptor = new EditSubjectDescriptor();
    }

    public EditSubjectDescriptorBuilder(EditCommand.EditSubjectDescriptor descriptor) {
        this.descriptor = new EditSubjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSubjectDescriptor} with fields containing {@code subject}'s details
     */
    public EditSubjectDescriptorBuilder(Subject subject) {
        descriptor = new EditSubjectDescriptor();
        descriptor.setName(subject.getName());
        descriptor.setPhone(subject.getPhone());
        descriptor.setEmail(subject.getEmail());
        descriptor.setAddress(subject.getAddress());
        descriptor.setTags(subject.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditSubjectDescriptor} that we are building.
     */
    public EditSubjectDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditSubjectDescriptor} that we are building.
     */
    public EditSubjectDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditSubjectDescriptor} that we are building.
     */
    public EditSubjectDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditSubjectDescriptor} that we are building.
     */
    public EditSubjectDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditSubjectDescriptor}
     * that we are building.
     */
    public EditSubjectDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditSubjectDescriptor build() {
        return descriptor;
    }
}
