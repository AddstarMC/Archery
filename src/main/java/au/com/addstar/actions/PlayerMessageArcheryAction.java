package au.com.addstar.actions;

import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class PlayerMessageArcheryAction extends MessageArcheryAction {
    private static final String name = "PLAYER_MESSAGE";
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getCategory() {
        return "MESSAGE";
    }
    
    @Override
    public void executeAction(MinigamePlayer player, Target target) {
    
    }
    
    @Override
    public List<String> describe() {

        return (message.getFlag() !=null)?Collections.singletonList("Message:" + message):Collections.EMPTY_LIST;
    }
    
    @Override
    public Material getDisplayItem() {
        return Material.PAPER;
    }

    @Override
    public Map<String, String> saveParameters() {
        return Collections.singletonMap("message",message.getFlag());
    }

    @Override
    public void loadParameters(Map<String, String> map) {
        setMessage(map.get("message"));
    }

    public ArcheryActionInterface setMessage(String message) {
        this.message.setFlag(message);
        return this;
    }
    

}
