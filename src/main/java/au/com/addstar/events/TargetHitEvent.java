package au.com.addstar.events;

import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.events.AbstractMinigameEvent;
import au.com.mineauz.minigames.minigame.Minigame;
import org.bukkit.entity.Projectile;


/**
 * au.com.addstar.events
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class TargetHitEvent extends AbstractMinigameEvent {
    
    private final Target target;
    private final MinigamePlayer shooter;
    private final Projectile projectile;
    
    public TargetHitEvent(Minigame mgm, Target target, MinigamePlayer shooter, Projectile projectile) {
        super(mgm);
        this.target = target;
        this.shooter = shooter;
        this.projectile = projectile;
    }
    
    public Target getTarget() {
        return target;
    }
    
    public MinigamePlayer getShooter() {
        return shooter;
    }
    
    public Projectile getProjectile() {
        return projectile;
    }
}
