package ua.sumdu.j2se.innapolushkina.tasks.controller;

import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.LinkedTaskList;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskIO;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskList;

import java.io.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.*;


public class TaskListController {

    private List<Task> linkedTaskList = new LinkedList<>();
    private ObservableList<Task> observableList;
    private Notificator notificator;
    private static final String savedTasksFileName = "tasks.txt";
    private static final String tasklistsDir = "D:\\JAVA\\TaskManagerPolushkinaInna\\src\\ua\\sumdu\\j2se\\innapolushkina\\tasks\\";

    private File tasksFile = new File(new StringBuilder(tasklistsDir).append(savedTasksFileName).toString());
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
    private TableColumn<Task, String> dateTask;
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
    @FXML
    public Button exit;
    public File c = getTasksFile();



    public TaskListController() {
        //tasksFile = f;
    }

    public File getTasksFile() {
        return tasksFile;
    }

    public void setTasksFile(String wayToLastFile) {
        this.tasksFile = new File(wayToLastFile);
    }

    @FXML
    private void initialize() throws IOException{
        nameTask.setCellValueFactory(new PropertyValueFactory<Task,String>("observableTitle"));
        dateTask.setCellValueFactory(new PropertyValueFactory<Task,String>("observableTime"));

        //tasksFile = new File(new StringBuilder(tasklistsDir).append(savedTasksFileName).toString());
        observableList = loadTaskList();
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
        Utill.hideNodes(dateEnd, dateEndLabel, daysTxt, daysLabel, minutesLabel, minutesTxt, hoursTxt, hoursLabel, endTime, intervalLabel);
        notifyUser();

    }


    public void editTask(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel selectionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = selectionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        Task taskEdit = observableList.get(row);


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
        Task d = observableList.get(row);
        String title = d.getTitle();
        String start = d.getStartTime().toString();
        String end = d.getObservableEnd();
        String time = d.getObservableTime();
        String active = d.getObservableIsActive();
        String interval = d.getObservableInterval();
        if ( d.isRepeated() ) {
            details.setText("Name task - " + title + ". State -  " + active + ". Task start at " + start +", finish at  " + end + ". \nTask repeats - " + interval + ". Next time  "  + d.nextTimeAfter(new Date()).toString());
        }
        else {
            details.setText("Name task - " + title + ". State - " + active + ". Time " + time);
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
       }
   }


    public void exit(ActionEvent event) {
        //tasksFile = new File(new StringBuilder(tasklistsDir).append(savedTasksFileName).toString());
        File storedDefaultFilePath = new File("storedDefaultFilePath.txt");
        if(!storedDefaultFilePath.exists()){
            try {
                storedDefaultFilePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Writer writer =  new BufferedWriter(new FileWriter(storedDefaultFilePath));
            writer.write(tasksFile.getAbsolutePath());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File previousFile = new File("D:\\JAVA\\TaskManagerPolushkinaInna\\src\\ua\\sumdu\\j2se\\innapolushkina\\tasks\\previousFile.txt");
        TaskList list = new LinkedTaskList();
        for (Task t:observableList
             ) {
            list.add(t);
        }
        // при нажатии происходит запись созданного списка задач в файл(создаеться автоматически)
        System.out.println(new StringBuilder(getClass().getName()).append(": saving the tasks"));
        try {
            TaskIO.writeText(list,tasksFile);
            TaskIO.writeText(list,previousFile);
            System.out.println(new StringBuilder(getClass().getName()).append(": tasks have been successfully saved"));
        }catch (IOException e) {
            System.out.println(e.getCause());
        }
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();

    }

    private ObservableList<Task> loadTaskList() throws IOException {
        System.out.println(new StringBuilder(getClass().getName()).append(": loading the task list"));
        List <Task> listlist = new LinkedList<>();
        TaskList list = new LinkedTaskList();
        if(tasksFile == null) {
            observableList = FXCollections.observableArrayList(Collections.synchronizedList(listlist));
        }
        else {
            TaskIO.readText(list,tasksFile);
            for (Task t:list
            ) {
                listlist.add(t);
                linkedTaskList.add(t);
            }
            observableList = FXCollections.observableArrayList(Collections.synchronizedList(listlist));
        }


        //TaskIO.readText(observableList,tasksFile);
        System.out.println(new StringBuilder(getClass().getName()).append(": the list has benn loaded: ").append(observableList));
        return observableList;
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
        //repeat.setSelected(false);

    }

    public void removeTask(ActionEvent event) {
        TableView.TableViewSelectionModel seltionModel = listTasks.getSelectionModel();
        ObservableList selectedCells = seltionModel.getSelectedCells();
        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
        int row = tablePosition.getRow();
        Task removedTask = observableList.get(row);
        //linkedTaskList.remove(removedTask);
        observableList.remove(removedTask);
        if (observableList.size() == 0) {
            details.setText("Add task to list and select it");
        }
    }

    public void calendar(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        Calendar controller = new Calendar(linkedTaskList);
        loader.setController(controller);
        try {
            loader.setLocation(Calendar.class.getResource("..//calendar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }
    }

    private void notifyUser(){
        System.out.println(new StringBuilder(getClass().getName()).append(": launching the notificator starts"));
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
        System.out.println(new StringBuilder(getClass().getName()).append(": launching the notificator finished"));
    }


}
