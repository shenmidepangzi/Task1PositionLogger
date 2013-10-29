package de.uni_stuttgart.yi.task1positionlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class PositionLoggerService extends Service {
	private final String TAG = "PositionLoggerService";
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String locationProvider;
	private double longitude;
	private double latitude;
	private double distance;
	private double speed;
	private Calendar timeServiceStarted;
	private File gpsFile;
	private BufferedWriter out;
	private Location oldLocation;
	
	@Override
	public void onCreate() {
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//When service is started.
		timeServiceStarted = Calendar.getInstance();//Keep the time when is the service started to compute average speed later
		distance = 0;//make distance to zero at the beginning
		speed = 0;//average speed = 0 m/s
		Toast.makeText(this, "service is started", Toast.LENGTH_SHORT).show();
		//log file used to log the location information
		File sdDir = Environment.getExternalStorageDirectory();//get the root directory of the SD card
    	gpsFile = new File(sdDir, "gps_data.txt");//new file 'gps_data.txt'
    	//open the BufferedWriter here
    	try {
			out = new BufferedWriter(new FileWriter(gpsFile));
		} catch (IOException e) {
			Log.d(TAG, "Could not open writer: " + e.getMessage());
		}
    	//LocationManager: using GPS provider
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	locationProvider = LocationManager.GPS_PROVIDER;
    	locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				//callback method when the location is changed, we update
				//the four variable 'longitude', 'latitude', 'distance', 'speed' here.
				
				longitude = location.getLongitude();
				latitude = location.getLatitude();
				//'oldLocation' stores the previous location before the location is changed
				//and at the very beginning it is 'null'
				if (oldLocation != null) {
					//the new distance is the sum of old distance and the distance newly traveled between
					//'location' and 'oldLocation'
					distance = distance + location.distanceTo(oldLocation);
				}
				oldLocation = location;//update 'oldLocation'
				Calendar rightNow = Calendar.getInstance();//get the time
				speed = computeAvgSpeed(distance, rightNow);//compute the speed
				
				//write location data to the log
				String locationString = longitude + ", " + latitude;
				try {
	            	DateFormat format = DateFormat.getDateTimeInstance();
	            	String time_rightNow = format.format(rightNow.getTime());//time
	            	out.append(time_rightNow + " " + locationString + "\n");//time + location
	    		} catch (IOException e) {
	    			Log.d(TAG, "Could not write to SD card: " + e.getMessage());
	    		}
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
    	};
    	locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    	
		return 0;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		//return the implementation of the interface that is used by clients to interact with the service
		//see details below
		return mBinder;
	}
	
	@Override
	public void onDestroy() {
		//When the service is stopped, we do 2 things here:
		//1. close the BufferedWriter,
		//2. stop updating the location information.
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				Log.d(TAG, "Could not close writer: " + e.getMessage());
			}
		}
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	
	//'mBinder' is an object of the implementation of the interface 'IPositionLoggerService' that is used
	//by the client to interact with the service.
	//There are three functions:
	//	- getLocation returns the longitude and latitude information in a String
	//	- getDistance returns the distance (m) traveled so far in a String
	//	- getAverageSpeed returns the average speed (m/s) in a String
	//All the variables needed here are: 'longitude', 'latitude', 'distance', 'speed', and they all are private
	//variables of the service class 'PositionLoggerService'. They are updated in the LocationListener of the LocationManager.
	//So when a client binds to the service, we just return the variables the client needed here.
	private final IPositionLoggerService.Stub mBinder = new IPositionLoggerService.Stub() {
		@Override
		public String getLocation() throws RemoteException {
        	return longitude + " " + latitude;
		}

		@Override
		public String getDistance() throws RemoteException {
			return distance + "m";
		}

		@Override
		public String getAverageSpeed() throws RemoteException {
			return speed + "m/s";
		}
    };    
    
    //function used to compute the average speed, the Calendar object 'timeServiceStarted'
    //contains the time when service is started. In the function we first get the time now,
    //and then compute the time interval 'timeDiff'. At last we compute the speed by dividing the distance
    //traveled so far by 'timeDiff'
    private double computeAvgSpeed(double distance, Calendar rightNow) {
    	long timeDiff = rightNow.getTimeInMillis() - timeServiceStarted.getTimeInMillis();
    	return distance * 1000.0 / timeDiff;
    }
}
