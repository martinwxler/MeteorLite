package com.owain.chinLogin;

import com.google.inject.Provides;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import javax.inject.Inject;
import meteor.config.Config;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;

@PluginDescriptor(
    name = "Chin login",
    description = "Automatically logs you in on the login screen because a 6 hour log is annoying",
    enabledByDefault = false
)
public class ChinLoginPlugin extends Plugin {
  @Inject
  private Client client;

  @Inject
  private ChinLoginConfig config;

  @Inject
  private ConfigManager configManager;

  private ExecutorService executorService = null;
  private boolean loginClicked = false;


  @Provides
  public ChinLoginConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(ChinLoginConfig.class);
  }

  @Override
  public void startup() {
    configManager.setConfiguration("loginscreen", "hideDisconnect", true);
    client.setHideDisconnect(true);
    executorService = Executors.newSingleThreadExecutor();
  }

  @Override
  public void shutdown() {
    if (executorService != null) {
      executorService.shutdown();
    }
  }


  @Subscribe
  private void onGameTick(GameTick gametick) {
    if (!loginClicked) {
      handleLoginScreen();
    }
  }

  @Subscribe
  private void onGameStateChanged(GameStateChanged gameStateChanged) {
    loginClicked = false;

    if (executorService == null || config.email().equals("") || config.password().equals("")) {
      return;
    }

    executorService.submit((Runnable) () -> {
      if (client.getGameState() == GameState.LOGIN_SCREEN) {
        String username = config.email();
        String password = config.password();
        if (!username.equals("")  && !password.equals("")) {
          waitDelayTime(400, 600);

          sendKey(KeyEvent.VK_ENTER);

          client.setUsername(config.email());
          client.setPassword(config.password());

          waitDelayTime(400, 600);

          sendKey(KeyEvent.VK_ENTER);
          sendKey(KeyEvent.VK_ENTER);
        }
      }
    });
  }


  private void handleLoginScreen() {
    Widget login = client.getWidget(WidgetID.LOGIN_CLICK_TO_PLAY_GROUP_ID, 87);

    if (login != null && login.getText().equals("CLICK HERE TO PLAY")) {
      if (login.getBounds().x != -1 && login.getBounds().y != -1) {
        executorService.submit(() -> {
          {
            click(login.getBounds());
            waitDelayTime(400, 600);
          }

          loginClicked = true;
        });
      }
    }
  }

  private void click(Rectangle rectangle) {
    Point point = getClickPoint(rectangle);
    click(point, client);
  }

  private void click(Point p, Client client) {
    if (client.isStretchedEnabled()) {
      Dimension stretched = client.getStretchedDimensions();
      Dimension real = client.getRealDimensions();
      int width = (int) (stretched.width / real.getWidth());
      int height = (int) (stretched.height / real.getHeight());
      Point point = new Point((p.getX() * width), (p.getY() * height));

      mouseEvent(MouseEvent.MOUSE_PRESSED, point, false);
      mouseEvent(MouseEvent.MOUSE_RELEASED, point, false);
      mouseEvent(MouseEvent.MOUSE_FIRST, point, false);

      return;
    }

    mouseEvent(MouseEvent.MOUSE_PRESSED, p, false);
    mouseEvent(MouseEvent.MOUSE_RELEASED, p, false);
    mouseEvent(MouseEvent.MOUSE_FIRST, p, false);
  }

  private Point getClickPoint(Rectangle rect) {
    int x = (int) (rect.getX() + getRandomIntBetweenRange(rect.getWidth() / 6 * -1,
            (int) (rect.getWidth() / 6)) + rect.getWidth() / 2);
    int y = (int) (rect.getY() + getRandomIntBetweenRange(rect.getHeight() / 6 * -1,
            (int) (rect.getHeight() / 6)) + rect.getHeight() / 2);

    return new Point(x, y);
  }

  private int getRandomIntBetweenRange(double min, int max) {
    return (int) (Math.random() * (max - min + 1) + min);
  }

  private void mouseEvent(int id, Point point, boolean move) {
    int i;
    if (move)
      i = 0;
    else
      i = 1;
    MouseEvent e = new MouseEvent(
        client.getCanvas(), id,
        System.currentTimeMillis(),
        0, point.getX(), point.getY(),
        i, false, 1
        );

    if (client.getGameState() != GameState.LOGGED_IN) {
      return;
    }

    client.getCanvas().dispatchEvent(e);
  }

  private void sendKey(int key) {
    keyEvent(KeyEvent.KEY_PRESSED, key);
    keyEvent(KeyEvent.KEY_RELEASED, key);
  }

  private void keyEvent(int id, int key) {
    KeyEvent e = new KeyEvent(
        client.getCanvas(), id, System.currentTimeMillis(),
        0, key, KeyEvent.CHAR_UNDEFINED
    );
    client.getCanvas().dispatchEvent(e);
  }

  private void waitDelayTime(int lowerDelay, int upperDelay) {
    try {
      Thread.sleep(lowerDelay + ThreadLocalRandom.current().nextInt(upperDelay - lowerDelay));
    } catch (InterruptedException e) {
      //ignore
    }
  }
}
