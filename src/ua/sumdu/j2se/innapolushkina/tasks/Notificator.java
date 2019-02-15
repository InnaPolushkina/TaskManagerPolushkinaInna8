package ua.sumdu.j2se.innapolushkina.tasks;

import javafx.collections.ObservableList;
import ua.sumdu.j2se.innapolushkina.tasks.Utill;
import ua.sumdu.j2se.innapolushkina.tasks.model.Task;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.*;
import java.util.Date;

/**
 * The class that notifies the user that some event is going to happen and event is active
 * in less than 1 minutes.
 * Shows a tray-notification
 * */
public class Notificator extends Thread {

    private ObservableList<Task> list;
    private TrayIcon trayIcon;

    /**
     * constructor for class Notificator
     * @param list
     * @param trayIcon
     */
    public Notificator(ObservableList<Task> list, TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        this.list = list;
    }

    /**
     * the method for run notification to user
     */
    @Override
    public void run() {
        System.out.println(new StringBuilder("ua.sumdu.j2se.innapolushkina.tasks.Notificator thread starts: ").append(currentThread().toString()));
        while (true) {
            for (Task task : list){
                Date nextTimeAfterNow = task.nextTimeAfter(new Date());
                //boolean active = task.isActive();
                if(nextTimeAfterNow == null){
                    continue;
                }
                if(nextTimeAfterNow.getTime() < new Date(System.currentTimeMillis() + 60000).getTime() && task.isActive()){
                    trayIcon.displayMessage(task.getTitle(), new StringBuilder("At ").append(Utill.dateToLocalDateTime(task.nextTimeAfter(new Date()))).toString(), TrayIcon.MessageType.INFO);
                    System.out.println(new StringBuilder("Notified: ").append(task.getTitle()).append(", incoming time: "). append(task.nextTimeAfter(new Date())));
                }
            }
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
