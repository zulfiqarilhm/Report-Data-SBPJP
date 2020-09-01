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

public class Jpss extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpss);

        ambil_dataJpss();
    }


    void ambil_dataJpss()
    {
        String link="http://10.2.1.200/datareport/Android/json_jpss.php";
        StringRequest responJpss = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectJpss=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectJpss.getJSONArray("Jpss");
                            ArrayList<Get_dataJpss> list_dataJpss;
                            list_dataJpss=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Jpss = jsonArray.getJSONObject(i);
                                String  NamaDir=Jpss.getString("NamaDir");
                                String  Hasil=Jpss.getString("Hasil");

                                list_dataJpss.add(new Get_dataJpss(
                                        NamaDir,
                                        Hasil
                                ));
                            }
                            ListView listViewJpss=findViewById(R.id.list_jpss);
                            Custom_adapterJpss adapterJpss=new Custom_adapterJpss(Jpss.this,list_dataJpss);
                            listViewJpss.setAdapter(adapterJpss);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Jpss.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responJpss);
    }
}

class Get_dataJpss {
    String NamaDir="", Hasil="";
    Get_dataJpss (String NamaDir, String Hasil)
    {
        this.NamaDir=NamaDir;
        this.Hasil=Hasil;

    }

    public String getNamaDir() {
        return NamaDir;
    }

    public String getHasil() {
        return Hasil;
    }


}

class Custom_adapterJpss extends BaseAdapter
{

    Context contextJpss;
    LayoutInflater layoutInflaterJpss;
    ArrayList<Get_dataJpss>modelJpss;
    Custom_adapterJpss(Context contextJpss, ArrayList<Get_dataJpss>modelJpss)
    {
        layoutInflaterJpss=LayoutInflater.from(contextJpss);
        this.contextJpss=contextJpss;
        this.modelJpss=modelJpss;

    }

    @Override
    public int getCount() {
        return modelJpss.size();
    }

    @Override
    public Object getItem(int position) {
        return modelJpss.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterJpss.inflate(R.layout.list_jpss,null);
        TextView NamaDir, HasilJpss;

        NamaDir=view.findViewById(R.id.NamaDirJpss);
        HasilJpss=view.findViewById(R.id.HasilJpss);


        NamaDir.setText(modelJpss.get(position).getNamaDir());
        HasilJpss.setText(modelJpss.get(position).getHasil());

        return view;

    }
}