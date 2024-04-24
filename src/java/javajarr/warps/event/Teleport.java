package javajarr.warps.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Position;

public class Teleport implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Position pos = player.getPosition();
        // Check if the teleport was a warp
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN)
            return;
        player.setImmobile(true);

        // Check if the target chunk is loaded
        if (!pos.getChunk().isLoaded()) {
            // Wait for the chunk to load
            player.getLevel().loadChunk(pos.getChunk().getX(), pos.getChunk().getZ());
        }
        player.setImmobile(false);
    }
}