package au.com.addstar;



import au.com.addstar.events.TargetHitEvent;
import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.MinigameUtils;
import au.com.mineauz.minigames.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArcheryEventListener implements Listener {

    public ArcheryEventListener() {
    }


	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		MinigameUtils.debugMessage("ProjectileHitEvent:" + event.getHitBlock());

		if(event.getEntity().getShooter() instanceof Player){
			MinigameUtils.debugMessage("ProjectileHitEvent:" +((Player) event.getEntity().getShooter()).getName());
			MinigamePlayer ply = Archery.getMinigamePlugin().getPlayerManager().getMinigamePlayer((Player)event.getEntity().getShooter());
			if(ply != null && ply.isInMinigame()){
				Minigame mg = ply.getMinigame();
				MinigameUtils.debugMessage("PlayerProjectileHit:" + event.getEntity().toString());
				Target target = null;
				if(event.getHitEntity() != null){
					MinigameUtils.debugMessage("EntityHit:" + event.getHitEntity());
					ArcheryModule module = (ArcheryModule) mg.getModule(ArcheryModule.name);
					if(module.isTarget(event.getHitEntity())){
						target = module.getTarget(event.getHitEntity());
					}
				}
				if(event.getHitBlock() != null){
					MinigameUtils.debugMessage("BlockHit:" + event.getHitBlock());

					ArcheryModule module = (ArcheryModule) mg.getModule(ArcheryModule.name);
					if(module.isTarget(event.getHitBlock())){
						target = module.getTarget(event.getHitBlock());
					}
				}
				if(target != null){
					TargetHitEvent targetevent = new TargetHitEvent(mg,target,ply,event.getEntity());
					Bukkit.getPluginManager().callEvent(targetevent);
					MinigameUtils.debugMessage("Target detected");
					target.executeActions(ply);
				}else{
                    MinigameUtils.debugMessage("No Target detected");
                }
			}
		}
	}
}		//todo do something ....

