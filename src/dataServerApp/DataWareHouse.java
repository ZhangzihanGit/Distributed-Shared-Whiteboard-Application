package dataServerApp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * This class holds the responsibility for writing & retrieving data to local file system.
 * The class is only kept under DataServerApplication class and only responds to that database server.
 * The writing and retrieving functions are performed without authentication checking, since the
 * Authentication is checked before the DataWareHouse object is called.
 */
// Assume it is after authentication.
public class DataWareHouse {
    private Logger logger = Logger.getLogger(DataWareHouse.class);

    private int numOfCanvas = 0;
    private final static String STORAGE = "authentication.txt";

    // Build a map such that each manager has a counter associated with it.
    // HashMap doesn't allow duplicate key value, which is good(since manager names cannot be the same. )
    // Duplication check is also done in the a
    private Path absolutePath = null;
    private Path storagePath = null;
    private HashMap<String, String[]> localPassbook = null;

    public DataWareHouse() {
        this.localPassbook = new HashMap<>();
        this.absolutePath = Paths.get(".").toAbsolutePath().normalize();
        this.storagePath = Paths.get(this.absolutePath.toString()+"/storage");
        initialiseStorage();
//        iteratePassbook();
    }
    private void initialiseStorage(){
        if(!Files.exists(storagePath)){
            createDirectory();
        }
        loadDb();
    }
    public HashMap<String, String[]> getLocalPassbook(){
        return this.localPassbook;
    }
    void writeDb(String username, String hashpass, String salt){
        try{
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream
                    (this.storagePath+"/"+STORAGE,true),"utf-8"));
            writer.append("\n").append(username).append(";").append(hashpass).append(";").append(salt);
            writer.flush();
            writer.close();
        }catch (IOException e){
            logger.fatal("Error writing to the local storage. Abort.");
            e.printStackTrace();
        }
    }
    private void loadDb(){
        try{
            logger.info("Storge file is: "+this.storagePath+"/"+STORAGE);
            BufferedReader br = new BufferedReader(new FileReader(this.storagePath+"/"+STORAGE));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            String[] hashAndSalt = new String[2];
            hashAndSalt[0] = line.split(";")[1];
            hashAndSalt[1] = line.split(";")[2];
            this.localPassbook.put(line.split(";")[0],hashAndSalt);
            while(line!=null){
                if(line.isEmpty()){
                    line=br.readLine();
                    continue;
                }
                // [0] is hashpass
                hashAndSalt[0] = line.split(";")[1];
                // [1] is salt
                hashAndSalt[1] = line.split(";")[2];
                this.localPassbook.put(line.split(";")[0],hashAndSalt);
                line = br.readLine();
            }
        }catch (IOException e){
            logger.fatal("Error loading the local storage file. ");
            e.printStackTrace();
        }
    }
    public void iteratePassbook(){
        Iterator it = this.localPassbook.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair= (Map.Entry)it.next();
            logger.info(pair.getKey().toString() + "  "+pair.getValue().toString());
        }
    }


    // Only used if the storage directory is not found.
    public void createDirectory(){
        try {
            logger.info(absolutePath);
            Files.createDirectories(Paths.get(absolutePath.toString()+"/storage/"));
        }catch (IOException e){
            logger.fatal("Fail to create the directory for storing authentication details. ");
            e.printStackTrace();
        }
    }
}
