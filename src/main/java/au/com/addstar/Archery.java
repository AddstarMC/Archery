package au.com.addstar;


import au.com.addstar.targets.BlockTarget;
import au.com.addstar.targets.EntityTarget;
import au.com.mineauz.minigames.MinigameSave;
import au.com.mineauz.minigames.Minigames;
import au.com.mineauz.minigames.tool.ToolModes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Archery extends JavaPlugin {
	
	public static Minigames getMinigamePlugin() {
		return minigamePlugin;
	}
	
	private static Minigames minigamePlugin;
	public static FileConfiguration lang;
	//ClassListeners
	private final Listener eventListener = new ArcheryEventListener();

	public void onDisable() {
		if(minigamePlugin == null)return;
		minigamePlugin.getMinigameManager().removeModule(ArcheryModule.name,ArcheryModule.class);
		ToolModes.removeToolMode(ArcheryToolMode.name);
		minigamePlugin.getLogger().info("Archery module disabled");
	}

	public void onEnable() {
		

		PluginManager pm = this.getServer().getPluginManager();
		Plugin plugin = pm.getPlugin("Minigames");
		if(plugin != null && plugin instanceof Minigames && plugin.isEnabled()){
			minigamePlugin = (Minigames) plugin;
		}else{
			Bukkit.getLogger().severe("Could not find dependency: Minigames - disabling" );
			this.getPluginLoader().disablePlugin(this);
			return;
		}
		loadLanguage();

		minigamePlugin.getMinigameManager().addModule(ArcheryModule.class);
		ToolModes.addToolMode(new ArcheryToolMode());

		ConfigurationSerialization.registerClass(BlockTarget.class);
		ConfigurationSerialization.registerClass(EntityTarget.class);
		// you can register multiple classes to handle events if you want
		// just call pm.registerEvents() on an instance of each class
		pm.registerEvents(eventListener, this);
		// do any other initialisation you need here...
	}
	
	private void loadLanguage(){
        String locale = minigamePlugin.getConfig().getString("lang","en_AU");
        MinigameSave sv = new MinigameSave("lang/archery_" + locale);
		lang = sv.getConfig();
		InputStream is = getClassLoader().getResourceAsStream(locale+".yml");
		if(is == null)is = getClassLoader().getResourceAsStream("en_AU.yml");
		OutputStream os=null;
		try {
		    File file = new File(minigamePlugin.getDataFolder() + "/lang/archery_"+locale+".yml");
		    if(!file.exists())file.createNewFile();
			os = new FileOutputStream(file);
		}catch (IOException e){
			e.printStackTrace();
		}
        byte[] buffer = new byte[4096];
		int length;

            try {
                if(os !=null) {
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    os.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

		MinigameSave svb = new MinigameSave("lang/archery_"+locale);
		lang.setDefaults(svb.getConfig());
	}
	
}
