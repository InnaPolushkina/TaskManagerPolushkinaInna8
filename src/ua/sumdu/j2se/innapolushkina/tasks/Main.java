package ua.sumdu.j2se.innapolushkina.tasks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.controller.ChooseTaskList;
import ua.sumdu.j2se.innapolushkina.tasks.controller.EditTask;
import ua.sumdu.j2se.innapolushkina.tasks.controller.TaskListController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(ChooseTaskList.class.getResource("../chooseTaskList.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
