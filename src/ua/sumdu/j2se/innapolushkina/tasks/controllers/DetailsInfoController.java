package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.views.DetailsInfoView;
import ua.sumdu.j2se.innapolushkina.tasks.Utill.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.dateToLocalDateTime;
import static ua.sumdu.j2se.innapolushkina.tasks.Utill.dateToLocalTime;

/**
 * the class for show details info about selected task
 */
public class DetailsInfoController {
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
        System.out.println(task.toString());
        stage.setTitle("Details info about selected task");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(detailsInfoView);
        try {
            loader.setLocation(DetailsInfoView.class.getResource("detailsInfo.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            detailsInfo();

        }
        catch (IOException ex) {
            System.out.println(ex);
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
            detailsInfoView.setRepaetDetails("Yes");
            LocalDate dateEnd = dateToLocalDateTime(task.getEndTime());
            detailsInfoView.setDateEndDetails(dateEnd.toString());
            LocalTime timeEnd = dateToLocalTime(task.getEndTime());
            detailsInfoView.setTimeEndDetails(timeEnd.toString());
        }
        else {
            detailsInfoView.setRepaetDetails("No");
            // detailsInfoView.getDateEndDetails().setText(" ");
            detailsInfoView.setDateEndDetails(" - ");
            detailsInfoView.setTimeEndDetails(" - ");
            // detailsInfoView.getDateEndDetails().setText(" ");
        }
    }
}
