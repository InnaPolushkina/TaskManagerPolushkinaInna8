package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.model.Tasks;
import ua.sumdu.j2se.innapolushkina.tasks.views.CalendarView;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * class CalendarController for searching tasks with entered dates
 */
public class CalendarController {
    private static final Logger logger = Logger.getLogger(CalendarController.class);
    private List<Task> taskListCalendar = new LinkedList<>();
    private CalendarView calendarView = new CalendarView();
    private Stage stage = new Stage();

    /**
     * constructor for class CalendarController
     * open window with calendar
     * @param taskListCalendar have tasks list which will be used for tasks searching
     * set actions and handlers for view elements
     */
    public CalendarController(List<Task> taskListCalendar) {
        stage.setTitle("Calendar");
        this.taskListCalendar = taskListCalendar;
        //calendarView.setObservableList(taskListCalendar);
        FXMLLoader loader = new FXMLLoader();
        loader.setController(calendarView);
        try {
            loader.setLocation(CalendarView.class.getResource("fxml/calendar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            logger.info("open calendar");
        }
        catch (IOException ex){
            logger.error("calendar was not opened " + ex.getMessage());
        }
        calendarView.getSearchDates().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
            }
        });
        calendarView.getCloseCalendar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeCalendar();
            }
        });
        calendarView.setUserMessage("");
        calendarView.setErrorUserMessage("");


    }

    /**
     * the method for searching tasks which have dates what user enter
     * if dates isn't valid show message for user, else put searched tasks into view
     * if method  didn't search any tasks show message
     */
    public void search() {
        calendarView.getObservableList().removeAll(taskListCalendar);
        LinkedList<Task> searchedList = new LinkedList<>();
        try {
            Date start = java.sql.Date.valueOf(calendarView.getStartDate());
            Date end = java.sql.Date.valueOf(calendarView.getEndDate());

            try {
                for (Task task : Tasks.incoming(taskListCalendar, start, end)) {
                    searchedList.add(task);
                }
                calendarView.setErrorUserMessage("");
                if (searchedList.size() == 0) {
                    calendarView.setUserMessage("For this request is not any task");
                } else {
                    calendarView.setUserMessage("");
                }
            } catch (IllegalArgumentException ex) {
                logger.warn("try to search tasks with incorrectly dates");
                calendarView.setErrorUserMessage("Enter correct dates !");
            }
        }
        catch (NullPointerException ex) {
            logger.warn("try to search task without dates");
            calendarView.setErrorUserMessage("Enter both of date");
        }
        calendarView.setObservableList(searchedList);

    }

    /**
     * the method for close window calendar
     */
    public void closeCalendar() {
        stage.close();
        logger.info("calendar was closed");
    }
}
