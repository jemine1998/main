package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.subject.SubjectBook;
import seedu.address.model.tag.SubjectTag;

/**
 * A utility class containing a list of {@code SubjectTag} objects to be used in tests.
 */
public class TypicalSubjects {

    public static final SubjectTag ENGLISH = new SubjectTagBuilder().withTags("english").build();
    public static final SubjectTag CHINESE = new SubjectTagBuilder().withTags("chinese").build();
    public static final SubjectTag MATH = new SubjectTagBuilder().withTags("math").build();
    public static final SubjectTag CHEMISTRY = new SubjectTagBuilder().withTags("chemistry").build();
    public static final SubjectTag PHYSICS = new SubjectTagBuilder().withTags("physics").build();
    public static final SubjectTag GEOGRAPHY = new SubjectTagBuilder().withTags("geography").build();
    public static final SubjectTag HISTORY = new SubjectTagBuilder().withTags("history").build();


    private TypicalSubjects() {} // prevents instantiation

    /**
     * Returns an {@code FlashBook} with all the typical subjects.
     */
    public static SubjectBook getTypicalSubjectBook() {
        SubjectBook sb = new SubjectBook();
        for (SubjectTag subjectTag : getTypicalSubjects()) {
            sb.addSubject(subjectTag);
        }
        return sb;
    }

    public static List<SubjectTag> getTypicalSubjects() {
        return new ArrayList<>(Arrays.asList(ENGLISH, CHINESE, MATH, CHEMISTRY, PHYSICS, GEOGRAPHY, HISTORY));
    }
}
