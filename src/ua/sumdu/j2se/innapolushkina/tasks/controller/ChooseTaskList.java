package ua.sumdu.j2se.innapolushkina.tasks.controller;

/*import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class ChooseTaskList {
    private static final String savedTasksFileName = "tasks.txt";
    private static final String tasklistsDir = "\\src\\ua\\sumdu\\j2se\\innapolushkina\\tasks\\";
    @FXML
    private TextField inna;
    private Stage stage;
    private String storedDefaultFilePath;
   // private TextField inna;


    public ChooseTaskList() throws IOException {
        File file = new File("storedDefaultFilePath.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        storedDefaultFilePath = file.getAbsolutePath();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadFromFile(ActionEvent actionEvent) {

       FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt" );
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);


        FXMLLoader loader = new FXMLLoader();
        TaskListController controller = new TaskListController();
        //System.out.println(file.getCanonicalPath());
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

    public void createNewList(ActionEvent actionEvent){
        if(inna.getText() == null){
            return;
        }
        File file = new File(inna.getText());
        /*if(new File("tasks.txt").exists()){
            return;
        }*/
        try {
            new File(inna.getText()).createNewFile();
            FXMLLoader loader = new FXMLLoader();
            TaskListController controller = new TaskListController();
            //System.out.println(file.getCanonicalPath());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void previousList(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        TaskListController controller = new TaskListController();
        //controller.setTasksFile(new File(new StringBuilder(tasklistsDir).append(savedTasksFileName).toString());
        String defaultFilePath = null;
        try {
            Reader reader = new BufferedReader(new FileReader(storedDefaultFilePath));
            defaultFilePath = ((BufferedReader) reader).readLine();
            System.err.println(defaultFilePath);
            reader.close();
            controller.setTasksFile(defaultFilePath);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
