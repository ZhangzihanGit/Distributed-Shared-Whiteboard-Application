package clientPre.clientViewControllers.whiteBoardController;

import clientApp.ClientAppFacade;
import clientPre.pop_ups.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class whiteBoardController<list> {

    private static whiteBoardController instance;
    public static whiteBoardController getInstance(){
        if(instance == null){
            instance = new whiteBoardController();
        }
        return instance;
    }


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
    @FXML
    private TextArea messageRecord;
    @FXML
    private TextField sendMessage;
    @FXML
    private Button send;
    @FXML
    private ListView listView;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane pane;


    private String clientType = "manager";

    private String mode = "draw";

    private String saveFilePath = "";

    private double[] beginCoordinate = {0,0};

    private void initSendMessage(){

        messageRecord.setEditable(false);
        ArrayList<String> messages = new ArrayList<>();
        send.setOnAction(e->{
            messages.add(sendMessage.getText());
            String content[] = messages.toString().
                    replace("[", "").replace("]", "").split(",");
            String text = "";
            int i = 0;
            for(String s: content){
                i ++;
                if(i == content.length){
                    text += s;
                }
                else{
                    text += (s + "\n");
                }

            }
            messageRecord.setText(text);
            sendMessage.clear();
        });
    }
    public void updateWhiteBoard(){
        pane.getChildren().remove(canvas);
        pane.getChildren().add(canvas);
    }

    private void initLeftButtons(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.setStyle("-fx-background-color: white");
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
    }

    private void initDrawMethods(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(e->{
            double x = e.getX();
            double y = e.getY();

            if(mode.equals("draw") || mode.equals("line") ){
                gc.beginPath();
                gc.lineTo(x, y);
                gc.stroke();
            }
            else if(mode.equals("circle") || mode.equals("rectangle") || mode.equals("oval")){
                beginCoordinate[0] = x;
                beginCoordinate[1] = y;
            }
            else if(mode.equals("text")){
                InputText inputText = new InputText();
                String content = inputText.display();
                gc.fillText(content, x, y);
            }
        });

        canvas.setOnMouseReleased(e->{
            double x = e.getX();
            double y = e.getY();
            double originX = beginCoordinate[0];
            double originY = beginCoordinate[1];
            double width = Math.abs(x - originX);
            double height = Math.abs(y - originY);
            double upLeftX = (originX - x > 0) ? x : originX;
            double upLeftY = (originY - y > 0) ? y : originY;
            double distance = Math.sqrt(Math.pow(x - originX, 2) + Math.pow(y - originY, 2));
            double middleX = (originX + x)/2;
            double middleY = (originY + y)/2;

            if(mode.equals("line")){
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(mode.equals("rectangle")){
                gc.strokeRect(upLeftX, upLeftY, width, height);
            }
            else if(mode.equals("circle")){
                gc.strokeOval(middleX - distance/2, middleY - distance/2, distance, distance);
            }
            else if(mode.equals("oval")){
                gc.strokeOval(upLeftX , upLeftY , width, height);
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

    ObservableList<String> list = FXCollections.observableArrayList(
            "Manager", "coworker1", "coworker2", "coworker3");

    private void initListView(ObservableList<String> list){
        listView.setItems(list);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new StringAndButtonList(clientType);
            }
        });
    }

    public void initialize(){
        initLeftButtons();
        boolean isManager = ClientAppFacade.getInstance().isManager();
        if(isManager){
            clientType = "manager";
        }
        else{
            clientType = "client";
        }
        if(!clientType.equals("manager")){
            menuBar.setVisible(false);
        }
        initSendMessage();
        StringAndButtonList.list = list;
        initListView(StringAndButtonList.list);
        StringAndButtonList.list.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                initListView(StringAndButtonList.list);
            }
        });
        initDrawMethods();
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
            File file = new File(fileLocation +"/" + fileName +"."+ fileType);

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
        OpenFrom openFrom= new OpenFrom();
        String filePath =  openFrom.display();
        if(!filePath.isEmpty()){

            String imagePath = "file:/" + filePath;
            Image image = new Image(imagePath);
            double h = canvas.getHeight();
            double w = canvas.getWidth();
            gc.clearRect(0, 0, w, h);
            gc.drawImage(image, 0, 0, w, h);
            saveFilePath = filePath;
        }
    }

    public void close(){
        System.exit(0);
    }

    public void updateUserList(String msg) {
        System.out.println(msg);
        this.list.removeAll();
        this.list.addAll(Arrays.asList(msg.split(",")));
        StringAndButtonList.list = list;
        initListView(StringAndButtonList.list);
    }

    public void changeListView(){
        String a = "111";
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(Arrays.asList(a.split(",")));
        StringAndButtonList.list = list;
        initListView(StringAndButtonList.list);
    }
}

