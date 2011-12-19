package com.risorsexml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class RisorseXMLActivity extends Activity {
	/** Called when the activity is first created. */

	File sdcard = Environment.getExternalStorageDirectory();
	File file = new File(sdcard,"log.txt");

	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		creafile();
		XmlResourceParser parser = getResources().getXml(R.xml.compilation);
		try {
			int eventType = parser.getEventType();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					String tagName = parser.getName();
					if ("brano".equals(tagName)) {
						String id = parser.getAttributeValue(0);
						String str = "Brano:" + id;
						Log.d("XML PARSER",str);
						scrivi(1,str);
					}
				}
				else if (eventType == XmlResourceParser.TEXT) {
					String elementValue = parser.getText();
					String str = elementValue;
					Log.d("XML PARSER",str);
					scrivi(2,str);
				}
				eventType = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void creafile() {
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else {
			try
			{
				file.delete();
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void scrivi(int num, String text) {
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(num + Time.MONTH_DAY + " " + Time.MONTH + " "
					+ Time.YEAR + "/" + Time.HOUR + " " +
					Time.MINUTE + " " + Time.SECOND + " --> " + text);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),"Done writing SD 'log.txt'",Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
		}
	}
}