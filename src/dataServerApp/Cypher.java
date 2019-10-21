package dataServerApp;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Cypher {
    private final Logger logger = Logger.getLogger(Cypher.class);
    private final String encryptPassword;   // EncryptPassword means to keep secret.
    private final String salt;
    private final TextEncryptor encryptor;
    private final TextEncryptor decryptor;

    private final String encodedPassword; // This can be exposed to outside and stored in file system.

    public Cypher(String password){
        this.encryptPassword = readPassword();
        this.salt = KeyGenerators.string().generateKey();
        this.encryptor = encryptor();
        this.decryptor = decryptor();
        encodedPassword = encrypt(password);
        logger.info("Password encrypted: "+encodedPassword);
    }
    private String readPassword(){
        try{
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
        return Encryptors.text(this.encryptPassword, this.salt);
    }
    private TextEncryptor decryptor(){
        return Encryptors.text(this.encryptPassword,this.salt);
    }
    String encrypt(String password){
        return this.encryptor.encrypt(password);
    }
    String decrypt(String encryptText){
        return this.decryptor.decrypt(encryptText);
    }
    String getEncrypPassword(){
        return this.encodedPassword;
    }
}
