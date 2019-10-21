package dataServerApp;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Cypher {
    private final Logger logger = Logger.getLogger(Cypher.class);
    private final String encrypPassword;
    private final String salt;
    private final TextEncryptor encryptor;
    private final TextEncryptor decryptor;

    public Cypher(String password){
        this.encrypPassword = readPassword();
        logger.info("Password read is: "+this.encrypPassword);
        this.salt = KeyGenerators.string().generateKey();
        this.encryptor = encryptor();
        this.decryptor = decryptor();
    }
    private String readPassword(){
        try{
            logger.info(new File(".").getAbsolutePath());
            BufferedReader br = new BufferedReader(new FileReader("password.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            // Ignore the line separator.
            while(line!=null){
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private TextEncryptor encryptor(){
        TextEncryptor encryptor = Encryptors.text(this.encrypPassword, this.salt);
        return encryptor;
    }
    private TextEncryptor decryptor(){
        TextEncryptor decryptor = Encryptors.text(this.encrypPassword,this.salt);
        return decryptor;
    }
    private String encrypt(String password){
        return this.encryptor.encrypt(password);
    }
    private String decrypt(String encryptText){
        return this.decrypt(encryptText);
    }
}
