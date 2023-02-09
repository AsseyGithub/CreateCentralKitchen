package plus.dragons.createcentralkitchen.common.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LoadedPacket<T extends SimplePacketBase> {
    private static int index = 0;
    private final BiConsumer<T, FriendlyByteBuf> encoder;
    private final Function<FriendlyByteBuf, T> decoder;
    private final BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
    private final Class<T> type;
    private final NetworkDirection direction;
    
    public LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
        encoder = T::write;
        decoder = factory;
        handler = T::handle;
        this.type = type;
        this.direction = direction;
    }
    
    public void register() {
        CentralKitchenNetwork.CHANNEL.messageBuilder(type, index++, direction)
            .encoder(encoder)
            .decoder(decoder)
            .consumer(handler)
            .add();
    }
    
}
