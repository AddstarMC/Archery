package au.com.addstar;

import au.com.addstar.targets.BlockTarget;
import au.com.addstar.targets.EntityTarget;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.minigame.Minigame;
import au.com.mineauz.minigames.minigame.Team;
import au.com.mineauz.minigames.tool.MinigameTool;
import au.com.mineauz.minigames.tool.ToolMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * au.com.addstar
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class ArcheryToolMode implements ToolMode {

    public static final String name = "ARCHERY";

    @Override
    public  String getName() {
        return name;
    }
    
    @Override
    public String getDisplayName() {
        return "Archery Target Creation";
    }
    
    @Override
    public String getDescription() {
        return "Creates an Archery Target from the clicked object; Entity or Block";
    }
    
    @Override
    public Material getIcon() {
        return Material.BOW;
    }
    
    @Override
    public void onSetMode(MinigamePlayer player, MinigameTool tool) {
    
    }
    
    @Override
    public void onUnsetMode(MinigamePlayer player, MinigameTool tool) {
    
    }
    
    @Override
    public void onLeftClick(MinigamePlayer player, Minigame minigame, Team team, PlayerInteractEvent event) {
        ArcheryModule module = (ArcheryModule) minigame.getModule(ArcheryModule.name);
        if(module.isTarget(event.getClickedBlock())) {
            player.sendInfoMessage(Archery.lang.getString("Target_is_Target"));
        } else {
            module.targets.add(new BlockTarget(event.getClickedBlock(),0));
        }
        setTargetBlock(player,minigame,module, event);
    }

    private void setTargetBlock(MinigamePlayer player,Minigame minigame, ArcheryModule module, PlayerInteractEvent event) {
        if(module.isTarget(event.getClickedBlock())){
            event.setCancelled(true);
            player.sendInfoMessage(event.getClickedBlock().getType().name() + " is now a target in " +minigame.getName(false));
            player.getPlayer().sendBlockChange(event.getClickedBlock().getLocation(), Material.REDSTONE_BLOCK, (byte) 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Archery.getMinigamePlugin(), () -> player.getPlayer().sendBlockChange(event.getClickedBlock().getLocation(),event.getClickedBlock().getType(), event.getClickedBlock().getData()),100);
        }
    }

    @Override
    public void onRightClick(MinigamePlayer player, Minigame minigame, Team team, PlayerInteractEvent event) {
        ArcheryModule module = (ArcheryModule) minigame.getModule(ArcheryModule.name);
        if(event.getClickedBlock() == null)return;
        if(module.isTarget(event.getClickedBlock())) {
            player.sendInfoMessage(Archery.lang.getString("Target_is_Target"));
        } else {
            module.targets.add(new BlockTarget(event.getClickedBlock(),0));
        }
        setTargetBlock(player,minigame,module, event);
    }

    public void onEntityLeftClick(MinigamePlayer player, Minigame minigame, Team team, EntityDamageByEntityEvent event) {
        ArcheryModule module = (ArcheryModule) minigame.getModule(ArcheryModule.name);
        if(module.isTarget(event.getEntity())) {
            player.sendInfoMessage(Archery.lang.getString("Target_is_Target"));
        } else {
            module.targets.add(new EntityTarget(event.getEntity(),0));
        }
        if(module.isTarget(event.getEntity())){
            event.setCancelled(true);
            player.sendInfoMessage(event.getEntity().getType().name() + " is now a target in " +minigame.getName(false));
            event.getEntity().setGlowing(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Archery.getMinigamePlugin(), () -> event.getEntity().setGlowing(false),100);
        }
    }
    
    public void onEntityRightClick(MinigamePlayer player, Minigame minigame, Team team, PlayerInteractEntityEvent event) {
        ArcheryModule module = (ArcheryModule) minigame.getModule(ArcheryModule.name);
        if(module.isTarget(event.getRightClicked())) {
            player.sendInfoMessage(Archery.lang.getString("Target_is_Target"));
        } else {
            module.targets.add(new EntityTarget(event.getRightClicked(),0));
        }
        if(module.isTarget(event.getRightClicked())){
            event.setCancelled(true);
            player.sendInfoMessage(event.getRightClicked().getType().name() + " is now a target in " +minigame.getName(false));
            event.getRightClicked().setGlowing(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Archery.getMinigamePlugin(), () -> event.getRightClicked().setGlowing(false),100);
        }
    }
    
    @Override
    public void select(MinigamePlayer player, Minigame minigame, Team team) {
    
    }
    
    @Override
    public void deselect(MinigamePlayer player, Minigame minigame, Team team) {
    
    }
}
