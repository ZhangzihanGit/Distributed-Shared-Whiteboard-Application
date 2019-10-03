package clientPre.clientViewControllers;

import clientPre.pop_ups.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class whiteBoardController {
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider slider;
    @FXML
    private Label label;
    @FXML
    private Button eraser;
    @FXML
    private Button line;
    @FXML
    private Button circle;
    @FXML
    private Button oval;
    @FXML
    private Button rectangle;
    @FXML
    private Button pencil;
    @FXML
    private Button text;

    private String mode = "draw";

    private String saveFilePath = "";

    public void initialize(){
        text.setOnMousePressed(e->{
            mode = "text";
        });
        pencil.setOnMousePressed(e->{
            mode = "draw";
        });

        eraser.setOnMousePressed(e->{
            mode = "erase";
        });

        line.setOnMousePressed(e->{
            mode = "line";
        });

        circle.setOnMousePressed(e->{
            mode = "circle";
        });

        oval.setOnMousePressed(e->{
            mode = "oval";
        });
        rectangle.setOnMousePressed(e->{
            mode = "rectangle";
        });

        label.setText("1.0");

        GraphicsContext gc = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e->{
            gc.setStroke(colorPicker.getValue());
        });

        slider.setMin(1);
        slider.setMax(20);
        slider.valueProperty().addListener(e->{
            double value = slider.getValue();
            String str = String.format("%.1f", value);
            label.setText(str);
            gc.setLineWidth(value);
        });
        canvas.setOnMousePressed(e->{
            if(mode.equals("draw") || mode.equals("line") ){
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(mode.equals("circle")){
                SetRadius setRadius = new SetRadius();
                Double radius = setRadius.display();
                gc.strokeOval(e.getX() - radius/2, e.getY() - radius/2, radius, radius);
            }
            else if(mode.equals("rectangle")){
                SetHeightAndWidth setHeightAndWidth = new SetHeightAndWidth();
                List<Double> list = setHeightAndWidth.display();
                Double width = 0.0;
                Double height = 0.0;
                if(list.size() == 2){
                    width = list.get(0);
                    height = list.get(1);
                }

                gc.strokeRect(e.getX() - width/2, e.getY() - height/2, width, height);
            }
            else if(mode.equals("oval")){
                SetHeightAndWidth setHeightAndWidth = new SetHeightAndWidth();
                List<Double> list = setHeightAndWidth.display();
                Double width = 0.0;
                Double height = 0.0;
                if(list.size() == 2){
                    width = list.get(0);
                    height = list.get(1);
                }

                gc.strokeOval(e.getX() - width/2, e.getY() - height/2, width, height);
            }
            else if(mode.equals("text")){
                InputText inputText = new InputText();
                String content = inputText.display();
                gc.fillText(content, e.getX(), e.getY());
            }
        });

        canvas.setOnMouseReleased(e->{
            if(mode.equals("line")){
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        canvas.setOnMouseDragged(e->{
            double x = e.getX();
            double y = e.getY();
            if(mode.equals("draw")){

                gc.lineTo(x, y);
                gc.stroke();
            }
            else if(mode.equals("erase")){
                gc.clearRect(x, y, slider.getValue(), slider.getValue());
            }

        });
    }

    public void saveAs(){
        SaveAs saveAsBox = new SaveAs();
        List<String> saveInfo = saveAsBox.display();

        if(saveInfo.size() == 3){
            String fileLocation = saveInfo.get(0);
            String fileName = saveInfo.get(1);
            String fileType = saveInfo.get(2);

            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            WritableImage image = canvas.snapshot(sp, null);
            File file = new File(fileLocation +"\\" + fileName +"."+ fileType);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), fileType, file);
                AlertBox box = new AlertBox();
                box.display("information", "You have successfully saved the picture");
            } catch (IOException ex) {
                AlertBox box = new AlertBox();
                box.display("information", "Saving failed!");
                System.out.println("fail!" + ex.getMessage());
            }
        }
    }

    public void newCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DoYouWantToSave d = new DoYouWantToSave();
        Boolean choice = d.display();
        if(choice){
            save();
        }
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        gc.clearRect(0, 0, w, h);
        saveFilePath = "";
    }

    public void save(){
        if(!saveFilePath.equals("")){
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            WritableImage image = canvas.snapshot(sp, null);
            File file = new File(saveFilePath);
            String[] array = saveFilePath.split("[.]");
            String fileType = array[1];
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), fileType, file);
                AlertBox box = new AlertBox();
                box.display("information", "You have successfully saved the picture");
            } catch (IOException ex) {
                AlertBox box = new AlertBox();
                box.display("information", "Saving failed!");
                System.out.println("fail!" + ex.getMessage());
            }
        }
        else{
            saveAs();
        }
    }
    public void open(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DoYouWantToSave d = new DoYouWantToSave();
        Boolean choice = d.display();
        if(choice){
            save();
        }
        OpenFrom openFrom= new OpenFrom();
        String filePath =  openFrom.display();
        if(!filePath.isEmpty()){

            String imagePath = "file:\\" + filePath;
            Image image = new Image(imagePath);
            double h = canvas.getHeight();
            double w = canvas.getWidth();
            gc.clearRect(0, 0, w, h);
            gc.drawImage(image, 0, 0, w, h);
            saveFilePath = filePath;
        }

    }


    public void close(){

    }
}
