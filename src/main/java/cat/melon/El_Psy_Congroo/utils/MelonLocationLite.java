package cat.melon.el_psy_congroo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;

public class MelonLocationLite implements Cloneable{
    private UUID worldUUID;
    private int x;
    private int y;
    private int z;

    public MelonLocationLite(UUID worldUUID, int x, int y, int z) {
        this.worldUUID = worldUUID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MelonLocationLite(Location location) {
        worldUUID = location.getWorld().getUID();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Location getBukkitLocation(double offsetX, double offsetY, double offsetZ) {
        return new Location(Bukkit.getWorld(worldUUID), x + offsetX, y + offsetY, z + offsetZ, 0, 0);
    }

    public Location getBukkitLocation() {
        return new Location(Bukkit.getWorld(worldUUID), x, y, z, 0, 0);
    }

    public long distanceSquared(MelonLocationLite o) {
        int dx = this.x - o.x;
        int dy = this.y - o.y;
        int dz = this.z - o.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(MelonLocationLite o){
        return Math.sqrt(this.distanceSquared(o));
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public MelonLocationLite clone(){
        return new MelonLocationLite(worldUUID,x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MelonLocationLite that = (MelonLocationLite) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
