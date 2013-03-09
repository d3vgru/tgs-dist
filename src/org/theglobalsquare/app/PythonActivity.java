package org.theglobalsquare.app;

import java.io.File;

import android.os.Bundle;


public class PythonActivity extends org.renpy.android.PythonActivity {
	// main activity for the application
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String appPath = getApplicationContext().getFilesDir().getAbsolutePath();
		String sitePkgs = appPath + "/lib/python2.7/site-packages";
		android.util.Log.e("PythonActivity", "sitePkgs: " + sitePkgs);
		 String files;
		  File folder = new File(sitePkgs);
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
}

