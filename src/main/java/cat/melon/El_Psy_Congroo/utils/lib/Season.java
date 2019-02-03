package cat.melon.el_psy_congroo.utils.lib;

public enum Season {
    SPRING,SUMMER,AUTUMN,WINTER;

    public static Season getSeason(int season){
        Season[] tmp = {SPRING,SUMMER,AUTUMN,WINTER};
        try{
            return tmp[season];
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
}
