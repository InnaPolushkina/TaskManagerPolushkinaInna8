package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * class EdiTaskView have methods for for getting/setting element(or its values) from view of edit task
 */
public class EditTaskView {

    @FXML
    private Label dateStartLabelEdited;
    @FXML
    private Label intervalEditedLabel;
    @FXML
    private Label dateEndLabelEdited;
    @FXML
    private CheckBox activeEdited;
    @FXML
    private CheckBox repeatEdited;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField titleEdited;
    @FXML
    private DatePicker dateStartEdited;
    @FXML
    private DatePicker dateEndEdited;
    @FXML
    private JFXTimePicker startTimeEdited;
    @FXML
    private JFXTimePicker endTimeEdited;
    @FXML
    private TextField daysTxtEdited;
    @FXML
    private TextField hoursTxtEdited;
    @FXML
    private TextField minutesTxtEdited;
    @FXML
    private Label daysLabelEdited;
    @FXML
    private Label hoursLabelEdited;
    @FXML
    private Label minutesLabelEdited;
    @FXML
    private Label errorUserMessage;

    /**
     * the method initialize view for edit task
     */
    public void initialize() {
        chengeModel();
    }

    /**
     * the method set boolean values about active edited task
     * @param activeEdited
     */
    public void setActiveEdited(boolean activeEdited) {
        this.activeEdited.setSelected(activeEdited);
    }

    /**
     * the method set values into CheckBox for repeated task
     * @param repeatEdited have boolean values
     */
    public void setRepeatEdited(boolean repeatEdited) {
        this.repeatEdited.setSelected(repeatEdited);
    }
    /**
     * the method set value into TextField for title of edited task
     * @param title
     */
    public void setTitleEdited(String title) {
        titleEdited.setText(title);
    }
    /**
     * the method set values into DatePicker date
     * @param dateStartEdited
     */
    public void setDateStartEdited(LocalDate dateStartEdited) {
        this.dateStartEdited.setValue(dateStartEdited);
    }
    /**
     * the method set values into DatePicker dateEnd
     * @param dateEndEdited
     */
    public void setDateEndEdited(LocalDate dateEndEdited) {
        this.dateEndEdited.setValue(dateEndEdited);
    }
    /**
     * the method set values into JFXTimePicker startTime
     * @param startTimeEdited
     */
    public void setStartTimeEdited(LocalTime startTimeEdited) {
        this.startTimeEdited.setValue(startTimeEdited);
    }
    /**
     * the method set values into JFXTimePicker endTime
     * @param endTimeEdited
     */
    public void setEndTimeEdited(LocalTime endTimeEdited) {
        this.endTimeEdited.setValue(endTimeEdited);
    }
    /**
     * the method set values into text field with data of days
     * @param daysTxtEdited have string with values days
     */
    public void setDaysTxtEdited(String daysTxtEdited) {
        this.daysTxtEdited.setText(daysTxtEdited);
    }
    /**
     * the method set values into text field with data of hours
     * @param hoursTxtEdited have string with values hours
     */
    public void setHoursTxtEdited(String hoursTxtEdited) {
        this.hoursTxtEdited.setText(hoursTxtEdited);
    }
    /**
     * the method set values into text field with data of minutes
     * @param minutesTxtEdited have string with values minutes
     */
    public void setMinutesTxtEdited(String minutesTxtEdited) {
        this.minutesTxtEdited.setText(minutesTxtEdited);
    }

    /**
     * the method get button for save changes selected task
     * @return button
     */
    public Button getSaveButton() {
        return saveButton;
    }

    /**
     * the method get button for cancel changes
     * @return button
     */
    public Button getCancelButton() {
        return cancelButton;
    }

    /**
     * @return value from checkbox for active
     */
    public boolean getActiveEdited() {
        return activeEdited.isSelected();
    }
    /**
     * @return  values of checkbox for repeat
     */
    public boolean getRepeatEdited() {
        return repeatEdited.isSelected();
    }
    /**
     * the method get value from TextField for title of task
     * @return text
     */
    public String getTitleEdited() {
        return titleEdited.getText();
    }
    /**
     * @return value from DatePicker for date of start
     */
    public LocalDate getDateStartEdited() {
        return dateStartEdited.getValue();
    }
    /**
     * @return value from DatePicker for date of end
     */
    public LocalDate getDateEndEdited() {
        return dateEndEdited.getValue();
    }
    /**
     * @return value from JFXTimePicker for start time
     */
    public LocalTime getStartTimeEdited() {
        return startTimeEdited.getValue();
    }
    /**
     * @return value from JFXTimePicker for end time
     */
    public LocalTime getEndTimeEdited() {
        return endTimeEdited.getValue();
    }
    /**
     * the method get string values from textField for days
     * @return text
     */
    public String getDaysTxtEdited() {
        return daysTxtEdited.getText();
    }
    /**
     * the method get string values from textField for hours
     * @return text
     */
    public String getHoursTxtEdited() {
        return hoursTxtEdited.getText();
    }

    /**
     * the method get string values from textField for minutes
     * @return text
     */
    public String getMinutesTxtEdited() {
        return minutesTxtEdited.getText();
    }

    /**
     * the method for set string message into label errorUserMessage
     * @param errorMessage have string with message, if user enter invalid data
     */
    public void setErrorUserMessage(String errorMessage) {
        this.errorUserMessage.setText(errorMessage);
    }

    /**
     * the method get CheckBox for repeat edited task
     * @return
     */
    public CheckBox getRepeatCheckBox() {
        return repeatEdited;
    }
    /**
     * the method change Model, when user add task
     * view of Model depends on variant task: repeated/ no repeated
     */
    public void chengeModel() {
        if(repeatEdited.isSelected()) {
            dateStartLabelEdited.setText("Date of start");
            Utill.showNodes(dateEndEdited, dateEndLabelEdited, daysTxtEdited, daysLabelEdited, minutesLabelEdited, minutesTxtEdited, hoursTxtEdited, hoursLabelEdited, endTimeEdited, intervalEditedLabel);
        }
        else {
            dateStartLabelEdited.setText("Date");
            Utill.hideNodes(dateEndEdited, dateEndLabelEdited, daysTxtEdited, daysLabelEdited, minutesLabelEdited, minutesTxtEdited, hoursTxtEdited, hoursLabelEdited, endTimeEdited, intervalEditedLabel);
        }
    }
}
