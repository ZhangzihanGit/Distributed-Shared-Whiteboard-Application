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

/**
 * This class holds the responsibility for writing & retrieving data to local file system.
 * The class is only kept under DataServerApplication class and only responds to that database server.
 * The writing and retrieving functions are performed without authentication checking, since the
 * Authentication is checked before the DataWareHouse object is called.
 */
// Assume it is after authentication.
class DataWareHouse {
    private String managerName = null;

    DataWareHouse(String managerName) {
        this.managerName = managerName;
    }
    void save(JSONObject message){
        try{
//            File file = new File("storage/"+this.managerName+"-canvas.json");
//            FileWriter writer = new FileWriter(file);
//            System.out.println("File saved");
//            writer.write(message.toJSONString());
//            writer.flush();
//            writer.close();

//            File file = new File("storage/"+this.managerName+"-canvas.json");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            outputStream.write(message.toJSONString().getBytes());
//            outputStream.flush();
//            outputStream.close();


                // 这种可以写出，但是不知道为什么没有新建文件夹。
//            Charset charset = Charset.forName("US-ASCII");
//            Path path = FileSystems.getDefault().getPath("storage", this.managerName+"-canvas.json");
//            BufferedWriter bufferedWriter = Files.newBufferedWriter(path, charset,CREATE);
//            bufferedWriter.write(message.toJSONString());
//            System.out.println("File saved");
//            bufferedWriter.flush();
//            bufferedWriter.close();

//            String output = (String) message.toJSONString();
//            byte data[]= output.getBytes();
//            Path path = Paths.get("storage", this.managerName+"-canvas.json");
//            File file = new File("storage/"+this.managerName+"-canvas.json");
//            System.out.println(path);
//            Path relativePath = Paths.get(".").toAbsolutePath().normalize();
//            System.out.println(relativePath.toString());

//            file.mkdirs();
//            file.createNewFile();
//            OutputStream out = new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND));
//            out.write(data);
//            out.flush();
//            out.close();

            Path absolutePath = Paths.get(".").toAbsolutePath().normalize();

//            File file = new File(absolutePath.toString()+"storage/"+this.managerName+"-canvas.json");
            File directory = new File(absolutePath.toString()+"/storage");
            directory.mkdirs();

            String dbPathString = absolutePath.toString()+"/storage/"+this.managerName+"-canvas.json";
            Path dbPath = Paths.get(dbPathString);
            System.out.println(dbPathString);
            File data = new File(dbPathString);
            data.createNewFile();

            OutputStream out = new BufferedOutputStream(Files.newOutputStream(dbPath, CREATE, APPEND));
            out.flush();
            out.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
