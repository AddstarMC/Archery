package au.com.addstar.targets;

import au.com.addstar.actions.Actions;
import au.com.addstar.actions.ArcheryActionInterface;
import au.com.mineauz.minigames.MinigamePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * au.com.addstar.targets
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public abstract class AbstractTarget implements Target {
    private static final long serialVersionUID = 1050372101296461857L;
    private String type = "ABSTRACT";
    private final List<ArcheryActionInterface> actions = new ArrayList<>();
    
    @Override
    public String getType() {
        return type;
    }
    
    void setType(String type) {
        this.type = type;
    }
    
    public void addAction(ArcheryActionInterface action) {
        actions.add(action);
    }
    
    @Override
    public void executeActions(MinigamePlayer player) {
        for(ArcheryActionInterface actions: getActions()){
            actions.executeAction(player,this);
        }
    }
    
    public List<ArcheryActionInterface> getActions() {
        return actions;
    }
    
    static void loadActions(Map<String, Object> map, Target target) {
        Map<String, Map<String, String>> actions = new HashMap<>();
        try {
            actions = (Map<String, Map<String, String>>) map.get("actions");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Map<String, String>> item : actions.entrySet()) {
            ArcheryActionInterface action = Actions.getAction(item.getKey());
            action.loadParameters(item.getValue());
            target.addAction(action);
        }
        
        
    }
    
    static void saveActions(Map<String, Object> map, Target target) {
        Map<String, Map<String, String>> actions = new HashMap<>();
        for (ArcheryActionInterface action : target.getActions()) {
            actions.put(action.getName(), action.saveParameters());
        }
        map.put("actions", actions);
    }
    
    public static AbstractTarget deserialize(Map<String, Object> map) {
        throw new UnsupportedOperationException("Cannot Deserialise Abract Class");
    }
    
    
}