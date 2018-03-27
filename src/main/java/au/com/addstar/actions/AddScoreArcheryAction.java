package au.com.addstar.actions;

import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.MinigameUtils;
import au.com.mineauz.minigames.menu.Menu;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class AddScoreArcheryAction implements ArcheryActionInterface {
    private static final String name = "ADD_SCORE";
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getCategory() {
        return "SCORE";
    }
    
    @Override
    public void executeAction(MinigamePlayer player, Target target) {
        player.addScore(target.getScore());
        MinigameUtils.debugMessage("Scored:" + target.getScore());
    }
    
    @Override
    public List<String> describe() {
        return Collections.singletonList("Add the Target's Score");
    }
    
    @Override
    public Material getDisplayItem() {
        return Material.GREEN_RECORD;
    }
    
    @Override
    public boolean displayMenu(MinigamePlayer player, Menu previous) {
        return false;
    }

    @Override
    public Map<String, String> saveParameters() {
        return Collections.emptyMap();
    }

    @Override
    public void loadParameters(Map<String, String> map) {
    }

}
