package com.example.androiddevelopment.turistickivodic.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.turistickivodic.R;
import com.example.androiddevelopment.turistickivodic.adapters.DrawerListAdapter;
import com.example.androiddevelopment.turistickivodic.adapters.NavigationItem;
import com.example.androiddevelopment.turistickivodic.db.DatabaseHelper;
import com.example.androiddevelopment.turistickivodic.model.Atrakcija;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }


    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    // Intent KEY
    public static String KEY_ID = "KEY_ID";

    // Preferences KEY
    public static String NOTIFICATION_TOAST = "notification_toast";
    public static String NOTIFICATION_STATUS = "notification_status";

    // Navigation Items
    private ArrayList<NavigationItem> items = new ArrayList<>();


    // Navigation Drawer
    private DrawerLayout drawerLayout;
    private ListView drawerNavList;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout drawerPane;
    private CharSequence drawerTitle;
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);


        final ListView listView = (ListView)findViewById(R.id.lv_main_list_items);
        try {
            List<Atrakcija> list = getDatabaseHelper().getAtrakcijaDao().queryForAll();
            ListAdapter adaprer = new ArrayAdapter<>(MainActivity.this,R.layout.list_item, list);
            listView.setAdapter(adaprer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atrakcija e = (Atrakcija) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra(KEY_ID, e.getId());
                startActivity(intent);
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Draws Navigation Items
        items.add(new NavigationItem("Home", "Sve turisticne atrakcije", R.drawable.ic_action_home));
        items.add(new NavigationItem("Settings", "Change App Settings", R.drawable.ic_action_settings));
        items.add(new NavigationItem("About", "O aplikaciji", R.drawable.ic_action_about));
        title = drawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerNavList = (ListView) findViewById(R.id.drawer_navigation_list);

        // Populate the Navigation Drawer with options
        drawerPane = (LinearLayout) findViewById(R.id.drawer_pane);

        DrawerListAdapter drawerAdapter =  new DrawerListAdapter(this,items);
        drawerNavList.setOnItemClickListener(new DrawerItemClickListener());
        drawerNavList.setAdapter(drawerAdapter);

        // Action Bar
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // Creates call to onPrepareOptionMenu
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_dialog) {
            addAtrakcijaDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAtrakcijaDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_atrakcija);
        dialog.setCancelable(false);

        final EditText etName = (EditText)dialog.findViewById(R.id.et_dialog_name);
        final EditText etDescription = (EditText)dialog.findViewById(R.id.et_dialog_description);
        final EditText etAddress = (EditText)dialog.findViewById(R.id.et_dialog_address);
        final EditText etPhoneNumber = (EditText)dialog.findViewById(R.id.et_dialog_phone_number);
        final EditText etWebAdresa = (EditText)dialog.findViewById(R.id.et_dialog_web);
        final EditText etRadnoVreme = (EditText)dialog.findViewById(R.id.et_dialog_radno_vreme);
        final EditText etUlaznica = (EditText)dialog.findViewById(R.id.et_dialog__ulaznice);


        Button btnOK = (Button)dialog.findViewById(R.id.btn_dialog_ok);
        Button btnCancel = (Button)dialog.findViewById(R.id.btn_dialog_cancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String address = etAddress.getText().toString();
                int phoneNumber = Integer.parseInt(etPhoneNumber.getText().toString());
                String webAdresa = etWebAdresa.getText().toString();
                String radnoVreme = etRadnoVreme.getText().toString();
                double ulaznica = Double.parseDouble(etUlaznica.getText().toString());

                Atrakcija atrakcija = new Atrakcija();
                atrakcija.setName(name);
                atrakcija.setDescription(description);
                atrakcija.setAddress(address);
                atrakcija.setPhoneNumber(phoneNumber);
                atrakcija.setWebAdresa(webAdresa);
                atrakcija.setRadnoVreme(radnoVreme);
                atrakcija.setUlaznica(ulaznica);

                try {
                    getDatabaseHelper().getAtrakcijaDao().create(atrakcija);

                    boolean toast = sharedPreferences.getBoolean(NOTIFICATION_TOAST,false);

                    if (toast) Toast.makeText(MainActivity.this, "Nova turisticka atrakcija je dodata.", Toast.LENGTH_SHORT).show();

                    refreshList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ListView listView = (ListView) findViewById(R.id.lv_main_list_items);

        if (listView != null) {
            ArrayAdapter<Atrakcija> adapter = (ArrayAdapter<Atrakcija>) listView.getAdapter();

            if (adapter != null) {

                try {
                    adapter.clear();
                    List<Atrakcija> turizam = getDatabaseHelper().getAtrakcijaDao().queryForAll();

                    adapter.addAll(turizam);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void selectItemFromDrawer(int position) {
        if (position == 0) {
            // MainListActivity

        } else if (position == 1) {
            // SettingsActivity
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));

        }

        drawerNavList.setItemChecked(position,true);
        setTitle(items.get(position).getTitle());
        drawerLayout.closeDrawer(drawerPane);
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