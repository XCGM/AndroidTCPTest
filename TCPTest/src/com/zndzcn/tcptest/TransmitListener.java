package com.zndzcn.tcptest;

public interface TransmitListener {
	
	public void onDataReceived(byte [] data, int length);
}
