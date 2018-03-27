package au.com.addstar.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 23/03/2018.
 */
public class Actions {
    
    public static List<String> getActions() {
        return  new ArrayList<>(actions.keySet());
    }
    
    private static final Map<String, ArcheryActionInterface> actions = new HashMap<>();
    
    static {
        try {
            addAction(new AddScoreArcheryAction());
            addAction(new DeductScoreArcheryAction());
            addAction(new PlayerMessageArcheryAction());
            addAction(new ServerBroadCastArcheryAction());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }
    private static void addAction(ArcheryActionInterface action) throws InvalidActionException {
        if(actions.containsKey(action.getName()))
            throw new InvalidActionException("A trigger already exists by that name!");
        else
            actions.put(action.getName(), action);
    }
    
    public static ArcheryActionInterface getAction(String action){
        return actions.get(action);
        
    }
}
