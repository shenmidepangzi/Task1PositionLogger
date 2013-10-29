package de.uni_stuttgart.yi.task1positionlogger;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String TAG = "MainActivity";
	private Context context = this;
	IPositionLoggerService mIPositionLoggerService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//set all button actions here
		
		//start service button
		Button startServiceButton = (Button) findViewById(R.id.start_service_button);
		startServiceButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        //start service
		        Intent intent = new Intent(context, PositionLoggerService.class);
		        startService(intent);
		    }
		});
		
		//stop service button
		Button stopServiceButton = (Button) findViewById(R.id.stop_service_button);
		stopServiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//stop service
		        Intent intent = new Intent(context, PositionLoggerService.class);
		        stopService(intent);
		        Toast.makeText(context, "service is stopped", Toast.LENGTH_SHORT).show();
			}
		});
		
		//update values button
		Button updateValuesButton = (Button) findViewById(R.id.update_values_button);
		updateValuesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Bind to the service
		        Intent intent = new Intent(context, PositionLoggerService.class);
		        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		        
		        //Unbind from the service
		        if (mIPositionLoggerService != null) {
		            unbindService(mConnection);
		            mIPositionLoggerService = null;
		        }
			}
		});
		
		//exit button
		Button exitButton = (Button) findViewById(R.id.exit_button);
		exitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Defines callback methods for service binding, passed to bindService()
	private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
        	mIPositionLoggerService = IPositionLoggerService.Stub.asInterface(service);
        	
        	//use the service
            try {
            	String location = mIPositionLoggerService.getLocation();
            	String distance = mIPositionLoggerService.getDistance();
            	String averageSpeed = mIPositionLoggerService.getAverageSpeed();
            	//location String is something like this: "longitude latitude"
            	//that is the longitude and latitude information separated by a blank char ' '
            	String[] longitude_latitude = new String[2];
            	//if we do not get the location information from the service
            	if (location == null) {
            		longitude_latitude[0] = "No location Data is available!";
            		longitude_latitude[1] = "";
            	}
            	else {
            		longitude_latitude = location.split(" ");
            	}
            	
            	//update all the TextView to show the informations
            	TextView longitudeTextView = (TextView) findViewById(R.id.longitude_text);
            	longitudeTextView.setText(longitude_latitude[0]);
		        TextView latitudeTextView = (TextView) findViewById(R.id.latitude_text);
		        latitudeTextView.setText(longitude_latitude[1]);
		        TextView distanceTextView = (TextView) findViewById(R.id.distance_text);
		        distanceTextView.setText(distance);
            	TextView avgSpeedTextView = (TextView) findViewById(R.id.avg_speed_text);
            	avgSpeedTextView.setText(averageSpeed);
    		} catch (RemoteException e) {
    			Log.d(TAG, e.getMessage());
    		}
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        	Log.e(this.getClass().getName(), "Service has unexpectedly disconnected");
        	mIPositionLoggerService = null;
        }
    };

}
