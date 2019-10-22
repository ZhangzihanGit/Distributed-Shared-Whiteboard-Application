
package clientPre.clientViewControllers.whiteBoardController;

import clientApp.ClientAppFacade;
import clientPre.pop_ups.AlertBox;
import clientPre.pop_ups.InputText;
import clientPre.pop_ups.OpenFrom;
import clientPre.pop_ups.SaveAs;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class whiteBoardController {

    private static whiteBoardController instance;
    public static whiteBoardController getInstance(){
        if(instance == null){
            instance = new whiteBoardController();
        }
        return instance;
    }

    private static Canvas canvas = new Canvas(903, 511);
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
    @FXML private TextField msgField;
    @FXML private TextArea msgArea;
    @FXML private Button sendBtn;


    private String clientType = "manager";

    private String mode = "draw";

    private String saveFilePath = "";

    private double[] beginCoordinate = {0,0};

//    private void initSendMessage(){
//
//        messageRecord.setEditable(false);
//        ArrayList<String> messages = new ArrayList<>();
//        send.setOnAction(e->{
//            messages.add(sendMessage.getText());
//            String content[] = messages.toString().
//                    replace("[", "").replace("]", "").split(",");
//            String text = "";
//            int i = 0;
//            for(String s: content){
//                i ++;
//                if(i == content.length){
//                    text += s;
//                }
//                else{
//                    text += (s + "\n");
//                }
//
//            }
//            messageRecord.setText(text);
//            sendMessage.clear();
//        });
//    }

    private void initLeftButtons(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.setStyle("-fx-background-color: white");

        text.setOnMousePressed(e->{
            mode = "text";
        });
        pencil.setOnMousePressed(e->{
            mode = "draw";
            ImageCursor cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/pencil.png").toExternalForm()));
            canvas.setCursor(cursor);
        });

        eraser.setOnMousePressed(e->{
            mode = "erase";
            ImageCursor cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/pencil.png").toExternalForm()));
            canvas.setCursor(cursor);
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
        System.out.println("GC IS: "+gc +"  "+"Canvas is:  "+ canvas);
        canvas.setOnMousePressed(e->{
            double x = e.getX();
            double y = e.getY();
            String msg = "";
            if(mode.equals("draw") || mode.equals("line") ){
                gc.beginPath();
                gc.lineTo(x, y);
                gc.stroke();
                // 5 components.
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," +x
                        + "," + y+ "," + 0;
                ClientAppFacade.getInstance().updateWb(msg);
            }
            else if(mode.equals("circle") || mode.equals("rectangle") || mode.equals("oval")){
                beginCoordinate[0] = x;
                beginCoordinate[1] = y;
            }
            else if(mode.equals("text")){
                InputText inputText = new InputText();
                String content = inputText.display();
                if (content!=null){
                    gc.fillText(content, x, y);
                    msg = gc.getStroke() + ","+gc.getLineWidth()+","+mode+","+x+","+y+","+content;
                    ClientAppFacade.getInstance().updateWb(msg);
                }
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
            String msg = "";
            if(mode.equals("line")){
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();

                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," +x
                        + "," + y+ "," + 1;
                ClientAppFacade.getInstance().updateWb(msg);
            }
            else if(mode.equals("rectangle")){
                gc.strokeRect(upLeftX, upLeftY, width, height);
                ClientAppFacade.getInstance().updateWb("r," + gc.getStroke());
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + upLeftX
                        + "," + upLeftY + "," + width + "," + height;
                ClientAppFacade.getInstance().updateWb(msg);
            }
            else if(mode.equals("circle")){
                gc.strokeOval(middleX - distance/2, middleY - distance/2, distance, distance);
                ClientAppFacade.getInstance().updateWb("r," + gc.getStroke());
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + (middleX - distance/2)
                        + "," + (middleY - distance/2) + "," + distance + "," + distance;
                ClientAppFacade.getInstance().updateWb(msg);
            }
            else if(mode.equals("oval")){
                gc.strokeOval(upLeftX , upLeftY , width, height);
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + upLeftX
                        + "," + upLeftY + "," + width + "," + height;
                ClientAppFacade.getInstance().updateWb(msg);
            }
        });

        canvas.setOnMouseDragged(e->{
            double x = e.getX();
            double y = e.getY();
            String msg = "";
            if(mode.equals("draw")){

                gc.lineTo(x, y);
                gc.stroke();
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," +x
                        + "," + y+ "," + 1;
                ClientAppFacade.getInstance().updateWb(msg);
            }
            else if(mode.equals("erase")){
                gc.clearRect(x, y, slider.getValue(), slider.getValue());
                msg = gc.getStroke()+"," +gc.getLineWidth()+","+mode+","+x+","+y+","+slider.getValue();
                ClientAppFacade.getInstance().updateWb(msg);
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
        pane.getChildren().add(canvas);
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
//        initSendMessage();
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
        System.out.println(canvas);
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
        StringAndButtonList.list.setAll(Arrays.asList(msg.split(",")));
    }

    public void updateWhiteBoard(String msg){
        if(msg.equals("1")){

        }
        else if(msg.equals("1,2")){

        }
        else{
            GraphicsContext gc = canvas.getGraphicsContext2D();
            System.out.println("GC IS: "+gc +"  "+"Canvas is:  "+ canvas);
            Paint originalColor = gc.getStroke();
            double originLineWidth = gc.getLineWidth();

            ArrayList<String> inst = new ArrayList<>(Arrays.asList(msg.split(",")));
            System.out.println("Message@@@@@@ is :"+msg);
            Color c = Color.web(inst.get(0),1.0);
            gc.setStroke(c);
            gc.setLineWidth(Double.parseDouble(inst.get(1)));
            if(inst.get(2).equals("oval")){

                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));
                System.out.println("Enter oval drawing:  "+x +y+width+width);
                gc.strokeOval(x,y,width,height);
            }
            else if(inst.get(2).equals("rectangle")){

                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));
                System.out.println("Enter oval drawing:  "+x +y+width+width);
                gc.strokeRect(x,y,width,height);
            }
            else if (inst.get(2).equals("circle")){
                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));
                System.out.println("Enter oval drawing:  "+x +y+width+width);
                gc.strokeOval(x,y,width,height);
            }
            else if(inst.get(2).equals("draw")){
                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                if(inst.get(5).equals("0")){
                    gc.beginPath();
                    gc.lineTo(x, y);
                    gc.stroke();
                }
                if(inst.get(5).equals("1")){
                    gc.lineTo(x, y);
                    gc.stroke();
                }
            }
            else if(inst.get(2).equals("text")){
                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                String content = inst.get(5);
                gc.strokeText(content,x,y);
            }
            else if(inst.get(2).equals("line")){
                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                if(inst.get(5).equals("0")){
                    gc.beginPath();
                    gc.lineTo(x, y);
                    gc.stroke();
                }
                if(inst.get(5).equals("1")){
                    gc.lineTo(x,y);
                    gc.stroke();
                }
            }
            else if(inst.get(2).equals("erase")){
                double x = Double.parseDouble(inst.get(3));
                double y = Double.parseDouble(inst.get(4));
                gc.clearRect(x,y,Double.parseDouble(inst.get(5)),Double.parseDouble(inst.get(5)));
            }

            gc.setStroke(originalColor);
            gc.setLineWidth(originLineWidth);
        }
    }

    /**
     * For a single user, he press btn to send the msg to his own text area
     */
    public void controlSendMsg() {

        String msg = this.msgField.getText();
        boolean isEmpty = msg == null || msg.isEmpty();

        ClientAppFacade clientApp = ClientAppFacade.getInstance();
        String userName = clientApp.getUsername();

        if (!isEmpty) {
            this.msgArea.appendText(userName + ": " + msg + "\n");
            clientApp.sendMsg(msg);
            this.msgField.clear();
        }
    }

}
