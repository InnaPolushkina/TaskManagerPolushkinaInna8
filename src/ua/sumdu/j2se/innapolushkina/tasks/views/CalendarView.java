package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;

import java.time.LocalDate;
import java.util.List;

public class CalendarView {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button searchDates;
    @FXML
    private Button closeCalendar;
    @FXML
    private Label errorUserMessage;
    @FXML
    private Label userMessage;
    @FXML
    private TableView listTasksCalendar;
    @FXML
    private TableColumn<Task, String> nameTaskCalendar;
    @FXML
    private TableColumn<Task, String> dateTaskCalendar;
    private ObservableList<Task> observableList;

    public LocalDate getStartDate() {
        return startDate.getValue();
    }

    public LocalDate getEndDate() {
        return endDate.getValue();
    }

    public Button getSearchDates() {
        return searchDates;
    }

    public void setErrorUserMessage(String errorUserMessage) {
        this.errorUserMessage.setText(errorUserMessage);
    }

    public void setUserMessage(String userMessage) {
        this.userMessage.setText(userMessage);
    }

    public void setObservableList(List<Task> calendarList) {
        //observableList.removeAll()
        for (Task t:calendarList) {
            observableList.add(t);
        }
    }

    public ObservableList<Task> getObservableList() {
        return observableList;
    }

    public Button getCloseCalendar() {
        return closeCalendar;
    }

    public void initialize() {
        observableList = FXCollections.observableArrayList();
        nameTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTitle"));
        dateTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTime"));
        listTasksCalendar.setItems(observableList);
   }
}
