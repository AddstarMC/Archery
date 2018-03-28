package au.com.addstar;

import au.com.addstar.actions.*;
import au.com.addstar.targets.BlockTarget;
import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.Minigames;
import au.com.mineauz.minigames.minigame.Minigame;
import org.bukkit.*;
import org.bukkit.block.Block;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 25/03/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class,Minigames.class,Archery.class})
@SuppressStaticInitializationFor("au.com.mineauz.minigames.MinigameUtils")
public class ArcheryModuleTest {
    private static final Block blockTarget  =  mock(Block.class);
    private static final Block otherblock = mock(Block.class);
    private static final World world = mock(World.class);
    private static final Location loc = new Location(world,0,0,0);
    private static final Location otherLoc = new Location(world,1,1,1);
    
    private static final Minigame game = mock(Minigame.class);
    private static final Player player = mock(Player.class);
    private static MinigamePlayer minigamePlayer;
    private static final Minigames plugin = mock(Minigames.class);
    private static final Server server = mock(Server.class);
    private static final PluginManager pluginManager = mock(PluginManager.class);
    private static final UUID uuid = UUID.randomUUID();
    private static final Logger log = Logger.getLogger("Test");
    
    @BeforeClass
    public static void Setup(){
        
        when(blockTarget.getLocation()).thenReturn(loc);
        when(otherblock.getLocation()).thenReturn(otherLoc);
        when(player.getDisplayName()).thenReturn("TestPlayer");
        when(player.getName()).thenReturn("TestPlayer");
        when(player.getUniqueId()).thenReturn(uuid);
        when(world.getName()).thenReturn("TestWorld");
        when(blockTarget.getType()).thenReturn(Material.STONE);
        when(otherblock.getType()).thenReturn(Material.WOOL);
        when(game.getName(false)).thenReturn("Test");
        when(game.getName(true)).thenReturn("Test");
        when(game.usePlayerDisplayNames()).thenReturn(true);
        PowerMockito.mockStatic(Bukkit.class);
        PowerMockito.mockStatic(Minigames.class);
        PowerMockito.mockStatic(Archery.class);
        when(Archery.getMinigamePlugin()).thenReturn(plugin);
        when(Minigames.getPlugin()).thenReturn(plugin);
        when(plugin.isDebugging()).thenReturn(true);
        when(plugin.getLogger()).thenReturn(log);
        when(Bukkit.getServer()).thenReturn(server);
        when(Bukkit.getPluginManager()).thenReturn(pluginManager);
        doNothing().when(pluginManager).callEvent(Matchers.isA(Event.class));
        when(server.broadcast(Matchers.anyString(),Matchers.anyString())).thenReturn(1);
        minigamePlayer = new MinigamePlayer(player);
        minigamePlayer.setMinigame(game);
    }
    @Test
    public void save() {
        String message = "Test Message";
        File file = new File("target/test","test.yml");
        if(file.exists())file.delete();
        try {
            if(!new File(file.getParent()).exists())new File(file.getParent()).mkdir();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArcheryModule mod = new ArcheryModule(game);
        BlockTarget target = new BlockTarget(blockTarget,1);
        mod.targets.add(target);
        target.addAction(new AddScoreArcheryAction());
        target.addAction(new DeductScoreArcheryAction());
        target.addAction(new PlayerMessageArcheryAction().setMessage(message));
        target.addAction(new ServerBroadCastArcheryAction().setMessage(message));
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        mod.save(config);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(mod.isTarget(target.getBlock()));
        Object o = config.getList("minigame.ARCHERY.targets").get(0);
        assertTrue(o instanceof BlockTarget);
        Target b = (BlockTarget) o;
        assertTrue(minigamePlayer.getScore() == 0);
        assertTrue(b.getActions() == target.getActions());
        for(ArcheryActionInterface action: b.getActions()){
            action.executeAction(minigamePlayer,target);
            if(action instanceof AddScoreArcheryAction){
                assertEquals(1,minigamePlayer.getScore());
            }
        }
        assertTrue(minigamePlayer.getScore() == 0);
        assertTrue(mod.isTarget(blockTarget));
        assertEquals(target,mod.getTarget(blockTarget));
        assertFalse(mod.isTarget(otherblock));
        assertEquals(null,mod.getTarget(otherblock));
    }
}