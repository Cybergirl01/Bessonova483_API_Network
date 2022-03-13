package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewNodesInMapActivity extends AppCompatActivity {
    String token;
    Integer mid;
    Toast msgerr;
    ListView lv;


    String text;
    int id;
    String iditem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nodes_in_map);
        Intent itoken = getIntent();
        token = itoken.getStringExtra("token");
        mid = Integer.parseInt(itoken.getStringExtra("mapid"));
        msgerr = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        lv = findViewById(R.id.Nodelist);
        ArrayList<String> listnodes = new ArrayList<String>();
        listnodes.clear();
        JSONObject obj = new JSONObject();
        APIClass req = new APIClass(this) {

            @Override
            public void onError() {
                msgerr.setText("Request failed!");
                msgerr.show();
            }

            @Override
            public void onSuccess(String data) {

                if (data == "null") {
                    msgerr.setText("Invalid credentials!");
                    msgerr.show();
                } else {
                    token = data.replace("\"", "");
                    mid = Integer.parseInt(data.replace("\"", ""));
                    Log.i("API", "token=" + token);
                    Log.i("API", "mid=" + mid);
                }
            }
        };


        try {
            obj.put("tok", token);
            obj.put("mid", mid);
            JSONArray array = new JSONArray(obj);
            if (array != null) {
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    listnodes.add(array.get(i).toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listnodes);
                    lv.setAdapter(adapter);
                    registerForContextMenu(lv);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String payload = obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/get_nodes", payload);
    }

    @Override
    public void onCreateContextMenu(ContextMenu cmenu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(cmenu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cmenu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                Intent iUpdate = new Intent(this, UpdateMap.class);
                iUpdate.putExtra("token", token); lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView tv = (TextView) view.findViewById(R.id.list_item);
                    text = tv.getText().toString();
                    iUpdate.putExtra("Mapname", text);
                    long id = adapterView.getId();
                    iUpdate.putExtra("Mapid", id);
                }
            });
                startActivity(iUpdate);
                return true;
            case 1:
                APIClass req = new APIClass(this) {

                    @Override
                    public void onError() {
                        msgerr.setText("Request failed!");
                        msgerr.show();
                    }

                    @Override
                    public void onSuccess(String data) {

                        if (data == "null") {
                            msgerr.setText("Invalid credentials!");
                            msgerr.show();
                        } else {
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    TextView tv = (TextView) view.findViewById(R.id.list_item);
                                    text = tv.getText().toString();
                                    id = adapterView.getId();
                                    iditem = String.valueOf(id);
                                }
                            });
                            token = data.replace("\"", "");
                            iditem = data.replace("\"", "");

                        }
                    }
                };
                JSONObject obj = new JSONObject();
                try {
                    obj.put("tok", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    obj.put("nid", iditem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String payload=obj.toString();
                req.Post("http://v1.fxnode.ru:8081/rpc/delete_node", payload);
                return true;
        }

        return super.onContextItemSelected(item);
    }
    public void onBackClick(View v){
        Intent iBack = new Intent(this, ListMapsActivity.class);
        startActivity(iBack);
    }
    public void onAddNodeClick(View v){
        Intent iaddnode = new Intent(this, CreateMapActivity.class);
        iaddnode.putExtra("Token", token);
        iaddnode.putExtra("mid", mid);
        startActivity(iaddnode);
    }
}
