package apps.trackyou.com.trackyou;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by abhaym on 2/16/2016.
 */
public class LocationUpdater {

    public void updateLocation(ContactModelObservable contact) {
        // send request and process
        LatLng updatedLocation = new LatLng(123l, 123l);
        contact.setLocaDetail(updatedLocation);
    }
}
