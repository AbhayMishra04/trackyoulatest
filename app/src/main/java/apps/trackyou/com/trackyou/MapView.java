package apps.trackyou.com.trackyou;

import com.google.android.gms.maps.model.Marker;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import apps.trackyou.com.bean.Contacts;

/**
 * Created by abhaym on 2/16/2016.
 */
public class MapView implements Observer {

    private Set<Observable> contactSet;
    private MapActivityController mapActivityController;

    public MapView() {
        contactSet = new HashSet<Observable>();
        mapActivityController = new MapActivityController();
    }

    @Override
    public void update(Observable observable, Object data) {
        ContactModelObservable contact;
        if (observable instanceof ContactModelObservable) {
            contact = (ContactModelObservable) observable;
        } else {
            return;
        }
        System.out.println("updated loc = " + contact.getLocaDetail().toString());
        updateMarkerView(contact);
    }

    public MapActivityController getMapActivityController() {
        return mapActivityController;
    }

    public void addNewContact(Contacts newContact) {
        mapActivityController.addNewContact(newContact, this);

    }

    public void updateMarkerView(ContactModelObservable con) {
        MapsActivity.getInstance().runOnUiThread(new AddNewMarker(con));
    }

    class AddNewMarker implements Runnable {
        ContactModelObservable con;

        AddNewMarker(ContactModelObservable con) {
            this.con = con;
        }

        @Override
        public void run() {
            Marker contactMarker = con.getContactMarker();
            if (contactMarker == null) {
                try {
                    contactMarker = MapsActivity.getInstance().getGoogleMap().addMarker(con.getMarkerOption());
                    con.setContactMarker(contactMarker);
                } catch (Exception e) {
                    System.out.print(e.getMessage());

                }
            } else {
                contactMarker.setPosition(con.getLocaDetail());
            }
            MapsActivity.getInstance().updateCamera(con.getLocaDetail());
            /*
            * here you can add a thread to zoom camera to accomodate all the marker, for this create a set to store all marker and try following code
             *
             * LatLngBounds.Builder builder = new LatLngBounds.Builder();
             * for (Marker marker : markers) {
             * builder.include(marker.getPosition());
             * }
             * LatLngBounds bounds = builder.build();
             *
             * int padding = 0; // offset from edges of the map in pixels
             * CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
             *
             * googleMap.moveCamera(cu);
             *  googleMap.animateCamera(cu);
             *
            * */
        }
    }
}
