package youyihj.herodotusutils.entity;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public class EntityExtraGolem extends EntityIronGolem {
    private IExtraGolemInfo info;

    public EntityExtraGolem(World worldIn) {
        super(worldIn);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("type", info.getName());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setInfo(IExtraGolemInfo.REGISTRY.get(compound.getString("type")));
    }

    public void setInfo(IExtraGolemInfo info) {
        this.info = info;
    }

    public IExtraGolemInfo getInfo() {
        return info;
    }
}
