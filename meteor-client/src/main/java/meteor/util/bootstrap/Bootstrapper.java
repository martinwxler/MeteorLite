package meteor.util.bootstrap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.sponge.util.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Bootstrapper {

    private static File jpackageDir = new File("./meteor-client/build/jpackage/meteor-client/");
    private static File outputDir = new File("./meteor-client/build/bootstrap/");
    private static File filesOutput = new File("./meteor-client/build/bootstrap/bootstrap.json");
    private static File headerOutput = new File("./meteor-client/build/bootstrap/header.json");
    private static List<UpdateFile> clientFiles = new ArrayList<>();
    private static UpdateHeader header = new UpdateHeader();

    private static Logger log = new Logger("Bootstrapper");

    private static MessageDigest md5Digest;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        header.versionMajor = 1;
        header.versionMinor = 0;
        header.versionPatch = 1;
        md5Digest = MessageDigest.getInstance("MD5");
        populateClientFiles();
        saveBootstrap();
    }

    private static void saveBootstrap() {
        outputDir.mkdirs();
        if (!filesOutput.exists()) {
            try {
                filesOutput.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!headerOutput.exists()) {
            try {
                headerOutput.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Writer writer = new FileWriter(headerOutput)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(header, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new FileWriter(filesOutput)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(clientFiles, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void populateClientFiles() {
        if (jpackageDir != null) {
            if (!jpackageDir.exists())
                jpackageDir.mkdirs();
            if (jpackageDir.listFiles() != null)
                for (File f : jpackageDir.listFiles()) {
                    if (f.isDirectory())
                        scanDirectory(f);
                    else
                        processFile(f);
                }
        }
    }

    private static void scanDirectory(File f) {
        if (f.listFiles() != null)
            for (File f1 : f.listFiles())
                if (f1.isDirectory())
                    scanDirectory(f1);
                else
                    processFile(f1);

    }

    private static void processFile(File f) {
        if (!f.getName().endsWith(".msi")) {
            UpdateFile file = new UpdateFile();
            file.relativeName = f.getPath().replace(".\\meteor-client\\build\\jpackage\\meteor-client\\", "");
            file.size = f.length();
            file.hash = getFileChecksum(md5Digest, f);
            //log.debug(f.getPath());
            clientFiles.add(file);
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
