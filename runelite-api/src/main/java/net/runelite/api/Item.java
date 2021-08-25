/*
 * Copyright (c) 2019, Adam <Adam@sigterm.info>
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
package net.runelite.api;

import lombok.Data;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

import java.util.Arrays;
import java.util.List;
import net.runelite.api.widgets.WidgetItem;

@Data
public class Item implements Interactable {
    private final int id;
    private final int quantity;

    private Client client;
    private int slot;
    private String[] actions;

    // Interaction
    private WidgetInfo widgetInfo;
    private int identifier;
    private int actionParam;
    private int widgetId;

    public String getName() {
        return client.getItemComposition(getId()).getName();
    }

    @Override
    public String[] getActions() {
        return actions;
    }

    @Override
    public int getActionId(int action) {
        switch (action) {
            case 0:
                if (getActions()[0] == null) {
                    return MenuAction.ITEM_USE.getId();
                }

                return MenuAction.ITEM_FIRST_OPTION.getId();
            case 1:
                return MenuAction.ITEM_SECOND_OPTION.getId();
            case 2:
                return MenuAction.ITEM_THIRD_OPTION.getId();
            case 3:
                return MenuAction.ITEM_FOURTH_OPTION.getId();
            case 4:
                return MenuAction.ITEM_FIFTH_OPTION.getId();
            default:
                throw new IllegalArgumentException("action = " + action);
        }
    }

    @Override
    public List<String> actions() {
        return Arrays.asList(actions);
    }

    @Override
    public void interact(String action) {
        interact(actions().indexOf(action));
    }

    @Override
    public void interact(int index) {
        if (widgetInfo.getGroupId() == WidgetInfo.EQUIPMENT.getGroupId()) {
            interact(index, index > 4 ? MenuAction.CC_OP_LOW_PRIORITY.getId() : MenuAction.CC_OP.getId());
            return;
        }

        if (widgetInfo == WidgetInfo.BANK_ITEM_CONTAINER || widgetInfo == WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER) {
            interact(index, MenuAction.CC_OP.getId());
            return;
        }

        interact(getId(), getActionId(index));
    }

    public void interact(int index, int menuAction) {
        if (widgetInfo.getGroupId() == WidgetInfo.EQUIPMENT.getGroupId()) {
            interact(index + 1, menuAction, actionParam, widgetId);
            return;
        }

        if (widgetInfo == WidgetInfo.BANK_ITEM_CONTAINER || widgetInfo == WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER) {
            interact(index, menuAction, actionParam, widgetId);
            return;
        }

        interact(getId(), menuAction, actionParam, widgetId);
    }

    @Override
    public void interact(int identifier, int opcode, int param0, int param1) {
        client.interact(identifier, opcode, param0, param1);
    }

    public void useOn(TileObject object) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(getSlot());
        client.setSelectedItemID(getId());
        object.interact(0, MenuAction.ITEM_USE_ON_GAME_OBJECT.getId());
    }

    public void useOn(Item item) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(item.getIndex());
        client.setSelectedItemID(item.getId());
        item.interact(0, MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId());
    }

    public void useOn(Actor actor) {
        MenuAction menuAction = actor instanceof NPC ? MenuAction.ITEM_USE_ON_NPC : MenuAction.ITEM_USE_ON_PLAYER;
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(getIndex());
        client.setSelectedItemID(getId());
        actor.interact(0, menuAction.getId());
    }

    public void useOn(Widget widget) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(getIndex());
        client.setSelectedItemID(getId());
        interact(0, MenuAction.ITEM_USE_ON_WIDGET.getId(), widget.getIndex(), widget.getId());
    }


    public void useOn(Item item) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(item.getSlot());
        client.setSelectedItemID(item.getId());
        client.invokeMenuAction("", "", getId(),
            MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(), getSlot(), WidgetInfo.INVENTORY.getId());
    }


    public void useOn(WidgetItem item) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(item.getSlot());
        client.setSelectedItemID(item.getId());
        client.invokeMenuAction("", "", getId(),
            MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(), getSlot(), WidgetInfo.INVENTORY.getId());
    }

    public void useOn(NPC npc) {
        client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
        client.setSelectedItemSlot(getSlot());
        client.setSelectedItemID(getId());
        client.invokeMenuAction("", "", npc.getIndex(),
            MenuAction.ITEM_USE_ON_NPC.getId(), 0, 0);
    }
}
