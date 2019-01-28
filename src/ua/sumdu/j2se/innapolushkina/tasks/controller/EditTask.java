package ua.sumdu.j2se.innapolushkina.tasks.controller;

import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.dateToLocalDateTime;
import static ua.sumdu.j2se.innapolushkina.tasks.Utill.dateToLocalTime;

public class EditTask {
    @FXML
    private Label dateStartLabelEdited;
    @FXML
    private Label intervalEditedLabel;
    @FXML
    private Label dateEndLabelEdited;
    @FXML
    private CheckBox repeatEdited;
    @FXML
    private CheckBox activeEdited;
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

    private Task editTask;

    public EditTask(Task task) {
        editTask = task;
    }
    @FXML
    private void initialize(){
        titleEdited.setText(editTask.getTitle());
        activeEdited.setSelected(editTask.isActive());
        repeatEdited.setSelected(editTask.isRepeated());
        LocalDate start = dateToLocalDateTime(editTask.getStartTime());
        dateStartEdited.setValue(start);
        LocalDate end = dateToLocalDateTime(editTask.getEndTime());
        dateEndEdited.setValue(end);
        startTimeEdited.setValue(dateToLocalTime(editTask.getStartTime()));
        endTimeEdited.setValue(dateToLocalTime(editTask.getEndTime()));
        //daysTxtEdited.setText();



    }

    public Task getEditTask() {
        return editTask;
    }

    public void editTask(ActionEvent event) {
        Task taskEdited;
        String details = titleEdited.getText();
        editTask.setTitle(titleEdited.getText());
        System.out.println(editTask.toString());

       if(details == null) {
            titleEdited.setPromptText("Please, enter the task");
            return;
        }
        if("".equals(details) || details == null) {
            System.err.println(new StringBuilder("Try to edit an empty task ").append(getClass()));
            return;
        }
        Date start =  java.sql.Date.valueOf(dateStartEdited.getValue());
        start.setTime(start.getTime() + startTimeEdited.getValue().getHour() * 3600000 + startTimeEdited.getValue().getMinute() * 60000);
        if(repeatEdited.isSelected()) {
            Date end =  java.sql.Date.valueOf(dateEndEdited.getValue());
            end.setTime(end.getTime() + endTimeEdited.getValue().getHour() * 3600000 + endTimeEdited.getValue().getMinute() * 60000);
            taskEdited = new Task(details, start, end,
                    Utill.getIntervalFromStrings(daysTxtEdited.getText(), hoursTxtEdited.getText(), minutesTxtEdited.getText()));
        }else {
            taskEdited = new Task(details, start);
        }
        taskEdited.setActive(activeEdited.isSelected());
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent event) {

    }

}
