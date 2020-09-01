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

public class Radar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        ambil_dataRadar();
    }


    void ambil_dataRadar()

    {
        String link="http://10.2.1.200/datareport/Android/json_sar.php";
        StringRequest responRadar = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectRadar=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectRadar.getJSONArray("Radar");
                            ArrayList<Get_dataRadar> list_dataRadar;
                            list_dataRadar=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Radar = jsonArray.getJSONObject(i);
                                String  Satelit=Radar.getString("Satelit");
                                String  Tanggal=Radar.getString("Tanggal");
                                String  Start=Radar.getString("Start");
                                String  End=Radar.getString("End");
                                String  Hasil=Radar.getString("Hasil");

                                list_dataRadar.add(new Get_dataRadar(
                                        Satelit,
                                        Tanggal,
                                        Start,
                                        End,
                                        Hasil
                                ));
                            }
                            ListView listViewRadar=findViewById(R.id.list_radar);
                            Custom_adapterRadar adapterRadar=new Custom_adapterRadar(Radar.this,list_dataRadar);
                            listViewRadar.setAdapter(adapterRadar);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Radar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responRadar);
    }
}

class Get_dataRadar {
    String Satelit="", Tanggal="", Start="", End="", Hasil="";
    Get_dataRadar (String Satelit, String Tanggal, String Start, String End, String Hasil)
    {
        this.Satelit=Satelit;
        this.Tanggal=Tanggal;
        this.Start=Start;
        this.End=End;
        this.Hasil=Hasil;

    }

    public String getSatelit() {
        return Satelit;
    }
    public String getTanggal() {
        return Tanggal;
    }
    public String getStart() {
        return Start;
    }
    public String getEnd() {
        return End;
    }
    public String getHasil() {
        return Hasil;
    }


}

class Custom_adapterRadar extends BaseAdapter
{

    Context contextRadar;
    LayoutInflater layoutInflaterRadar;
    ArrayList<Get_dataRadar>modelRadar;
    Custom_adapterRadar(Context contextRadar, ArrayList<Get_dataRadar>modelRadar)
    {
        layoutInflaterRadar=LayoutInflater.from(contextRadar);
        this.contextRadar=contextRadar;
        this.modelRadar=modelRadar;

    }

    @Override
    public int getCount() {
        return modelRadar.size();
    }

    @Override
    public Object getItem(int position) {
        return modelRadar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterRadar.inflate(R.layout.list_radar,null);
        TextView Satelit, Tanggal, Start, End, Hasil;

        Satelit=view.findViewById(R.id.satelit);
        Tanggal=view.findViewById(R.id.tanggal);
        Start=view.findViewById(R.id.start);
        End=view.findViewById(R.id.end);
        Hasil=view.findViewById(R.id.hasil);


        Satelit.setText(modelRadar.get(position).getSatelit());
        Tanggal.setText(modelRadar.get(position).getTanggal());
        Start.setText(modelRadar.get(position).getStart());
        End.setText(modelRadar.get(position).getEnd());
        Hasil.setText(modelRadar.get(position).getHasil());

        return view;

    }
}
