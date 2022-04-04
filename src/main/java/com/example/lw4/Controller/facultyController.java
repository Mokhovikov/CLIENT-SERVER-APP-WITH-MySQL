package com.example.lw4.Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.lw4.Database.Configs;
import com.example.lw4.Object.FacultyRecord;
import com.example.lw4.Object.FacultyResultRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class facultyController extends Configs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<FacultyResultRecord, Double> AverageMarkColumn;

    @FXML
    private TableColumn<FacultyResultRecord, Integer> CourseColumn;

    @FXML
    private TableColumn<FacultyResultRecord, String> SpecialityColumn;

    @FXML
    private TableColumn<FacultyResultRecord, String> SpecializationColumn;

    @FXML
    private TableColumn<FacultyResultRecord, String> SrudentColumn;

    @FXML
    private Button exitButton;

    @FXML
    private MenuItem facultyMenu;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TableColumn<FacultyResultRecord, Integer> numberColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<FacultyResultRecord> resultTable;

    @FXML
    private MenuItem studentMenu;

    int count;

    @FXML
    void initialize() {
        facultyMenu.setOnAction(actionEvent -> {

            refreshButton.getScene().getWindow().hide();
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

        studentMenu.setOnAction(actionEvent -> {

            refreshButton.getScene().getWindow().hide();
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

        printFacultyInfo();
        refresh();

    }
    public void printFacultyInfo() {

        try {
            String url = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                    + dbPort + "/" + dbName;

            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ObservableList<FacultyResultRecord> facultyInfo = FXCollections.observableArrayList();



            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                Statement statement = conn.createStatement();

                PreparedStatement prSt1 = conn.prepareStatement("SELECT  averageMark from mydb.student");
                ResultSet rs= prSt1.executeQuery();
                while (rs.next()){
                    count = rs.getInt(1)-1;
                }
                System.out.println("Number of records in the cricketers_data table: "+count);


                String sql1 = "INSERT INTO mydb.facultyresult ( specialization, speciality,  course,student)" +
                        "   SELECT  faculty.specialization, faculty.speciality,faculty.course, faculty.student " +
                        "FROM mydb.faculty ON DUPLICATE" +
                        " KEY UPDATE facultyresult.student=faculty.student";



                PreparedStatement prSt = conn.prepareStatement(sql1);
                prSt.executeUpdate();
                refresh();

                ResultSet resultSet = prSt.executeQuery("SELECT  * from mydb.facultyresult");

                while (resultSet.next()) {
                    FacultyResultRecord facultyRecord = new FacultyResultRecord();

                    facultyRecord.setId(Integer.parseInt(resultSet.getString(1)));
                    facultyRecord.setSpecialization(resultSet.getString(2));
                    facultyRecord.setSpeciality(((resultSet.getString(3))));
                    facultyRecord.setCourse(Integer.parseInt(resultSet.getString(4)));
                    facultyRecord.setSecondName(resultSet.getString(5));
                    facultyRecord.setAverageMark(Double.valueOf((resultSet.getString(6))));






                    facultyInfo.add(facultyRecord);
                }


            }



            resultTable.setItems(facultyInfo);
            numberColumn.setCellValueFactory(f -> f.getValue().idProperty().asObject());
            SpecializationColumn.setCellValueFactory(f -> f.getValue().specializationProperty());
            SpecialityColumn.setCellValueFactory(f -> f.getValue().specialityProperty());
            CourseColumn.setCellValueFactory(f -> f.getValue().courseProperty().asObject());
            SrudentColumn.setCellValueFactory(f -> f.getValue().secondNameProperty());
            AverageMarkColumn.setCellValueFactory(f -> f.getValue().averageMarkProperty().asObject());






        } catch (ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

        public void refresh() {


                try {
                    String url = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                            + dbPort + "/" + dbName;

                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    ObservableList<FacultyResultRecord> facultyInfo = FXCollections.observableArrayList();


                    try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {

                        String sql = "update mydb.facultyresult set facultyresult.averagemark = (select student.averageMark from mydb.student " +
                                " where student.student = facultyresult.student LIMIT 1) ";


                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.executeUpdate();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }


