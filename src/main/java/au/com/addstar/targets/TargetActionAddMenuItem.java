package au.com.addstar.targets;


import au.com.addstar.actions.Actions;
import au.com.addstar.actions.MenuItemArcheryAction;
import au.com.mineauz.minigames.menu.Menu;
import au.com.mineauz.minigames.menu.MenuItem;
import au.com.mineauz.minigames.menu.MenuItemBack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * au.com.addstar.targets
 * Created for the Addstar MC for Archery
 * Created by Narimm on 23/03/2018.
 */
public class TargetActionAddMenuItem extends MenuItem {
    
    private final Target target;
    
    public TargetActionAddMenuItem(String name, Material displayItem, Target target) {
        super(name, displayItem);
        this.target = target;
    }
    
    public TargetActionAddMenuItem(String name, List<String> description, Material displayItem, Target target) {
        super(name, description, displayItem);
        this.target = target;
    
    }
    
    @Override
    public ItemStack onClick(){
        Menu m = new Menu(6, "Select Trigger", getContainer().getViewer());
        m.setPreviousPage(getContainer());
        List<String> actions = new ArrayList<>(Actions.getActions());
        Collections.sort(actions);
        
        for(String act : actions){
            m.addItem(new MenuItemArcheryAction(target,Actions.getAction(act)));
        }
        
        m.addItem(new MenuItemBack(getContainer()), m.getSize() - 9);
        
        m.displayMenu(getContainer().getViewer());
        
        return null;
    }
}
