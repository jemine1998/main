package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FlashBook;
import seedu.address.model.ReadOnlyFlashBook;
import seedu.address.model.flashcard.Content;
import seedu.address.model.flashcard.Deadline;
import seedu.address.model.flashcard.Difficulty;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Topic;
import seedu.address.model.tag.SubjectTag;

/**
 * Contains utility methods for populating {@code FlashBook} with sample data.
 */
public class SampleDataUtil {
    public static final Deadline EMPTY_DEADLINE = new Deadline("");
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {

            // list of flashcards in subject Mathematics
            new Flashcard(new Topic("Formula of Pythagoras Theorem"), new Difficulty("1"),
                new Content("a^2 + b^2 = c^2"), EMPTY_DEADLINE,
                getTagSet("Mathematics")),

            // list of flashcards in subject Chemistry
            new Flashcard(new Topic("Define Acid"), new Difficulty("1"),
                new Content("An acid is a compound that dissociates to produce hydrogen ions "
                        + "when it is dissolved in water."), EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("Define Strong Acid"), new Difficulty("1"),
                new Content("A strong acid dissociates or ionises completely into ions. "
                        + "All the acid molecules become ions in water"), EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("Define Weak Acid"), new Difficulty("1"),
                new Content("A weak acid only slightly or partially ionised. "
                        + "Not all the acid molecules become ions in water."), EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("Give an example of a Weak Acid"), new Difficulty("2"),
                new Content("Ethanoic acid is a weak acid. Most of the acid molecules remain unchanged in water "
                        + "as very few molecules are ionised to produce hydrogen ions."), EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("Is Ammonia an Acid"), new Difficulty("2"),
                new Content("No, ammonia is not an acid. It does not produce hydrogen ions when dissolved in water."),
                    EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("Define Basicity of an Acid"), new Difficulty("1"),
                new Content("Basicity of an acid refers to the number of moles of hydrogen ions that can be "
                        + "produced by one mole of acid."), EMPTY_DEADLINE,
                getTagSet("Chemistry")),
            new Flashcard(new Topic("What is the difference between Organic and Inorganic Acids"), new Difficulty("3"),
                new Content("Organic acids are obtained from plants and animals whereas Inorganic acids can be "
                        + "prepared in the laboratory from mineral elements or inorganic matter."), EMPTY_DEADLINE,
                getTagSet("Chemistry")),

            // list of flashcards in subject Physics
            new Flashcard(new Topic("Define Energy of a System"), new Difficulty("1"),
                new Content("Energy of a system is defined as its capacity to do work"), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("What is the Principle of Conservation of Energy"), new Difficulty("2"),
                new Content("The Principle of Conservation of Energy states that energy cannot be created nor "
                        + "destroyed in any process. Total amount of energy of a closed system remains constant. "
                        + "Energy can also be converted or transformed from one form to another."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("List the forms of Energy"), new Difficulty("2"),
                new Content("Potential Energy, Kinetic Energy, Electrical Energy, Thermal Energy, Light Energy and "
                        + "Nuclear Energy."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("Define Kinetic Energy"), new Difficulty("1"),
                new Content("Kinetic Energy is the energy a body possessed by virtue of its motion."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("Define Potential Energy"), new Difficulty("1"),
                new Content("Potential Energy is the stored energy in a system."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("Define Gravitational Potential Energy"), new Difficulty("1"),
                new Content("Gravitational Potential Energy is defined as the amount of work done in order to "
                        + "raise the body to the height h from a reference level."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("Define Friction"), new Difficulty("1"),
                new Content("Friction is the force that resists the motion of one surface relative to another with "
                        + "which it is in contact. It is parallel to the contact surfaces and opposite to the "
                        + "direction of motion or impeding motion."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("What are the Advantages of Friction"), new Difficulty("3"),
                new Content("Friction prevents slipping when walking. Friction is also used in braking pads to "
                        + "slow down cars."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("What are the Disadvantages of Friction"), new Difficulty("3"),
                new Content("Friction reduces speed of motion and causes wear and tear. Energy is also wasted as "
                        + "work done to overcome friction."), EMPTY_DEADLINE,
                getTagSet("Physics")),
            new Flashcard(new Topic("List Methods to Reduce Friction"), new Difficulty("3"),
                new Content("Lubricate the surfaces in contact. Smoothen the surfaces in contact by polishing. "
                        + "Place ball bearings, rollers between surfaces."), EMPTY_DEADLINE,
                getTagSet("Physics")),

            // list of flashcards in subject Geography
            new Flashcard(new Topic("List some Cities that are experiencing Housing Shortage"), new Difficulty("2"),
                new Content("Los Angeles, USA. Beijing, China. Istanbul, Turkey. Cairo, Egypt. Lagos, Nigeria. "
                        + "Mumbai, India."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What are the Reasons for Housing Shortage"), new Difficulty("1"),
                new Content("Limited land supply. Push-pull factors in rural-urban migration. High birth rate. "
                        + "Landuse competition."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What are the Consequences of Housing Shortage"), new Difficulty("2"),
                new Content("Homelessness. Slums and squatter settlements which result in environmental pollution, "
                        + "low level of health due to poor living conditions, and vulnerability."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("List the Strategies that are used to manage Housing Shortage"),
                    new Difficulty("3"),
                new Content("Slum upgrading and provision of public housing."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What are the types of Floods"), new Difficulty("1"),
                new Content("Coastal floods, river floods and flash floods."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What is a Coastal Flood"), new Difficulty("1"),
                new Content("A coastal flood is the flooding along the coast, especially low-lying coastal land "
                        + "and river mouths, caused by storm surge from typhoons or hurricanes or tsunamis."),
                    EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What is a River Flood"), new Difficulty("1"),
                new Content("A river flood happens when rivers overflow its banks due to increase discharge in the "
                        + "river due to snow melt, heavy rainfall or dam failure."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("What is a Flash Flood"), new Difficulty("1"),
                new Content("A flash flood is a quick rise in water level and takes place suddenly. "
                        + "It lasts for a short period of time and can be caused by heavy rainfall or coastal "
                        + "and river floods."), EMPTY_DEADLINE,
                getTagSet("Geography")),
            new Flashcard(new Topic("Why is Singapore more prone to Flooding"), new Difficulty("3"),
                new Content("Singapore is a densely built-up area with a high proportion of concrete surfaces "
                        + "which are impermeable. When it rains heavily, there are little of low levels of "
                        + "infiltration and this leads to high surface run-off. The drains are unable to cope with "
                        + "the high volume of water. Moreover, debris also chokes up the drains and water is "
                        + "unable to flow smoothly. As a result, the drains overflow, resulting in flash floods."),
                    EMPTY_DEADLINE,
                getTagSet("Geography"))
        };
    }

    /* public static SubjectBook[] getSampleSubjects() {
        return new SubjectBook[] {
                new SubjectBook(new SubjectTag("math")),
                new SubjectBook(new SubjectTag("science")),
                new SubjectBook(new SubjectTag("english"))
        };
    }

    public static ReadOnlySubjectBook getSampleSubjectBook() {
        SubjectBook sampleSB = new SubjectBook();
        for (SubjectBook sampleSubject : getSampleSubjects()) {
            sampleSB.addSubject(sampleSubject);
        }
        return sampleSB;
    }*/

    public static ReadOnlyFlashBook getSampleFlashBook() {
        FlashBook sampleAb = new FlashBook();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleAb.addFlashcard(sampleFlashcard);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<SubjectTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(SubjectTag::new)
                .collect(Collectors.toSet());
    }

}
