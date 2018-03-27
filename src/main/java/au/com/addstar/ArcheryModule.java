package au.com.addstar;

import au.com.addstar.targets.BlockTarget;
import au.com.addstar.targets.EntityTarget;
import au.com.addstar.targets.TargetMenuItem;
import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.config.Flag;
import au.com.mineauz.minigames.menu.Menu;
import au.com.mineauz.minigames.menu.MenuItemCustom;
import au.com.mineauz.minigames.menu.MenuItemPage;
import au.com.mineauz.minigames.minigame.Minigame;
import au.com.mineauz.minigames.minigame.modules.MinigameModule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.*;

/**
 * au.com.addstar
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class ArcheryModule extends MinigameModule {
    static final String name = "ARCHERY";
    public final List<Target> targets;
    
    public ArcheryModule(Minigame mgm) {
        super(mgm);
        targets = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, Flag<?>> getFlags() {
        return null;
    }
    
    public boolean useSeparateConfig() {
        return false;
    }
    
    public void save(FileConfiguration config) {
        ConfigurationSection base = config.createSection(getMinigame()+"."+getName());
        base.set("targets",targets);
        //Integer i = 0;
        //for(Target target: targets){
        //    ConfigurationSection sec = base.createSection(i.toString());
        //    sec.set("target",target.getType());
        //    if(target instanceof EntityTarget){
        //        sec.set("entity:",((EntityTarget) target).getTarget().getUniqueId().toString());
        //    }
        //}

    }
    
    public void load(FileConfiguration config) {
        String game = getMinigame().getName(false);
        ConfigurationSection base = config.getConfigurationSection(game+"."+name);
        if(base != null) {
            List result = base.getList("targets", Collections.EMPTY_LIST);
            if (result != null) {
                for (Object res : result) {
                    if (res != null && res instanceof Target) {
                        if(res instanceof EntityTarget){
                            if (((EntityTarget) res).getTarget() != null){
                                targets.add((EntityTarget) res);
                            }
                        }
                        if(res instanceof BlockTarget){
                            if(((BlockTarget) res).getBlock() != null){
                                targets.add((BlockTarget) res);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void addEditMenuOptions(Menu menu) {
        MenuItemCustom archeryOptions = new MenuItemCustom("Archery Settings", Material.SPECTRAL_ARROW);
        archeryOptions.setClick(arg0 -> {
            Menu subMenu = createMenu(menu, menu.getViewer());
            subMenu.displayMenu(menu.getViewer());

            return null;
        });
    
        menu.addItem(archeryOptions);
    }
    
    private Menu createMenu(Menu menu, MinigamePlayer viewer) {
        final Menu archeryMenu = new Menu(6, "Archery Settings", viewer);
        for(Target target: targets){
            archeryMenu.addItem(new TargetMenuItem(target.getName(),target.getDisplayItem(),target,this));
        }
        if(menu != null)
            archeryMenu.addItem(new MenuItemPage("Back", Material.REDSTONE_TORCH_ON, menu), archeryMenu.getSize() - 9);
        return archeryMenu;
    }
    
    
    public boolean displayMechanicSettings(Menu previous) {
        return false;
    }
    
    public boolean isTarget(Entity entity){
        for(Target target: targets){
            if(target instanceof EntityTarget){
                if(((EntityTarget) target).getTarget().getUniqueId() == entity.getUniqueId())return true;
            }
        }
        return false;
    }
    
    public boolean isTarget(Block block){
        for(Target target: targets){
            if(target instanceof BlockTarget){
                if(((BlockTarget) target).getBlock() == block)return true;
            }
        }
        return false;
    }
    
    public Target getTarget(Entity entity){
        for(Target target: targets){
            if(target instanceof EntityTarget){
                if(((EntityTarget) target).getTarget().getUniqueId() == entity.getUniqueId())return target;
            }
        }
        return null;
    }
    
    public Target getTarget(Block block){
        for(Target target: targets){
            if(target instanceof BlockTarget){
                if(((BlockTarget) target).getBlock() == block)return target;
            }
        }
        return null;
    }
}
