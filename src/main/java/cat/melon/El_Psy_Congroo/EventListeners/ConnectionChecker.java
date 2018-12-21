package cat.melon.El_Psy_Congroo.EventListeners;

import cat.melon.El_Psy_Congroo.Init;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;

public class ConnectionChecker {
    private Init instance;
    private ProtocolManager protocolManager;

    public ConnectionChecker(Init instance) {
        protocolManager = ProtocolLibrary.getProtocolManager();
        this.instance = instance;
        protocolManager.addPacketListener(this.getBlockMOTDPacketListener());
        protocolManager.addPacketListener(this.getMOTDSender());
    }

    private PacketListener getBlockMOTDPacketListener() {
        return new PacketAdapter(PacketAdapter.params()
                .plugin(instance)
                .serverSide()
                .gamePhase(GamePhase.LOGIN)
                .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
                .types(PacketType.Status.Server.SERVER_INFO)
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                super.onPacketSending(event);
                event.setCancelled(true);
            }
        };
    }

    private PacketListener getMOTDSender(){
        return new PacketAdapter(PacketAdapter.params()
                .plugin(instance)
                .clientSide()
                .gamePhase(GamePhase.LOGIN)
                .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
                .types()//这里的PacketType不知道是什么
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                super.onPacketReceiving(event);
                if(event.getPacket().getIntegers().getValues().get(0)!=404){
                    //这里发送含有版本信息的MOTD数据包
                }else{
                    //这里发送普通的MOTD数据包
                }
            }
        };
    }


}
