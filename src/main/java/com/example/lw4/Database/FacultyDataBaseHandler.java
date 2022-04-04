package com.example.lw4.Database;

import com.example.lw4.Object.Faculty;
import com.example.lw4.Object.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacultyDataBaseHandler extends Configs{
    static Connection dbConnection;

    public FacultyDataBaseHandler() {
    }

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void addFaculty(Faculty faculty){
        String insert = "INSERT INTO " + Const.FACULTY_TABLE + "("  +
                Const.FACULTY_SPECIALIZATION+ "," + Const.FACULTY_SPECIALITY + "," + Const.FACULTY_COURSE + ","+ Const.FACULTY_STUDENT + ")" +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, faculty.getSpecialization());
            prSt.setString(2, faculty.getSpeciality());
            prSt.setInt(3, faculty.getCourse());
            prSt.setString(4, faculty.getSecondName());
            prSt.executeUpdate();





        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }

    }

}
