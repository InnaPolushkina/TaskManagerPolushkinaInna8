package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.LinkedTaskList;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskIO;
import ua.sumdu.j2se.innapolushkina.tasks.model.TaskList;
import ua.sumdu.j2se.innapolushkina.tasks.views.TaskListView;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * the class controls main tasks list
 * have method for adding, removing, editing, details info about task, open window with calendar tasks, can save/open tasks list from file
 */

public class TaskListController {
    //public static final Logger logger = Logger.getLogger(TaskListController.class);
    private static final Logger logger = LogManager.getLogger(TaskListController.class);
    private List<Task> taskList = new LinkedList<>();
    private TaskListView view = new TaskListView();
    private Stage stage = new Stage();
    private File tasksFile;
    private File storedDefaultFilePath = new File("storedDefaultFilePath.txt");
    private CalendarController calendarController;
    private  DetailsInfoController detailsInfoController;
    private EditTaskController editTaskController;

    /**
     * constructor of class TaskListController
     * open window with main tasks list
     * @param tasksFilePath have string with path to file witch have tasks list and write list in end of the app work
     * @throws FileNotFoundException if path to file is not found (catch in ChooseTaskList, when user start work)
     * constructor set actions and handlers for elements of view
     * handlers cause method this class or create controllers for cause action other app windows
     */

