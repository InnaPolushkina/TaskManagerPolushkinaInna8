package ua.sumdu.j2se.innapolushkina.tasks.controller;

import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Matcher;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.*;

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
    @FXML
    private Button cancel;
    @FXML
    private Button save;

    private Task editTask;


    public EditTask(Task task) {
        editTask = task;
    }
   /* @FXML
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
        int interval = editTask.getRepeatInterval();
        daysTxtEdited.setText(Integer.toString(secondsToIntegerDays(interval)));
        hoursTxtEdited.setText(Integer.toString(secondsToIntegerHours(interval)));
        minutesTxtEdited.setText(Integer.toString(secondsToIntegerMinutes(interval)));


    }*/

    @FXML
    public void initialize() {
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
        int interval = editTask.getRepeatInterval();
        daysTxtEdited.setText(Integer.toString(secondsToIntegerDays(interval)));
        hoursTxtEdited.setText(Integer.toString(secondsToIntegerHours(interval)));
        minutesTxtEdited.setText(Integer.toString(secondsToIntegerMinutes(interval)));

        if ( !editTask.isRepeated() ) {
            Utill.hideNodes(dateEndEdited, dateEndLabelEdited, daysTxtEdited, daysLabelEdited, minutesLabelEdited, minutesTxtEdited, hoursTxtEdited, hoursLabelEdited, endTimeEdited, intervalEditedLabel);
        }

        System.out.println(new StringBuilder(getClass().getName()).append(": initalizing starts"));
        titleEdited.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue == null || "".equals(newValue) || newValue.length() == 0 || "\uFEFF".equals(newValue)){
                    save.setVisible(false);
                } else {
                    save.setVisible(true);
                }
            }
        });

        daysTxtEdited.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    daysTxtEdited.setText(oldValue);
                }
                if(Integer.parseInt(daysTxtEdited.getText()) > 24854){
                    daysTxtEdited.setText(oldValue);
                }
            }
        });
        hoursTxtEdited.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    hoursTxtEdited.setText(oldValue);
                }
                if(Integer.parseInt(hoursTxtEdited.getText()) > 23){
                    hoursTxtEdited.setText(oldValue);
                }
            }
        });
        minutesTxtEdited.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    minutesTxtEdited.setText(oldValue);
                }
                if(Integer.parseInt(minutesTxtEdited.getText()) > 59){
                    minutesTxtEdited.setText(oldValue);
                }
            }
        });

      //  refreshValues();


        dateStartEdited.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if(!repeatEdited.isSelected()){
                    return;
                }
                if(newValue == null){
                    dateStartEdited.setValue(oldValue);
                }
                LocalDateTime newLDT = LocalDateTime.of(newValue, startTimeEdited.getValue());
                LocalDateTime toLDT = LocalDateTime.of(dateEndEdited.getValue(), endTimeEdited.getValue());
                if(!newLDT.isBefore(toLDT)){
                    dateStartEdited.setValue(oldValue);
                }
            }
        });
        startTimeEdited.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if(!repeatEdited.isSelected()){
                    return;
                }
                if(newValue == null){
                    startTimeEdited.setValue(oldValue);
                }
                LocalDateTime newLDT = LocalDateTime.of(dateStartEdited.getValue(),newValue);
                LocalDateTime toLDT = LocalDateTime.of(dateEndEdited.getValue(),endTimeEdited.getValue());
                if(!newLDT.isBefore(toLDT)){
                    startTimeEdited.setValue(oldValue);
                }
            }
        });
        dateEndEdited.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if(!repeatEdited.isSelected()){
                    return;
                }
                if(newValue == null){
                    dateEndEdited.setValue(oldValue);
                }
                LocalDateTime newLDT = LocalDateTime.of(newValue, endTimeEdited.getValue());
                LocalDateTime fromLDT = LocalDateTime.of(dateEndEdited.getValue(), startTimeEdited.getValue());
                if(!newLDT.isAfter(fromLDT)){
                    dateEndEdited.setValue(oldValue);
                }
            }
        });
        endTimeEdited.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if(!repeatEdited.isSelected()){
                    return;
                }
                if(newValue == null){
                    endTimeEdited.setValue(oldValue);
                }
                LocalDateTime newLDT = LocalDateTime.of(dateEndEdited.getValue(),newValue);
                LocalDateTime fromLDT = LocalDateTime.of(dateStartEdited.getValue(),startTimeEdited.getValue());
                if(!newLDT.isAfter(fromLDT)){
                    endTimeEdited.setValue(oldValue);
                }
            }
        });
        System.out.println(new StringBuilder(getClass().getName()).append(": initalizing finished"));
    }

    @FXML
    private void editTask(ActionEvent event) {
        System.out.println(new StringBuilder(getClass().getName()).append(": saving the changes starts"));
        String title = titleEdited.getText();
        Date start = Utill.localDateTimeToDate(LocalDateTime.of(dateStartEdited.getValue(),startTimeEdited.getValue()));
        if(repeatEdited.isSelected()){
            Date end = Utill.localDateTimeToDate(LocalDateTime.of(dateEndEdited.getValue(),endTimeEdited.getValue()));
            int interval = Utill.getIntervalFromStrings(daysTxtEdited.getText(), hoursTxtEdited.getText(), minutesTxtEdited.getText());
            editTask.setTime(start, end, interval);
        }else {
            editTask.setTime(start);
        }
        //editTask.setObservableTime(title);
        editTask.setTitle(title);
        editTask.setActive(activeEdited.isSelected());


        System.out.println(new StringBuilder(getClass().getName()).append(": saving the changes finished"));
        System.out.println(editTask.toString());

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public Task getEditTask() {
        return editTask;
    }
/*
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
    }*/

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void changeCreationMode() {
        if(repeatEdited.isSelected()) {
            dateStartLabelEdited.setText("Date of start");
            Utill.showNodes(dateEndEdited, dateEndLabelEdited, daysTxtEdited, daysLabelEdited, minutesLabelEdited, minutesTxtEdited, hoursTxtEdited, hoursLabelEdited, endTimeEdited, intervalEditedLabel);
        } else {
            dateStartLabelEdited.setText("Date");
            Utill.hideNodes(dateEndEdited, dateEndLabelEdited, daysTxtEdited, daysLabelEdited, minutesLabelEdited, minutesTxtEdited, hoursTxtEdited, hoursLabelEdited, endTimeEdited, intervalEditedLabel);

        }
    }



}
