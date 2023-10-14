package azisaba.net.mmoutils.utils;

import io.netty.channel.Channel;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class PlayerUtil {

    public static PlayerConnection getConnection(Player p) throws NoSuchFieldException, IllegalAccessException {

        EntityPlayer player = ((CraftPlayer) p).getHandle();
        Field f = player.getClass().getDeclaredField("b");
        f.setAccessible(true);
        return (PlayerConnection) f.get(player);
    }

    public static NetworkManager getNetwork(Player p) throws NoSuchFieldException, IllegalAccessException {

        PlayerConnection connection = getConnection(p);
        Field manager = connection.getClass().getDeclaredField("h");
        manager.setAccessible(true);
        return  (NetworkManager) manager.get(connection);
    }

    @NotNull
    public static Channel getChannel(@NotNull Player p) throws IllegalAccessException, NoSuchFieldException {

        NetworkManager network = getNetwork(p);
        Field f = network.getClass().getDeclaredField("m");
        f.setAccessible(true);

        return (Channel) f.get(network);
    }

    public static void sendPacketPlayer(Player p, Packet<?> packet) throws NoSuchFieldException, IllegalAccessException {

       getConnection(p).b(packet);
    }

    public static void dropPlayer(@NotNull Player player, ItemStack stack) {

        for (ItemStack item: player.getInventory().addItem(stack).values()) {
            if (item == null) continue;
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }
}
