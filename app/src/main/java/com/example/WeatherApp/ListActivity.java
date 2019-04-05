package com.example.WeatherApp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends Activity implements View.OnClickListener {
    WifiManager wifi;
    ListView list;
    TextView tS;
    Button bS;
    int size = 0;
    List<ScanResult> results;
    ArrayList<String> S = new ArrayList<>();
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();


    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("LOP");
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
            }
        }
        tS = (TextView) findViewById(R.id.textStatus);
        bS = (Button) findViewById(R.id.buttonScan);
        bS.setOnClickListener(this);
        list = (ListView) findViewById(R.id.list);
        CustomAdapter custom = new CustomAdapter(getApplicationContext(), S);
        list.setAdapter(custom);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        if (wifi.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
        //this.adapter = new SimpleAdapter(ListActivity.this, arraylist, R.layout.custom_layout, new String[] { ITEM_KEY }, new int[] { R.id.list_value });
//        list.setAdapter(this.adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clicked = String.valueOf(parent.getItemAtPosition(position));


                String mac = getMAC(clicked.toUpperCase() );

                Intent intent = new Intent(getApplicationContext(), DeviceDetailActivity.class);
                intent.putExtra("mac", mac);
                startActivity(intent);
            }
        });
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                results = wifi.getScanResults();
                size = results.size();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void onClick(View view) {
        arraylist.clear();
        wifi.startScan();

        Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show();
        try {
            size = size - 1;
            while (size >= 0) {

                //  HashMap<String, String> item = new HashMap<String, String>();
//                item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities + results.get(size).BSSID);
//                arraylist.add(item);
                if (results.get(size).SSID.equalsIgnoreCase("ESPap"))
                    S.add(results.get(size).SSID + "\n" + "Mac@" + results.get(size).BSSID);
                size--;
                //adapter.notifyDataSetChanged();
            }
            System.out.print(arraylist.toString());
            CustomAdapter custom = new CustomAdapter(getApplicationContext(), S);
            list.setAdapter(custom);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("LOP");
    }

    public String getMAC(String s) {
        String r = "";
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == '@') {
                j++;
                int t = j;
                for (; j < s.length(); j++) {
                    if (j == t + 1) {
                        r = r + (char) ((int) (s.charAt(j) - 2));

                    } else
                        r = r + s.charAt(j);
                }
            }

        }
        System.out.println("nouran3" + r);
        return r;

    }
}