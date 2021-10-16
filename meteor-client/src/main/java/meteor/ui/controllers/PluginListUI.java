package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import impl.org.controlsfx.skin.CustomTextFieldSkin;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import meteor.MeteorLiteClientLauncher;
import meteor.PluginManager;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.events.ExternalsReloaded;
import meteor.plugins.Plugin;
import meteor.ui.components.*;
import meteor.util.MeteorConstants;
import org.controlsfx.control.textfield.CustomTextField;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PluginListUI extends BorderPane {
	private static final Logger log = new Logger("PluginListUI");
	private static final String MAIN_CATEGORY_NAME = "Plugins";
	public static final String EXTERNAL_CATEGORY_NAME = "Externals";

	private FontAwesomeIconView addCategory;
	public ScrollPane scrollPane;

	public static Plugin lastPluginInteracted;

	public static Map<String, PluginToggleButton> toggleButtons = new HashMap<>();

	ObservableList<PluginListCell> plugins = FXCollections.observableArrayList();

	public static boolean overrideToggleListener = false;

	@Inject
	private ConfigManager configManager;

	public static PluginListUI INSTANCE;

	@Inject
	private EventBus eventBus;

	public static ArrayList<Category> categories = new ArrayList<>();

	public PluginListUI() {
		MeteorLiteClientLauncher.injector.injectMembers(this);
		eventBus.register(this);
		INSTANCE = this;
		categories.clear();

		setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), null, null)));

		setMinWidth(MeteorConstants.PANEL_WIDTH);
		setMaxWidth(MeteorConstants.PANEL_WIDTH);

		addCategory = new FontAwesomeIconView();
		addCategory.setFill(Color.AQUA);
		addCategory.setGlyphName("PLUS_SQUARE");
		addCategory.setSize("28");

		FilteredList<PluginListCell> filteredData = new FilteredList<>(plugins, s -> true);

		CustomTextField searchBar = new CustomTextField();
		searchBar.setStyle("-fx-text-inner-color: white;");
		searchBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("121212"), null, null)));
//		searchBar.setBorder(new Border(new BorderStroke(Paint.valueOf("121212"), BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

		FontAwesomeIconView searchIcon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		searchIcon.setFill(Color.CYAN);
		searchBar.setLeft(searchIcon);

		searchBar.textProperty().addListener(obs->{
			String filter = searchBar.getText().toLowerCase();
			if(filter.length() == 0) {
				filteredData.setPredicate(s -> true);
			}
			else {
				filteredData.setPredicate(s -> s.getPluginName().toLowerCase().contains(filter));
			}
		});

		HBox.setHgrow(searchBar, Priority.ALWAYS);
		HBox.setMargin(searchBar, new Insets(4,4,4,8));
		HBox.setMargin(addCategory, new Insets(4,8,4,4));
		setTop(new HBox(searchBar, addCategory));

		scrollPane = new ScrollPane();
		scrollPane.getStylesheets().add("css/plugins/jfx-scrollbar.css");
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), null, null)));

		VBox pluginListView = new VBox();
		pluginListView.setPadding(new Insets(4));
		pluginListView.setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), null, null)));

		filteredData.addListener((ListChangeListener.Change<? extends PluginListCell> c) -> {
			pluginListView.getChildren().clear();
			pluginListView.getChildren().addAll(filteredData);
		});

		scrollPane.setContent(pluginListView);
		setCenter(scrollPane);

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
				if (name.length() <= 0) {
					continue;
				}

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

		Category all = new Category(MAIN_CATEGORY_NAME);
		for (Plugin p : PluginManager.plugins) {
			all.plugins.add(p.getName());
		}

		categories.add(all);

		saveCategories();
		refreshPlugins();
	}

	public void saveCategories() {
		StringBuilder categoriesConfig = new StringBuilder();
		int i = 0;

		for (Category c : categories) {
			StringBuilder pluginsConfig = new StringBuilder();

			if (!c.name.equals(MAIN_CATEGORY_NAME)) {
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
		categories.forEach(Category::clear);
		toggleButtons.clear();

		for (Category c : categories) {
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

			if (!c.name.equals(MAIN_CATEGORY_NAME)) {
				contextMenu.setStyle("-fx-background-color: #121212;");
				CategoryMenuItem moveDown = new CategoryMenuItem(c, null, "Remove");
				moveDown.setStyle("-fx-background-color: #121212; -fx-text-fill: cyan; -fx-highlight-fill: #424242");
				moveDown.setOnAction((event) -> {
					remove(c);
					saveCategories();
				});
				contextMenu.getItems().add(moveDown);
			}

			TitledPane titlePane = c.getTitlePane();
			Text title = c.getSectionPane().getTitle();
			title.setOnContextMenuRequested(e -> contextMenu.show(titlePane, e.getScreenX(), e.getScreenY()));

//			pluginList.getChildren().add(titlePane);

			for (String s : c.plugins) {
				Plugin p = PluginManager.getInstance(s);

				if (p != null) {
					addPlugin(p, c);
				}
			}
			plugins.sort(Comparator.comparing(PluginListCell::getPluginName));
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
			findOrCreateCategory(nameField.getText());
		});
		rootPanel.getChildren().add(nameField);
		rootPanel.getChildren().add(okButton);
		AnchorPane.setLeftAnchor(okButton, 75.0);
		AnchorPane.setTopAnchor(okButton, 27.0);
		Scene stageScene = new Scene(rootPanel, 200, 65);
		newStage.setScene(stageScene);
		newStage.show();
	}

	public Category findOrCreateCategory(final String name) {
		Optional<Category> categoryOptional =
						categories.stream()
										.filter(c -> c.name.equals(name))
										.findFirst();

		if (categoryOptional.isPresent()) {
			return categoryOptional.get();
		}

		ArrayList<Category> oldCategories = categories;
		ArrayList<Category> newCategories = new ArrayList<>();
		Category c = new Category(name);
		newCategories.add(c);
		newCategories.addAll(oldCategories);
		categories = newCategories;
		saveCategories();
		refreshPlugins();
		return c;
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
		categories.remove(category);
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
		ContextMenu contextMenu = new ContextMenu();

		PluginListCell panel = new PluginListCell(p, contextMenu, configManager);

		plugins.add(panel);

		toggleButtons.put(p.getClass().getSimpleName(), panel.getToggleButton());

		contextMenu.setStyle("-fx-background-color: #121212;");
		for (Category c : categories) {
			if (c.name.equals(MAIN_CATEGORY_NAME)) {
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

		if (!category.name.equals(MAIN_CATEGORY_NAME)) {
			if (getPluginPosition(category, p.getName()) > 0) {
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
		category.addNode(panel);
	}

	@Subscribe
	public void onExternalsReloaded(ExternalsReloaded e) {
		refreshPlugins();
	}
}
