package com.gje.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	private static final String HOSTNAME = "192.168.1.102";
	private static final int PORT = 1234;
	private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
	static NioSocketConnector connector = new NioSocketConnector();
	static
	{
		//将连接超时值设置为毫秒
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		//指定过滤器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//指定处理器
		connector.setHandler(new TimeClientHandler());
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 60000; i++) {
			sendData();
		}
		//彻底释放Session,退出程序时调用不需要重连的可以调用这句话，也就是短连接不需要重连。长连接不要调用这句话，注释掉就OK。
		Thread.sleep(5000);
		connector.dispose();//释放所有资源
	}
	
	public static void sendData() {
		
		
		
		IoSession session = null;
		//死循环直到链接成功
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
			future.awaitUninterruptibly();//等待异步操作完成
			session = future.getSession();
			System.out.println(">>>>客户端链接对象："+session);
		} catch (RuntimeIoException e) {
			e.printStackTrace();
		}
		if(session == null)
		{
			return;
		}
		
		session.write("123456");
		//等待连接关闭
		CloseFuture closeFuture = session.getCloseFuture().awaitUninterruptibly();
		if(closeFuture.isClosed())
		{
			System.out.println("关闭连接");
		}
	}
}
