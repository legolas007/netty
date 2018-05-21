package websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: Usher
 * @Description:
 */
public class WSChatServerInitializer extends
        ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());//将请求和应答消息编码或解码为http协议的消息
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new ChunkedWriteHandler());//向客户端发送html页面文件
        pipeline.addLast(new HttpRequestHandler("/ws"));//处理不发送到/ws的URI请求
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));//按照WebSocket处理，如果握手成功，
        // 那么所需的 ChannelHandler 将会被添加到 ChannelPipeline
        //中，而那些不再需要的 ChannelHandler 则将会被移除
        pipeline.addLast(new TextWebSocketFrameHandler());//处理Websocket帧和完成握手事件

    }
}