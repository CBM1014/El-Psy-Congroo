package cat.melon.El_Psy_Congroo.Utils.lib;

import org.bukkit.Color;

public enum Subtitles {
    HOT("\uD83D\uDD25",Color.fromRGB(255,85,85)), COLD("❄",Color.AQUA),
    WET("\uD83D\uDCA7",Color.BLUE),DRY("☼",Color.fromRGB(255,85,85));

    String subtitle;

    Subtitles(String char1, Color color) {
        this.subtitle = color+char1+" ";
    }

    Subtitles(String char1, String char2, Color color, Color color2) {
        this.subtitle = color+char1+char2+color2;
    }

    @Override
    public String toString(){
        return subtitle;
    }

}
