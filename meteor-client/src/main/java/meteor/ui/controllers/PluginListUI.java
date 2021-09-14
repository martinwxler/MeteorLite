package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import meteor.MeteorLiteClientLauncher;
import meteor.MeteorLiteClientModule;
import meteor.PluginManager;
import meteor.config.ConfigGroup;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.events.ExternalsReloaded;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.components.Category;
import meteor.ui.components.CategoryMenuItem;
import meteor.ui.components.PluginConfigButton;
import meteor.ui.components.PluginToggleButton;
import net.runelite.api.Client;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static meteor.MeteorLiteClientModule.pluginsPanelVisible;

public class PluginListUI {

  @FXML
  private FontAwesomeIconView addCategory;

  @FXML
  private VBox pluginList;

  @FXML
  public ScrollPane scrollPane;

  public static Plugin lastPluginInteracted;

  public static Map<String, PluginToggleButton> configGroupPluginMap = new HashMap<>();

  public static Map<Plugin, AnchorPane> pluginPanels = new HashMap<>();

  public static boolean overrideToggleListener = false;

  @Inject
  private ConfigManager configManager;

  public static PluginListUI INSTANCE;

  @Inject
  private EventBus eventBus;

  public static ArrayList<Category> categories = new ArrayList<>();

  @FXML
  public void initialize() {
    MeteorLiteClientModule.instanceInjectorStatic.injectMembers(this);
    eventBus.register(this);
    INSTANCE = this;
    categories.clear();

    addCategory.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      createCategory();
      refreshPlugins();
    });

    String[] categoriesConfigArray = null;
    String categoriesConfig = configManager.getConfiguration("meteorlite", "categories");
    if (categoriesConfig != null) {
      categoriesConfigArray = categoriesConfig.split(",");
    }

    if (categoriesConfigArray != null) {
      for (String name : categoriesConfigArray) {
        if (name.length() > 0) {
          Category category = new Category(name);
          String categoryPluginsConfig = configManager.getConfiguration("category", name);

          if (categoryPluginsConfig != null) {
            String[] categoryPlugins = categoryPluginsConfig.split(",");
            for (String s : categoryPlugins) {
              if (!s.equals("")) {
                category.plugins.add(s);
              }
            }
          }

          categories.add(category);
        }
      }
    }

    Category all = new Category("MeteorLite");
    for (Plugin p : PluginManager.plugins) {
      all.plugins.add(p.getName());
    }

    all.open = true;
    categories.add(all);

    saveCategories();
    refreshPlugins();
  }

  public void saveCategories() {
    StringBuilder categoriesConfig = new StringBuilder();
    int i = 0;

    for (Category c : categories) {
      StringBuilder pluginsConfig = new StringBuilder();

      if (!c.name.equals("MeteorLite")) {
        int k = 0;

        for (String p : c.plugins) {
          if (k != 0) {
            pluginsConfig.append(",");
          }

          pluginsConfig.append(p);
          k++;
        }

        configManager.setConfiguration("category", c.name, pluginsConfig.toString());
        if (i != 0) {
          categoriesConfig.append(",");
        }

        categoriesConfig.append(c.name);
        i++;
      }
    }

    configManager.setConfiguration("meteorlite", "categories", categoriesConfig.toString());
    configManager.saveProperties(true);
  }

  public void refreshPlugins() {
    pluginList.getChildren().clear();
    pluginPanels.clear();
    configGroupPluginMap.clear();

    for (Category c : categories) {
      AnchorPane categoryPanel = new AnchorPane();
      categoryPanel.setStyle("-fx-border-color: transparent;");
      FontAwesomeIconView tick;
      if (!c.open) {
        tick = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_RIGHT);
      } else {
        tick = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOWN);
      }

      tick.setFill(Paint.valueOf("CYAN"));
      tick.setSize("28");
      AnchorPane.setLeftAnchor(tick, 8.0);
      AnchorPane.setTopAnchor(tick, 8.0);
      AnchorPane.setBottomAnchor(tick, 8.0);

      categoryPanel.setStyle("-fx-background-color: #212121; -fx-border-style: solid;  -fx-border-color: #121212; -fx-border-width: 1;");
      categoryPanel.getStylesheets().add("css/plugins/jfx-contextmenu.css");
      Text categoryName = new Text();
      categoryName.setText(c.name);
      categoryName.setFill(Paint.valueOf("CYAN"));
      AnchorPane.setLeftAnchor(categoryName, 44.0);
      AnchorPane.setTopAnchor(categoryName, 8.0);
      categoryName.setFont(Font.font(18));

      ContextMenu contextMenu = new ContextMenu();
      contextMenu.setStyle("-fx-highlight-fill: #424242");
      int position = getCategoryPosition(c);

      if (position > 0) {
        contextMenu.setStyle("-fx-background-color: #121212;");
        CategoryMenuItem moveUp = new CategoryMenuItem(c, null, "Move up");
        moveUp.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan; -fx-highlight-fill: #424242");
        moveUp.setOnAction((event) -> {
          moveUp(c);
          saveCategories();
        });

        contextMenu.getItems().add(moveUp);
      }

      if (position < categories.size() - 1) {
        contextMenu.setStyle("-fx-background-color: #121212;");
        CategoryMenuItem moveDown = new CategoryMenuItem(c, null, "Move Down");
        moveDown.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan; -fx-highlight-fill: #424242");
        moveDown.setOnAction((event) -> {
          moveDown(c);
          saveCategories();
        });

        contextMenu.getItems().add(moveDown);
      }

      if (!c.name.equals("MeteorLite")) {
        contextMenu.setStyle("-fx-background-color: #121212;");
        CategoryMenuItem moveDown = new CategoryMenuItem(c, null, "Remove");
        moveDown.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan; -fx-highlight-fill: #424242");
        moveDown.setOnAction((event) -> {
          remove(c);
          saveCategories();
        });
        contextMenu.getItems().add(moveDown);
      }

      categoryPanel.getChildren().add(tick);
      categoryPanel.getChildren().add(categoryName);
      categoryPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
        if (e.getButton() == MouseButton.PRIMARY) {
          c.open = !c.open;
          refreshPlugins();
        } else if (e.getButton() == MouseButton.SECONDARY) {
          Bounds bounds = categoryName.getBoundsInLocal();
          Bounds screenBounds = categoryName.localToScreen(bounds);
          contextMenu.show(categoryName, screenBounds.getMinX() + 150, screenBounds.getMinY() + 20);
        }
      });

      pluginList.getChildren().add(categoryPanel);

      if (c.open) {
        for (String s : c.plugins) {
          Plugin p = PluginManager.getInstance(s);

          if (p != null) {
            addPlugin(p, c);
          }
        }
      }
    }
  }

  public void createCategory() {
    Stage newStage = new Stage();
    AnchorPane rootPanel = new AnchorPane();
    rootPanel.setStyle("-fx-background-color: #252525;");
    JFXTextField nameField = new JFXTextField("Name");
    nameField.setMinSize(600, 25);
    nameField.getStylesheets().add("css/plugins/jfx-textfield.css");
    JFXButton okButton = new JFXButton("OK");
    okButton.setMinSize(60, 20);
    okButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      newStage.close();
      ArrayList<Category> oldCategories = categories;
      ArrayList<Category> newCategories = new ArrayList<>();
      Category c = new Category(nameField.getText());
      newCategories.add(c);
      newCategories.addAll(oldCategories);
      categories = newCategories;
      saveCategories();
      refreshPlugins();
    });
    rootPanel.getChildren().add(nameField);
    rootPanel.getChildren().add(okButton);
    AnchorPane.setLeftAnchor(okButton, 75.0);
    AnchorPane.setTopAnchor(okButton, 27.0);
    Scene stageScene = new Scene(rootPanel, 200, 65);
    newStage.setScene(stageScene);
    newStage.show();
  }

  private int getCategoryPosition(Category category) {
    int i = 0;
    for (Category c : categories) {
      if (c == category) {
        return i;
      }

      i++;
    }
    return -1;
  }

  private int getPluginPosition(Category category, String plugin) {
    int i = 0;
    for (String c : category.plugins) {
      if (c.equals(plugin)) {
        return i;
      }

      i++;
    }
    return -1;
  }

  private void remove(Category category) {
    ArrayList<Category> temp = new ArrayList<>();
    for (Category c : categories) {
      if (!c.name.equals(category.name)) {
        temp.add(c);
      }
    }

    categories = temp;
    saveCategories();
    refreshPlugins();
  }

  private void moveUp(Category category) {
    ArrayList<Category> temp = new ArrayList<>();
    int categoryPosition = getCategoryPosition(category);
    int currentPos = 0;
    Category replaced = null;
    for (Category c : categories) {
      if (currentPos == categoryPosition - 1) {
        replaced = c;
        temp.add(category);
      } else if (currentPos == categoryPosition) {
        temp.add(replaced);
      } else {
        temp.add(c);
      }

      currentPos++;
    }

    categories = temp;
    refreshPlugins();
  }

  private void moveUp(Category category, String plugin) {
    ArrayList<String> temp = new ArrayList<>();
    int categoryPosition = getPluginPosition(category, plugin);
    int currentPos = 0;
    String replaced = null;
    for (String p : category.plugins) {
      if (currentPos == categoryPosition - 1) {
        replaced = p;
        temp.add(plugin);
      } else if (currentPos == categoryPosition) {
        temp.add(replaced);
      } else {
        temp.add(p);
      }

      currentPos++;
    }

    category.plugins = temp;
    refreshPlugins();
  }

  private void moveDown(Category category) {
    ArrayList<Category> temp = new ArrayList<>();
    int categoryPosition = getCategoryPosition(category);
    int currentPos = 0;
    Category replaced = null;
    for (Category c : categories) {
      if (currentPos == categoryPosition + 1) {
        replaced = c;
      }

      currentPos++;
    }
    currentPos = 0;
    for (Category c : categories) {
      if (currentPos == categoryPosition) {
        temp.add(replaced);
      } else if (currentPos == categoryPosition + 1) {
        replaced = c;
        temp.add(category);
      } else {
        temp.add(c);
      }

      currentPos++;
    }

    categories = temp;
    refreshPlugins();
  }

  private void moveDown(Category category, String plugin) {
    ArrayList<String> temp = new ArrayList<>();
    int categoryPosition = getPluginPosition(category, plugin);
    int currentPos = 0;
    String replaced = null;
    for (String c : category.plugins) {
      if (currentPos == categoryPosition + 1) {
        replaced = c;
      }

      currentPos++;
    }

    currentPos = 0;
    for (String c : category.plugins) {
      if (currentPos == categoryPosition) {
        temp.add(replaced);
      } else if (currentPos == categoryPosition + 1) {
        replaced = c;
        temp.add(plugin);
      } else {
        temp.add(c);
      }

      currentPos++;
    }
    category.plugins = temp;
    refreshPlugins();
  }

  private void addPlugin(Plugin p, Category category) {
    AnchorPane pluginPanel = new AnchorPane();
    pluginPanels.put(p, pluginPanel);
    pluginPanel.setStyle("-fx-border-color: transparent;");
    pluginPanel.setStyle("-fx-background-color: #212121; -fx-border-style: solid;  -fx-border-color: #121212; -fx-border-width: 1;");

    String configGroup = p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value();
    PluginToggleButton toggleButton = null;
    if (configGroup != null) {
      toggleButton = configGroupPluginMap.get(configGroup);
    }

    if (!p.getClass().getAnnotation(PluginDescriptor.class).cantDisable()) {
      toggleButton = new PluginToggleButton(p);
      toggleButton.setSize(6);
      AnchorPane.setTopAnchor(toggleButton, 2.0);
      AnchorPane.setBottomAnchor(toggleButton, 2.0);
      AnchorPane.setRightAnchor(toggleButton, 8.0);

      PluginToggleButton finalToggleButton = toggleButton;
      toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
        if (newValue == finalToggleButton.plugin.isEnabled()) {
          return;
        }
        if (!overrideToggleListener) {
          p.toggle();
        }
      });

      toggleButton.setStyle("-fx-text-fill: CYAN;");
      try {
        if (p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class) != null) {
          configGroupPluginMap.put(p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), toggleButton);
          toggleButton.setSelected(Boolean.parseBoolean(configManager.getConfiguration(p.getConfig(configManager)
              .getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), "pluginEnabled")));
        }
      } catch (Exception e) {
        e.printStackTrace();
        new Logger("PluginListUI").error(p.getName() + " has an incorrect getConfig(). Fix it.");
      }
    }

    if (toggleButton != null) {
      toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
        if (p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class) != null) {
          configManager.setConfiguration(p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), "pluginEnabled", newValue);
        }
      });
    }

    PluginConfigButton configButton = new PluginConfigButton(p);
    if (p.getConfig(MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class)) != null) {
      if (p.getConfig(MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class)).getClass().getDeclaredMethods().length > 4) {
        AnchorPane.setRightAnchor(configButton, 48.0);
        AnchorPane.setTopAnchor(configButton, 4.0);
        AnchorPane.setBottomAnchor(configButton, 4.0);

        FontAwesomeIconView cog = new FontAwesomeIconView(FontAwesomeIcon.COG);
        cog.setFill(Paint.valueOf("CYAN"));
        cog.setSize("18");
        configButton.setGraphic(cog);
        configButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
          lastPluginInteracted = p;
          p.showConfig();
          pluginsPanelVisible = !pluginsPanelVisible;
        });
      }
    }

    Text pluginName = new Text();
    pluginName.setText(p.getClass().getAnnotation(PluginDescriptor.class).name());
    pluginName.setFill(Paint.valueOf("WHITE"));
    AnchorPane.setLeftAnchor(pluginName, 8.0);
    AnchorPane.setTopAnchor(pluginName, 8.0);
    AnchorPane.setBottomAnchor(pluginName, 8.0);
    pluginName.setFont(Font.font(18));

    if (p.getClass().getAnnotation(PluginDescriptor.class).description().length() > 0) {
      JFXTooltip tooltip = new JFXTooltip();
      tooltip.setText(p.getClass().getAnnotation(PluginDescriptor.class).description());

      pluginName.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            tooltip.showOnAnchors(pluginName, 0, -50);
      });

      pluginName.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
          tooltip.hide();
      });
    }


    pluginPanel.getChildren().add(pluginName);
    pluginPanel.getChildren().add(configButton); //Order matters here! Very Important! uwu
    if (toggleButton != null) {
      pluginPanel.getChildren().add(toggleButton);
    }

    ContextMenu contextMenu = new ContextMenu();
    contextMenu.setStyle("-fx-background-color: #121212;");
    for (Category c : categories) {
      if (c.name.equals("MeteorLite")) {
        continue;
      }
      if (!c.plugins.contains(p.getName())) {
        CategoryMenuItem menuItem = new CategoryMenuItem(c, p, "Add : " + c.name);
        menuItem.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan;");
        menuItem.setOnAction((event) -> {
          if (!c.plugins.contains(p.getName())) {
            c.plugins.add(p.getName());
            refreshPlugins();
            saveCategories();
          }
        });
        contextMenu.getItems().add(menuItem);
      } else {
        CategoryMenuItem menuItem = new CategoryMenuItem(c, p, "Remove : " + c.name);
        menuItem.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan;");
        menuItem.setOnAction((event) -> {
          if (c.plugins.contains(p.getName())) {
            c.plugins.remove(p.getName());
            refreshPlugins();
            saveCategories();
          }
        });
        contextMenu.getItems().add(menuItem);
      }
    }

    if (!category.name.equals("MeteorLite")) {
      if (getPluginPosition(category, p.getName()) > 0) {
        new Logger("").debug(getPluginPosition(category, p.getName()));
        CategoryMenuItem moveUpMenuItem = new CategoryMenuItem(category, p, "Move up");
        moveUpMenuItem.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan;");
        moveUpMenuItem.setOnAction((event) -> {
          moveUp(category, p.getName());
          refreshPlugins();
          saveCategories();
        });
        contextMenu.getItems().add(moveUpMenuItem);
      }

      if (getPluginPosition(category, p.getName()) < category.plugins.size() - 1) {
        CategoryMenuItem moveDownMenuItem = new CategoryMenuItem(category, p, "Move Down");
        moveDownMenuItem.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan;");
        moveDownMenuItem.setOnAction((event) -> {
          if (category.plugins.contains(p.getName())) {
            moveDown(category, p.getName());
            refreshPlugins();
            saveCategories();
          }
        });
        contextMenu.getItems().add(moveDownMenuItem);
      }
    }

    pluginPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      Bounds bounds = pluginPanel.getBoundsInLocal();
      Bounds screenBounds = pluginPanel.localToScreen(bounds);
      contextMenu.show(pluginName, screenBounds.getMinX() + 150, screenBounds.getMinY() + 20);
    });
    pluginList.getChildren().add(pluginPanel);
  }

  @Subscribe
  public void onExternalsReloaded(ExternalsReloaded e) {
    refreshPlugins();
  }
}
