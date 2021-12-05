package youyihj.herodotusutils.entity.golem;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import youyihj.herodotusutils.util.SharedRiftAction;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class EntityExtraIronGolem extends EntityIronGolem {

    private Color color;
    private Shape shape;
    private int level;

    public EntityExtraIronGolem(World worldIn) {
        super(worldIn);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
        nbtTagCompound.setString("Color", color.name());
        nbtTagCompound.setString("Shape", shape.name());
        nbtTagCompound.setInteger("Level", level);
        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.color = Color.valueOf(compound.getString("Color"));
        this.shape = Shape.valueOf(compound.getString("Shape"));
        this.level = compound.getInteger("Level");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean result = super.attackEntityFrom(source, amount);
        if (result && !world.isRemote) {
            AttackType attackType = null;
            if (source == SharedRiftAction.RIFT) {
                attackType = amount >= 10.0f ? AttackType.HARD_RIFT : AttackType.SOFT_RIFT;
            } else if (amount >= 2.0f) {
                attackType = AttackType.NORMAL;
            }
            if (attackType != null && color != null && shape != null & level != 0) {
                ItemStack drop = GolemDrops.getDrop(color, shape, level, world.rand, attackType);
                world.spawnEntity(new EntityItem(world, posX + 0.5f, posY + 0.5f, posZ + 0.5f, drop));
            }
        }
        return result;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }
}
