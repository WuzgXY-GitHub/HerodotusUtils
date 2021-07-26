package youyihj.herodotusutils.entity;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityRedSlime extends EntitySlime {

    public EntityRedSlime(World world) {
        super(world);
    }

    public double baseMaxHealth = 4 * 4;
    public double baseAttackStrength = 4;

    @Override
    protected EntityRedSlime createInstance() {
        return new EntityRedSlime(world);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.baseMaxHealth = compound.getDouble("baseMaxHealth");
        this.baseAttackStrength = compound.getDouble("baseAttackStrength");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setDouble("baseMaxHealth", baseMaxHealth);
        compound.setDouble("baseAttackStrength", baseAttackStrength);
    }

    @Override
    protected int getAttackStrength() {
        return ((int) (baseAttackStrength * getSlimeSize() / 4));
    }

    @Override
    protected void setSlimeSize(int size, boolean resetHealth) {
        super.setSlimeSize(size, resetHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(baseMaxHealth * getSlimeSize() / 4);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setSlimeSize(4, true);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
        this.setLeftHanded(false);
        return livingdata;
    }

    @Override
    public void setDead() {

        // Copied from subclass
        int i = this.getSlimeSize();

        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
                EntityRedSlime entityRedSlime = this.createInstance(); // edited
                entityRedSlime.syncBaseValue(this); // edited

                if (this.hasCustomName()) {
                    entityRedSlime.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired()) {
                    entityRedSlime.enablePersistence();
                }

                entityRedSlime.setSlimeSize(i / 2, true);
                entityRedSlime.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entityRedSlime);
            }
        }
    }

    private void syncBaseValue(EntityRedSlime other) {
        this.baseAttackStrength = other.baseAttackStrength;
        this.baseMaxHealth = other.baseMaxHealth;
    }
}
