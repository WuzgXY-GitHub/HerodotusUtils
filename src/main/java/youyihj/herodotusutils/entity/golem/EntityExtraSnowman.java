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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import youyihj.herodotusutils.util.DataSerializerEnum;
import youyihj.herodotusutils.util.SharedRiftAction;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * @author youyihj
 */
public class EntityExtraSnowman extends EntityGolem {
    private static final DataParameter<Color> COLOR = EntityDataManager.createKey(EntityExtraSnowman.class, DataSerializerEnum.of(Color.class));
    private static final DataParameter<Shape> SHAPE = EntityDataManager.createKey(EntityExtraSnowman.class, DataSerializerEnum.of(Shape.class));
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(EntityExtraSnowman.class, DataSerializers.VARINT);

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(COLOR, Color.UNSET);
        this.dataManager.register(SHAPE, Shape.UNSET);
        this.dataManager.register(LEVEL, 0);
    }

    public EntityExtraSnowman(World worldIn) {
        super(worldIn);
    }

    public Color getColor() {
        return dataManager.get(COLOR);
    }

    public void setColor(Color color) {
        dataManager.set(COLOR, color);
    }

    public Shape getShape() {
        return dataManager.get(SHAPE);
    }

    public void setShape(Shape shape) {
        dataManager.set(SHAPE, shape);
    }

    public int getLevel() {
        return dataManager.get(LEVEL);
    }

    public void setLevel(int level) {
        dataManager.set(LEVEL, level);
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
        nbtTagCompound.setString("Color", this.dataManager.get(COLOR).name());
        nbtTagCompound.setString("Shape", this.dataManager.get(SHAPE).name());
        nbtTagCompound.setInteger("Level", this.dataManager.get(LEVEL));
        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.dataManager.set(COLOR, Color.valueOf(compound.getString("Color")));
        this.dataManager.set(SHAPE, Shape.valueOf(compound.getString("Shape")));
        this.dataManager.set(LEVEL, compound.getInteger("Level"));
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
            if (attackType != null && getLevel() != 0) {
                ItemStack drop = GolemDrops.getDrop(getColor(), getShape(), getLevel(), world.rand, attackType);
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
    public ITextComponent getDisplayName() {
        TextComponentTranslation textComponent = new TextComponentTranslation("entity.hdsutils.golem.name",
                new TextComponentTranslation("color." + getColor().name().toLowerCase(Locale.ENGLISH)),
                new TextComponentTranslation("base.material." + getShape().name().toLowerCase(Locale.ENGLISH)),
                getLevel());
        textComponent.getStyle().setHoverEvent(this.getHoverEvent());
        textComponent.getStyle().setInsertion(this.getCachedUniqueIdString());
        return textComponent;
    }


}
