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

public class Modis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modis);

        ambil_dataModis();
    }


    void ambil_dataModis()
    {
        String link="http://10.2.1.200/datareport/Android/json_modis.php";
        StringRequest responModis = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectModis=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectModis.getJSONArray("Modis");
                            ArrayList<Get_dataModis> list_dataModis;
                            list_dataModis=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Modis = jsonArray.getJSONObject(i);
                                String  Satelit=Modis.getString("Satelit");
                                String  Tanggal=Modis.getString("Tanggal");
                                String  Jam=Modis.getString("Jam");
                                String  Hasil=Modis.getString("Hasil");

                                list_dataModis.add(new Get_dataModis(
                                        Satelit,
                                        Tanggal,
                                        Jam,
                                        Hasil
                                ));
                            }
                            ListView listViewModis=findViewById(R.id.list_modis);
                            Custom_adapterModis adapterModis=new Custom_adapterModis(Modis.this,list_dataModis);
                            listViewModis.setAdapter(adapterModis);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Modis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responModis);
    }
}

class Get_dataModis {
    String Satelit="", Tanggal="", Jam="", Hasil="";
    Get_dataModis (String Satelit, String Tanggal, String Jam,  String Hasil)
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

class Custom_adapterModis extends BaseAdapter
{

    Context contextModis;
    LayoutInflater layoutInflaterModis;
    ArrayList<Get_dataModis>modelModis;
    Custom_adapterModis(Context contextModis, ArrayList<Get_dataModis>modelModis)
    {
        layoutInflaterModis=LayoutInflater.from(contextModis);
        this.contextModis=contextModis;
        this.modelModis=modelModis;

    }

    @Override
    public int getCount() {
        return modelModis.size();
    }

    @Override
    public Object getItem(int position) {
        return modelModis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterModis.inflate(R.layout.list_modis,null);
        TextView Satelit, Tanggal, Jam, HasilModis;

        Satelit=view.findViewById(R.id.satelit);
        Tanggal=view.findViewById(R.id.tanggal);
        Jam=view.findViewById(R.id.jam);
        HasilModis=view.findViewById(R.id.hasil);


        Satelit.setText(modelModis.get(position).getSatelit());
        Tanggal.setText(modelModis.get(position).getTanggal());
        Jam.setText(modelModis.get(position).getJam());
        HasilModis.setText(modelModis.get(position).getHasil());

        return view;

    }
}
