package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.views.ChooseTaskListView;

import java.io.*;
import java.util.logging.Logger;

/**
 * the class ChooseTaskListController have methods for start work with app
 * user can choose three variants: create new list, open previous or load tasks list from any text file
 */
public class ChooseTaskListController {
    //private static Logger logger = Logger.getLogger(ChooseTaskListController.class);
    private ChooseTaskListView chooseTaskListView = new ChooseTaskListView();
    private Stage stage = new Stage();
    private String storedDefaultFilePath = "storedDefaultFilePath.txt";


    /**
     * controller for ChooseTaskListController
     * open window for choosing tasks list when user start work with app
     * constructor add action and handlers to elements view (ChooseTasksListView)
     */
    public ChooseTaskListController() {
        stage.setTitle("Open list ");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(chooseTaskListView);
        try{
            loader.setLocation(ChooseTaskListView.class.getResource("chooseTaskList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex){
            System.out.println( ex);
        }
        // create new file for tasks list
        chooseTaskListView.getCreateNewListButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createNewTaskList();
            }
        });
        // open previous tasks list
        chooseTaskListView.getPreviousListButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousTaskList();
            }
        });
        //open file chooser for load file with tasks list
        chooseTaskListView.getLoadFromFileButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadTaskListFromFile();
            }
        });
    }

    /**
     * the method create new file for tasks list and open window with main tasks list (TaskListController)
     * if user don't write name for file, show message for him
     * close stage for start work if work the method was successful
     */

    public void createNewTaskList() {
        if(chooseTaskListView.getNameNewFile() == null || "".equals(chooseTaskListView.getNameNewFile())){
            try {
                throw new IOException("File without name");
            }
            catch (IOException ex) {
                System.out.println(ex);
                chooseTaskListView.setUserMessage("You don`t enter file name !");
               // chooseTaskListView.setNameNewFile("Enter name of FILE ! ! !");
            }
        }
        else {
            File file = new File(chooseTaskListView.getNameNewFile() + ".txt");
            try {
                new File(chooseTaskListView.getNameNewFile() + ".txt").createNewFile();
                System.out.println("file created");
                TaskListController taskListController = new TaskListController(file.getPath());
                stage.close();
                if(file.exists()){
                    return;
                }
            }
            catch (IOException ex) {
                System.out.println(ex);
                chooseTaskListView.setNameNewFile("Enter name of FILE ! ! !");

            }
        }

    }

    /**
     * the method open main tasks list window with previous file
     * method get path to previous file from storedDefaultFilePath
     * method show message to user if previous file will not found
     * close stage for start work if work the method was successful
     */
    public void previousTaskList () {
        String defaultFilePath = null;
        try{
            Reader reader = new BufferedReader(new FileReader(storedDefaultFilePath));
            defaultFilePath = ((BufferedReader) reader).readLine();
            System.out.println(" Path to load file " + defaultFilePath);
            reader.close();
           // File prevFile = new FileReader(defaultFilePath);
            try {
                TaskListController taskListController = new TaskListController(defaultFilePath);
                stage.close();
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex);
                System.out.println("106 line");
                chooseTaskListView.setUserMessage("Program did not find previous file, file could deleted or no created in last session");
            }
            catch (NullPointerException ex) {
                System.out.println(ex);
                chooseTaskListView.setUserMessage("Program did not find previous file, file could deleted or no created in last session");
            }

        }
        catch(IOException ex) {
            System.out.println(ex);
        }

    }

    /**
     * the method for loading file with tasks list
     * the method open file chooser with filter txt files
     * show message, if close file chooser and didn't choose any file
     * if file was selected open window whit main tasks list(create TaskListController) and close stage for start work
     */
    public void loadTaskListFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt" );
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File file = fileChooser.showOpenDialog(stage);
            TaskListController taskListController = new TaskListController(file.getAbsolutePath());
            stage.close();
        } catch (NullPointerException ex) {
            System.out.println(ex + "  - user don`t choose file for loading tasks list");
            chooseTaskListView.setUserMessage("You don't choose file ");
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }


    }
}
