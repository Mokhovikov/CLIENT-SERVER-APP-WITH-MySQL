package com.example.lw4.Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.lw4.Database.Configs;
import com.example.lw4.Object.StudentRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class studentController extends Configs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;



    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<StudentRecord, String> AverageMarkColumn;

    @FXML
    private TableColumn<StudentRecord, Integer> ageColumn;

    @FXML
    private TableColumn<StudentRecord, Integer> courseColumn;

    @FXML
    private Button exitButton;

    @FXML
    private MenuItem facultyMenu;

    @FXML
    private TableView<StudentRecord> facultyTable;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TableColumn<StudentRecord, Integer> numberColumn;

    @FXML
    private MenuItem resultMenu;


    @FXML
    private TableColumn<StudentRecord, String> studentColumn;

    @FXML
    void initialize() {
        facultyMenu.setOnAction(actionEvent -> {

            facultyTable.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/lw4/facultyInfo.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        resultMenu.setOnAction(actionEvent -> {

            facultyTable.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/lw4/faculty.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        });

        refreshButton.setOnAction(actionEvent -> {
            facultyTable.refresh();
        });

        addButton.setOnAction(actionEvent -> {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/lw4/addStudentForm.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        });



            printStudentInfo();

    }

    public void printStudentInfo() {

        try {
            String url = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                    + dbPort + "/" + dbName;

            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ObservableList<StudentRecord> studentInfo = FXCollections.observableArrayList();

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.student");
                while (resultSet.next()) {
                    StudentRecord studentRecord = new StudentRecord();
                    studentRecord.setId(Integer.parseInt(resultSet.getString(1)));
                    studentRecord.setSecondName(resultSet.getString(2));
                    studentRecord.setCourse(Integer.parseInt(resultSet.getString(3)));
                    studentRecord.setAverageMark((resultSet.getString(4)));
                    studentRecord.setAge(Integer.parseInt(resultSet.getString(5)));




                    studentInfo.add(studentRecord);


                }
            }
            facultyTable.setItems(studentInfo);
            numberColumn.setCellValueFactory(f->f.getValue().idProperty().asObject());
            studentColumn.setCellValueFactory(f -> f.getValue().secondNameProperty());
            courseColumn.setCellValueFactory(f -> f.getValue().courseProperty().asObject());
            AverageMarkColumn.setCellValueFactory(f -> f.getValue().averageMarkProperty());
            ageColumn.setCellValueFactory(f -> f.getValue().ageProperty().asObject());

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
