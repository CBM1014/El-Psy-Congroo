package cat.melon.El_Psy_Congroo.Utils.lib;

public enum Seasons {
    SPRING,SUMMER,AUTUMN,WINTER;

    public static Seasons getSeason(int season){
        Seasons[] tmp = {SPRING,SUMMER,AUTUMN,WINTER};
        try{
            return tmp[season];
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
}
