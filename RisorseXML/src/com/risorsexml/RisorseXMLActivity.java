// Aggiunta scrittura di un file "log.txt" che salva il risultato dei log.d,
// includendo ora e giorno.



package com.risorsexml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class RisorseXMLActivity extends Activity {
	/** Called when the activity is first created. */

	File sdcard = Environment.getExternalStorageDirectory();
	File file = new File(sdcard,"log.txt");
	int i = 0;
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
			i = 1;
			scrivi(3,"FINE");

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
			BufferedWriter myOutWriter = new BufferedWriter(new FileWriter(file, true)); 
			Calendar ci = Calendar.getInstance();

			String ora = "" + ci.get(Calendar.YEAR) + "-" + 
					(ci.get(Calendar.MONTH) + 1) + "-" +
					ci.get(Calendar.DAY_OF_MONTH) + " " +
					ci.get(Calendar.HOUR) + ":" +
					ci.get(Calendar.MINUTE) +  ":" +
					ci.get(Calendar.SECOND) + " ";
			myOutWriter.append(num + " --> " + ora + text + "\n");
			myOutWriter.newLine();
			myOutWriter.close();
			//			Non necessaria...esegue Toast per la scrittura
			//			Toast.makeText(getBaseContext(),"Done writing SD 'log.txt'",Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
		}
	}
}