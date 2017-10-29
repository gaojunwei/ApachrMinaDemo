package com.gje.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.FiletoBase64.FileUitl;
import com.alibaba.fastjson.JSON;

public class MinaClient {
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 9123;
	private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
	
	public static void main(String[] args) throws Exception {
		NioSocketConnector connector = new NioSocketConnector();
		//将连接超时值设置为毫秒
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		//指定过滤器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//指定处理器
		connector.setHandler(new TimeClientHandler());
		
		IoSession session;
		//死循环直到链接成功
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
				future.awaitUninterruptibly();//等待异步操作完成
				session = future.getSession();
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}
		
		List<String> list = new ArrayList<String>();
		list.add("价格1");
		list.add("价格2");
		list.add("价格3");
		
		String base64Code = FileUitl.encodeBase64File("c:/cs/02-3C-1-1.bin");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("reason", "");
		map.put("data", base64Code);
		
		
		
		session.write(JSON.toJSONString(map));
		
		// wait until the summation is done
		//等待连接关闭
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();//释放所有资源
	}
}