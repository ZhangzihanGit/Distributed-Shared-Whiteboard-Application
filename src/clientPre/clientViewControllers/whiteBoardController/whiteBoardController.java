package clientPre.clientViewControllers.whiteBoardController;

import clientApp.ClientAppFacade;
import clientPre.clientViewControllers.ClientGUIController;
import clientPre.pop_ups.AlertBox;
import clientPre.pop_ups.InputText;
import clientPre.pop_ups.OpenFrom;
import clientPre.pop_ups.SaveAs;
import com.sun.security.ntlm.Client;
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
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


public class whiteBoardController {
    private final static Logger logger = Logger.getLogger(whiteBoardController.class);
    private static whiteBoardController instance;
    private final char[] ILLEGAL_CHARACTERS = {'/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

    public static whiteBoardController getInstance() {
        if (instance == null) {
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
    private ListView listView;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane pane;
    @FXML
    private TextField msgField;
    @FXML
    private ScrollPane msgPane;

    static private TextArea msgArea = new TextArea();
    private ImageCursor cursor;


    private String clientType = "manager";

    private String mode = "draw";

    private String saveFilePath = "";

    private double[] beginCoordinate = {0, 0};

    private double[] drawCoordinate = {0, 0};

    private double[] lineCoordinate = {0, 0};

    private void sendMsgAndRecordIt(String msg) {
        ClientAppFacade.getInstance().updateWb(msg, "");
    }

    private void initLeftButtons() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.setStyle("-fx-background-color: white");
        label.setText("1.0");

        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e -> {
            gc.setStroke(colorPicker.getValue());
        });

        slider.setMin(1);
        slider.setMax(20);
        slider.valueProperty().addListener(e -> {
            double value = slider.getValue();
            String str = String.format("%.1f", value);
            label.setText(str);
            gc.setLineWidth(value);
        });
    }

    @FXML
    private void onClickPencil() {
        mode = "draw";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/pencil.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickEraser() {
        mode = "erase";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/eraser.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickRectan() {
        mode = "rectangle";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/rectangle.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickLine() {
        mode = "line";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/line.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickCircle() {
        mode = "circle";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/circle.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickOval() {
        mode = "oval";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/oval.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }

    @FXML
    private void onClickText() {
        mode = "text";
//        this.cursor = new ImageCursor(new Image(getClass().getResource("../../../assets/imgs/text.png").toExternalForm()));
//        canvas.setCursor(cursor);
    }


    private void initDrawMethods() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(e -> {
            double x = e.getX();
            double y = e.getY();
            String msg = "";
            if (mode.equals("draw")) {
                drawCoordinate[0] = x;
                drawCoordinate[1] = y;
            } else if (mode.equals("line")) {
                lineCoordinate[0] = x;
                lineCoordinate[1] = y;
            } else if (mode.equals("circle") || mode.equals("rectangle") || mode.equals("oval")) {
                beginCoordinate[0] = x;
                beginCoordinate[1] = y;
            } else if (mode.equals("text")) {
                InputText inputText = new InputText();
                String content = inputText.display();
                if (content != null) {
                    gc.fillText(content, x, y);
                    msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + x + "," + y + "," + content;
                    sendMsgAndRecordIt(msg);
                }
            }
        });

        canvas.setOnMouseReleased(e -> {
            double x = e.getX();
            double y = e.getY();
            double originX = beginCoordinate[0];
            double originY = beginCoordinate[1];
            double width = Math.abs(x - originX);
            double height = Math.abs(y - originY);
            double upLeftX = (originX - x > 0) ? x : originX;
            double upLeftY = (originY - y > 0) ? y : originY;
            double distance = Math.sqrt(Math.pow(x - originX, 2) + Math.pow(y - originY, 2));
            double middleX = (originX + x) / 2;
            double middleY = (originY + y) / 2;
            String msg = "";
            if (mode.equals("line")) {
//                gc.lineTo(e.getX(), e.getY());
//                gc.stroke();
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + lineCoordinate[0] + ","
                        + lineCoordinate[1] + "," + x + "," + y;
                sendMsgAndRecordIt(msg);
            } else if (mode.equals("rectangle")) {
                gc.strokeRect(upLeftX, upLeftY, width, height);
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + upLeftX
                        + "," + upLeftY + "," + width + "," + height;
                sendMsgAndRecordIt(msg);
            } else if (mode.equals("circle")) {
                gc.strokeOval(middleX - distance / 2, middleY - distance / 2, distance, distance);
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + (middleX - distance / 2)
                        + "," + (middleY - distance / 2) + "," + distance + "," + distance;
                sendMsgAndRecordIt(msg);
            } else if (mode.equals("oval")) {
                gc.strokeOval(upLeftX, upLeftY, width, height);
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + upLeftX
                        + "," + upLeftY + "," + width + "," + height;
                sendMsgAndRecordIt(msg);
            }
        });

        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            String msg = "";
            if (mode.equals("draw")) {
                double x1 = drawCoordinate[0];
                double y1 = drawCoordinate[1];
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + x1
                        + "," + y1 + "," + x + "," + y + ",";
                drawCoordinate[0] = x;
                drawCoordinate[1] = y;
                sendMsgAndRecordIt(msg);

            } else if (mode.equals("erase")) {
                gc.clearRect(x, y, slider.getValue(), slider.getValue());
                msg = gc.getStroke() + "," + gc.getLineWidth() + "," + mode + "," + x + "," + y + "," + slider.getValue();
                sendMsgAndRecordIt(msg);
            }
        });
    }

    ObservableList<String> list = FXCollections.observableArrayList(
            "Manager", "coworker1", "coworker2", "coworker3");

    private void initListView(ObservableList<String> list) {
        listView.setItems(list);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new StringAndButtonList(clientType);
            }
        });
    }

