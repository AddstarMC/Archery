package au.com.addstar.actions;

import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.MinigameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.*;

/**
 * Allows an action that triggers a server wide broadcast of a Message that can optionally include the Players name that
 * triggered the action.  Simply use `%s` in your message at the place you want the name to appear.
 *
 * au.com.addstar.actions
 *
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class ServerBroadCastArcheryAction extends MessageArcheryAction {
    private static final String name = "MESSAGE_BROADCAST";
    
    public ArcheryActionInterface setMessage(String message) {
        this.message.setFlag(message);
        return this;
    }
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
        String msg = String.format(message.getFlag(),player.getDisplayName(player.getMinigame().usePlayerDisplayNames()));
        MinigameUtils.broadcast(msg,player.getMinigame(), ChatColor.AQUA);
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
        message.setFlag(map.get("message"));
    }

    
}
