package de.teamlapen.werewolves.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class CodecExtensions {

    public static final StreamCodec<ByteBuf, int[]> INT_ARRAY = new StreamCodec<>() {
        @Override
        public int @NotNull [] decode(ByteBuf pBuffer) {
            int size = pBuffer.readInt();
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = pBuffer.readInt();
            }
            return array;
        }

        @Override
        public void encode(ByteBuf pBuffer, int[] pValue) {
            pBuffer.writeInt(pValue.length);
            for (int i : pValue) {
                pBuffer.writeInt(i);
            }
        }
    };
}
