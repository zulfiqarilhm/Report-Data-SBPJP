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

public class Npp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npp);

        ambil_dataNpp();
    }


    void ambil_dataNpp()

    {
        String link="http://10.2.1.200/datareport/Android/json_npp.php";
        StringRequest responNpp = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectNpp=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectNpp.getJSONArray("Npp");
                            ArrayList<Get_dataNpp> list_dataNpp;
                            list_dataNpp=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Npp = jsonArray.getJSONObject(i);
                                String  NamaDir=Npp.getString("NamaDir");
                                String  Hasil=Npp.getString("Hasil");

                                list_dataNpp.add(new Get_dataNpp(
                                        NamaDir,
                                        Hasil
                                ));
                            }
                            ListView listViewNpp=findViewById(R.id.list_npp);
                            Custom_adapterNpp adapterNpp=new Custom_adapterNpp(Npp.this,list_dataNpp);
                            listViewNpp.setAdapter(adapterNpp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Npp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responNpp);
    }
}

class Get_dataNpp {
    String NamaDir="", Hasil="";
    Get_dataNpp (String NamaDir, String Hasil)
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

class Custom_adapterNpp extends BaseAdapter
{

    Context contextNpp;
    LayoutInflater layoutInflaterNpp;
    ArrayList<Get_dataNpp>modelNpp;
    Custom_adapterNpp(Context contextNpp, ArrayList<Get_dataNpp>modelNpp)
    {
        layoutInflaterNpp=LayoutInflater.from(contextNpp);
        this.contextNpp=contextNpp;
        this.modelNpp=modelNpp;

    }

    @Override
    public int getCount() {
        return modelNpp.size();
    }

    @Override
    public Object getItem(int position) {
        return modelNpp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterNpp.inflate(R.layout.list_npp,null);
        TextView DatasetNameNpp, HasilNpp;

        DatasetNameNpp=view.findViewById(R.id.DatasetNameNpp);
        HasilNpp=view.findViewById(R.id.HasilNpp);


        DatasetNameNpp.setText(modelNpp.get(position).getNamaDir());
        HasilNpp.setText(modelNpp.get(position).getHasil());

        return view;

    }
}
