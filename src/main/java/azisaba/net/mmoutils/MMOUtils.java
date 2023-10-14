package azisaba.net.mmoutils;

import azisaba.net.mmoutils.utils.PlayerUtil;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythiccrucible.MythicCrucible;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MMOUtils extends JavaPlugin {

    private static MMOUtils utils;

    @Override
    public void onEnable() {
        // Plugin startup logic
        utils = this;
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
    }

    public static MMOUtils getUtils() {return utils;}

    public static MythicCrucible getCrucible() {
        return MythicCrucible.inst();
    }

    public static MythicBukkit getMythic() {
        return MythicBukkit.inst();
    }

    public void packetSetUP(Player p, String id, ChannelHandler handler) {

        try {
            Channel channel = PlayerUtil.getChannel(p);
            channel.pipeline().addBefore("packet_handler", id, handler);
        } catch (Exception ex) {
            getLogger().warning("Failed to inject channel handler to " + p.getName() + ": catch :" + ex);
        }
    }

    public void packetRemove(Player p, String id) {
        try {
            ChannelPipeline pipeline = PlayerUtil.getChannel(p).pipeline();
            if (pipeline.get(id) != null) {
                pipeline.remove(id);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
