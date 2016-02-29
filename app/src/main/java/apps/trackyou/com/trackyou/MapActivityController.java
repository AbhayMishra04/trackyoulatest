package apps.trackyou.com.trackyou;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import apps.trackyou.com.bean.Contacts;

/**
 * Created by abhaym on 2/15/2016.
 */
/*
* 1= 05 min
* 2= 15 min
* 3= 30 min
* 4= 01 hr
* 5= 05 hr
* 6= 12 hr
* 7 = 1 day
* */
public class MapActivityController {
    private List<ContactModelObservable> observableContacts;
    private MapView mapObserver;
    private TrackerEngineThread tet;

    private List<ContactModelObservable> cmoTrackList;

    public MapActivityController() {
        cmoTrackList = Collections.synchronizedList(new ArrayList<ContactModelObservable>());
        tet = new TrackerEngineThread(cmoTrackList);
        new Thread(tet).start();
    }

    public void addNewContact(Contacts newContact, Observer mapViewTab) {
        //construct ContactModelObservable
        ContactModelObservable cmo = new ContactModelObservable(newContact);
        // attach observer to ContactModelObservable
        requestPermission(cmo, mapViewTab);
    }

    public void requestPermission(ContactModelObservable cmo, Observer mapViewTab) {
        long startTime = System.currentTimeMillis();
        // Send permisstion request -- wait notify on cmo
        int permissionGrantID = 1;
        if (permissionGrantID > 0) {
            if (permissionGrantID == 1) {// calculate for request expiry
                if ((startTime + 5 * 60 * 1000) > System.currentTimeMillis()) {
                    addToTrackList(cmo, mapViewTab);
                }
            }
        } else {
            // pop up permission denied
            return;
        }
    }

    private void addToTrackList(ContactModelObservable cmo, Observer mapViewTab) {
        cmo.addObserver(mapViewTab);
        cmoTrackList.add(cmo);
    }


    public List<ContactModelObservable> getCmoTrackList() {
        return cmoTrackList;
    }


    public List<ContactModelObservable> getObservableContacts() {
        return observableContacts;
    }

}
