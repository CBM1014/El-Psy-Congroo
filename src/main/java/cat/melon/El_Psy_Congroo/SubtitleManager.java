package cat.melon.El_Psy_Congroo;

import cat.melon.El_Psy_Congroo.Utils.Player;
import cat.melon.El_Psy_Congroo.Utils.lib.Subtitles;

public class SubtitleManager {
    private StringBuilder sb = new StringBuilder();

    public SubtitleManager(){}

    public String getSubtitle(Player player){
        sb.delete(0,sb.length());

        //add more conditions here
        if(player.getTemperature()>35){
            sb.append(Subtitles.HOT);
        }else if(player.getTemperature()<0){
            sb.append(Subtitles.COLD);
        }

        return sb.toString();
    }

}
