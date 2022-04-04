package com.example.lw4.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class addNewStudentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addNewStudent;

    @FXML
    private AnchorPane addScene;

    @FXML
    private TextField ageField;

    @FXML
    private TextField averageMarkField;

    @FXML
    private Spinner<Integer> courseField;

    @FXML
    private TextField secondNameField;

    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;

    static int size;

    @FXML
    void initialize() {
        String ip = "127.0.0.1";
        try{
            sock = new Socket(InetAddress.getByName(ip),1024);
        } catch (NumberFormatException | IOException e) {
        }
        addTextLimiter(averageMarkField,3);
        addTextLimiter(ageField,2);
        addTextFormatter(averageMarkField);
        final int initialValue = 1;
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);

        courseField.setValueFactory(valueFactory);

        addNewStudent.setOnAction(actionEvent -> {

            addNewStudentM();
            addNewStudent.getScene().getWindow().hide();



        });
    }

    public static int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addNewStudentM() throws NumberFormatException {

        if (sock == null) {
            return;
        }
        try {
            is = sock.getInputStream(); // входной поток для чтения данных
            os = sock.getOutputStream();// выходной поток для записи данных
            String numbers = ""; //перменная,в которую записываются введенные числа



            numbers += secondNameField.getText() + " ";
            numbers += courseField.getValue() + " ";
            numbers += averageMarkField.getText() + " ";
            numbers += ageField.getText() + " ";


            size = numbers.getBytes().length;
            System.out.println(size);
            setSize(size);
            os.write(numbers.getBytes()); // отправляем введенные данные. Тип string переводим в byte
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();//закрытие выходного потока
                is.close();//закрытие входного потока
                sock.close();//закрытие сокета, выделенного для работы с сервером
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }


    public static void addTextLimiter(final TextField tf, final int maxLength) {



        tf.textProperty().addListener((ov, oldValue, newValue) -> {

            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }

        });

    }

    public static void addTextFormatter(final TextField tf) throws StackOverflowError{
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);

        tf.setTextFormatter(textFormatter);
    }

}
