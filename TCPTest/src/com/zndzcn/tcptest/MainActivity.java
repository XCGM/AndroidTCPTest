package com.zndzcn.tcptest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity 
	implements OnClickListener, TransmitListener, OnEditorActionListener{

	private EditText ip,port,input;
	private TextView output;
	private TransmitData transmitData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ip=(EditText)findViewById(R.id.ip);
		port=(EditText)findViewById(R.id.port);
		input=(EditText)findViewById(R.id.input);
		output=(TextView)findViewById(R.id.output);

		findViewById(R.id.submit).setOnClickListener(this);
		input.setOnEditorActionListener(this);
	}
	protected void onDestroy()
	{
		super.onDestroy();
		close();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.submit:
			new Thread(new Runnable(){
				public void run()
				{
					String[] ipAndPort={ip.getEditableText().toString(),
							port.getEditableText().toString()};
					transmitData=new TransmitData(ipAndPort, TransmitData.Net);
					transmitData.addReadListener(MainActivity.this);
				}
			}).start();
			break;
		}
	}
	public void close()
	{
		if(transmitData!=null)
			transmitData.close();
	}
	@Override
	public void onDataReceived(byte[] data, int length) {
		// TODO Auto-generated method stub
		final String str=new String(data,0,length);
		Log.v("方法","onDatareceived");
		runOnUiThread(new Runnable(){
			public void run()
			{
				output.append(str);
			}
		});
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.v("说明","event:"+event.getKeyCode());
		switch(v.getId())
		{
		case R.id.input:
			if(transmitData!=null)
			{
				transmitData.write(input.getText().toString().getBytes());
				input.setText("");
			//	return true;
			}
			break;
		}
		return false;
	}

}
