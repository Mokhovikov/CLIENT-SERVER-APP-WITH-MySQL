package com.example.lw4.Server;

import com.example.lw4.Database.FacultyDataBaseHandler;
import com.example.lw4.Database.StudentDataBaseHandler;
import com.example.lw4.Object.Student;

import java.net.*;
import java.io.*;

public class ServerApp {
    static int countclients = 0;//счетчик подключившихся клиентов
    public static void main(String[] args) throws IOException, IndexOutOfBoundsException, NumberFormatException {
        ServerSocket sock = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            sock = new ServerSocket(1024); //создаем серверный сокет работающий локально по порту 1024
            while (true) { //бесконечный цикл для возможности подключения последовательно нескольних клиентов
                Socket client = sock.accept(); //сработает, когда клиент подключится,
//                для него выделится отдельный сокет client
                countclients++; //количество подключившихся клиентов увеличивается на 1
                System.out.println("=======================================");
                System.out.println("Client " + countclients + " connected");
                is = client.getInputStream(); //получили входной поток для чтения данных
                os = client.getOutputStream();//получили выходной поток для записи данных

                    byte[] bytes = new byte[client.getReceiveBufferSize()];
                    is.read(bytes); //чтение иформации, посланной клиентом, из вхоного потока в массив bytes[]
                    String str = new String(bytes, "UTF-8"); // переводим тип byte в тип String

                    String[] numbers = str.split(" "); // разбиваем строку на подстроки пробелами

                    String m = "";
                    String res = "";
                    for (int i = 0; i < (numbers.length); i++) {




                        if(i==numbers.length-1){
                            System.out.println("Клиент прислал данные студента: " + "\n"
                                  + "Фамилия: " +numbers[0] + "\n"
                                    + "Курс: "  + numbers[1] + "\n"
                                    + "Средний балл: "  + numbers[2] + "\n"
                                    + "Возраст: "  + numbers[3] + "\n"
                            );


                            String secondName = (numbers[0]);
                            int course = ParseInteger(numbers[1]);
                            double averageMark = ParseDouble(numbers[2]);
                            //String ageStr = num.get(3)+num.get(4);
                            int age = ParseInteger(numbers[3]);
                            StudentDataBaseHandler dbHandler = new StudentDataBaseHandler();
                            Student student = new Student(secondName,course,averageMark,age);
                            dbHandler.addProduct(student);
                            System.out.println(student.getSecondName() + "\n" +
                                    student.getCourse() + "\n" + student.getAverageMark() + "\n" + student.getAge());

                        }
                    }





                    bytes = res.getBytes();
                    os.write(bytes); // отправляем клиенту информацию
                }


        } catch (Exception exception){
            System.out.println(exception);
        }finally {
            is.close();//закрытие входного потока
            os.close();//закрытие входного потока
            sock.close();//закрытие сокета, выделенного для работы с подключившимся клиентом
            System.out.println("Client " + countclients + " disconnected");
        }
    }
    static double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }

    static int ParseInteger(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {

                return Integer.parseInt(strNumber);

            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }

}
