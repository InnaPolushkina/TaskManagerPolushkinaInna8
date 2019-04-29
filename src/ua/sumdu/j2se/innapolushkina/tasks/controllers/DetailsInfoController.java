package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.views.DetailsInfoView;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.log4j.Logger;
import static ua.sumdu.j2se.innapolushkina.tasks.Utill.*;

/**
 * the class for show details info about selected task
 */
public class DetailsInfoController {
    private static Logger logger = Logger.getLogger(DetailsInfoController.class);
    private DetailsInfoView detailsInfoView = new DetailsInfoView();
    private Stage stage = new Stage();
    private Task task;

    /**
     * the constructor for class DetailsInfoController
     * open window with details info about selected task
     * have action and handler for elements view (DetailsInfoView)
     * @param task
     */
    public DetailsInfoController(Task task) {
        this.task = task;
        stage.setTitle("Details info about selected task");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(detailsInfoView);
        try {
            loader.setLocation(DetailsInfoView.class.getResource("fxml/detailsInfo.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            detailsInfo();
            logger.info("show details info about task ' " + task.getTitle() + " ' ");
        }
        catch (IOException ex) {
            logger.error("window for details info about task was not loaded ",ex);
        }


        detailsInfoView.getOkCloseWindow().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeWindow();
            }
        });

    }

    /**
     * the method close window with details info about selected task
     */
    public void closeWindow() {
        stage.close();
    }

    /**
     * the method show details info about selected task in text format
     */
    public void detailsInfo() {
        detailsInfoView.setNameTaskDetails(task.getTitle());
        if (task.isActive()) {
            detailsInfoView.setActiveDetails("Yes");
        }
        else {
            detailsInfoView.setActiveDetails("No");
        }
        LocalDate date = dateToLocalDateTime(task.getStartTime());
        LocalTime time = dateToLocalTime(task.getStartTime());
        detailsInfoView.setTimeDatails(time.toString());
        detailsInfoView.setDateDateils(date.toString());
        if (task.isRepeated()) {
            int interval = task.getRepeatInterval();
            detailsInfoView.setRepaetDetails("every " + Integer.toString(secondsToIntegerDays(interval)) + " day " + Integer.toString(secondsToIntegerHours(interval)) + " hours " + Integer.toString(secondsToIntegerMinutes(interval)) + " min");
            LocalDate dateEnd = dateToLocalDateTime(task.getEndTime());
            detailsInfoView.setDateEndDetails(dateEnd.toString());
            LocalTime timeEnd = dateToLocalTime(task.getEndTime());
            detailsInfoView.setTimeEndDetails(timeEnd.toString());
        }
        else {
            detailsInfoView.setRepaetDetails("No");
            detailsInfoView.setDateEndDetails(" - ");
            detailsInfoView.setTimeEndDetails(" - ");
        }
    }
}
