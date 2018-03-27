package au.com.addstar.targets;

import au.com.mineauz.minigames.config.IntegerFlag;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * au.com.addstar
 * Created for the Addstar MC for Archery
 * Created by Narimm on 22/03/2018.
 */
public class BlockTarget extends AbstractTarget {
    private final static String name = "BLOCKTARGET";
    private static final long serialVersionUID = 2608534332325726598L;
    
    public Block getBlock() {
        return block;
    }
    
    private final Block block;
    
    public void setScore(int score) {
        this.score.setFlag(score);
    }
    
    private final IntegerFlag score;
    
    public BlockTarget(Block block, int score) {
        setType(name);
        this.score = new IntegerFlag(0,"score");
        this.block = block;
        this.score.setFlag(score);
    }
    
    public int getScore(){
        return score.getFlag();
    }
    
    @Override
    public String getName() {
        return block.getType().name()+" "+block.getLocation().getBlockX()+","+ block.getLocation().getBlockY() +","+ block.getLocation().getBlockZ();
    }
    
    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack(block.getType(),1);
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("blockLocation",block.getLocation());
        result.put("score",score.getFlag());
        saveActions(result,this);
        return result;
    }

    @SuppressWarnings("unused")
    public static BlockTarget deserialize(Map<String, Object> map){
        Location location = (Location) map.get("blockLocation");
        int score = (int) map.get("score");
        if(location !=null) {
            BlockTarget target = new BlockTarget(location.getBlock(), score);
            loadActions(map, target);
            return target;
        }
        return new BlockTarget(null,0);
    }
    
    
}
