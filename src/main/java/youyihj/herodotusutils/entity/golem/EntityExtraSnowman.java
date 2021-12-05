package youyihj.herodotusutils.entity.golem;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
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
public class EntityExtraSnowman extends EntityGolem {
    private Color color;
    private Shape shape;
    private int level;

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

    public EntityExtraSnowman(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 1.9F);
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
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D, 1.0000001E-5F));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
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
            if (attackType != null) {
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
