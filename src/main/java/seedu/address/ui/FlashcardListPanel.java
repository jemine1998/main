package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.SubjectTag;

/**
 * Panel containing the list of persons.
 */
public class FlashcardListPanel extends UiPart<Region> {
    private static final String FXML = "FlashcardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private ListView<Flashcard> flashcardListView;

    private FilteredList<Flashcard> filteredList;

    private ObservableList<SubjectTag> subjectListView;

    private ObservableValue<SubjectTag> s;
    private ObservableValue<Flashcard> f;
    private ObservableList<Flashcard> list = FXCollections.observableArrayList();
    private ObservableList<Flashcard> newFlashcardList = FXCollections.observableArrayList();


    public FlashcardListPanel(ObservableList<Flashcard> flashcardList,
                              ObservableValue<SubjectTag> selectedSubject,
                              ObservableValue<Flashcard> selectedFlashcard,
                              Consumer<Flashcard> onSelectedFlashcardChange) {
        super(FXML);
        flashcardListView.setItems(flashcardList);
        flashcardListView.setCellFactory(listView -> new FlashcardListViewCell());
        flashcardListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in flashcard list panel changed to : '" + newValue + "'");
            onSelectedFlashcardChange.accept(newValue);
        });

        selectedSubject.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                flashcardListView.setItems(flashcardList);
            } else {
                this.newFlashcardList.clear();
                this.newFlashcardList = updateFlashcardList(newValue, flashcardList);
                flashcardListView.setItems(newFlashcardList);
                flashcardListView.setCellFactory(listView -> new FlashcardListViewCell());
            }

        });


        selectedFlashcard.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected flashcard changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(flashcardListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                flashcardListView.getSelectionModel().clearSelection();
            } else {
                int index = flashcardListView.getItems().indexOf(newValue);
                flashcardListView.scrollTo(index);
                flashcardListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class FlashcardListViewCell extends ListCell<Flashcard> {
        @Override
        protected void updateItem(Flashcard flashcard, boolean empty) {
            super.updateItem(flashcard, empty);

            if (empty || flashcard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FlashcardCard(flashcard, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Updates list of flashcards to be displayed in FlashcardListPanel based on selected subject
     */
    private ObservableList<Flashcard> updateFlashcardList(SubjectTag subject,
                                                          ObservableList<Flashcard> flashcardList) {
        for (Flashcard f : flashcardList) {
            if (subject.equals(f.getSubject())) {
                list.add(f);
            }
        }
        return list;
    }


}
