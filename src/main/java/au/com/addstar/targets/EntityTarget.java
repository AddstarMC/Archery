package au.com.addstar.targets;

import au.com.addstar.Archery;
import au.com.mineauz.minigames.config.IntegerFlag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * au.com.addstar
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class EntityTarget extends AbstractTarget {
    private final static String name = "ENTITYTARGET";
    private static final long serialVersionUID = -9031542301133584289L;
    private final IntegerFlag score;
    
    public Entity getTarget() {
        return target;
    }
    
    private final Entity target;
    
    public EntityTarget(Entity target, int score) {
        setType(name);
        this.target = target;
        this.score = new IntegerFlag(0,"score");
        this.score.setFlag(score);
    }
    
    
    public int getScore() {
        return score.getFlag();
    }
    
    @Override
    public void setScore(int a) {
        score.setFlag(a);
    }
    
    @Override
    public String getName() {
        return target.getName()+"("+target.getEntityId()+")";
    }
    
    @Override
    public ItemStack getDisplayItem() {
        Material material = Material.MONSTER_EGG;
        ItemStack itemStack = new ItemStack(material);
        ((SpawnEggMeta)itemStack.getItemMeta()).setSpawnedType(target.getType());
        return itemStack;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("entityUUID", target.getUniqueId().toString());
        result.put("score", score.getFlag());
        saveActions(result,this);
        return result;
    }

    @SuppressWarnings("unused")
    public static EntityTarget deserialize(Map<String, Object> map) {
        String u = (String) map.get("entityUUID");
        UUID uuid = UUID.fromString(u);
        int score = (int) map.get("score");
        Entity t = Bukkit.getEntity(uuid);
        if(t ==  null ){
            Archery.getMinigamePlugin().getLogger().warning("EntityTarget null: Entity NOT Found: " + uuid);
            return new EntityTarget(null,0);
        }
        EntityTarget target = new EntityTarget(t,score);
        loadActions(map, target);
        return target;
    }
}
