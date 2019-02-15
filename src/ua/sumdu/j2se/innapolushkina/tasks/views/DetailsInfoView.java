package ua.sumdu.j2se.innapolushkina.tasks.views;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DetailsInfoView {
    @FXML
    private Label dateEndDatailsLabel;
    @FXML
    private Label timeEndDatailsLabel;
    @FXML
    private Label nameTaskDetails;
    @FXML
    private Label activeDetails;
    @FXML
    private Label dateDateils;
    @FXML
    private Label timeDatails;
    @FXML
    private Label repaetDetails;
    @FXML
    private Label dateEndDetails;
    @FXML
    private Label timeEndDetails;
    @FXML
    private Button okCloseWindow;
    @FXML
    private Button editTask;

    /**
     * getter for label dateEndDetails
     * @return label
     * this method can use in controller for hide nodes if task is not repeated
     * */
    public Label getDateEndDetails() {
        return dateEndDetails;
    }
    /**
     * getter for label timeEndDetails
     * @return label
     * this method can use in controller for hide nodes if task is not repeated
     */
    public Label getTimeEndDetails() {
        return timeEndDetails;
    }
    /**
     * getter for button okCloseWindow
     * @return Button
     * this method use in controller for adding action for button
     */
    public Button getOkCloseWindow() {
        return okCloseWindow;
    }
    /**
     * getter for button okCloseWindow
     * @return Button
     * this method use in controller for editing action for button
     */
    public Button getEditTask() {
        return editTask;
    }

    /**
     * this method for setting value into label nameTaskDetails
     * @param nameTask have string with name selected task
     */
    public void setNameTaskDetails(String nameTask) {
        this.nameTaskDetails.setText(nameTask);
    }

    /**
     *  this method for setting value into label activeDetails
     * @param active have string with active selected task
     */
    public void setActiveDetails(String active) {
        this.activeDetails.setText(active);
    }

    /**
     * this method for setting value into label dateDetails
     * @param date have string with date selected task, if task is repeated or date of start, if task isn`t repeated
     */
    public void setDateDateils(String date) {
        this.dateDateils.setText(date);
    }

    /**
     * this method for setting value into label timeDetails
     * @param time have string with time selected task, if task is repeated or time of start, if task isn`t repeated
     */
    public void setTimeDatails(String time) {
        this.timeDatails.setText(time);
    }

    /**
     * this method for setting value into label setRepeatDetails
     * @param repaet have string with repeat selected task
     */
    public void setRepaetDetails(String repaet) {
        this.repaetDetails.setText(repaet);
    }

    /**
     * this method for setting value into label dateEndDetails
     * @param dateEnd have string with date of end selected task, if task is repeated
     */
    public void setDateEndDetails(String dateEnd) {
        this.dateEndDetails.setText(dateEnd);
    }

    /**
     * this method for setting value into label timeEndDetails
     * @param timeEnd have string with time of end selected task, if task is repeated
     */
    public void setTimeEndDetails(String timeEnd) {
        this.timeEndDetails.setText(timeEnd);
    }
}
