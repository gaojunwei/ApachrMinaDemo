package com.gje.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter{
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
		System.out.println(session.getId()+"服务器返回的数据："+message);
		session.closeNow();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println( "Client IDLE " + session.getIdleCount( status ));
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		//System.out.println("已发送信息。。。关闭连接");
		//session.closeOnFlush();
	}
	
}
