package cat.melon.el_psy_congroo.eventlisteners;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.nbt.NbtBase;

public class SeasonalBiome implements Listener {
    private final Plugin pluginInstance;
    
    private SeasonalBiome(Plugin instance) {
        pluginInstance = instance;
        
        // Transform packets
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(instance, PacketType.Play.Server.MAP_CHUNK) {
            @Override
            public void onPacketSending(PacketEvent event) {
                final PacketType type = event.getPacketType();
                
                if (type == PacketType.Play.Server.MAP_CHUNK)
                    translateMapChunk(event.getPacket(), event.getPlayer());
            }     
        });
        
        Bukkit.getPluginManager().registerEvents(this, instance);
    }
    
    public static void registerAsListener(Plugin instance) {
        new SeasonalBiome(instance);
    }
    
    private static final int BIOME_ARRAY_LENGTH = 256;
    
    // Used to pass around detailed information about chunks
    private static class ChunkInfo {
        public Player player;
        
        public int chunkX;
        public int chunkZ;
        public int availableSections;
        public byte[] data;
        public List<NbtBase<?>> tileEntityTags;
        public boolean fullChunk;
    }
    
    public void translateMapChunk(PacketContainer packet, Player player) {
        StructureModifier<Integer> ints = packet.getIntegers();
        StructureModifier<byte[]> byteArray = packet.getByteArrays();
        StructureModifier<List<NbtBase<?>>> nbtList = packet.getListNbtModifier();
        StructureModifier<Boolean> booleans = packet.getBooleans();
        
        // Create an info objects
        ChunkInfo info = new ChunkInfo();
        info.player = player;
        info.chunkX = ints.read(0);     // packet.a;
        info.chunkZ = ints.read(1);     // packet.b;
        info.availableSections = ints.read(2);  // packet.c;
        info.data = byteArray.read(0); // packet.d
        info.tileEntityTags = nbtList.read(0); // packet.e
        info.fullChunk = booleans.read(0); // packet.f
        
        if (info.fullChunk && info.data != null)
            translateChunkInfo(info, info.data);
    }
    
    private void translateChunkInfo(ChunkInfo info, byte[] chunkData) {
        int biomeIndex = chunkData.length - BIOME_ARRAY_LENGTH;
        
        for (int biomeDataIndex = biomeIndex; biomeDataIndex < chunkData.length; biomeDataIndex++)
            chunkData[biomeDataIndex] = 0;
    }
}
