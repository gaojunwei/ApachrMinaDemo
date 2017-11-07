package com.gje.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 
 * @author gjw-pc
 */
public class MinaServer {
	private static final int PORT  = 1234;
	public static void main(String[] args) throws IOException {
		//监听传入连接的对象
		IoAcceptor acceptor = new NioSocketAcceptor();
		//filter设置
		//这个过滤器将会日志记录所有信息，比如 session 的新建、接收到的消息、发送的消息、session 的关闭等
		acceptor.getFilterChain().addLast( "logger", new LoggingFilter());
		
		TextLineCodecFactory codecFactory = new TextLineCodecFactory(Charset.forName("UTF-8"));//行文本解析
		codecFactory.setDecoderMaxLineLength(1024*1024);//1M 设置要解码的行的最大允许大小(默认1KB)
		//codecFactory.setEncoderMaxLineLength(1024*1024);//1M 设置已编码行的允许最大大小(默认Integer.MAX_VALUE)
		
		acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(codecFactory));
		//处理器
		acceptor.setHandler(new TimeServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);//设置空闲时间
		//设置服务器监听的端口
		acceptor.bind( new InetSocketAddress(PORT));
	}
}
