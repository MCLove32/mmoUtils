package azisaba.net.mmoutils.utils;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock;
import org.jetbrains.annotations.NotNull;

public class BlockUtil {

    public static void breakBlockType1(@NotNull World w, BlockPosition position, IBlockData data) {

        WorldServer access = ((CraftWorld) w).getHandle();
        CraftBlock.setTypeAndData(access, position, access.a_(position), data, true);
    }

    public static void breakBlockType2(World w, BlockPosition position, IBlockData data) {

        WorldServer access = ((CraftWorld) w).getHandle();

        IChunkAccess chunkAccess = access.x(position);
        chunkAccess.a(position, data, true);

    }
}
