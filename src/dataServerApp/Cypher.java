package dataServerApp;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class Cypher {
    private final Logger logger = Logger.getLogger(Cypher.class);
    private String encryptPassword = null;   // EncryptPassword means to keep secret.
    private String salt;
    private KeyGenerator keyGenerator;
    private static SecretKey secretKey;
    private static Cipher cipher;

    private String encodedPassword = null; // This can be exposed to outside and stored in file system.
    private static Cypher instance =null;
    private Cypher() {
        try {

            this.keyGenerator = KeyGenerator.getInstance("AES");
            this.keyGenerator.init(128); // block size is 128bits
            secretKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");

            initialise();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Cypher getInstance(){
        if(instance == null){
            instance = new Cypher();
        }
        Logger.getLogger(Cypher.class).info("KEYKEYKYE: "+secretKey);
        return instance;
    }
    private void initialise(){
        if(!Files.exists(Paths.get("key.txt"))){
            writeKey();
        }
        else{
            loadSecretKey();
        }
    }
    public String encrypt(String password){
        try{
            byte[] plainTextByte = password.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedByte);
            return encryptedText;
        }catch (Exception e){
            logger.fatal("Encryption fail. ");
            e.printStackTrace();
            return null;
        }
    }
    public String decrypt(String encryptPassword){
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedTextByte = decoder.decode(encryptPassword);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            String decryptedText = new String(decryptedByte);
            return decryptedText;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private boolean writeKey(){
        try{
            String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            logger.info("Write!!!");
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("key.txt"),"UTF-8"));
            writer.write(key);
            writer.flush();
            writer.close();
            return true;
        }catch (IOException e){
            logger.info("Store key failing.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean loadSecretKey () {
        try {
            BufferedReader br = new BufferedReader(new FileReader("key.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            // Ignore the line separator.
            byte[] decodedKey = Base64.getDecoder().decode(line);
            this.secretKey = new SecretKeySpec(decodedKey, "AES");
            return true;
        } catch (IOException e) {
            logger.info("Loading key fail. ");
            e.printStackTrace();
            return false;
        }
    }
}
