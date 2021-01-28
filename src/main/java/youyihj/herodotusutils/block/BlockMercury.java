package youyihj.herodotusutils.block;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import youyihj.herodotusutils.fluid.FluidMercury;
import youyihj.zenutils.world.ZenUtilsWorld;

import java.util.Random;

/**
 * @author youyihj
 */
public class BlockMercury extends BlockFluidClassic {
    private BlockMercury() {
        super(FluidMercury.INSTANCE, Material.WATER);
        this.setTickRandomly(true);
        this.setRegistryName(FluidMercury.INSTANCE.getName());
    }

    public static final String TAG_POLLUTION = "mercury_polluted";
    public static final BlockMercury INSTANCE = new BlockMercury();

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote) {
            worldIn.setBlockToAir(pos);
            IData toUpdateData = new DataMap(Maps.asMap(Sets.newHashSet(TAG_POLLUTION), (s) -> new DataInt(1)), true);
            ZenUtilsWorld.updateCustomChunkData(CraftTweakerMC.getIWorld(worldIn), toUpdateData, CraftTweakerMC.getIBlockPos(pos));
        }
    }
}
