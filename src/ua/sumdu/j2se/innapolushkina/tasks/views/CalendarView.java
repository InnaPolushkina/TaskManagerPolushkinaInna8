package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;

import java.time.LocalDate;
import java.util.List;

/**
 * class Calendar have methods for getting/setting values into view
 */
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

    /**
     * the method get value from DatePicker for start date
     * @return LocalDate value
     */
    public LocalDate getStartDate() {
        return startDate.getValue();
    }
    /**
     * the method get value from DatePicker for end date
     * @return LocalDate value
     */
    public LocalDate getEndDate() {
        return endDate.getValue();
    }

    /**
     * the method get button for searching task
     * @return button
     */
    public Button getSearchDates() {
        return searchDates;
    }

    /**
     * the method set message about error for user
     * @param errorUserMessage have string with message
     */
    public void setErrorUserMessage(String errorUserMessage) {
        this.errorUserMessage.setText(errorUserMessage);
    }

    /**
     * the method set message about something events  for user
     * @param userMessage have string with message
     */
    public void setUserMessage(String userMessage) {
        this.userMessage.setText(userMessage);
    }

    /**
     * the method for setting values into table view
     * @param calendarList list for tasks
     */
    public void setObservableList(List<Task> calendarList) {
        //observableList.removeAll()
        for (Task t:calendarList) {
            observableList.add(t);
        }
    }
    /**
     * the method for getting values from table view
     * @return observableList
     */
    public ObservableList<Task> getObservableList() {
        return observableList;
    }
    /**
     * the method get button for close calendar window
     * @return button
     */
    public Button getCloseCalendar() {
        return closeCalendar;
    }

    /**
     * the method for initializing view during load window for calendar
     */
    public void initialize() {
        observableList = FXCollections.observableArrayList();
        nameTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTitle"));
        dateTaskCalendar.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTime"));
        listTasksCalendar.setItems(observableList);
   }
}
