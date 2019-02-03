package cat.melon.el_psy_congroo.eventlisteners;

import cat.melon.el_psy_congroo.Init;

import java.util.Set;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.google.common.collect.Sets;

public class ConnectionChecker {
    private Init instance;
    private ProtocolManager protocolManager;
    private final Set<Player> tempPlayerList = Sets.newHashSet();

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
                if (tempPlayerList.remove(event.getPlayer())) {
                    event.getPacket().getServerPings().getValues().get(0).setVersionName("这里是自定义版本数据");
                }
            }
        };
    }

    private PacketListener getMOTDSender(){
        return new PacketAdapter(PacketAdapter.params()
                .plugin(instance)
                .clientSide()
                .gamePhase(GamePhase.LOGIN)
                .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
                .types(PacketType.Handshake.Client.SET_PROTOCOL)
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                super.onPacketReceiving(event);
                if(event.getPacket().getIntegers().getValues().get(0) != 404){
                    //这里发送含有版本信息的MOTD数据包
                    tempPlayerList.add(event.getPlayer());
                } else {
                    //这里发送普通的MOTD数据包
                }
            }
        };
    }


}
