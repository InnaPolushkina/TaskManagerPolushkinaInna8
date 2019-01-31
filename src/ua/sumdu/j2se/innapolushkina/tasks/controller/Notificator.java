package ua.sumdu.j2se.innapolushkina.tasks.controller;

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
 * The class that notifies the user that some event is going to happen
 * in less than 10 minutes.
 * Shows a tray-notification every minute
 *
 * */
public class Notificator extends Thread {

    private ObservableList<Task> list;
    private TrayIcon trayIcon;

    public Notificator(ObservableList<Task> list, TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println(new StringBuilder("Notificator thread starts: ").append(currentThread().toString()));
        while (true) {
            for (Task task : list){
                Date nextTimeAfterNow = task.nextTimeAfter(new Date());
                //boolean active = task.isActive();
                if(nextTimeAfterNow == null){
                    continue;
                }
                if(nextTimeAfterNow.getTime() < new Date(System.currentTimeMillis() + 600000).getTime()){
                    trayIcon.displayMessage(task.getTitle(), new StringBuilder("At ").append(Utill.dateToLocalDateTime(task.nextTimeAfter(new Date()))).toString(), TrayIcon.MessageType.INFO);
                    System.out.println(new StringBuilder("Notified: ").append(task.getTitle()).append(", incoming time: "). append(task.nextTimeAfter(new Date())));
                }
            }
            try {
                sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
