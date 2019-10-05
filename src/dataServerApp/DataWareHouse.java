package dataServerApp;

import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.util.HashMap;
import org.apache.log4j.Logger;
/**
 * This class holds the responsibility for writing & retrieving data to local file system.
 * The class is only kept under DataServerApplication class and only responds to that database server.
 * The writing and retrieving functions are performed without authentication checking, since the
 * Authentication is checked before the DataWareHouse object is called.
 */
// Assume it is after authentication.
class DataWareHouse {
    private Logger logger = Logger.getLogger(DataWareHouse.class);

    private int numOfCanvas = 0;

    // Build a map such that each manager has a counter associated with it.
    // HashMap doesn't allow duplicate key value, which is good(since manager names cannot be the same. )
    // Duplication check is also done in the a
    private HashMap<String, Integer> mapManagerWithCanvas = null;
    private Path absolutePath = null;

    DataWareHouse() {
        // This manager is responsible for creating the directory
        this.mapManagerWithCanvas = new HashMap<>();
        this.absolutePath = Paths.get(".").toAbsolutePath().normalize();
    }


    /**
     * Save the message from Web Server.
     * Both manager Name and registered message should be provided.
     * @param managerName The name of the manager. Will be the directory.
     * @param message Can be some other format/to be discussed more
     */
    boolean save(String managerName, JSONObject message){
        try{

            File directory = new File(absolutePath.toString()+"/storage"+"/"+managerName);
            directory.mkdirs();

//            String dbPathString = absolutePath.toString()+"/storage/"+this.managerName+"-canvas.json";
            String dbPathString = absolutePath.toString()+"/storage/"+managerName+"/"+"canvas"+numOfCanvas+".json";
            Path dbPath = Paths.get(dbPathString);
//            File data = new File(dbPathString);
//            data.createNewFile();

            OutputStream out = new BufferedOutputStream(Files.newOutputStream(dbPath, CREATE_NEW));
            out.write("\n".getBytes());
            byte writeOutput[] =message.toJSONString().getBytes();
            out.write(writeOutput);
            out.flush();
            out.close();

            this.numOfCanvas++;
            this.mapManagerWithCanvas.put(managerName, numOfCanvas);
            return true;

        }catch (IOException e){
            logger.info("File saved failed. ");
            e.printStackTrace();
            return false;
        }
    }
    // TODO: 未完成： 需要返回一个Canva, 不是String.
    String retrieveData(String targetManager){
        try{
            int order = numOfCanvas -1;
            String dbPathString = absolutePath.toString()+"/storage/"+targetManager+"/"+"canvas"+order+".json";
            Path dbPath = Paths.get(dbPathString);

            InputStream inputStream = new BufferedInputStream(Files.newInputStream(dbPath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // 这一部分的返回值将会因Web Server而改变。
            ////////////////////////////////////////
            String line =null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
            }
            return null;
            ////////////////////////////////////////

        }catch (Exception e){
            logger.info("Target Manager not found on database. ");
            e.printStackTrace();

        }
        return null;
    }
    void deleteManagerData(String targetManager){

    }

    public HashMap<String, Integer> getMapManagerWithCanvas() {
        return mapManagerWithCanvas;
    }
}
