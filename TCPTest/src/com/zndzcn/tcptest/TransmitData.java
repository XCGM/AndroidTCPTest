package com.zndzcn.tcptest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class TransmitData{

	public static final int WiFi=0;
	public static final int SerialPort=1;
	public static final int Bluetooth=2;
	public static final int Net=3;

	private InputStream inputStream=null;
	private OutputStream outputStream=null;
	private int theType=0;
	private TransmitListener theti=null;
	private ReadThread rt;		

	
	Object obj=null;
	public TransmitData(String[] arg0, int type)
	{
		theType=type;
		final String[] tmp=arg0;
		switch(type)
		{
		case WiFi:
			break;
		case SerialPort:
			break;
		case Bluetooth:
			break;
		case Net:
					obj=new Net(tmp[0], Integer.parseInt(tmp[1]));
					outputStream=((Net)obj).getOutputStream();
					inputStream=((Net)obj).getInputStream();
					
			break;
		}
	}
	public Object getObj()
	{
		return obj;
	}
	public void addReadListener(TransmitListener ti)
	{
		if(theti!=null)
		{
			theti=ti;
		}
		else
		{
			theti=ti;
			rt=new ReadThread();
			rt.start();
		}
	}
	public void removeReadListener()
	{
		theti=null;
		if(rt!=null)
			rt.interrupt();
		rt=null;
	}
	public void write(byte[]data)
	{
		Log.v("����","TransmitData.write()");
		try {
			outputStream.write(data);
			outputStream.flush();//ǿ��ˢ��
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close()
	{//�رգ��ȹر�������������ٹر�ͨ���豸
		if(obj!=null)
		{
			try {
				if(rt!=null)
					rt.interrupt();
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(theType)
			{
			case WiFi:
				break;
			case SerialPort:
				break;
			case Bluetooth:
				break;
			case Net:
				((Net)obj).close();
				break;
			}
			
		}
	}
	
	class ReadThread extends Thread
	{
		public void run()
		{
			int size=0;
			byte [] data=new byte[300];
			Log.v("����","ReadThread.run()");
			while(!isInterrupted())
			{
				try
				{
//					Log.v("˵��","ѭ���߳�");
					if(inputStream==null)
					{
						Log.v("˵��","inputStream Is null");
						return;
					}
					if(inputStream.available()>0){
						size=inputStream.read(data);
						Log.v("˵��", "size��"+size);
						if(size>0)
						{
							if(theti!=null)
								theti.onDataReceived(data,size);
						}
					}
				}catch(Exception e)
				{
					Log.v("TransmitData.ReadThread.run()",""+e);
					return;
				}
			}
			rt=null;
		}
	}	
}
