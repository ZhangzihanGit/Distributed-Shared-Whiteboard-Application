package dataServerApp;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
// TODO: 修改现有的加密方式： 新用户注册后，产生一个随机salt，以及进行md5 hash，一起写入authentication.txt. 格式为： [username]:[passhash]:[salt]
import org.apache.log4j.Logger;

public class Cipher {
    private final Logger logger = Logger.getLogger(Cipher.class);
    private String encryptPassword = null;   // EncryptPassword means to keep secret.
    private String salt;
    private KeyGenerator keyGenerator;
    private static SecretKey secretKey;
    private static javax.crypto.Cipher cipher;

    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;

    private static Cipher instance =null;
    private Path storagePath;
    private Cipher() {
//        try {
//
//            // SHA-256 is not secure(can be exposed quickly)
////            this.md = MessageDigest.getInstance("SHA-256");
////            this.storagePath = Paths.get(Paths.get(".").toAbsolutePath().normalize().toString()
////                    +"/storage");
////            initialise();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private String decodeToString(byte[] input){
        Base64.Encoder encoder = Base64.getEncoder();
        logger.info("New generated salt is: "+ encoder.encodeToString(input));
        return encoder.encodeToString(input);
    }

    private String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        return decodeToString(bytes);
    }
    public String[] getSaltAndPassSalt(String password){
        logger.info("Salt generate once");
        String salt = generateSalt();
        String passsalt = generatePassHash(salt, password);
        logger.info("Salt is: "+salt +"   " +passsalt);
        String[] output = new String[2];
        output[0] = passsalt;
        output[1] = salt;
        return output;
    }
    private String generatePassHash(String salt, String password){
        return decodeToString(hash(password,salt));
    }

//    public boolean saveUserHash(String username, String password){
//        String salt = generateSalt();
//        String output = "";
//        String passhash = decodeToString(hash(password,salt));
//        try{
//            logger.info("Write to authentication.txt file");
//            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("authentication.txt"),"UTF-8"));
//            writer.write(username+";"+passhash+";"+salt);
//            writer.flush();
//            writer.close();
//            return true;
//        }catch (IOException e){
//            logger.info("Locally stored failed");
//            e.printStackTrace();
//            return false;
//        }
//    }
    // Check if the password matches.
    public boolean isExpectedPassword(String passToCheck, String salt, String expectHash){
        byte[] pwdHash = hash(passToCheck, salt);
        Arrays.fill(passToCheck.toCharArray(), Character.MIN_VALUE);

        String decodeString = decodeToString(pwdHash);
        if(!decodeString.equals(expectHash)){
            logger.info("@@@@@@@@@@@ / "+decodeString + " @@@@@@@@/"+expectHash);
            return false;
        }
//        if (pwdHash.length != expectHash.getBytes().length) {
//            logger.info("Length is not the same.");
//            return false;
//        }
//        for (int i = 0; i < pwdHash.length; i++) {
//            logger.info("The index is: "+i);
//            logger.info("@@@"+pwdHash[i]+"####"+expectHash.getBytes()[i]);
//            if (pwdHash[i] != expectHash.getBytes()[i]) return false;
//        }
        return true;
    }

    private byte[] hash(String password, String salt){
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
        Arrays.fill(password.toCharArray(),Character.MIN_VALUE);
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            spec.clearPassword();
        }
        return null;
    }
    public static Cipher getInstance(){
        if(instance == null){
            instance = new Cipher();
        }
        Logger.getLogger(Cipher.class).info("KEYKEYKYE: "+secretKey);
        return instance;
    }
    private void initialise(){
        if(!Files.exists(storagePath)){
            try {
                Files.createDirectories(Paths.get(storagePath.toString()+"/"));
            }catch (IOException e){
                logger.fatal("Fail to create the directory for storing authentication details. ");
                e.printStackTrace();
            }
        }
    }
    public String encrypt(String password){
        try{
            byte[] plainTextByte = password.getBytes();
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
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
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
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
