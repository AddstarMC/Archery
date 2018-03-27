package au.com.addstar.actions;

import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.menu.Menu;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public interface ArcheryActionInterface {
    
    String getName();
    String getCategory();
    void executeAction(MinigamePlayer player, Target target);
    List<String> describe();
    Material getDisplayItem();
    boolean displayMenu(MinigamePlayer player, Menu previous);
    Map<String,String> saveParameters();
    void loadParameters(Map<String,String> map);
    
    
}
