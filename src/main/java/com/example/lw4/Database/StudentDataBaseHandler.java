package com.example.lw4.Database;

import com.example.lw4.Object.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDataBaseHandler extends Configs{
    static Connection dbConnection;

    public StudentDataBaseHandler() {
    }

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" // jdbc plugin, which connect with db
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void addProduct(Student student){
        String insert = "INSERT INTO " + Const.STUDENT_TABLE + "("  +
                Const.STUDENT_STUDENT+ "," + Const.STUDENT_COURSE + "," + Const.STUDENT_AVERAGEMARK + ","+ Const.STUDENT_AGE + ")" +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, student.getSecondName());
            prSt.setInt(2, student.getCourse());
            prSt.setDouble(3, student.getAverageMark());
            prSt.setInt(4, student.getAge());
            prSt.executeUpdate();





        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }

    }

}
