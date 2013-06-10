package com.zndzcn.tcptest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.util.Log;

public class Net {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String ip;
	private int port;
	
	public Net(String ip, int port) {
		// TODO Auto-generated constructor stub
		setNet(ip, port);
	}
	public void setIP(String ip)
	{
		this.ip=ip;
	}
	public void setPort(int port)
	{
		this.port=port;
	}
	public void connect()
	{
		if(socket!=null&&socket.isConnected())
			close();
		try {
			socket=new Socket(ip, port);
			inputStream=socket.getInputStream();
			outputStream=socket.getOutputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("´íÎó","e:"+e);
			e.printStackTrace();
		}
	}
	public boolean isConnected()
	{
		if(socket!=null)
			return socket.isConnected();
		else
			return false;
	}
	public void setNet(String ip, int port)
	{
		setIP(ip);
		setPort(port);
		connect();
	}
	public InputStream getInputStream()
	{
		return inputStream;
	}
	public OutputStream getOutputStream()
	{
		return outputStream;
	}
	public void close()
	{
		if(socket.isConnected())
		{
			try {
				inputStream.close();
				outputStream.close();
				socket.close();
				inputStream=null;
				outputStream=null;
				socket=null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
