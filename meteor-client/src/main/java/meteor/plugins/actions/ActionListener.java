/*
 * Copyright (c) 2018, Seth <https://github.com/sethtroll>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.plugins.actions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import meteor.eventbus.Subscribe;
import meteor.input.KeyListener;
import meteor.input.MouseAdapter;
import meteor.plugins.botutils.BotUtils;
import meteor.plugins.botutils.Spells;
import net.runelite.api.Client;
import net.runelite.api.events.CanvasSizeChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

public class ActionListener extends MouseAdapter implements KeyListener {

  private static final int ALT = KeyEvent.VK_ALT;
  private static final int CTRL = KeyEvent.VK_CONTROL;
  private static final int ESC = KeyEvent.VK_ESCAPE;
  Point clickPoint;

  @Inject
  private Client client;

  @Inject
  private BotUtils botUtils;

  private boolean altPressed = false;
  private boolean ctrlPressed = false;

  private boolean enabled = false;

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Subscribe
  public void onGameTick(GameTick event) {
    Widget tele = (client.getWidget(WidgetInfo.SPELL_CAMELOT_TELEPORT));
    if (enabled)
      if (tele != null)
        if (!tele.isHidden())
          botUtils.click(botUtils.getSpellWidget(Spells.CAMELOT_TELEPORT.getName()).getBounds());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == ALT) {
      altPressed = true;
    }
    if (e.getKeyCode() == CTRL) {
      ctrlPressed = true;
    }
    if (e.getKeyCode() == ESC) {
      reset();
    }
  }

  public void reset() {
    enabled = false;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == ALT) {
      altPressed = false;
    }
    if (e.getKeyCode() == CTRL) {
      ctrlPressed = false;
    }
  }

  @Override
  public MouseEvent mousePressed(MouseEvent e) {
    if (ctrlPressed && altPressed) {
      if (SwingUtilities.isLeftMouseButton(e)) {
        enabled = true;
      } else if (SwingUtilities.isRightMouseButton(e)) {
        //ignore
      }
    }

    return e;
  }
}

