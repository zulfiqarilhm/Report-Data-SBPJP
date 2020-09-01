package com.example.datareports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.widget.ProgressBar;



public class Storage extends AppCompatActivity {

    private static final String CHANNEL_ID = "Storage";
    private static final String CHANNEL_NAME = "Kapasitas";
    private static final String CHANNEL_DESC = "Kapasitas Storage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        ambil_datastorage();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel1);
        }

    }


    void ambil_datastorage()
    {
        String link="http://10.2.1.200/datareport/Android/kapasitas.php";
        StringRequest responStorage = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectStorage=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectStorage.getJSONArray("Hasil");
                            ArrayList<Get_datastorage> list_datastorage;
                            list_datastorage=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject hasilstorage = jsonArray.getJSONObject(i);
                                String  Storage=hasilstorage.getString("Storage");
                                String  TotalStorage=hasilstorage.getString("TotalStorage");
                                String  FreeStorage=hasilstorage.getString("FreeStorage");
                                String  UsedStorage=hasilstorage.getString("UsedStorage");
                                String  Persen=hasilstorage.getString("Persen");
                                String  Limit=hasilstorage.getString("Limit" );

                                int persen = Integer.parseInt(Persen);
                                int limit = Integer.parseInt(Limit);

                                if ( persen > limit){
                                    notifikasi();
                                }


                                list_datastorage.add(new Get_datastorage(
                                        Storage,
                                        TotalStorage,
                                        FreeStorage,
                                        UsedStorage,
                                        Persen
                                ));
                            }
                            ListView listViewstorage=findViewById(R.id.list_storage);
                            Custom_adapterstorage adapterstorage=new Custom_adapterstorage(Storage.this,list_datastorage);
                            listViewstorage.setAdapter(adapterstorage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Storage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responStorage);
    }

    private void notifikasi() {
        Intent intent = new Intent(this, Storage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_warning_black_24dp)
                    .setContentTitle(" WARNING !!! ")
                    .setContentText(" Kapasitas Storage Sudah Mencapai Batas Maksimum !! ")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());

    }

}

class Get_datastorage {
    String Storage="", TotalStorage="", FreeStorage="", UsedStorage="", Persen="";
    Get_datastorage (String Storage, String TotalStorage, String FreeStorage, String UsedStorage, String Persen)
    {
        this.Storage=Storage;
        this.TotalStorage=TotalStorage;
        this.FreeStorage=FreeStorage;
        this.UsedStorage=UsedStorage;
        this.Persen=Persen;
    }

    public String getStorage() {

        return Storage;
    }

    public String getTotalStorage() {

        return TotalStorage;
    }

    public String getFreeStorage() {

        return FreeStorage;
    }

    public String getUsedStorage() {

        return UsedStorage;
    }
    public String getPersen() {

        return Persen;
    }

}

class Custom_adapterstorage extends BaseAdapter
{

    Context contextstorage;
    LayoutInflater layoutInflaterstorage;
    ArrayList<Get_datastorage>modelstorage;
    Custom_adapterstorage(Context contextstorage, ArrayList<Get_datastorage>modelstorage)
    {
        layoutInflaterstorage=LayoutInflater.from(contextstorage);
        this.contextstorage=contextstorage;
        this.modelstorage=modelstorage;

    }

    @Override
    public int getCount() {
        return modelstorage.size();
    }

    @Override
    public Object getItem(int position) {
        return modelstorage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterstorage.inflate(R.layout.list_storage,null);
        TextView Storage, TotalStorage, FreeStorage, UsedStorage;
        ProgressBar progressBar;
        Storage=view.findViewById(R.id.Storage);
        TotalStorage=view.findViewById(R.id.TotalStorage);
        FreeStorage=view.findViewById(R.id.FreeStorage);
        UsedStorage=view.findViewById(R.id.UsedStorage);

        progressBar=view.findViewById(R.id.pbar);

        int used = Integer.parseInt(modelstorage.get(position).getPersen());

        if ( used >= 86) {

            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }else if ( used <= 85){
            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }else if ( used >=0 ){
            progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }

        Storage.setText(modelstorage.get(position).getStorage());
        TotalStorage.setText(modelstorage.get(position).getTotalStorage());
        FreeStorage.setText(modelstorage.get(position).getFreeStorage());
        UsedStorage.setText(modelstorage.get(position).getUsedStorage());
        progressBar.setProgress(used);
        progressBar.setScaleY(5f);

        return view;
    }

}
