package ua.sumdu.j2se.innapolushkina.tasks.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChooseTaskList {
    private static final String savedTasksFileName = "tasks.txt";
    private static final String tasklistsDir = "D:\\JAVA\\TaskManagerPolushkinaInna\\src\\ua\\sumdu\\j2se\\innapolushkina\\tasks\\";
    private Stage stage;


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("/tasks"));
        fileChooser.setTitle("Choose file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt" );
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);


        FXMLLoader loader = new FXMLLoader();
        TaskListController controller = new TaskListController();
        controller.setTasksFile(file.getAbsolutePath());
        loader.setController(controller);
        try {
            loader.setLocation(TaskListController.class.getResource("..//sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }
    }
    public void previousList(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        TaskListController controller = new TaskListController();
        //controller.setTasksFile(new File(new StringBuilder(tasklistsDir).append(savedTasksFileName).toString());
        controller.setTasksFile("D:\\JAVA\\TaskManagerPolushkinaInna\\src\\ua\\sumdu\\j2se\\innapolushkina\\tasks\\previousFile.txt");
        loader.setController(controller);
        try {
            loader.setLocation(TaskListController.class.getResource("..//sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }
    }
    public void createNewList (ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        TaskListController controller = new TaskListController();
        loader.setController(controller);
        try {
            loader.setLocation(TaskListController.class.getResource("..//sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("exception " + ex);
        }
    }
}
