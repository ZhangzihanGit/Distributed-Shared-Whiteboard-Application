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

/**
 * This class holds the responsibility for writing & retrieving data to local file system.
 * The class is only kept under DataServerApplication class and only responds to that database server.
 * The writing and retrieving functions are performed without authentication checking, since the
 * Authentication is checked before the DataWareHouse object is called.
 */
// Assume it is after authentication.
class DataWareHouse {

    private int numOfCanvas = 0;

    // Build a map such that each manager has a counter associated with it.
    // HashMap doesn't allow duplicate key value, which is good(since manager names cannot be the same. )
    // Duplication check is also done in the a
    private HashMap<String, Integer> mapManagerWithCanvas = null;

    DataWareHouse() {
        // This manager is responsible for creating the directory
        this.mapManagerWithCanvas = new HashMap<>();
    }

    /**
     * Create a json file for each save request.
     * Manager name should be provided.
     * @param message
     */
    void save(String managerName, JSONObject message){
        try{

            Path absolutePath = Paths.get(".").toAbsolutePath().normalize();

//            File file = new File(absolutePath.toString()+"storage/"+this.managerName+"-canvas.json");
            File directory = new File(absolutePath.toString()+"/storage"+"/"+managerName);
            directory.mkdirs();

//            String dbPathString = absolutePath.toString()+"/storage/"+this.managerName+"-canvas.json";
            String dbPathString = absolutePath.toString()+"/storage/"+managerName+"/"+"canvas"+numOfCanvas+".json";

            Path dbPath = Paths.get(dbPathString);
//            System.out.println(dbPathString);
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


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Retrieve file that contain the name for the manager.
    void retrieveData(String targetManager){

    }

}
