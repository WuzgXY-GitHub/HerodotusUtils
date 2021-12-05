package youyihj.herodotusutils.entity.golem;

import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author youyihj
 */
@SideOnly(Side.CLIENT)
public class RenderExtraIronGolem extends RenderIronGolem {
    public RenderExtraIronGolem(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityIronGolem entity) {
        // TODO: texture
        return super.getEntityTexture(entity);
    }
}
