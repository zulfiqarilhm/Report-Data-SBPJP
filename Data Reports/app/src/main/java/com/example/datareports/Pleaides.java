package com.example.datareports;

import androidx.appcompat.app.AppCompatActivity;

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

public class Pleaides extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pleaides);

        ambil_dataPleaides();
    }

    void ambil_dataPleaides()
    {
        String link="http://10.2.1.200/datareport/Android/json_phr.php";
        StringRequest responPleaides = new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectPleaides=new JSONObject(response);
                            JSONArray jsonArray=jsonObjectPleaides.getJSONArray("Pleiades");
                            ArrayList<Get_dataPleaides> list_dataPleaides;
                            list_dataPleaides=new ArrayList<>();
                            for (int i=-0; i<jsonArray.length();i++)
                            {
                                JSONObject Pleaides = jsonArray.getJSONObject(i);
                                String  DatasetName=Pleaides.getString("DatasetName");
                                String  Hasil=Pleaides.getString("Hasil");

                                list_dataPleaides.add(new Get_dataPleaides(
                                        DatasetName,
                                        Hasil
                                ));
                            }
                            ListView listViewPleaides=findViewById(R.id.list_pleaides);
                            Custom_adapterPleaides adapterPleaides=new Custom_adapterPleaides(Pleaides.this,list_dataPleaides);
                            listViewPleaides.setAdapter(adapterPleaides);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Pleaides.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(responPleaides);
    }
}

class Get_dataPleaides {
    String DatasetName="", Hasil="";
    Get_dataPleaides (String DatasetName, String Hasil)
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

class Custom_adapterPleaides extends BaseAdapter
{

    Context contextPleaides;
    LayoutInflater layoutInflaterPleaides;
    ArrayList<Get_dataPleaides>modelPleaides;
    Custom_adapterPleaides(Context contextPleaides, ArrayList<Get_dataPleaides>modelPleaides)
    {
        layoutInflaterPleaides=LayoutInflater.from(contextPleaides);
        this.contextPleaides=contextPleaides;
        this.modelPleaides=modelPleaides;

    }

    @Override
    public int getCount() {
        return modelPleaides.size();
    }

    @Override
    public Object getItem(int position) {
        return modelPleaides.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflaterPleaides.inflate(R.layout.list_pleaides,null);
        TextView DatasetNamePleaides, HasilPleaides;

        DatasetNamePleaides=view.findViewById(R.id.DatasetNamePleaides);
        HasilPleaides=view.findViewById(R.id.HasilPleaides);


        DatasetNamePleaides.setText(modelPleaides.get(position).getDatasetName());
        HasilPleaides.setText(modelPleaides.get(position).getHasil());

        return view;

    }
}
