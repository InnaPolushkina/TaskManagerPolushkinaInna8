package ua.sumdu.j2se.innapolushkina.tasks.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.innapolushkina.tasks.Notificator;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.LinkedTaskList;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskIO;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskList;
import com.jfoenix.controls.JFXTimePicker;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * class TaskListView have methods for getting/setting element(or its values) from/to view of main tasks list
 */
public class TaskListView {
    private ObservableList<Task> observableList;
    private static Logger logger = Logger.getLogger(TaskListView.class);
    @FXML
    private Button addButton;
    @FXML
    private TableView listTasks;
    @FXML
    private Button exitButton;
    @FXML
    private TextField title;
    @FXML
    private CheckBox active;
    @FXML
    private DatePicker date;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private CheckBox repeat;
    @FXML
    private TextField daysTxt;
    @FXML
    private TextField hoursTxt;
    @FXML
    private TextField minutesTxt;
    @FXML
    private MenuItem removeTask;
    @FXML
    private MenuItem editTask;
    @FXML
    private Label detailsInfoTask;
    @FXML
    private Label dateStartLabel;
    @FXML
    private Label dateEndLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label daysLabel;
    @FXML
    private Label hoursLabel;
    @FXML
    private Label minutesLabel;
    @FXML
    private Button calendarButton;
    @FXML
    private Label errorMessageUser;
    @FXML
    private Label emptyListMessage;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button detailsButton;
    @FXML
    private TableColumn<Task, String> nameTask;
    @FXML
    private TableColumn<Task, String> dateTask;
    private File tasksFile;

    /**
     * the method set file with tasks
     * @param tasksFile
     */
    public void setTasksFile(File tasksFile) {
        this.tasksFile = tasksFile;
    }

    /**
     * the for initialize view TaskList
     */
    @FXML
    public void initialize() {
        nameTask.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTitle"));
        dateTask.setCellValueFactory(new PropertyValueFactory<Task, String>("observableTime"));

        try {
            observableList = loadTaskList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setEmptyListMessage(" ");
        if (observableList.size() == 0) {
            Utill.hideNodes(removeButton,editButton,detailsButton,calendarButton);
            setEmptyListMessage("Some functions is not available, because your tasks list is empty. Please add any task");
        }
        listTasks.setItems(observableList);
        Utill.hideNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
        notifyUser();
        setErrorMessageUser(" ");
    }

    /**
     * the method change Model, when user add task
     * view of Model depends on variant task: repeated/ no repeated
     */
    public void chengeModel() {
        if(repeat.isSelected()) {
            dateStartLabel.setText("Date of start");
            Utill.showNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
        }
        else {
            dateStartLabel.setText("Date");
            Utill.hideNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
        }
    }

    /**
     * the method get Button for remove selected task
     * @return button
     */
    public Button getRemoveButton() {
        return removeButton;
    }

    /**
     * the method get Button for edit selected task
     * @return button
     */
    public Button getEditButton() {
        return editButton;
    }

    /**
     * the method get Button for details info about selected task
     * @return button
     */
    public Button getDetailsButton() {
        return detailsButton;
    }

    /**
     * the method get Button for add new task
     * @return button
     */
    public Button getAddButton() {
        return addButton;
    }

    /**
     * the method get Button for exit from app
     * @return button
     */
    public Button getExitButton() {
        return exitButton;
    }

    /**
     * the method set string value to emptyListMessage
     * @param emptyListMessage have string with message to user
     */
    public void setEmptyListMessage(String emptyListMessage) {
        this.emptyListMessage.setText(emptyListMessage);
    }


    /**
     * the method get value from TextField for title of task
     * @return text
     */
    public String getTitle() {
        return title.getText();
    }

    /**
     * the method get TextField for title of task
     * @return TextField
     */
    public TextField getTitleTextField() {
        return title;
    }

    /**
     * @return value from checkbox for active
     */
    public boolean getActiveCheckbox() {
        return active.isSelected();
    }

    /**
     * @return value from DatePicker for date of start
     */
    public LocalDate getDate() {
        return date.getValue();
    }

    /**
     * @return value from DatePicker for date of end
     */
    public LocalDate getDateEnd() {
        return dateEnd.getValue();
    }

    /**
     * @return value from JFXTimePicker for start time
     */
    public LocalTime getStartTime() {
        return startTime.getValue();
    }

    /**
     * @return value from JFXTimePicker for end time
     */
    public LocalTime getEndTime() {
        return endTime.getValue();
    }

    /**
     * @return  values of checkbox for repeat
     */
    public boolean getRepeat() {
        return repeat.isSelected();
    }

    /**
     * the method get checkBox for repeat
     * @return checkbox
     */
    public CheckBox getRepeatCheckbox() {
        return repeat;
    }

    /**
     * the method get string values from textField for days
     * @return text
     */
    public String getDaysTxt() {
        return daysTxt.getText();
    }

    /**
     * the method get string values from textField for hours
     * @return text
     */
    public String getHoursTxt() {
        return hoursTxt.getText();
    }

    /**
     * the method get string values from textField for minutes
     * @return text
     */
    public String getMinutesTxt() {
        return minutesTxt.getText();
    }

    /**
     * the method get item from context menu
     * @return
     */
    public MenuItem getRemoveTask() {
        return removeTask;
    }

    /**
     * the method get item from context menu
     * @return
     */
    public MenuItem getEditTask() {
        return editTask;
    }

    /**
     * the method set value into TextField for title of task
     * @param title
     */
    public void setTitle(String title) {
        this.title.setText("");
    }

    /**
     * the method set active into checkbox
     * @param active
     */
    public void setActive(Boolean active) {
        this.active.setSelected(active);
    }

    /**
     * the method set values into DatePicker date
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date.setValue(date);
    }

    /**
     * the method set values into DatePicker dateEnd
     * @param dateEnd
     */
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd.setValue(dateEnd);
    }

