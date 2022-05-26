package com.zpedroo.voltzchain.utils.builder;

import com.zpedroo.voltzchain.VoltzChain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
public abstract class InventoryUtils {
=======
public class InventoryUtils {
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

    private static InventoryUtils instance;
    public static InventoryUtils get() { return instance; }

<<<<<<< HEAD
    private final Map<Player, InventoryBuilder> viewers = new HashMap<>(16);

    public InventoryUtils() {
        instance = this;
=======
    private Map<Player, InventoryBuilder> viewers;

    public InventoryUtils() {
        instance = this;
        this.viewers = new HashMap<>(128);
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1
        VoltzChain.get().getServer().getPluginManager().registerEvents(new ActionListeners(), VoltzChain.get());
    }

    public Map<Player, InventoryBuilder> getViewers() {
        return viewers;
    }

    private class ActionListeners implements Listener {

        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
        public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            if (!viewers.containsKey(player)) return;

            InventoryBuilder inventory = viewers.get(player);
            if (inventory.getInventory().getViewers().isEmpty()) {
                viewers.remove(player);
                return;
            }

            event.setCancelled(true);
            if (event.getClickedInventory() == null) return;
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;

            int slot = event.getSlot();

            ActionType actionType = ActionType.ALL_CLICKS;
            if (inventory.getAction(slot, actionType) == null) {
                switch (event.getClick()) {
                    case LEFT:
                    case SHIFT_LEFT:
                        actionType = ActionType.LEFT_CLICK;
                        break;
                    case RIGHT:
                    case SHIFT_RIGHT:
                        actionType = ActionType.RIGHT_CLICK;
                        break;
                    case MIDDLE:
                        actionType = ActionType.SCROLL;
                        break;
                }
            }

            Action action = inventory.getAction(slot, actionType);
            if (action != null) action.getAction().run();
        }
    }

    public static class Action {

<<<<<<< HEAD
        private final Runnable action;
        private final ActionType actionType;
=======
        private Runnable action;
        private ActionType actionType;
>>>>>>> 89d28eac5053a399057f3d2b91243b8fed94e3e1

        public Action(Runnable action, ActionType actionType) {
            this.action = action;
            this.actionType = actionType;
        }

        public Runnable getAction() {
            return action;
        }

        public ActionType getActionType() {
            return actionType;
        }
    }

    public enum ActionType {
        LEFT_CLICK,
        RIGHT_CLICK,
        ALL_CLICKS,
        SCROLL
    }
}