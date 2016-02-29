package apps.trackyou.com.trackyou;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Observable;
import java.util.Observer;

import apps.trackyou.com.bean.Contacts;

/**
 * Created by abhaym on 2/15/2016.
 */
public class ContactModelObservable extends Observable {
    private Contacts contact;
    private Observer mapView;
    private Marker contactMarker = null;

    private long startTrackTime;

    private long endTrackTime;

    private LatLng locaDetail;
    private MarkerOptions markerOptions;

    public ContactModelObservable(Contacts contact) {
        this.contact = contact;
    }

    public void setStartTrackTime(long startTrackTime) {
        this.startTrackTime = startTrackTime;
    }

    public long getStartTrackTime() {
        return startTrackTime;
    }

    public void setEndTrackTime(long endTrackTime) {
        this.endTrackTime = endTrackTime;
    }

    public long getEndTrackTime() {
        return endTrackTime;
    }

    public LatLng getLocaDetail() {
        return locaDetail;
    }

    public void setLocaDetail(LatLng locaDetail) {
        this.locaDetail = locaDetail;
    }

    public MarkerOptions getMarkerOption() {
        if (markerOptions == null) {
            markerOptions = new MarkerOptions().position(getLocaDetail());
        }
        return markerOptions;
    }

    public Marker getContactMarker() {
        return contactMarker;
    }

    public void setChanged() {
        super.setChanged();
    }

    public void setContactMarker(Marker contactMarker) {
        this.contactMarker = contactMarker;
    }

}
