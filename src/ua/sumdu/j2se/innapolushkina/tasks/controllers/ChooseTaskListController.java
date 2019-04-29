package ua.sumdu.j2se.innapolushkina.tasks.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.sumdu.j2se.innapolushkina.tasks.views.ChooseTaskListView;

import java.io.*;


/**
 * the class ChooseTaskListController have methods for start work with app
 * user can choose three variants: create new list, open previous or load tasks list from any text file
 */
public class ChooseTaskListController {
   private static final Logger logger = LogManager.getLogger(ChooseTaskListController.class);
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
            loader.setLocation(ChooseTaskListView.class.getResource("fxml/chooseTaskList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            logger.info("open window for choosing lists in start work of app");
        }
        catch (IOException ex){
            logger.error("can not load window for choosing file"+ ex.getMessage());
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
                chooseTaskListView.setUserMessage("You don`t enter file name !");
                logger.warn("try to create file without name");
            }
        }
        else {
            File file = new File(chooseTaskListView.getNameNewFile() + ".txt");
            try {
                new File(chooseTaskListView.getNameNewFile() + ".txt").createNewFile();
                logger.info("create file" + file.getAbsolutePath());
                TaskListController taskListController = new TaskListController(file.getPath());
                stage.close();
                if(file.exists()){
                    return;
                }
            }
            catch (IOException ex) {
                logger.warn("try to create file without name");
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
            logger.info("open previous tasks list " + defaultFilePath);
            reader.close();
            try {
                TaskListController taskListController = new TaskListController(defaultFilePath);
                stage.close();
            }
           catch (FileNotFoundException ex) {
               logger.error("previous file " + defaultFilePath + " was not found",ex);
                chooseTaskListView.setUserMessage("Program did not find previous file, file could deleted or no created in last session");
            }
            catch (NullPointerException ex) {
                logger.error("previous file " + defaultFilePath +" was not found",ex);
                chooseTaskListView.setUserMessage("Program did not find previous file, file could deleted or no created in last session");
            }

        }
        catch(IOException ex) {
            logger.error("error while reading path to previous file",ex);
            chooseTaskListView.setUserMessage("If you firstly open the app, please create file for tasks list,\nelse data about last list was lost, please crete new list");
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
        logger.info("open fileChooser for load tasks list from any text file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt" );
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File file = fileChooser.showOpenDialog(stage);
            logger.info("open file" + file.getAbsolutePath() + " with fileChooser");
            TaskListController taskListController = new TaskListController(file.getAbsolutePath());
            stage.close();
        } catch (NullPointerException ex) {
            logger.warn("close fileChooser and don't choose file ",  ex);
            chooseTaskListView.setUserMessage("You don't choose file ");
        }
        catch (FileNotFoundException ex) {
            logger.warn("file was not found", ex);
        }


    }
}
