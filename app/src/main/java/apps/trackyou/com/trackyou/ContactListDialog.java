package apps.trackyou.com.trackyou;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import apps.trackyou.com.bean.Contacts;

/**
 * Created by abhaym on 2/15/2016.
 */
public class ContactListDialog extends Dialog implements View.OnClickListener {
    private Context dialogContext;
    public static final int MIN_PHONE_NO_LENGTH = 8;
    private ArrayList<Contacts> alContacts;

    public ContactListDialog(Context context) {
        super(context);
        this.dialogContext = context;
        init();
    }

    private void init() {

        this.setContentView(R.layout.contactlist);
        this.setTitle("Contact List.");
        // set the custom dialog components - text, image and button
        Button closeDialogButton = (Button) this.findViewById(R.id.button_contactClose);
        // if button is clicked, close the custom dialog

        alContacts = updateContact();
        ContactAdapter adapter = new ContactAdapter(dialogContext, alContacts);

        ListView listView = (ListView) this.findViewById(R.id.listView_Contacts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvNum = (TextView) view.findViewById(R.id.textView_contactNumber);
                String number = tvNum.getText().toString();

                TextView tvName = (TextView) view.findViewById(R.id.textView_contactName);
                String name = tvName.getText().toString();

                MapsActivity.getInstance().getMapView().addNewContact(alContacts.get(position));
            }
        });

        closeDialogButton.setOnClickListener(this);
    }

    private ArrayList updateContact() {
        alContacts = new ArrayList<Contacts>();
        ContentResolver cr = MapsActivity.getInstance().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        if (contactNumber.length() > MIN_PHONE_NO_LENGTH && !contactName.isEmpty()) {
                            Contacts newContact = new Contacts(contactName, contactNumber);
                            if (!alContacts.contains(newContact)) {
                                alContacts.add(newContact);
                            }
                        }
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }
        Collections.sort(alContacts);
        return alContacts;
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
