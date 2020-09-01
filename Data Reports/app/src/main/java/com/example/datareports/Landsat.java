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

public class Landsat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landsat);

        ambil_dataLandsat();
    }


    void ambil_dataLandsat()
    {
        String link="http://10.2.1.200/datareport/Android/json_landsat.php";
        StringRequest responLandsat = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectLandsat=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectLandsat.getJSONArray("Landsat");
                            ArrayList<Get_dataLandsat> list_dataLandsat;
                            list_dataLandsat=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Landsat = jsonArray.getJSONObject(i);
                                String  Satelit=Landsat.getString("Satelit");
                                String  Tanggal=Landsat.getString("Tanggal");
                                String  Path=Landsat.getString("Path");
                                String  Row=Landsat.getString("Row");

                                list_dataLandsat.add(new Get_dataLandsat(
                                        Satelit,
                                        Tanggal,
                                        Path,
                                        Row
                                ));
                            }
                            ListView listViewLandsat=findViewById(R.id.list_landsat);
                            Custom_adapterLandsat adapterLandsat=new Custom_adapterLandsat(Landsat.this,list_dataLandsat);
                            listViewLandsat.setAdapter(adapterLandsat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Landsat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responLandsat);
    }
}

class Get_dataLandsat {
    String Satelit="", Tanggal="", Path="", Row="";
    Get_dataLandsat (String Satelit, String Tanggal, String Path,  String Row)
    {
        this.Satelit=Satelit;
        this.Tanggal=Tanggal;
        this.Path=Path;
        this.Row=Row;

    }

    public String getSatelit() {
        return Satelit;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public String getPath() {return Path; }

    public String getRow() {
        return Row;
    }

}

class Custom_adapterLandsat extends BaseAdapter
{

    Context contextLandsat;
    LayoutInflater layoutInflaterLandsat;
    ArrayList<Get_dataLandsat>modelLandsat;
    Custom_adapterLandsat(Context contextLandsat, ArrayList<Get_dataLandsat>modelLandsat)
    {
        layoutInflaterLandsat=LayoutInflater.from(contextLandsat);
        this.contextLandsat=contextLandsat;
        this.modelLandsat=modelLandsat;

    }

    @Override
    public int getCount() {
        return modelLandsat.size();
    }

    @Override
    public Object getItem(int position) {
        return modelLandsat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterLandsat.inflate(R.layout.list_landsat,null);
        TextView Satelit, Tanggal, Path, Row;

        Satelit=view.findViewById(R.id.satelitt);
        Tanggal=view.findViewById(R.id.tanggall);
        Path=view.findViewById(R.id.pathh);
        Row=view.findViewById(R.id.roww);

        Satelit.setText(modelLandsat.get(position).getSatelit());
        Tanggal.setText(modelLandsat.get(position).getTanggal());
        Path.setText(modelLandsat.get(position).getPath());
        Row.setText(modelLandsat.get(position).getRow());

        return view;

    }
}
