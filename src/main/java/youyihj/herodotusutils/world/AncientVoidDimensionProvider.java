package youyihj.herodotusutils.world;

import com.bloodnbonesgaming.topography.config.ConfigurationManager;
import com.bloodnbonesgaming.topography.world.chunkgenerator.ChunkGeneratorVoid;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.herodotusutils.client.render.RiftSkyRenderer;
import youyihj.herodotusutils.proxy.CommonProxy;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author youyihj
 */
public class AncientVoidDimensionProvider extends WorldProvider {
    private IRenderHandler skyRenderer;

    @Override
    public DimensionType getDimensionType() {
        return CommonProxy.ANCIENT_VOID_DIMENSION;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        if (skyRenderer == null) {
            skyRenderer = new RiftSkyRenderer();
        }
        return skyRenderer;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return new BiomeProviderSingle(Objects.requireNonNull(Biome.getBiome(46)));
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        float f = MathHelper.cos(p_76562_1_ * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        float f1 = 0.627451F;
        float f2 = 0.5019608F;
        float f3 = 0.627451F;
        f1 = f1 * (f * 0.0F + 0.15F);
        f2 = f2 * (f * 0.0F + 0.15F);
        f3 = f3 * (f * 0.0F + 0.15F);
        return new Vec3d(f1, f2, f3);
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorVoid(world, 8151753964130558908L, ConfigurationManager.getInstance().getPreset().getDefinition(9));
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public int getMoonPhase(long worldTime) {
        return 0;
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.0F;
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    public boolean canRespawnHere() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 8.0F;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}