    public void initialize() {
        msgArea.setPrefSize(1199, 272);
        msgArea.setWrapText(true);
        msgPane.setContent(msgArea);
        msgPane.setFitToWidth(true);
        msgArea.setEditable(false);
        pane.getChildren().add(msgArea);
        pane.getChildren().add(canvas);
        initLeftButtons();
        boolean isManager = ClientAppFacade.getInstance().isManager();
        if (isManager) {
            clientType = "manager";
        } else {
            clientType = "client";
        }
        if (!clientType.equals("manager")) {
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

    private boolean isFileNameValid(String fileName) {

        if (fileName == null || fileName.length() > 255) {
            return false;
        } else {

            for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
                if (fileName.contains(Character.toString(ILLEGAL_CHARACTERS[i]))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveAs() {
        SaveAs saveAsBox = new SaveAs();
        List<String> saveInfo = saveAsBox.display();

        if (saveInfo.size() == 3) {
            String fileLocation = saveInfo.get(0);
            String fileName = saveInfo.get(1);
            String fileType = saveInfo.get(2);

            if (this.isFileNameValid(fileName)) {
                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);
                WritableImage image = canvas.snapshot(sp, null);
                String filePath = fileLocation + "/" + fileName + "." + fileType;
                File file = new File(filePath);

                if (fileType.equals("gif") || fileType.equals("png")) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        AlertBox box = new AlertBox();
                        box.display("information", "You have successfully saved the picture");
                    } catch (IOException ex) {
                        AlertBox box = new AlertBox();
                        box.display("information", "Saving failed!");
                    }
                }

                saveFilePath = filePath;
            } else {
                ClientGUIController.getInstance().showErrorView("fileName", "Invalid filename", "");
            }
        }
    }

    public void newCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        gc.clearRect(0, 0, w, h);
        saveFilePath = "";
        String msg = "newWB";
        sendMsgAndRecordIt(msg);
    }

    public void save() {
        if (!saveFilePath.equals("")) {
            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            WritableImage image = canvas.snapshot(sp, null);
            File file = new File(saveFilePath);
            String[] array = saveFilePath.split("[.]");
            String fileType = array[1];
            if (!fileType.equals("wb")) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), fileType, file);
                    AlertBox box = new AlertBox();
                    box.display("information", "You have successfully saved the picture");
                } catch (IOException ex) {
                    AlertBox box = new AlertBox();
                    box.display("information", "Saving failed!");
                }
            }


        } else {
            saveAs();
        }
    }

    public void open() throws Exception {
        OpenFrom openFrom = new OpenFrom();
        String filePath = openFrom.display();
        if (!filePath.isEmpty()) {
            File f = new File(filePath);
            String encodstring = encodeFileToBase64Binary(f);
            ClientAppFacade.getInstance().updateWb("open" + "," + encodstring, "");
            saveFilePath = filePath;
        }
    }

    private static String encodeFileToBase64Binary(File file) throws Exception {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void close() {
        ClientAppFacade.getInstance().closeWb();
    }

    public void updateUserList(String msg) {
        StringAndButtonList.list.setAll(Arrays.asList(msg.split(",")));
    }

    public void updateWhiteBoard(String msg) throws IOException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Paint originalColor = gc.getStroke();
        double originLineWidth = gc.getLineWidth();
        ArrayList<String> inst = new ArrayList<>(Arrays.asList(msg.split(",")));

        if (inst.get(0).equals("newWB")) {
            double h = canvas.getHeight();
            double w = canvas.getWidth();
            gc.clearRect(0, 0, w, h);
            saveFilePath = "";
        } else if (inst.get(0).equals("open")) {
            String imageString = inst.get(1);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(bis);
            double h = canvas.getHeight();
            double w = canvas.getWidth();
            gc.clearRect(0, 0, w, h);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            gc.drawImage(image, 0, 0, w, h);
        } else {
            Color c = Color.web(inst.get(0), 1.0);
            gc.setStroke(c);
            gc.setLineWidth(Double.parseDouble(inst.get(1)));
            double x = Double.parseDouble(inst.get(3));
            double y = Double.parseDouble(inst.get(4));
            if (inst.get(2).equals("oval")) {
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));

                gc.strokeOval(x, y, width, height);
            } else if (inst.get(2).equals("rectangle")) {
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));

                gc.strokeRect(x, y, width, height);
            } else if (inst.get(2).equals("circle")) {
                double width = Double.parseDouble(inst.get(5));
                double height = Double.parseDouble(inst.get(6));

                gc.strokeOval(x, y, width, height);
            } else if (inst.get(2).equals("draw")) {
                double x2 = Double.parseDouble(inst.get(5));
                double y2 = Double.parseDouble(inst.get(6));
                gc.strokeLine(x, y, x2, y2);
            } else if (inst.get(2).equals("text")) {
                String content = inst.get(5);
                gc.strokeText(content, x, y);
            } else if (inst.get(2).equals("line")) {
                double x2 = Double.parseDouble(inst.get(5));
                double y2 = Double.parseDouble(inst.get(6));
                gc.strokeLine(x, y, x2, y2);
            } else if (inst.get(2).equals("erase")) {
                gc.clearRect(x, y, Double.parseDouble(inst.get(5)), Double.parseDouble(inst.get(5)));
            }
        }


        gc.setStroke(originalColor);
        gc.setLineWidth(originLineWidth);

    }

    /**
     * For a single user, he press btn to send the msg to his own text area
     */
    public void controlSendMsg() {

        String msg = this.msgField.getText();
        boolean isEmpty = msg == null || msg.isEmpty();


        ClientAppFacade clientApp = ClientAppFacade.getInstance();
        String userName = clientApp.getUsername();

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        logger.info(time);

        if (!isEmpty) {
            clientApp.sendMsg(time, msg);
            this.msgField.clear();
        }
    }

    public void updateNewUserWB(String username) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(sp, null);
        String encodstring = encodeToString(image, "png");
        ClientAppFacade.getInstance().updateWb("open," + encodstring, username);

    }

    private static String encodeToString(WritableImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }


    public void updateMessage(String msg) {
        msgArea.appendText(msg + "\n");
    }

    public void clearTextArea() {
        msgArea.clear();
    }
}

