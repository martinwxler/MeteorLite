package meteor.util.bootstrap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.sponge.util.Logger;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Bootstrapper {

    private static final String version = "1.0.12";

    private static File shadowJar = new File("./meteor-client/build/libs/meteor-client-" + version + "-all.jar");
    private static File outputDir = new File("./meteor-client/build/bootstrap/");
    private static File updateOutput = new File("./meteor-client/build/bootstrap/bootstrap-meteorlite.json");
    private static File boostrapShadowJar = new File("./meteor-client/build/bootstrap/meteorlite.jar");
    private static Update update = new Update();

    private static Logger log = new Logger("Bootstrapper");

    private static MessageDigest md5Digest;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        update.version = version;
        md5Digest = MessageDigest.getInstance("MD5");
        processFile(shadowJar);
        saveBootstrap();
        copyShadowJar();
    }

    private static void copyShadowJar() {
        try {
            if (boostrapShadowJar.exists())
                boostrapShadowJar.delete();
            Files.copy(shadowJar.toPath(), new File("./meteor-client/build/bootstrap/meteorlite.jar").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveBootstrap() {
        outputDir.mkdirs();
        if (!updateOutput.exists()) {
            try {
                updateOutput.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!updateOutput.exists()) {
            try {
                updateOutput.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Writer writer = new FileWriter(updateOutput)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(update, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(File f) {
        if (!f.getName().endsWith(".msi")) {
            update.size = f.length();
            update.hash = getFileChecksum(md5Digest, f);
        }
    }

    private static String getFileChecksum(MessageDigest digest, File file)
    {
        //Get file input stream for reading the file content
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            //Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            //Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            };

            //close the stream; We don't need it now.
            fis.close();

            //Get the hash's bytes
            byte[] bytes = digest.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //return complete hash
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
