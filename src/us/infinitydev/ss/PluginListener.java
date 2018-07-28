package us.infinitydev.ss;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PluginListener implements Listener{
	
	@EventHandler
	public void onMilkDrink(PlayerItemConsumeEvent e) {
		if(ScuffedSpeedCore.instance.speedEnabled.contains(e.getPlayer().getUniqueId())) {
			if(e.getItem().getType().equals(Material.MILK_BUCKET)) {
				ScuffedSpeedCore.instance.speedEnabled.remove(e.getPlayer().getUniqueId());
				return;
			}else {
				return;
			}
		}else {
			return;
		}
	}

}
