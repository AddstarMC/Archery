package au.com.addstar.actions;


import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigameUtils;
import au.com.mineauz.minigames.menu.MenuItem;
import org.bukkit.inventory.ItemStack;


/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 23/03/2018.
 */
public class MenuItemArcheryAction extends MenuItem {
    
    private final ArcheryActionInterface action;
    private final Target target;
    
    public MenuItemArcheryAction(Target target, ArcheryActionInterface action) {
        super(MinigameUtils.capitalize(action.getName().replace("_", " ")), action.describe(), action.getDisplayItem());
        this.action = action;
        this.target = target;
    }
    
    @Override
    public ItemStack onClick(){
        if(target != null){
            if(!target.getActions().contains(action)){
                target.addAction(action);
                getContainer().getPreviousPage().displayMenu(getContainer().getViewer());
                return null;
            }
            if(action.displayMenu(getContainer().getViewer(), getContainer()))
                return null;
        } else {
            if(action.displayMenu(getContainer().getViewer(), getContainer()))
                return null;
        }
        return getItem();
    }

    @Override
    public ItemStack onRightClick() {
        if(target != null){
            if(target.getActions().contains(action)){
                target.getActions().remove(action);
            }
            getContainer().displayMenu(getContainer().getViewer());
        }
        return super.onRightClick();
    }
}
