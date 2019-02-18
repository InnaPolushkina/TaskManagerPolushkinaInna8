package ua.sumdu.j2se.innapolushkina.tasks;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.sumdu.j2se.innapolushkina.tasks.controllers.ChooseTaskListController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ChooseTaskListController chooseTaskListController = new ChooseTaskListController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
