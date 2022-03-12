package com.example.bessonova483_api_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
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

public class ListMapsActivity extends AppCompatActivity {
    String token;
    ListView lv;
    Toast msgerr;
    String text;
    int id;
    String iditem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_maps);
        ArrayList<String> listmaps = new ArrayList<String>();
        token = getIntent().getStringExtra("token");
        msgerr=Toast.makeText(this, "", Toast.LENGTH_SHORT);
listmaps.clear();
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
                    Log.i("API", "token=" + token);
                }
            }
        };


        try {
            obj.put("skey",token);

            JSONArray array = new JSONArray(obj);
            if(array !=null)
            {
                int len = array.length();
                for(int i=0;i<len;i++){
                    listmaps.add(array.get(i).toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listmaps);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                            Intent appInfo = new Intent(ListMapsActivity.this, ViewNodesInMapActivity.class);
                                  appInfo.putExtra("token", token);
                                  appInfo.putExtra("mapid", lv.getId());
                            startActivity(appInfo);
                        }
                    });
registerForContextMenu(lv);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String payload=obj.toString();
        req.Post("http://v1.fxnode.ru:8081/rpc/get_maps", payload);
    }

    @Override
    public void onCreateContextMenu(ContextMenu cmenu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(cmenu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cmenu.add("Rename");
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
                    obj.put("mid", iditem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String payload=obj.toString();
                req.Post("http://v1.fxnode.ru:8081/rpc/delete_map", payload);
                return true;
        }

return super.onContextItemSelected(item);
}
    public void onBackClick(View v){
        Intent iBack = new Intent(this, MainMenuActivity.class);
        startActivity(iBack);
    }
    public void onAddmapClick(View v){
        Intent iaddmap = new Intent(this, CreateMapActivity.class);
        iaddmap.putExtra("Token", token);
        startActivity(iaddmap);
    }
}