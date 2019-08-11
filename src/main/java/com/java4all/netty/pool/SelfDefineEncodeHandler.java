package com.java4all.netty.pool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author  IT云清
 */
public class SelfDefineEncodeHandler extends ByteToMessageDecoder {

    /**
     * Decode the from one {@link ByteBuf} to an other. This method will be called till either the
     * input {@link ByteBuf} has nothing to read when return from this method or till nothing was
     * read from the input {@link ByteBuf}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs
     * to
     * @param bufferIn the {@link ByteBuf} from which to read data
     * @param out the {@link List} to which decoded messages should be added
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bufferIn, List<Object> out)
            throws Exception {
        if (bufferIn.readableBytes() < 4) {
            return;
        }

        int beginIndex = bufferIn.readerIndex();
        int length = bufferIn.readInt();

        if (bufferIn.readableBytes() < length) {
            bufferIn.readerIndex(beginIndex);
            return;
        }

        bufferIn.readerIndex(beginIndex + 4 + length);

        ByteBuf otherByteBufRef = bufferIn.slice(beginIndex, 4 + length);

        otherByteBufRef.retain();

        out.add(otherByteBufRef);
    }
}
