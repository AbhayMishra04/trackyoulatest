package apps.trackyou.com.trackyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import apps.trackyou.com.bean.Contacts;

/**
 * Created by abhaym on 10/14/2015.
 */
public class ContactAdapter extends ArrayAdapter<Contacts> {
    public ContactAdapter(Context context, ArrayList<Contacts> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contacts user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);
        }
         //Lookup view for data population
        TextView contName = (TextView) convertView.findViewById(R.id.textView_contactName);
        contName.setText(user.getName());

        TextView contNum = (TextView) convertView.findViewById(R.id.textView_contactNumber);
        contNum.setText(user.getContactNumber());

         //Return the completed view to render on screen
        return convertView;
    }
}
