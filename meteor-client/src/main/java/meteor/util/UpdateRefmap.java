package meteor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;

/*
  This must be ran every revision for Jagex Reflection checks. (must use vm arg -noverify)
 */
public class UpdateRefmap {

  FontAwesomeIconView fontAwesomeIconView = null; // lol this is dumb but needed for something

  public static void main(String[] args) throws Exception {
    Map<String, Class<?>> classMap = new HashMap<>();
    Path path = new File("./runescape-client/build/classes/java/main/")
        .toPath();

    Files.walk(path)
        .filter(Files::isRegularFile)
        .forEach(f ->
        {
          String className = f
              .getName(f.getNameCount() - 1)
              .toString()
              .replace(".class", "");

          try {
            Class<?> clazz = Class.forName(className);

            ObfuscatedName obfuscatedName = clazz
                .getAnnotation(ObfuscatedName.class);

            if (obfuscatedName != null) {
              classMap.put(obfuscatedName.value(), clazz);
            }
          } catch (ClassNotFoundException ignore) {
          }
        });
    HashMap<String, String> refmap = new HashMap<>();
    for (String s : classMap.keySet())
    {
      refmap.put(s, classMap.get(s).getName());
    }
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (Writer writer = new FileWriter("./meteor-client/src/main/resources/osrs-refmap.json"))
    {
      gson.toJson(refmap, writer);
    }
    System.exit(0);
  }
}
