package com.example.lw4.Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.lw4.Database.Configs;
import com.example.lw4.Database.FacultyDataBaseHandler;
import com.example.lw4.Object.Faculty;
import com.example.lw4.Object.FacultyRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class facultyInfoController extends Configs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<FacultyRecord, Integer> courseColumn;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane facultyPane;

    @FXML
    private TableView<FacultyRecord> facultyTable;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TableColumn<FacultyRecord, Integer> numberColumn;

    @FXML
    private MenuItem resultMenu;

    @FXML
    private TableColumn<FacultyRecord, String> specialityColumn;

    @FXML
    private TableColumn<FacultyRecord, String> specializationColumn;

    @FXML
    private TableColumn<FacultyRecord, String> studentColumn;

    @FXML
    private MenuItem studentMenu;
    @FXML
    private ComboBox<String> specialityBox;

    @FXML
    private ComboBox<String> specializationBox;

    ObservableList<String> specialization;
    ObservableList<String> speciality;

    @FXML
    private Button addButton;



    @FXML
    void initialize() {
        studentMenu.setOnAction(actionEvent -> {

            facultyPane.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/lw4/Student.fxml"));

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

            facultyPane.getScene().getWindow().hide();
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


        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
        });


        printFacultyInfo();
        addButton.setOnAction(actionEvent -> {
            printFacultyInfo();
        });


    }


    public void printFacultyInfo() {
        speciality = FXCollections.observableArrayList("M-P","E-P","E-E-P","E-L-P");
        specialization = FXCollections.observableArrayList("EM","EEB","IS&T(E)","IS&T(L)");


        try {
            String url = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                    + dbPort + "/" + dbName;

            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ObservableList<FacultyRecord> facultyInfo = FXCollections.observableArrayList();

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                Statement statement = conn.createStatement();

                String sql1 = "INSERT INTO mydb.faculty ( student, course) SELECT student, course FROM mydb.student ON DUPLICATE KEY UPDATE faculty.student=student.student";
                PreparedStatement prSt = conn.prepareStatement(sql1);
                prSt.executeUpdate();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.faculty");

                while (resultSet.next()) {
                    FacultyRecord facultyRecord = new FacultyRecord();

                    facultyRecord.setId(Integer.parseInt(resultSet.getString(1)));
                    facultyRecord.setSpecialization(resultSet.getString(2));
                    facultyRecord.setSpeciality(((resultSet.getString(3))));
                    facultyRecord.setCourse(Integer.parseInt(resultSet.getString(4)));
                    facultyRecord.setSecondName(resultSet.getString(5));




                    facultyInfo.add(facultyRecord);
                }


            }


            facultyTable.setItems(facultyInfo);
            numberColumn.setCellValueFactory(f -> f.getValue().idProperty().asObject());

            specializationColumn.setCellValueFactory(f -> f.getValue().specializationProperty());
            specializationColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), specialization));

            specialityColumn.setCellValueFactory(f -> f.getValue().specialityProperty());
            specialityColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), speciality));

            courseColumn.setCellValueFactory(f -> f.getValue().courseProperty().asObject());
            studentColumn.setCellValueFactory(f -> f.getValue().secondNameProperty());

            specializationColumn.setOnEditCommit(facultyRecordStringCellEditEvent -> {
                System.out.println("Value: " + facultyRecordStringCellEditEvent.getNewValue());
                int index = facultyTable.getSelectionModel().getSelectedIndex();


                try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                    String sql = "update mydb.faculty set faculty.specialization = '" + facultyRecordStringCellEditEvent.getNewValue() + "' where idfaculty='" + numberColumn.getCellData(index)+"' ";

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            specialityColumn.setOnEditCommit(facultyRecordStringCellEditEvent -> {

                System.out.println("Value: " + facultyRecordStringCellEditEvent.getNewValue());
                int index = facultyTable.getSelectionModel().getSelectedIndex();

                try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                    String sql = "update mydb.faculty set faculty.speciality = '" + facultyRecordStringCellEditEvent.getNewValue() +  "' where idfaculty='" + numberColumn.getCellData(index)+"' ";

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });


            facultyTable.setEditable(true);

        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
