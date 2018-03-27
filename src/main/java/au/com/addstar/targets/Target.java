package au.com.addstar.targets;

import au.com.addstar.actions.ArcheryActionInterface;
import au.com.mineauz.minigames.MinigamePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

/**
 * Created for the AddstarMC Project.
 * Created by Narimm on 22/03/2018.
 */
public interface Target extends ConfigurationSerializable,Serializable {
    
    String getType();
    int getScore();
    void setScore(int a);
    List<ArcheryActionInterface> getActions();
    void addAction(ArcheryActionInterface action);
    String getName();
    ItemStack getDisplayItem();
    void executeActions(MinigamePlayer player);
}
