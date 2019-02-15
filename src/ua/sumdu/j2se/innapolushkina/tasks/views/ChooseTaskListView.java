package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public Button getCreateNewListButton() {
        return createNewListButton;
    }

    public Button getPreviousListButton() {
        return previousListButton;
    }

    public Button getLoadFromFileButton() {
        return loadFromFileButton;
    }

    public String getNameNewFile() {
        return nameNewFile.getText();
    }

    public void setUserMessage(String message) {
        this.userMessage.setText(message);
    }

    public void setNameNewFile(String pleasehoder) {
        //this.nameNewFile = nameNewFile;
        nameNewFile.setPromptText(pleasehoder);
    }
}
