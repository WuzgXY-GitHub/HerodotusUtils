package youyihj.herodotusutils.util.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;

/**
 * @author youyihj
 */
public interface IEntityContainment extends INBTSerializable<NBTTagString> {
    boolean containsEntity();

    EntityEntry getContainsEntity();

    void setContainsEntity(EntityEntry entityEntry);

    void summonContainsEntity(World world, double x, double y, double z);

    class Impl implements IEntityContainment {
        private EntityEntry entityEntry;

        @Override
        public boolean containsEntity() {
            return entityEntry != null;
        }

        @Override
        public EntityEntry getContainsEntity() {
            return entityEntry;
        }

        @Override
        public void setContainsEntity(EntityEntry entityEntry) {
            this.entityEntry = entityEntry;
        }

        @Override
        public void summonContainsEntity(World world, double x, double y, double z) {
            Entity entity = entityEntry.newInstance(world);
            entity.setPosition(x, y, z);
            world.spawnEntity(entity);
            entityEntry = null;
        }

        @Override
        public NBTTagString serializeNBT() {
            return Optional.ofNullable(entityEntry)
                    .map(EntityEntry::getRegistryName)
                    .map(Object::toString)
                    .map(NBTTagString::new)
                    .orElseGet(() -> new NBTTagString("empty"));
        }

        @Override
        public void deserializeNBT(NBTTagString nbt) {
            if (nbt == null) return;
            this.entityEntry = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString()));
        }
    }
}
