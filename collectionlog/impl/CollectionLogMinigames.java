package com.rs.game.player.content.collectionlog.impl;

import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.game.player.content.collectionlog.CollectionItems;

public class CollectionLogMinigames {
    static final int INTER = 3008;
    static final String SELECTED = "<col=ffb70f>";
    static final String SELECTED2 = "<col=c4bfc3>";

    public static ItemsContainer<Item> Rewards = new ItemsContainer<Item>(200, true);

    public static void sendInterface(Player player) {
        /*player.getPackets().sendHideIComponent(INTER, 81, true);
        player.getPackets().sendHideIComponent(INTER, 79, true);*/
        player.getPackets().sendIComponentText(INTER, 24, SELECTED2 + "Minigames</col>");
        sendItems(player, "Pest Control", CollectionItems.CollectionType.PEST_CONTROL, null, null, null, 52);
        player.getInterfaceManager().sendInterface(INTER);
        sendOptions(player);
    }

    public static void handleButtons(Player player, int componentId, int slotId2) {
        Item item = new Item(slotId2);
        switch (componentId) {
            case 31: //examine
                String itemName = new Item(slotId2).getName();
                if (player.getUniqueItemQuantity(slotId2) == 0) {
                    player.sm("I did not get any " + itemName + " yet.");
                } else {
                    player.sm("I have obtained <col=f00000>" + player.getUniqueItemQuantity(slotId2) + "</col>x " + itemName + ".");
                }
                break;
            case 52:
                sendItems(player, "Pest Control", CollectionItems.CollectionType.PEST_CONTROL, null, null, null, componentId);
                break;
            case 77:
                sendItems(player, "Fight Caves", CollectionItems.CollectionType.FIGHT_CAVES, null, player.getJadkills(), "Kills", componentId);
                break;
            case 36:
                sendItems(player, "Fight Kiln", CollectionItems.CollectionType.FIGHT_KILN, null, player.getHarAkenKills(), "Kills", componentId);
                break;
            case 78: //sara
                sendItems(player, "Dominion Tower", CollectionItems.CollectionType.DOMINION_TOWER, null, player.getDominionTower().getKilledBossesCount(), "Kills", componentId);
                break;
            case 53: //barrows
                sendItems(player, "Barrows", CollectionItems.CollectionType.BARROWS, null, player.getOpenedbarrowschest(), "Completed", componentId);
                break;
            /*case 54:
                sendItems(player, "Tormented Demon", CollectionItems.ShopType.TORMENTED, null, player.getTormKills(), "Kills", componentId);
                break;
            case 55:
                sendItems(player, "Giant Mole", CollectionItems.ShopType.GIANT_MOLE, null, player.getgiantmollykills(), "Kills", componentId);
                break;
            case 56:
                sendItems(player, "Dagannoth Kings", CollectionItems.ShopType.DKS, null, player.getDksKills(), "Kills", componentId);
                break;
            case 57:
                sendItems(player, "Corporeal Beast", CollectionItems.ShopType.CORPOREAL, null, player.getCorpkills(), "Kills", componentId);
                break;
            case 58:
                sendItems(player, "Kalphite Queen", CollectionItems.ShopType.KQ, null, player.getKqkills(), "Kills", componentId);
                break;
            case 59:
                sendItems(player, "Queen Black Dragon", CollectionItems.ShopType.QBD, null, player.getQbdkills(), "Kills", componentId);
                break;
            case 60:
                sendItems(player, "King Black Dragon", CollectionItems.ShopType.KBD, null, player.getKbdkills(), "Kills", componentId);
                break;
            case 61:
                sendItems(player, "Chaos Elemental", CollectionItems.ShopType.CHAOS_ELEMENTAL, null, player.getChaoselekills(), "Kills", componentId);
                break;
            case 62:
                sendItems(player, "Blink", CollectionItems.ShopType.BLINK, null, player.getBlinkKills(), "Kills", componentId);
                break;
            case 63:
                sendItems(player, "Sunfreet", CollectionItems.ShopType.SUNFREET, null, player.getSunfreetkills(), "Kills", componentId);
                break;*/
            /*categories*/
            case 17: //Bosses
                CollectionLogBosses.sendInterface(player);
                break;
            case 18: //Clues
                CollectionLogClues.sendInterface(player);
                break;
            case 20: //Others
                CollectionLogOthers.sendInterface(player);
                break;
            case 79: //Raids
                CollectionLogRaids.sendInterface(player);
                break;
        }
    }

