package com.gje.mina;

import java.util.Date;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.FiletoBase64.FileUitl;
import com.alibaba.fastjson.JSON;

public class TimeServerHandler extends IoHandlerAdapter{

	/**
	 * 1、exceptionCaught 应该总是在处理器中进行定义，以处理正常的远程连接过程时抛出的异常。
	 * 如果这一方法没有定义，可能无法正常报告异常。
	 * 2、exceptionCaught 方法将会对错误和 session 关闭进行简单 stack trace 打印。
	 * 对于更多的程序，这将是常规，除非处理器能够从异常情况下进行恢复。
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		System.out.println("服务器端收到信息："+str);
		
		Map<String, Object> map = (Map<String, Object>)JSON.parse(str);
		System.out.println(map.get("success").toString());
		System.out.println(map.get("reason").toString());
		String base64Code = map.get("data").toString();
		
		FileUitl.decoderBase64File(base64Code, "c:/cs/02-3C-1-1-副本.bin");
		//List<String> list = (List)map.get("data");
		
		/*for (String strs:list) {
			System.out.println("遍历集合："+strs);
		}*/
		
		if( str.trim().equalsIgnoreCase("quit") ) {
			System.out.println(session.getId()+"：关闭与服务器的链接。。。");
			session.closeNow();
			return;
		}
		Date date = new Date();
		session.write( date.toString());
		System.out.println("向客户端发送数据");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println( "IDLE " + session.getIdleCount( status ));
	}
	
}