    public TaskListController(String  tasksFilePath) throws FileNotFoundException{
        stage.setTitle("Main tasks list");
        this.tasksFile = new File(tasksFilePath);
          loadTasksFile();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(view);
        view.setTasksFile(tasksFile);
        try {
            loader.setLocation(TaskListView.class.getResource("sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex){
           // System.out.println("exception " + ex);
            logger.warn(ex);

        }
        logger.info("open main tasks list");
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addNewTask();
                logger.info("added new task");
            }
        });
        // action for exit from app and save list in file
        view.getExitButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        exit();
                    }
                }
        );
        //action for remove task with context menu
        view.getRemoveTask().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                remove();
            }
        });
        //action for remove task with button
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                remove();
            }
        });
        //action for change model where user add new task
        view.getRepeatCheckbox().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.chengeModel();
            }
        });
        // open new window with calendar
        view.getCalendarButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarController = new CalendarController(taskList);
            }
        });
        //action for edit task with context menu
        view.getEditTask().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               edit();
            }
        });
        //action for edit task with button
        view.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit();
            }
        });
        view.getTitleTextField().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.setErrorMessageUser(" ");
            }
        });
        //open new window with details info about selected task
        view.getDetailsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                detailsInfoController = new DetailsInfoController(view.getObservableList().get(view.selectedRow()));
            }
        });

    }

    /**
     * the method for adding new task in list
     * have control on entered data
     * show message for user, if data is not correct
     */
    public void addNewTask() {
        Task newTask;
        Date thisMoment = new Date();
        if (view.getTitle() == null || "".equals(view.getTitle())) {
            try {
                throw new IOException("Task without name ");
            }
            catch (IOException ex) {
                System.out.println(ex + "  -> adding task");
                view.setErrorMessageUser("Can`t add task without name !");
            }
        }
        else {
            String title = view.getTitle();
            try {
                Date date = java.sql.Date.valueOf(view.getDate());
                date.setTime(date.getTime() + view.getStartTime().getHour() * 3600000 + view.getStartTime().getMinute() * 60000);
                if (view.getRepeat()) {
                    Date end = java.sql.Date.valueOf(view.getDateEnd());
                    end.setTime(end.getTime() + view.getEndTime().getHour() * 3600000 + view.getEndTime().getMinute() * 60000);
                    if (date.after(end) || date.before(thisMoment)) {
                        try {
                            throw new IllegalArgumentException(" Incorrected date");
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex + "  -> adding task");
                            view.setErrorMessageUser("Please, enter correct data ! ! !");
                        }
                    } else {
                        try {
                            int interval = Utill.getIntervalFromStrings(view.getDaysTxt(), view.getHoursTxt(), view.getMinutesTxt());
                            newTask = new Task(title, date, end, interval);
                            newTask.setActive(view.getActiveCheckbox());
                            taskList.add(newTask);
                            logger.info("added new repeated task");
                            view.addTaskObserver(newTask);
                            view.notifyUser();
                            refresh();
                        } catch (NumberFormatException ex) {
                            System.out.println(ex + "   -> adding task");
                            view.setErrorMessageUser("Please, enter correct data ! ! !");
                        }
                        catch (StringIndexOutOfBoundsException ex) {
                            System.out.println(ex + "interval task is not correct");
                            view.setErrorMessageUser("Please, enter valid repeated interval");
                        }
                    }

                } else {
                    if (date.before(thisMoment)) {
                        try {
                            throw new IllegalArgumentException(" Incorrected date");
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex + " - > adding task");
                            view.setErrorMessageUser("Please, enter correct data ! ! !");
                        }
                    }
                    else {
                        newTask = new Task(title, date);
                        newTask.setActive(view.getActiveCheckbox());
                        taskList.add(newTask);
                        view.addTaskObserver(newTask);
                        view.notifyUser();
                        refresh();
                        logger.info("added new task");
                    }
                }

            } catch (NullPointerException ex) {
                view.setErrorMessageUser("Please, enter correct data ! ! !");
                System.out.println(ex + " adding task192");
            }
        }


    }

    /**
     * the method for correct completion work app
     * if file with path to previous file exists, new path will write in file, else method create new file and write there path
     * tasks list save in opened file
     * if app have opened other windows the method close its
     */
    public void exit() {
        if (!storedDefaultFilePath.exists()) {
            try {
                storedDefaultFilePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Writer writer = new BufferedWriter(new FileWriter(storedDefaultFilePath));
            writer.write(tasksFile.getAbsolutePath());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TaskList list = new LinkedTaskList();
        for (Task task:taskList) {
            list.add(task);
        }
        try {
            TaskIO.writeText(list, tasksFile.getAbsoluteFile());
            //TaskIO.writeText(list, previousFile);
            System.out.println(new StringBuilder(getClass().getName()).append(": tasks have been successfully saved"));
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
        stage.close();


        if(calendarController != null) {
            calendarController.closeCalendar();
        }
        if(detailsInfoController != null) {
            detailsInfoController.closeWindow();
        }
        if(editTaskController != null) {
            editTaskController.closeWindow();
        }
        logger.info("exit app");
    }

    /**
     * the method remove selected task from LinkedList taskList and from view
     */
    public void remove() {
        Task task = taskList.get(view.selectedRow());
        taskList.remove(task);
        view.getObservableList().remove(task);
    }

    /**
     * the method open new window with data of selected task
     * method replaces selected task with editions
     */
    public void edit() {
        Task task;
        int rownum = view.selectedRow();

        editTaskController = new EditTaskController(view.getObservableList().get(rownum));
        taskList.remove(rownum);
        task = editTaskController.getEditedTask();
        taskList.add(task);
    }

    /**
     * the method for load tasks list from file to LinkedList taskList
     */
    public void loadTasksFile(){
        TaskList tasksFromFile = new LinkedTaskList();
        try {
            TaskIO.readText(tasksFromFile, tasksFile);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        System.out.println(tasksFromFile.toString());
        for (Task task: tasksFromFile) {
            taskList.add(task);
        }
    }

    /**
     * method for refresh data in view after adding new task
     */
   private void refresh(){
        view.setErrorMessageUser(" ");
        view.setTitle("");

        LocalDate start = LocalDate.now();
        view.setDate(start);
        LocalTime startTime = LocalTime.now();
        view.setStartTime(startTime.plusHours(2));
        view.setEndTime(startTime.plusHours(1));
        view.setActive(false);
       view.setDateEnd(start.plusWeeks(1));
       view.setDaysTxt("1");
       view.setMinutesTxt("5");
       view.setHoursTxt("0");
    }


}
