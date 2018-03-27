package au.com.addstar;

import au.com.addstar.actions.AddScoreArcheryAction;
import au.com.addstar.targets.BlockTarget;
import au.com.addstar.targets.Target;
import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.MinigamePlayerManager;
import au.com.mineauz.minigames.Minigames;
import au.com.mineauz.minigames.minigame.Minigame;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.PluginManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created for the AddstarMC Project.
 * Created by Narimm on 27/03/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class,Minigames.class,Archery.class})
@SuppressStaticInitializationFor("au.com.mineauz.minigames.MinigameUtils")
public class ArcheryEventListenerTest {
    private static final Block blockTarget  =  mock(Block.class);
    private static final World world = mock(World.class);
    private static final Location loc = new Location(world,0,0,0);
    private static final Minigame game = mock(Minigame.class);
    private static final Player player = mock(Player.class);
    private static MinigamePlayer minigamePlayer;
    private static final Minigames plugin = mock(Minigames.class);
    private static final Server server = mock(Server.class);
    private static final PluginManager pluginManager = mock(PluginManager.class);
    private static final UUID uuid = UUID.randomUUID();
    private static final UUID projectileUUID = UUID.randomUUID();
    
    private static final Logger log = Logger.getLogger("Test");
    private static final Projectile projectile= mock(Projectile.class);
    private ArcheryModule mod;
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Bukkit.class);
        PowerMockito.mockStatic(Minigames.class);
        PowerMockito.mockStatic(Archery.class);
        when(blockTarget.getLocation()).thenReturn(loc);
        when(player.getDisplayName()).thenReturn("TestPlayer");
        when(player.getName()).thenReturn("TestPlayer");
        when(player.getUniqueId()).thenReturn(uuid);
        when(world.getName()).thenReturn("TestWorld");
        when(blockTarget.getType()).thenReturn(Material.STONE);
        when(game.getName(false)).thenReturn("Test");
        when(game.getName(true)).thenReturn("Test");
        when(game.usePlayerDisplayNames()).thenReturn(true);
        when(Minigames.getPlugin()).thenReturn(plugin);
        when(plugin.isDebugging()).thenReturn(true);
        when(plugin.getLogger()).thenReturn(log);
        when(Bukkit.getServer()).thenReturn(server);
        when(Bukkit.getPluginManager()).thenReturn(pluginManager);
        doNothing().when(pluginManager).callEvent(Matchers.isA(Event.class));
        when(server.broadcast(Matchers.anyString(),Matchers.anyString())).thenReturn(1);
        when(projectile.getShooter()).thenReturn(player);
        when(projectile.getServer()).thenReturn(server);
        when(projectile.getUniqueId()).thenReturn(projectileUUID);
        when(projectile.getWorld()).thenReturn(world);
        when(Archery.getMinigamePlugin()).thenReturn(plugin);
        MinigamePlayerManager manager = new MinigamePlayerManager();
        when(plugin.getPlayerManager()).thenReturn(manager);
        mod = new ArcheryModule(game);
        when(game.getModule(ArcheryModule.name)).thenReturn(mod);
        manager.addMinigamePlayer(player);
        manager.getMinigamePlayer(player).setMinigame(game);
        minigamePlayer = manager.getMinigamePlayer(player);
    }
    
    @Test
    public void TestonProjectileHit() {
        Target target = new BlockTarget(blockTarget,1);
        mod.targets.add(target);
        target.addAction(new AddScoreArcheryAction());
        ArcheryEventListener listener = new ArcheryEventListener();
        ProjectileHitEvent event = new ProjectileHitEvent(projectile,blockTarget);
        assertEquals(minigamePlayer.getScore(),0);
        listener.onProjectileHit(event);
        assertEquals(minigamePlayer.getScore(),1);
    
    }
}