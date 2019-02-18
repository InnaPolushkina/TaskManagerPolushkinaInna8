package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;
import ua.sumdu.j2se.innapolushkina.tasks.views.EditTaskView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.*;

/**
 * class EditTaskController for edit task
 */

public class EditTaskController {
    private static Logger logger = LogManager.getLogger(EditTaskController.class);
    private EditTaskView editTaskView = new EditTaskView();
    private Task editedTask;
    private Stage stage = new Stage();

    /**
     * constructor of EditTaskController class
     * @param editedTask task for edit
     * open window with data of selected task
     */

    public EditTaskController(Task editedTask) {
        stage.setTitle("Edit task");
        this.editedTask = editedTask;

        FXMLLoader loader = new FXMLLoader();
        loader.setController(editTaskView);
        try {
            loader.setLocation(EditTaskView.class.getResource("fxml/editTask.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            logger.info("open window for edit task");
            setValuesIntoView();
        }
        catch (IOException ex){
           logger.error("window for edit task was not loaded " + ex.getMessage());
        }

        editTaskView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeWindow();
            }
        });
        editTaskView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(save()) {
                    stage.close();
                }
            }
        });
        editTaskView.getRepeatCheckBox().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editTaskView.chengeModel();
            }
        });

    }

    /**
     * the method close edit task stage
     */
    public void closeWindow() {
        stage.close();
    }

    /**
     * the method for save  task changes
     * @return true if user enter correct data, else false and show message to user
     */
    public boolean save(){
        if (editTaskView.getTitleEdited() == null || "".equals(editTaskView.getTitleEdited())) {
            try {
                throw new IOException("Task without name ");
            }
            catch (IOException ex) {
                logger.warn("try to edit name task on task without name");
                editTaskView.setErrorUserMessage("Can`t save task without name !");
                return false;
            }
        }
        else {
            try {
                String title = editTaskView.getTitleEdited();
                editedTask.setTitle(title);
                editedTask.setActive(editTaskView.getActiveEdited());
                Date date = java.sql.Date.valueOf(editTaskView.getDateStartEdited());
                date.setTime(date.getTime() + editTaskView.getStartTimeEdited().getHour() * 3600000 + editTaskView.getStartTimeEdited().getMinute() * 60000);
                if ( date.before(new Date())) {
                    try {
                        throw new IllegalArgumentException("Incorrectly dates");
                    }
                    catch (IllegalArgumentException ex) {
                        logger.warn("try to edit date of task on date from the past");
                        editTaskView.setErrorUserMessage("Please, enter correct dates");
                        return false;
                    }
                }

                if (editTaskView.getRepeatEdited()) {
                    Date end = java.sql.Date.valueOf(editTaskView.getDateEndEdited());
                    end.setTime(end.getTime() + editTaskView.getEndTimeEdited().getHour() * 3600000 + editTaskView.getEndTimeEdited().getMinute() * 60000);
                    if(date.after(end) || date == end ) {
                        try {
                            throw new IllegalArgumentException("Incorrected dates");
                        }
                        catch (IllegalArgumentException ex) {
                            logger.warn("try to edit task on task with incorrectly dates");
                            editTaskView.setErrorUserMessage("Please, enter correct dates");
                            return false;
                        }
                    }
                    else {
                        try {
                            int intervalEdited = Utill.getIntervalFromStrings(editTaskView.getDaysTxtEdited(), editTaskView.getHoursTxtEdited(), editTaskView.getMinutesTxtEdited());
                            editedTask.setTime(date, end, intervalEdited);
                            return true;
                        }
                        catch (NumberFormatException ex) {
                            editTaskView.setErrorUserMessage("Please, enter correct data ! ! !");
                            return false;
                        }
                        catch (StringIndexOutOfBoundsException ex) {
                            editTaskView.setErrorUserMessage("Please, enter correct repeat interval");
                            return false;
                        }
                        catch (IllegalArgumentException ex) {
                            logger.warn("try to edit interval of task on zero");
                            editTaskView.setErrorUserMessage("The interval must be above zero ! ");
                            return false;
                        }

                    }
                } else {
                    editedTask.setTime(date);
                    return true;
                }


                //stage.close();
            }
            catch (NullPointerException ex) {
                logger.warn("try to edit task on task with");
                editTaskView.setErrorUserMessage("Task can not be with out dates !");
                return false;
            }

        }


    }

    /**
     * getter edited task
     * @return edited task
     */
     public Task getEditedTask() {
         logger.info("saving edition of task");
         return editedTask;
     }

    /**
     * the method for setting values selected task into window
     */
    public void setValuesIntoView() {
         editTaskView.setTitleEdited(editedTask.getTitle());
         editTaskView.setActiveEdited(editedTask.isActive());
         editTaskView.setRepeatEdited(editedTask.isRepeated());
         editTaskView.chengeModel();
         editTaskView.setDateStartEdited(dateToLocalDateTime(editedTask.getStartTime()));
         editTaskView.setDateEndEdited(dateToLocalDateTime(editedTask.getEndTime()));
         editTaskView.setStartTimeEdited(dateToLocalTime(editedTask.getStartTime()));
         editTaskView.setEndTimeEdited(dateToLocalTime(editedTask.getEndTime()));
         int interval = editedTask.getRepeatInterval();
         editTaskView.setDaysTxtEdited(Integer.toString(secondsToIntegerDays(interval)));
         editTaskView.setHoursTxtEdited(Integer.toString(secondsToIntegerHours(interval)));
         editTaskView.setMinutesTxtEdited(Integer.toString(secondsToIntegerMinutes(interval)));
         editTaskView.setErrorUserMessage(" ");
         if(!editedTask.isRepeated()) {
             editTaskView.setDateEndEdited(LocalDate.now().plusWeeks(1));
             editTaskView.setEndTimeEdited(LocalTime.now().plusHours(2));
             editTaskView.setDaysTxtEdited("1");
             editTaskView.setHoursTxtEdited("0");
             editTaskView.setMinutesTxtEdited("5");
         }
     }
}
