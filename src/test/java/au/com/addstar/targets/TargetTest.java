package au.com.addstar.targets;

import au.com.addstar.Archery;
import au.com.addstar.actions.AddScoreArcheryAction;
import au.com.addstar.actions.ArcheryActionInterface;
import au.com.addstar.actions.DeductScoreArcheryAction;
import au.com.addstar.actions.PlayerMessageArcheryAction;
import au.com.mineauz.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created for the AddstarMC Project.
 * Created by Narimm on 27/03/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, Archery.class})
public class TargetTest {
    
    
    private static UUID entityUUID = UUID.randomUUID();
    private static UUID otherUUID = UUID.randomUUID();
    private static final Block blockTarget  =  mock(Block.class);
    private static final World world = mock(World.class);
    private static Location loc = new Location(world,0,0,0);
    private static BlockTarget bTarget;
    private static EntityTarget eTarget;
    private static Map<String,Object> blockMap = new HashMap<>();
    private static Map<String,Object> entityMap = new HashMap<>();
    private static final Logger log = Logger.getLogger("Test");
    
    
    
    
    @Before
    public void setUp(){
        mockStatic(Bukkit.class);
        mockStatic(Archery.class);
        ItemFactory factory = mock(ItemFactory.class);
        Entity entity = mock(Skeleton.class);
        Minigames plugin = mock(Minigames.class);
        when(plugin.getLogger()).thenReturn(log);
        when(Bukkit.getEntity(entityUUID)).thenReturn(entity);
        when(Bukkit.getEntity(otherUUID)).thenReturn(null);
        when(Bukkit.getItemFactory()).thenReturn(factory);
        when(Archery.getMinigamePlugin()).thenReturn(plugin);
        SpawnEggMeta meta = mock(SpawnEggMeta.class);
        when(meta.getSpawnedType()).thenReturn(EntityType.SKELETON);
        when(meta.getDisplayName()).thenReturn("Skeleton");
        when(meta.hasDisplayName()).thenReturn(true);
        when(factory.getItemMeta(Material.MONSTER_EGG)).thenReturn(meta);
        when(entity.getUniqueId()).thenReturn(entityUUID);
        when(entity.getType()).thenReturn(EntityType.SKELETON);
        when(entity.getName()).thenReturn("Skeleton");
        when(entity.getEntityId()).thenReturn(1);
        when(entity.getLocation()).thenReturn(loc);
        when(blockTarget.getLocation()).thenReturn(loc);
        when(blockTarget.getType()).thenReturn(Material.STONE);
        when(world.getBlockAt(isA(Location.class))).thenReturn(blockTarget);
        bTarget = new BlockTarget(blockTarget,0);
        bTarget.addAction(new AddScoreArcheryAction());
        eTarget = new EntityTarget(entity,1);
        eTarget.addAction(new DeductScoreArcheryAction());
        blockMap.put("blockLocation",loc);
        blockMap.put(("score"),0);
        Map<String,String> params = new HashMap<>();
        params.put("message","Test Message");
        ArcheryActionInterface action = new PlayerMessageArcheryAction();
        Map<String,Map<String,String>> actions = new HashMap<>();
        actions.put(action.getName(),params);
        blockMap.put("actions",actions);
        entityMap.put("entityUUID",entityUUID.toString());
        entityMap.put("score",1);
    }
    
    @Test
    public void BlockTargetdeserialize() {
        Target t = BlockTarget.deserialize(blockMap);
        assertEquals("BLOCKTARGET",t.getType());
        BlockTarget target = (BlockTarget) t;
        assertEquals(loc,target.getBlock().getLocation());
        assertEquals(0,target.getScore());
        assertEquals(1,target.getActions().size());
        blockMap.put("actions"," Bad Value");
        target = BlockTarget.deserialize(blockMap);
        assertEquals(0,target.getActions().size());
        blockMap.put("blockLocation",null);
        target = BlockTarget.deserialize(blockMap);
        assertEquals(null,target.getBlock());
        
    }
    
    @Test
    public void BlockTargetserialize() {
        Map result = bTarget.serialize();
        assertEquals(loc,result.get("blockLocation"));
        assertEquals(0,result.get("score"));
        Map<String, Map<String, String>>  actions = (Map<String, Map<String, String>> ) result.get("actions");
        Set<String> keys = actions.keySet();
        assertEquals("ADD_SCORE",keys.iterator().next());
    }
    @Test
    public void EntityTargetserialize(){
        Map result = eTarget.serialize();
        assertEquals(entityUUID.toString(),result.get("entityUUID"));
        assertEquals(1, result.get("score"));
        Map<String, Map<String, String>>  actions = (Map<String, Map<String, String>> ) result.get("actions");
        Set<String> keys = actions.keySet();
        assertEquals("DEDUCT_SCORE",keys.iterator().next());
    
    
    }
    
    @Test
    public void EntityTargetdeserialize(){
        EntityTarget target = EntityTarget.deserialize(entityMap);
        assertEquals(1,target.getTarget().getEntityId());
        assertEquals(entityUUID,target.getTarget().getUniqueId());
        assertEquals(1,target.getScore());
        entityMap.put("entityUUID",otherUUID.toString());
        target = EntityTarget.deserialize(entityMap);
        assertEquals(null, target.getTarget());
    }
    @Test
    public void TargetSetScoreTest(){
        assertEquals(1,eTarget.getScore());
        eTarget.setScore(2);
        assertEquals(2,eTarget.getScore());
        assertEquals(0,bTarget.getScore());
        bTarget.setScore(1);
        assertEquals(1,bTarget.getScore());
    }
    @Test
    public void TargetNameTest(){
        assertEquals("Skeleton(1)", eTarget.getName());
        assertEquals("STONE 0,0,0",bTarget.getName());
    }
    @Test(expected = UnsupportedOperationException.class)
    public void AbstractTargetDeserialize(){
         AbstractTarget.deserialize(entityMap);
    }
    
    @Test
    public void TestgetDisplayItem(){
        ItemStack stack = eTarget.getDisplayItem();
        assertEquals(Material.MONSTER_EGG, stack.getType());
        assertEquals(1,stack.getAmount());
        ItemStack stack2 = bTarget.getDisplayItem();
        assertEquals(Material.STONE, stack2.getType());
        assertEquals(1,stack.getAmount());
    }
    
}