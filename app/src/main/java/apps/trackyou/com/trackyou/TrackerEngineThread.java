package apps.trackyou.com.trackyou;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abhaym on 2/16/2016.
 */
public class TrackerEngineThread implements Runnable {

    private LocationUpdater locUpdater;
    private List<ContactModelObservable> cmoTrackListRef;
    private FakeData fd;
    private List<LatLng> dataList;
    int pointer = 0;

    public TrackerEngineThread(List<ContactModelObservable> cmoTrackListRef) {
        this.cmoTrackListRef = cmoTrackListRef;
        locUpdater = new LocationUpdater();
        fd = new FakeData();
        HashMap
        dataList = fd.getData();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ContactModelObservable contact : cmoTrackListRef) {
                // send for location update
                LatLng newLatLong = getUpdatedLoction();
                contact.setLocaDetail(newLatLong);
                contact.setChanged();
                contact.notifyObservers();
            }
        }
    }

    private LatLng getUpdatedLoction() {
        if (!(pointer < dataList.size())) {
            pointer = 0;
        }
        return dataList.get(pointer++);
    }


}
