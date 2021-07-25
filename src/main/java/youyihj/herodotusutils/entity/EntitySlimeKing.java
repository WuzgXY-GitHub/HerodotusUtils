package youyihj.herodotusutils.entity;

import javax.annotation.Nonnull;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySlimeKing extends EntitySlime {

    public EntitySlimeKing(World world) {
        super(world);
    }

    @Override
    protected void setSlimeSize(int size, boolean resetHealth) {
        super.setSlimeSize(size, resetHealth);
    }

    @Nonnull
    @Override
    protected EntitySlime createInstance() {
        return new EntitySlimeKing(this.world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(800.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    }

    @Override
    protected void dealDamage(EntityLivingBase entityIn) {
        int size = this.getSlimeSize();
        int spawnAmount = this.getEntityData().getInteger("SpawnAmount") == 0 ? 1 : this.getEntityData().getInteger("SpawnAmount");
        float attackDamage = (float) Math.ceil(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / spawnAmount);

        if (this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * size * 0.6D * size && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), attackDamage)) {
            this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.applyEnchantments(this, entityIn);
        }
    }

    @Override
    public void setDead() {
        int size = this.getSlimeSize();

        if (!this.world.isRemote && size > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.rand.nextInt(3);

            double maxHealth = Math.ceil(this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() / j);
            double attackDamage = Math.ceil(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / j);

            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("ForgeData", this.getEntityData());
            nbt.getCompoundTag("ForgeData").setInteger("SpawnAmount", j);

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * size / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * size / 4.0F;
                EntitySlime entityslime = this.createInstance();

                if (this.hasCustomName()) {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired()) {
                    entityslime.enablePersistence();
                }

                entityslime.readFromNBT(nbt);

                ((EntitySlimeKing) entityslime).setSlimeSize(size / 2, true);
                entityslime.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
                entityslime.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attackDamage);
                entityslime.setHealth(entityslime.getMaxHealth());

                entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entityslime);
            }
        }

        this.isDead = true;
    }
}
