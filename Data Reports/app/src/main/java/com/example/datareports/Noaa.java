package com.example.datareports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

public class Noaa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noaa);

        ambil_dataNoaa();
    }


    void ambil_dataNoaa()
    {
        String link="http://10.2.1.200/datareport/Android/json_noaametop.php";
        StringRequest responNoaa = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectNoaa=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectNoaa.getJSONArray("NoaaMetop");
                            ArrayList<Get_dataNoaa> list_dataNoaa;
                            list_dataNoaa=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Noaa = jsonArray.getJSONObject(i);
                                String  Satelit=Noaa.getString("Satelit");
                                String  Tanggal=Noaa.getString("Tanggal");
                                String  Jam=Noaa.getString("Jam");
                                String  Hasil=Noaa.getString("Hasil");

                                list_dataNoaa.add(new Get_dataNoaa(
                                        Satelit,
                                        Tanggal,
                                        Jam,
                                        Hasil
                                ));
                            }
                            ListView listViewNoaa=findViewById(R.id.list_noaa);
                            Custom_adapterNoaa adapterNoaa=new Custom_adapterNoaa(Noaa.this,list_dataNoaa);
                            listViewNoaa.setAdapter(adapterNoaa);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Noaa.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responNoaa);
    }
}

class Get_dataNoaa {
    String Satelit="", Tanggal="", Jam="", Hasil="";
    Get_dataNoaa (String Satelit, String Tanggal, String Jam, String Hasil)
    {
        this.Satelit=Satelit;
        this.Tanggal=Tanggal;
        this.Jam=Jam;
        this.Hasil=Hasil;

    }

    public String getSatelit() {
        return Satelit;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public String getJam() {
        return Jam;
    }

    public String getHasil() {
        return Hasil;
    }


}

class Custom_adapterNoaa extends BaseAdapter
{

    Context contextNoaa;
    LayoutInflater layoutInflaterNoaa;
    ArrayList<Get_dataNoaa>modelNoaa;
    Custom_adapterNoaa(Context contextNoaa, ArrayList<Get_dataNoaa>modelNoaa)
    {
        layoutInflaterNoaa=LayoutInflater.from(contextNoaa);
        this.contextNoaa=contextNoaa;
        this.modelNoaa=modelNoaa;

    }

    @Override
    public int getCount() {
        return modelNoaa.size();
    }

    @Override
    public Object getItem(int position) {
        return modelNoaa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterNoaa.inflate(R.layout.list_noaa,null);
        TextView Satelit, Tanggal, Jam, HasilNoaa;

        Satelit=view.findViewById(R.id.satelitnoaa);
        Tanggal=view.findViewById(R.id.tanggalnoaa);
        Jam=view.findViewById(R.id.jamnoaa);
        HasilNoaa=view.findViewById(R.id.hasilnoaa);


        Satelit.setText(modelNoaa.get(position).getSatelit());
        Tanggal.setText(modelNoaa.get(position).getTanggal());
        Jam.setText(modelNoaa.get(position).getJam());
        HasilNoaa.setText(modelNoaa.get(position).getHasil());

        return view;

    }
}
