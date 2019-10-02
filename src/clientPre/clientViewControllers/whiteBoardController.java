package clientPre.clientViewControllers;

import clientPre.pop_ups.AlertBox;
import clientPre.pop_ups.InputText;
import clientPre.pop_ups.SaveAs;
import clientPre.pop_ups.SetHeightAndWidth;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
                gc.strokeOval(e.getX()-50, e.getY()-50, 100, 100);
            }
            else if(mode.equals("rectangle")){
                gc.strokeRect(e.getX()-50, e.getY()-50, 100, 100);
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

//                Double width = setHeightAndWidth.display().get(0);
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

//            System.out.println(x + " , " + y);
//            gc.setFill(colorPicker.getValue());
                gc.lineTo(x, y);
//            gc.fillRect(x, y, size, size);
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
            WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
            String fileLocation = saveInfo.get(0);
            String fileName = saveInfo.get(1);
            String fileType = saveInfo.get(2);
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

    }

    public void save(){

    }
    public void open(){

    }
    public void close(){

    }
}
