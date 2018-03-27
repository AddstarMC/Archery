package au.com.addstar.targets;


import au.com.addstar.ArcheryModule;
import au.com.addstar.actions.ArcheryActionInterface;
import au.com.addstar.actions.MenuItemArcheryAction;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.menu.Menu;
import au.com.mineauz.minigames.menu.MenuItem;
import au.com.mineauz.minigames.menu.MenuItemPage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TargetMenuItem extends MenuItem {

    private final Target target;
    private final ArcheryModule module;

    public TargetMenuItem(String name, List<String> description, ItemStack displayItem, ArcheryModule mod, Target target) {
        super(name, description, displayItem.getType());
        setItem(displayItem);
        this.module = mod;
        this.target = target;
    }
    
    public TargetMenuItem(String name, ItemStack displayItem, Target target, ArcheryModule module) {
        super(name, displayItem.getType());
        List<String> describe = new ArrayList<>();
        describe.add("Score: " + target.getScore());
        describe.add("Shit Left Click to open the Sub menu");
        describe.add("Left click to add to the score");
        describe.add("Right click to decrease the score");
        describe.add("Shit Right click removes the Target");
        
        setDescription(describe);
        setItem(displayItem);
        this.target = target;
        this.module = module;
    }
    
    @Override
    public ItemStack onClick() {
        target.setScore(target.getScore()+1);
        update();
        return super.onClick();
    }
    @Override
    public void update(){
        List<String> describe = new ArrayList<>();
        describe.add("Score: " + target.getScore());
        describe.add("Shit Left Click to open the Sub menu");
        describe.add("Left click to add to the score");
        describe.add("Right click to decrease the score");
        describe.add("Shit Right click removes the Target");
        setDescription(describe);
    }
    
    @Override
    public ItemStack onRightClick() {
        target.setScore(target.getScore()-1);
        update();
        return super.onRightClick();
    }
    
    @Override
    public ItemStack onShiftClick() {
        Menu m = createMenu(getContainer().getViewer(), getContainer(), target);
        m.displayMenu(getContainer().getViewer());
        return null;
    }

    @Override
    public ItemStack onShiftRightClick() {
        module.targets.remove(target);
        getContainer().removeItem(getSlot());
        return null;
    }
    
    @Override
    public void checkValidEntry(String entry) {
        super.checkValidEntry(entry);
    }
    
    private static Menu createMenu(MinigamePlayer viewer, Menu previousPage, Target target){
        
        Menu m = new Menu(3, "Target: " + target.getName(), viewer);
        m.setPreviousPage(previousPage);
        List<MenuItem> items = new ArrayList<>();
        for(ArcheryActionInterface ac : target.getActions()){
            items.add(new MenuItemArcheryAction(target, ac));
        }
        if(previousPage != null){
            m.addItem(new MenuItemPage("Back", Material.REDSTONE_TORCH_ON, previousPage), m.getSize() - 9);
        }
        m.addItem(new TargetActionAddMenuItem("Add Action", Material.ITEM_FRAME, target), m.getSize() - 1);
        m.addItems(items);
        
        return m;
    }
}
