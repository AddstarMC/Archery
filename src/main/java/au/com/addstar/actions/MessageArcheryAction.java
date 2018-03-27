package au.com.addstar.actions;

import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.config.StringFlag;
import au.com.mineauz.minigames.menu.Menu;
import au.com.mineauz.minigames.menu.MenuItemPage;
import org.bukkit.Material;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 27/03/2018.
 */
public abstract  class MessageArcheryAction implements ArcheryActionInterface{
    
    final StringFlag message = new StringFlag("Hello World","message");
    
    @Override
    public boolean displayMenu(MinigamePlayer player, Menu previous) {
        Menu m = new Menu(3, "Options", player);
        m.setPreviousPage(previous);
        m.addItem(message.getMenuItem("Message", Material.PAPER));
        m.addItem(new MenuItemPage("Back", Material.REDSTONE_TORCH_ON, m.getPreviousPage()), m.getSize() - 9);
        m.displayMenu(player);
        return true;
    }
}
