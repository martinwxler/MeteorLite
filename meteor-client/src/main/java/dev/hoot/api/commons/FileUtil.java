package dev.hoot.api.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import meteor.MeteorLiteClientLauncher;
import meteor.plugins.Plugin;
import org.sponge.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileUtil {
    private static final Logger log = new Logger("FileUtil");
    private static final File DATA_DIR = MeteorLiteClientLauncher.DATA_DIR;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void serialize(Plugin plugin, String fileName, Serializable data) {
        serialize(plugin.getClass().getSimpleName(), fileName, data);
    }

    public static void serialize(String dirName, String fileName, Serializable data) {
        File pluginDir = new File(DATA_DIR, dirName);
        try (FileOutputStream fos = new FileOutputStream(new File(pluginDir, fileName));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            log.error("Error serializing file: {}", e.getMessage());
        }
    }

    public static <T> T deserialize(Plugin plugin, String fileName) {
        return deserialize(plugin.getClass().getSimpleName(), fileName);
    }

    public static <T> T deserialize(String dirName, String fileName) {
        File pluginDir = new File(DATA_DIR, dirName);
        try (FileInputStream fis = new FileInputStream(new File(pluginDir, fileName));
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error deserializing file: {}", e.getMessage());
            return null;
        }
    }

    public static void writeJson(Plugin plugin, String fileName, Object data) {
        writeJson(plugin.getClass().getSimpleName(), fileName, data);
    }

    public static void writeJson(String dirName, String fileName, Object data) {
        File pluginDir = new File(DATA_DIR, dirName);
        try (FileWriter writer = new FileWriter(new File(pluginDir, fileName))) {
            GSON.toJson(data, writer);
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing json: {}", e.getMessage());
        }
    }

    public static <T> T readJson(Plugin plugin, String fileName, Class<T> type) {
        return readJson(plugin.getClass().getSimpleName(), fileName, type);
    }

    public static <T> T readJson(String dirName, String fileName, Class<T> type) {
        File pluginDir = new File(DATA_DIR, dirName);
        try (FileReader reader = new FileReader(new File(pluginDir, fileName))) {
            return GSON.fromJson(reader, type);
        } catch (IOException e) {
            log.error("Error reading json: {}", e.getMessage());
            return null;
        }
    }
}
