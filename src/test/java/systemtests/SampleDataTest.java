/*
package systemtests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.model.FlashBook;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends FlashBookSystemTest {
    */
/**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     *//*

    @Override
    protected FlashBook getInitialData() {
        return null;
    }

    */
/**
     * Returns a non-existent file location to force test app to load sample data.
     *//*

    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    */
/**
     * Deletes the file at {@code filePath} if it exists.
     *//*

    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    /**
     *  @Test
     *     public void flashBook_dataFileDoesNotExist_loadSampleData() {
     *         Flashcard[] expectedList = SampleDataUtil.getSampleFlashcards();
     *         assertListMatching(getFlashcardListPanel(), expectedList);
     *     }
     */

}
*/
