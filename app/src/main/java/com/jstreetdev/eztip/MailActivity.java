package com.jstreetdev.eztip;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;


public class MailActivity extends AppCompatActivity {
    EditText nameTxt;
    EditText phoneTxt;
    EditText emailTxt;
    EditText addressTxt;
    ListView contactListView;
    List<Contact> Contacts = new ArrayList<Contact>();
    ImageView contactImageImgVeiw;
    Uri imageUri = Uri.parse("android.resourse://com.jstreetdev.eztip/drawable/no_user.png");
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        nameTxt = (EditText) findViewById(R.id.nameTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneTxt);
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        addressTxt = (EditText) findViewById(R.id.addressTxt);
        contactListView = (ListView) findViewById(R.id.listView1);
        contactImageImgVeiw = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

        final Button addBtn = (Button) findViewById(R.id.submitBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameTxt.getText()), String.valueOf(phoneTxt.getText()), String.valueOf(emailTxt.getText()), String.valueOf(addressTxt.getText()), imageUri);
                Contacts.add(contact);
                populateList();
                Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " Has Been Added", Toast.LENGTH_SHORT).show();
            }
        });

        contactImageImgVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });




        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);


        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        List<Contact> addableContacts = dbHandler.getAllContacts();
        int contactCount = dbHandler.getContactsCount();

        for (int i = 0; i < contactCount; i++) {
            Contacts.add(addableContacts.get(i));
        }
        if (addableContacts.isEmpty())
            populateList();
    }
    public void onActivityResult(int reqCode, int resCode, Intent data){
      if (resCode == RESULT_OK){
          if (reqCode == 1) {
              imageUri = data.getData();
              contactImageImgVeiw.setImageURI(data.getData());
          }
      }
    }

private void populateList(){
    ArrayAdapter<Contact> adapter = new ContactListAdapter();
    contactListView.setAdapter(adapter);

}
    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



    }
    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super (MailActivity.this, R.layout.listview_item, Contacts);
        }
        @Override
        public View getView (int postion, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item,parent, false);
            Contact currentContact = Contacts.get(postion);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());

            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentContact.getPhone());

            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());

            TextView address  = (TextView) view.findViewById(R.id.cAddress);
            address.setText(currentContact.getAddress());
            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(currentContact.getImageURI());
return view;
        }
    }
   /** private void addContact(int id, String name, String phone, String email, String address){
Contacts.add(new Contact(id, name, phone, email, address, uri));


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return (true);
            case R.id.action_about:
                Intent i = new Intent(this, About_Activity.class);
                startActivity(i);
                return (true);
            case R.id.action_help:
                i = new Intent(this, Help_Activity.class);
                startActivity(i);
                return (true);
            case R.id.action_mail:
                i = new Intent(this, MailActivity.class);
                startActivity(i);
                return (true);
        }

        return super.onOptionsItemSelected(item);
    }


}

