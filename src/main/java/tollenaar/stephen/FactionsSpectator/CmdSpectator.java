package tollenaar.stephen.FactionsSpectator;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.TL;

public class CmdSpectator extends FCommand {

	public CmdSpectator() {
		super();
		this.aliases.add("spec");
		this.aliases.add("spectator");

		this.requirements = new CommandRequirements.Builder(Permission.FLY).memberOnly().playerOnly().build();
	}

	@Override
	public TL getUsageTranslation() {
		return null;
	}

	@Override
	public void perform(CommandContext ctx) {
		Player player = (Player) ctx.player;
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		Faction faction = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));

		// Checking is player is able to switch to spectator in its own claims
		if (player.getGameMode() == GameMode.SURVIVAL && faction.getId().equals(fplayer.getFactionId())) {
			player.setGameMode(GameMode.SPECTATOR);
		} else if (player.getGameMode() == GameMode.SPECTATOR) {
			player.setGameMode(GameMode.SURVIVAL);
		}
	}
}
