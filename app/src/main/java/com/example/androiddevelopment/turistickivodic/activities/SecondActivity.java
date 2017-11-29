package com.example.androiddevelopment.turistickivodic.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddevelopment.turistickivodic.adapters.NavigationItem;
import com.example.androiddevelopment.turistickivodic.db.DatabaseHelper;
import com.example.androiddevelopment.turistickivodic.model.Atrakcija;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by androiddevelopment on 29.11.17..
 */

public class SecondActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    private Atrakcija atrakcija;

    private EditText etName;
    private EditText etDescription;
    private EditText etAddress;
    private EditText etPhoneNumber;
    private EditText etWebAdresa;
    private EditText etRadnoVreme;
    private EditText etUlaznica;

    // Navigation Items
    private ArrayList<NavigationItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);

        int keyID = getIntent().getExtras().getInt(MainActivity.KEY_ID);
        try {
            atrakcija = getDatabaseHelper().getAtrakcijaDao().queryForId(keyID);

            etName = (EditText)findViewById(R.id.et_detail_name);
            etDescription = (EditText)findViewById(R.id.et_detail_description);
            etAddress = (EditText)findViewById(R.id.et_detail_address);
            etPhoneNumber = (EditText)findViewById(R.id.et_detail_phone_number);
            etWebAdresa = (EditText)findViewById(R.id.et_detail_web);
            etRadnoVreme = (EditText)findViewById(R.id.et_detail_radno_vreme);
            etUlaznica = (EditText)findViewById(R.id.et_detail_ulaznica);

            etName.setText(atrakcija.getName());
            etDescription.setText(atrakcija.getDescription());
            etAddress.setText(atrakcija.getAddress());
            etPhoneNumber.setText(String.valueOf(atrakcija.getPhoneNumber()));
            etWebAdresa.setText(atrakcija.getWebAdresa());
            etRadnoVreme.setText(atrakcija.getRadnoVreme());
            etUlaznica.setText(String.valueOf(atrakcija.getUlaznica()));

        } catch (SQLException e) {
            e.printStackTrace();
        }



        // Call Phone number
        etPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse(estate.getPhoneNumber()));
                startActivity(intent);
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                editAtrakcijaItem();
                break;
            case R.id.action_delete:
                deleteAtrakcijaItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAtrakcijaItem() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(android.R.layout.select_dialog_item, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_delete_estate);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (atrakcija != null) {
                    try {
                        getDatabaseHelper().getAtrakcijaDao().delete(atrakcija);
                        showToastMessage("Turisticka Atracija je obrisana");
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void editAtrakcijaItem() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(android.R.layout.select_dialog_item, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_update_estate);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                atrakcija.setName(etName.getText().toString());
                atrakcija.setDescription(etDescription.getText().toString());
                atrakcija.setAddress(etAddress.getText().toString());
                atrakcija.setPhoneNumber(Integer.parseInt(etPhoneNumber.getText().toString()));
                atrakcija.setWebAdresa(etWebAdresa.getText().toString());
                atrakcija.setRadnoVreme(etRadnoVreme.getText().toString());
                atrakcija.setUlaznica(Double.parseDouble(etUlaznica.getText().toString()));

                try {
                    getDatabaseHelper().getAtrakcijaDao().update(atrakcija);
                    showToastMessage("Turisticka atrakcija azurirana");

                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        alert.show();
    }
    private void showToastMessage(String message) {
        boolean toast = sharedPreferences.getBoolean(NOTIFICATION_TOAST,false);

        if (toast) Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

