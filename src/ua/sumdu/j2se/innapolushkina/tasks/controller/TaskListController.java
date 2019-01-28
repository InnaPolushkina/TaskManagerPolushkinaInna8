package ua.sumdu.j2se.innapolushkina.tasks.controller;

import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.LinkedTaskList;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskList;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Matcher;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.integerString;


public class TaskListController {

    private TaskList linkedTaskList = new LinkedTaskList();
    private Task task = new Task("today is happy day",new Date());
    private ObservableList<Task> observableList = FXCollections.observableArrayList(task);
    @FXML
    private javafx.scene.control.Label details;
    @FXML
    private CheckBox repeat;
    @FXML
    private CheckBox active;
    @FXML
    private DatePicker date;

    @FXML
    private TextField title;
    @FXML
    private Label dateStartLabel;

    @FXML
    private Label dateEndLabel;
    @FXML
    private DatePicker dateEnd;

    @FXML
    private TableView listTasks;

    @FXML
    private TableColumn<Task,String> nameTask;

    @FXML
    private TableColumn<Task, Date> dateTask;
    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private TextField daysTxt;

    @FXML
    private TextField hoursTxt;

    @FXML
    private TextField minutesTxt;

    @FXML
    private Label daysLabel;

    @FXML
    private Label hoursLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label intervalLabel;





    public TaskListController() {
    }

    @FXML
    private void initialize(){
        nameTask.setCellValueFactory(new PropertyValueFactory<Task,String>("title"));
        dateTask.setCellValueFactory(new PropertyValueFactory<Task,Date>("time"));
        listTasks.setItems(observableList);
         date.getValue();

        daysTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    daysTxt.setText(oldValue);
                }
                if(Integer.parseInt(daysTxt.getText()) > 24854) {
                    daysTxt.setText(oldValue);
                }
            }
        });
        hoursTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    hoursTxt.setText(oldValue);
                }
                if(Integer.parseInt(hoursTxt.getText()) > 23){
                    hoursTxt.setText(oldValue);
                }
            }
        });
        minutesTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Matcher matcher = integerString.matcher(newValue);
                if(!matcher.matches()){
                    minutesTxt.setText(oldValue);
                }
                if(Integer.parseInt(minutesTxt.getText()) > 59){
                    minutesTxt.setText(oldValue);
                }
            }
        });
       // dateStartLabel.setText("Date");
        Utill.hideNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
    }


    public void editTask(ActionEvent actionEvent) {

       /* try{
           Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("..//editTask.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            System.out.println("ioException");
        }*/
        TableView.TableViewSelectionModel selectionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = selectionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        Task taskEdit = linkedTaskList.getTask(row-1);

        FXMLLoader loader = new FXMLLoader();
        EditTask controller = new EditTask(taskEdit);
        loader.setController(controller);
        try {
            loader.setLocation(EditTask.class.getResource("..//editTask.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }


       //stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner();
    }

    @FXML
    private void changeCreationMode() {
        if(repeat.isSelected()) {
            dateStartLabel.setText("Date of start");
            Utill.showNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
        } else {
            dateStartLabel.setText("Date");
            Utill.hideNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);

        }
    }
    public void detailsInfoTask(MouseEvent mouseEvent) {

        //визначаемо номер рядка TableView для визначення номеру задачі в ObservableList
        TableView.TableViewSelectionModel seltionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = seltionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        String act;
        if(observableList.get(row).isActive()){
            act = " active";
        } else {
            act = "not active";
        }
        if(observableList.get(row).isRepeated()) {
            details.setText((row+1) +". Name task " + observableList.get(row).getTitle() + ", repeated with interval " + observableList.get(row).getRepeatInterval() + ", next time " + observableList.get(row).nextTimeAfter(new Date()) );
        }
        else{
            details.setText((row+1) +". Name task ' " + observableList.get(row).getTitle() + " ', task don't repeat. "+" At this moment task is " + act);
        }


    }

   @FXML
   public void add() {
       Task task;
       String details = title.getText();
       if(details == null) {
           title.setPromptText("Please, enter the task");
           return;
       }
       if("".equals(details) || details == null) {
           System.err.println(new StringBuilder("Try to add an empty task ").append(getClass()));
           return;
       }
       Date start =  java.sql.Date.valueOf(date.getValue());
       start.setTime(start.getTime() + startTime.getValue().getHour() * 3600000 + startTime.getValue().getMinute() * 60000);
       if(repeat.isSelected()) {
           Date end =  java.sql.Date.valueOf(dateEnd.getValue());
           end.setTime(end.getTime() + endTime.getValue().getHour() * 3600000 + endTime.getValue().getMinute() * 60000);
           task = new Task(details, start, end,
                   Utill.getIntervalFromStrings(daysTxt.getText(), hoursTxt.getText(), minutesTxt.getText()));
       }else {
           task = new Task(details, start);
       }
       task.setActive(active.isSelected());
       SystemTray tray = SystemTray.getSystemTray();
       Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
       TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
       trayIcon.setImageAutoSize(true);
       try {
           tray.add(trayIcon);
       } catch (AWTException e) {
           e.printStackTrace();
       }
       if(observableList.add(task)){
           linkedTaskList.add(task);
           refresh();
           trayIcon.displayMessage(task.getTitle(), new StringBuilder("The task: ").append(task.getTitle()).append(" has been successfully added").toString(), TrayIcon.MessageType.INFO);
          // refreshSublist();
       }
   }


    public void exit(ActionEvent event) {
        // при нажатии происходит запись созданного списка задач в файл(создаеться автоматически)

    }
    private void refresh() {
        title.setText("");
        date.setValue(LocalDate.now());
        startTime.setValue(LocalTime.now());
        dateEnd.setValue(LocalDate.now().plusDays(7));
        endTime.setValue(LocalTime.now());
        daysTxt.setText("0");
        hoursTxt.setText("0");
        minutesTxt.setText("10");

    }

    public void removeTask(ActionEvent event) {
        TableView.TableViewSelectionModel seltionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = seltionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        Task removedTask = linkedTaskList.getTask(row-1);
        linkedTaskList.remove(removedTask);
        observableList.remove(removedTask);
    }
}
