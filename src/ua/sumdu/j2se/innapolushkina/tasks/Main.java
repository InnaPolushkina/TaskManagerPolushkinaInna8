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
        FXMLLoader loader = new FXMLLoader();
        ChooseTaskList chooseFile = new ChooseTaskList();
        chooseFile.setStage(primaryStage);
        loader.setController(chooseFile);
        try {
            loader.setLocation(ChooseTaskList.class.getResource("..//chooseTaskList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
