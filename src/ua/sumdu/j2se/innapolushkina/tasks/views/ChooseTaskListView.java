package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * class ChooseTasksListView have methods for getting/setting element(or its values) from/to view of chooser files at start work of app
 */
public class ChooseTaskListView {

    @FXML
    private Button createNewListButton;
    @FXML
    private Button previousListButton;
    @FXML
    private Button loadFromFileButton;
    @FXML
    private TextField nameNewFile;
    @FXML
    private Label userMessage;

    /**
     * this method use in controller for adding action for button
     * @return button create new file
     */
    public Button getCreateNewListButton() {
        return createNewListButton;
    }

    /**
     * this method use in controller for adding action for button
     * @return button for load tasks list from previous file
     */
    public Button getPreviousListButton() {
        return previousListButton;
    }

    /**
     * this method use in controller for adding action for button
     * @return button for load tasks list from any text file
     */
    public Button getLoadFromFileButton() {
        return loadFromFileButton;
    }

    /**
     * the method get name of new file
     * @return string with new of new file
     */
    public String getNameNewFile() {
        return nameNewFile.getText();
    }

    /**
     * the method set message for user
     * @param message have string with message
     */
    public void setUserMessage(String message) {
        this.userMessage.setText(message);
    }

    /**
     * the method set message into text field for name of new file
     * @param pleasehoder have string with message
     */
    public void setNameNewFile(String pleasehoder) {
        nameNewFile.setPromptText(pleasehoder);
    }
}
