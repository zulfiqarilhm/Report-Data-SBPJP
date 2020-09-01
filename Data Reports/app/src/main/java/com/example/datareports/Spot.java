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

public class Spot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        ambil_dataSpot();

    }

    void ambil_dataSpot()
    {
        String link="http://10.2.1.200/datareport/Android/json_spot.php";
        StringRequest responSpot = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectSpot=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectSpot.getJSONArray("Spot");
                            ArrayList<Get_dataSpot> list_dataSpot;
                            list_dataSpot=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Spot = jsonArray.getJSONObject(i);
                                String  DatasetName=Spot.getString("DatasetName");
                                String  Hasil=Spot.getString("Hasil");

                                list_dataSpot.add(new Get_dataSpot(
                                        DatasetName,
                                        Hasil
                                ));
                            }
                            ListView listViewSpot=findViewById(R.id.list_spot);
                            Custom_adapterSpot adapterSpot=new Custom_adapterSpot(Spot.this,list_dataSpot);
                            listViewSpot.setAdapter(adapterSpot);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Spot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responSpot);
    }
}

class Get_dataSpot {
    String DatasetName="", Hasil="";
    Get_dataSpot (String DatasetName, String Hasil)
    {
        this.DatasetName=DatasetName;
        this.Hasil=Hasil;

    }

    public String getDatasetName() {
        return DatasetName;
    }

    public String getHasil() {
        return Hasil;
    }


}

class Custom_adapterSpot extends BaseAdapter
{

    Context contextSpot;
    LayoutInflater layoutInflaterSpot;
    ArrayList<Get_dataSpot>modelSpot;
    Custom_adapterSpot(Context contextSpot, ArrayList<Get_dataSpot>modelSpot)
    {
        layoutInflaterSpot=LayoutInflater.from(contextSpot);
        this.contextSpot=contextSpot;
        this.modelSpot=modelSpot;

    }

    @Override
    public int getCount() {
        return modelSpot.size();
    }

    @Override
    public Object getItem(int position) {
        return modelSpot.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterSpot.inflate(R.layout.list_spot,null);
        TextView DatasetNamePleaides, HasilPleaides;

        DatasetNamePleaides=view.findViewById(R.id.DatasetNameSpot);
        HasilPleaides=view.findViewById(R.id.HasilSpot);


        DatasetNamePleaides.setText(modelSpot.get(position).getDatasetName());
        HasilPleaides.setText(modelSpot.get(position).getHasil());

        return view;

    }
}
