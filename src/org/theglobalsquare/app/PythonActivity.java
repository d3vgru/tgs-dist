package org.theglobalsquare.app;

//import java.io.File;

import java.io.File;

import android.os.Bundle;

// TODO merge anything useful here into org.theglobalsquare.app.PythonService?
public class PythonActivity extends MainActivity {
	boolean ready = false;
	
	// main activity for the application
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		String appPath = getApplicationContext().getFilesDir().getAbsolutePath();
		String tgsPath = appPath;
		android.util.Log.e("PythonActivity", "filesPath: " + tgsPath);
		 String files;
		  File folder = new File(tgsPath);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
		 
		   if (listOfFiles[i].isFile()) 
		   {
		   files = listOfFiles[i].getName();
		   android.util.Log.w("PythonActivity", "F: " + files);
		      }
		   else if (listOfFiles[i].isDirectory()) 
		   {
		   files = listOfFiles[i].getName();
		   android.util.Log.w("PythonActivity", "D: " + files);
		      }
		  }
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}
}

