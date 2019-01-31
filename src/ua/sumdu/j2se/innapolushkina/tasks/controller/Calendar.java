package ua.sumdu.j2se.innapolushkina.tasks.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.model.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.localDateTimeToDate;

public class Calendar {
    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button search;

    @FXML
    private Button close;

    @FXML
    private TableView listTasksCalendar;

    @FXML
    private TableColumn<Task, String> nameTaskCalendar;

    @FXML
    private TableColumn<Task, Date> dateTaskCalendar;

    private List<Task> linkedTaskListCalendar;
    private ObservableList<Task> observableListCalendar;

    public Calendar(List<Task> linkedTaskList) {
        linkedTaskListCalendar = linkedTaskList;
    }

    @FXML
    private void initialize(){
        nameTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task,String>("title"));
        dateTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task,Date>("time"));

        observableListCalendar = FXCollections.observableArrayList(linkedTaskListCalendar);
        listTasksCalendar.setItems(observableListCalendar);
    }

    public void search(){
        Date start =  java.sql.Date.valueOf(startDate.getValue());
        Date end = java.sql.Date.valueOf(endDate.getValue());
        ArrayList<Task> arrayList = new ArrayList<>();
        for(Task task:Tasks.incoming(observableListCalendar,start,end) ){
            arrayList.add(task);
        }
        listTasksCalendar.setItems(FXCollections.observableArrayList(/*(List<Task>)*/arrayList));
    }

    public void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