    public static void sendItems(Player player, String bossName, CollectionItems.CollectionType collectionType1, CollectionItems.CollectionType collectionType2, Object bossKills, String killOrCompleted, int component) {
        Rewards.clear();
        Rewards.reset();
        player.getPackets().sendIComponentText(INTER, 26, "<col=ff9c24>"+bossName+"</col>");
        player.getPackets().sendIComponentText(INTER, 27, "Obtained: <col=ffd900>" + getObtainedItems(player, collectionType1) + "/"+getMax(collectionType1)+"</col>");
        /*player.getPackets().sendIComponentText(INTER, 28, killOrCompleted+": <col=ffffff>" + bossKills);*/
        if (killOrCompleted == null) {
            player.getPackets().sendHideIComponent(INTER, 28, true);
        } else {
            player.getPackets().sendIComponentText(INTER, 28, killOrCompleted + ": <col=ffffff>" + bossKills);
        }
        getItemsTable(player, collectionType1);
        if (collectionType2 != null) {
            getItemsTable(player, collectionType2);
        }
        player.getPackets().sendItems(90, false, Rewards);
        sendCattegoryNames(player, component, bossName);
        player.getPackets().sendIComponentText(INTER, component, SELECTED + bossName+"</col>");
    }

    public static void sendCattegoryNames(Player player, int componentId, String bossName) {
        player.getPackets().sendIComponentText(INTER, componentId, bossName);
        player.getPackets().sendIComponentText(INTER, 52, "Pest Control");
        player.getPackets().sendIComponentText(INTER, 77, "Fight Caves");
        player.getPackets().sendIComponentText(INTER, 36, "Fight Kiln");
        player.getPackets().sendIComponentText(INTER, 78, "Dominion Tower");
        player.getPackets().sendIComponentText(INTER, 53, "Barrows");
        /*player.getPackets().sendIComponentText(INTER, 54, "Tormented Demon");
        player.getPackets().sendIComponentText(INTER, 55, "Giant Mole");
        player.getPackets().sendIComponentText(INTER, 56, "Dagannoth Kings");
        player.getPackets().sendIComponentText(INTER, 57, "Corporeal Beast");
        player.getPackets().sendIComponentText(INTER, 58, "Kalphite Queen");
        player.getPackets().sendIComponentText(INTER, 59, "Queen Black Dragon");
        player.getPackets().sendIComponentText(INTER, 60, "King Black Dragon");
        player.getPackets().sendIComponentText(INTER, 61, "Chaos Elemental");
        player.getPackets().sendIComponentText(INTER, 62, "Blink");
        player.getPackets().sendIComponentText(INTER, 63, "Sunfreet");*/
        for (int i = 54; i <= 76; i++) {
            player.getPackets().sendHideIComponent(INTER, i, true);
        }
        for (int i2 = 39; i2 <= 51; i2++) {
            player.getPackets().sendHideIComponent(INTER, i2, true);
        }
    }

    public static void sendOptions(Player player) {
        player.getPackets().sendInterSetItemsOptionsScript(INTER, 31, 90, 7, 8, "Check"); //12 5
        player.getPackets().sendUnlockIComponentOptionSlots(INTER, 31, 0, 160, 0);
    }

    public static int getMax(CollectionItems.CollectionType collectionType) {
        int quant = 0;
        for (CollectionItems.Collection item : CollectionItems.Collection.values()) {
            if (item.getType() == collectionType) {
                quant++;
            }
        }
        return quant;
    }

    public static void getItemsTable(Player player, CollectionItems.CollectionType collectionType) {
        for (CollectionItems.Collection item : CollectionItems.Collection.values()) {
            if (item.getType() == collectionType) {
                Rewards.add(new Item(item.getId(), player.getUniqueItemQuantity(item.getId())));
            }
        }
    }

    public static int getObtainedItems(Player player, CollectionItems.CollectionType collectionType) {
        int quant = 0;
        for (CollectionItems.Collection item : CollectionItems.Collection.values()) {
            if (item.getType() == collectionType) {
                if (player.containsUniqueItem(item.getId())) {
                    if (player.isDebugMode()) {
                        player.sm("Entry on hashmap (id=" + item.getId() + ")");
                    }
                    quant++;
                }
            }
        }
        if (player.isDebugMode()) {
            player.sm("Count: " + quant);
        }
        return quant;
    }
}