package org.renpy.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import org.theglobalsquare.app.R;
import org.theglobalsquare.framework.TabbedFragmentActivity;

public class PythonActivity extends TabbedFragmentActivity implements Runnable {

    // The audio thread for streaming audio...
    private static AudioThread mAudioThread = null;

    // The SDLSurfaceView we contain.
    public static SDLSurfaceView mView = null;
	public static PythonActivity mActivity = null;

    // Did we launch our thread?
    private boolean mLaunchedThread = false;

    private ResourceManager resourceManager;

    // The path to the directory contaning our external storage.
    private File externalStorage;

    // The path to the directory containing the game.
    private File mPath = null;

    boolean _isPaused = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Hardware.context = this;
        Action.context = this;
		this.mActivity = this;

        getWindowManager().getDefaultDisplay().getMetrics(Hardware.metrics);

        resourceManager = new ResourceManager(this);
        externalStorage = new File(Environment.getExternalStorageDirectory(), getPackageName());

        // Figure out the directory where the game is. If the game was
        // given to us via an intent, then we use the scheme-specific
        // part of that intent to determine the file to launch. We
        // also use the android.txt file to determine the orientation.
        //
        // Otherwise, we use the public data, if we have it, or the
        // private data if we do not.
        if (getIntent().getAction().equals("org.renpy.LAUNCH")) {
            mPath = new File(getIntent().getData().getSchemeSpecificPart());

            Project p = Project.scanDirectory(mPath);

            if (p != null) {
                if (p.landscape) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }

            // Let old apps know they started.
            try {
                FileWriter f = new FileWriter(new File(mPath, ".launch"));
                f.write("started");
                f.close();
            } catch (IOException e) {
                // pass
            }



        } else if (resourceManager.getString("public_version") != null) {
            mPath = externalStorage;
        } else {
            mPath = getFilesDir();
        }

        /* ERK - don't do that, we need the title area for the action bar */
        // go to fullscreen mode
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
                             */

        setContentView(R.layout.main);

        // Start showing an SDLSurfaceView.
        mView = new SDLSurfaceView(
            this,
            mPath.getAbsolutePath());

        Hardware.view = mView;
//        setContentView(mView);
        
        LinearLayout container = (LinearLayout)findViewById(R.id.surface_container);
        container.addView(mView);
        
        LinearLayout ui = (LinearLayout)findViewById(R.id.native_ui);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.main_activity, ui);
    }

    /**
     * Show an error using a toast. (Only makes sense from non-UI
     * threads.)
     */
    public void toastError(final String msg) {

        final Activity thisActivity = this;

        runOnUiThread(new Runnable () {
                public void run() {
                    Toast.makeText(thisActivity, msg, Toast.LENGTH_LONG).show();
                }
            });

        // Wait to show the error.
        synchronized (this) {
            try {
                this.wait(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void recursiveDelete(File f) {
        if (f.isDirectory()) {
            for (File r : f.listFiles()) {
                recursiveDelete(r);
            }
        }
        f.delete();
    }


    /**
     * This determines if unpacking one the zip files included in
     * the .apk is necessary. If it is, the zip file is unpacked.
     */
    public void unpackData(final String resource, File target) {

        // The version of data in memory and on disk.
        String data_version = resourceManager.getString(resource + "_version");
        String disk_version = null;

        // If no version, no unpacking is necessary.
        if (data_version == null) {
            return;
        }

        // Check the current disk version, if any.
        String filesDir = target.getAbsolutePath();
        String disk_version_fn = filesDir + "/" + resource + ".version";

        try {
            byte buf[] = new byte[64];
            InputStream is = new FileInputStream(disk_version_fn);
            int len = is.read(buf);
            disk_version = new String(buf, 0, len);
            is.close();
        } catch (Exception e) {
            disk_version = "";
        }

        // If the disk data is out of date, extract it and write the
        // version file.
        if (! data_version.equals(disk_version)) {
            Log.v("python", "Extracting " + resource + " assets.");

            recursiveDelete(target);
            target.mkdirs();

            AssetExtract ae = new AssetExtract(this);
            if (!ae.extractTar(resource + ".mp3", target.getAbsolutePath())) {
                toastError("Could not extract " + resource + " data.");
            }

            try {
                // Write .nomedia.
                new File(target, ".nomedia").createNewFile();

                // Write version file.
                FileOutputStream os = new FileOutputStream(disk_version_fn);
                os.write(data_version.getBytes());
                os.close();
            } catch (Exception e) {
                Log.w("python", e);
            }
        }

    }

    public void run() {

        unpackData("private", getFilesDir());
        unpackData("public", externalStorage);

        System.loadLibrary("sdl");
        System.loadLibrary("sdl_image");
        System.loadLibrary("sdl_ttf");
        System.loadLibrary("sdl_mixer");
		System.loadLibrary("python2.7");
        System.loadLibrary("application");
        System.loadLibrary("sdl_main");

		System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_io.so");
        System.load(getFilesDir() + "/lib/python2.7/lib-dynload/unicodedata.so");

        try {
            System.loadLibrary("sqlite3");
            System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_sqlite3.so");
        } catch(UnsatisfiedLinkError e) {
        }

        try {
            System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_imaging.so");
            System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_imagingft.so");
            System.load(getFilesDir() + "/lib/python2.7/lib-dynload/_imagingmath.so");
        } catch(UnsatisfiedLinkError e) {
        }

        if ( mAudioThread == null ) {
            Log.i("python", "starting audio thread");
            mAudioThread = new AudioThread(this);
        }

        runOnUiThread(new Runnable () {
                public void run() {
                    mView.start();
                }
            });
    }

    @Override
    protected void onPause() {
        _isPaused = true;
        super.onPause();

        if (mView != null) {
            mView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        _isPaused = false;

        if (!mLaunchedThread) {
            mLaunchedThread = true;
            new Thread(this).start();
        }

        if (mView != null) {
            mView.onResume();
        }
    }

    public boolean isPaused() {
        return _isPaused;
    }

    /** ERK - let native UI handle it
    @Override
    public boolean onKeyDown(int keyCode, final KeyEvent event) {
        //Log.i("python", "key2 " + mView + " " + mView.mStarted);
        if (mView != null && mView.mStarted && SDLSurfaceView.nativeKey(keyCode, 1, event.getUnicodeChar())) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, final KeyEvent event) {
        //Log.i("python", "key up " + mView + " " + mView.mStarted);
        if (mView != null && mView.mStarted && SDLSurfaceView.nativeKey(keyCode, 0, event.getUnicodeChar())) {
            return true;
        } else {
    	 	
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (mView != null){
            mView.onTouchEvent(ev);
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }
    */

	protected void onDestroy() {
		if (mView != null) {
			mView.onDestroy();
		}
		//Log.i(TAG, "on destroy (exit1)");
        System.exit(0);
	}
}

