package me.mace.notify;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MaceNotify extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("MaceNotify enabled!");
    }

    @EventHandler
    public void onMaceCraft(CraftItemEvent event) {

        if (event.isCancelled()) {
            return;
        }

        ItemStack result = event.getRecipe().getResult();

        if (result.getType() != Material.MACE) {
            return;
        }

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        Bukkit.getScheduler().runTask(this, () -> {

            Component message = Component.text()
                    .append(Component.text(
                            "⚔ MACE CRAFT ALERT ⚔",
                            NamedTextColor.GOLD))
                    .append(Component.newline())
                    .append(Component.text(
                            player.getName() + " crafted a Mace!",
                            NamedTextColor.YELLOW))
                    .build();

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(message);
                online.playSound(
                        online.getLocation(),
                        Sound.BLOCK_ANVIL_USE,
                        1.0f,
                        1.0f
                );
            }

            getLogger().info(
                    player.getName() + " crafted a Mace!"
            );
        });
    }
}
