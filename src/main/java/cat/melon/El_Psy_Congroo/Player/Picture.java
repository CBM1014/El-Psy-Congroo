package cat.melon.El_Psy_Congroo.Player;


import org.bukkit.Material;

public class Picture {
    int[][][] picarr = new int[28][28][8]; //about 0.231M
    private Player player;
    public Picture(Player player){
        this.player = player;
    }

    public void printPixel(int x, int y, int z, Material material){
        try{
            picarr[x][y][z] = material.getId();
        }catch (IndexOutOfBoundsException e) {
            return;
        }
    }

}