    /**
     * the method set values into JFXTimePicker startTime
     * @param startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime.setValue(startTime);
    }

    /**
     * the method set values into JFXTimePicker endTime
     * @param endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime.setValue(endTime);
    }

    /**
     * the method set values into text field with data of days
     * @param daysTxt have string with values days
     */
    public void setDaysTxt(String daysTxt) {
        this.daysTxt.setText(daysTxt);
    }

    /**
     * the method set values into text field with data of hours
     * @param hoursTxt have string with values hours
     */
    public void setHoursTxt(String hoursTxt) {
        this.hoursTxt.setText(hoursTxt);
    }

    /**
     * the method set values into text field with data of minutes
     * @param minutesTxt have string with values minutes
     */
    public void setMinutesTxt(String minutesTxt) {
        this.minutesTxt.setText(minutesTxt);
    }

    /**
     * the method get observable list
     * @return observableList of Task
     */
    public ObservableList<Task> getObservableList() {
        return observableList;
    }

    /**
     * the method add task into observable list
     * @param task
     */
    public void addTaskObserver(Task task) {
        observableList.add(task);
    }

    /**
     * the method get selected row from table view
     * @return number of selected row
     */
    public int selectedRow() {
        TableView.TableViewSelectionModel seltionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = seltionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        return row;

    }

    /**
     * the method set vales into error message user
     * @param message have string with message
     */
    public void setErrorMessageUser(String message) {
        this.errorMessageUser.setText(message);
    }

    /**
     * getter for calendar button
     * @return button
     */
    public Button getCalendarButton() {
        return calendarButton;
    }

    /**
     * the method load into view tasks list from file
     * @return observable list
     * @throws IOException, when can not load tasks from file
     */
    private ObservableList<Task> loadTaskList() throws IOException {
        List <Task> listlist = new LinkedList<>();
        TaskList list = new LinkedTaskList();
        if(tasksFile == null) {
            observableList = FXCollections.observableArrayList(Collections.synchronizedList(listlist));
        }
        else {
            try {
                TaskIO.readText(list,tasksFile);
                for (Task t:list
                ) {
                    listlist.add(t);
                    //linkedTaskList.add(t);
                }
            }
            catch (IOException ex) {
                logger.error("tasks was not load into table view from file " + tasksFile.getAbsolutePath() + ex.getMessage());
            }
            observableList = FXCollections.observableArrayList(Collections.synchronizedList(listlist));
        }
        return observableList;
    }

    /**
     * the method notify users than some vent is going to happen less 1 minutes and event is active
     */
    public void notifyUser(){
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Notificator notificator = new Notificator(observableList, trayIcon);
        notificator.setDaemon(true);
        notificator.start();
    }

}

