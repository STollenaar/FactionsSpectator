package tollenaar.stephen.FactionsSpectator;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.FCmdRoot;

public class Core extends JavaPlugin implements Listener, CommandExecutor {


	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		// Adding the spectator command to the factions base command executor
		try {
		FCmdRoot root = (FCmdRoot) Bukkit.getPluginCommand("factions").getExecutor();
		root.addSubCommand(new CmdSpectator());
		}catch(Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, e.toString());
			Bukkit.getLogger().log(Level.SEVERE, "PLUGIN IS SHUTTING DOWN");
			// disabling this server
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
	}

	@EventHandler
	public void onPlayerMovement(PlayerMoveEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
			FPlayer fplayer = FPlayers.getInstance().getByPlayer(event.getPlayer());
			Faction faction = Board.getInstance().getFactionAt(new FLocation(event.getTo()));
			if (!faction.getId().equals(fplayer.getFactionId())) {
				event.getPlayer().setGameMode(GameMode.SURVIVAL);
			}
		}
	}


}