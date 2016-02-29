package apps.trackyou.com.trackyou;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements LocationListener {


    private GoogleMap googleMap;
    private Dialog dialog;
    private static MapsActivity _instance;
    private FragmentTransaction fragmentTransaction = null;
    private FragmentManager fragmentMangaer = null;

    private MapView mapView;
    double latitude = 0;
    double longitude = 0;

    private ImageButton addUser;
    final Context context = this;

    public MapsActivity() {
        _instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        fragmentMangaer = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mainMap);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        mapView = new MapView();
    }

    public void setFragmentID(int fragmentID, Bundle data) {
        fragmentTransaction = fragmentMangaer.beginTransaction();
//        switch(fragmentID){
//            case
//
//        }
    }


    private void init() {
        addUser = (ImageButton) findViewById(R.id.imageButton_addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactListDialog cld = new ContactListDialog(MapsActivity._instance);
                cld.show();
            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);

    }
    public void updateCamera(LatLng latLng){
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public static MapsActivity getInstance() {
        return _instance;
    }

    public MapView getMapView() {
        return mapView;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
