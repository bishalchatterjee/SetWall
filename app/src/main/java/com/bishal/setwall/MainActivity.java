package com.bishal.setwall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private EditText searchEdit;
    private ImageView searchIV;
    private RecyclerView categoryRV,wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;
    //API - 563492ad6f91700001000001988949b574e04734836ff91ee4b30488
    //pexels api


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdit=findViewById(R.id.idEdtSearch);
        searchIV= findViewById(R.id.idIVSearch);
        categoryRV=findViewById(R.id.idRVCategory);
        wallpaperRV=findViewById(R.id.idRVWallpapers);
        loadingPB=findViewById(R.id.idPBLoading);
        wallpaperArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        LinearLayoutManager linearLinearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLinearLayoutManager);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryClick);
        categoryRV.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter=new WallpaperRVAdapter(wallpaperArrayList,this);
        wallpaperRV.setAdapter(wallpaperRVAdapter);

        getCategories();
        getWallpapers();


        searchIV.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String searchStr = searchEdit.getText().toString();
                if(searchStr.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please Enter Your Search Query",Toast.LENGTH_SHORT).show();
                }else{
                    getWallpapersByCategories(searchStr);
                }
            }
        });


    }
    private void getWallpapersByCategories(String category){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url ="https://api.pexels.com/v1/search?query="+category+"&per_page=1000&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray photoArray= null;
                try {
                     photoArray=response.getJSONArray("photos");
                    for (int i=0;i<photoArray.length();i++){
                        JSONObject photoObj= photoArray.getJSONObject(i);
                        String imgUrl=photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    loadingPB.setVisibility(View.GONE);
                    wallpaperRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Failed to Load Wallpapers",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "563492ad6f91700001000001988949b574e04734836ff91ee4b30488");
                return headers;

            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    private void getCategories() {

        categoryRVModelArrayList.add(new CategoryRVModel("Nature","https://images.unsplash.com/photo-1433086966358-54859d0ed716?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bmF0dXJlfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Travel","https://images.unsplash.com/photo-1488646953014-85cb44e25828?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJhdmVsfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Music","https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bXVzaWN8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Abstract","https://images.unsplash.com/photo-1488554378835-f7acf46e6c98?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8YWJzdHJhY3R8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Cars","https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y2Fyc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Minimal","https://images.unsplash.com/photo-1622737133809-d95047b9e673?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8bWluaW1hbCUyMGJhY2tncm91bmR8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Animals","https://images.unsplash.com/photo-1587402092301-725e37c70fd8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YW5pbWFsJTIwbG92ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Architecture","https://images.unsplash.com/photo-1619544168405-fc21cdac2237?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGFyY2hpY3R1cmV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Football","https://images.unsplash.com/photo-1552318965-6e6be7484ada?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGZvb3RiYWxsfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Cricket","https://images.unsplash.com/photo-1531415074968-036ba1b575da?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8Y3JpY2tldHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Badminton","https://images.unsplash.com/photo-1564226803380-91139fdcb4d0?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YmFkbWludG9ufGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Chess","https://images.unsplash.com/photo-1586165368502-1bad197a6461?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8Y2hlc3N8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"));categoryRVModelArrayList.add(new CategoryRVModel("Food","https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8Zm9vZHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Black","https://images.unsplash.com/photo-1504805572947-34fad45aed93?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8YmxhY2t8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Hacker","https://images.unsplash.com/photo-1613918514536-2071b86612c4?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjl8fGhhY2tlcnxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Spiritual","https://images.unsplash.com/photo-1538024333176-f25f63f873ee?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNwaXJpdHVhbHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }


    private void getWallpapers() {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/curated?per_page=1000&page=1";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i=0;i<photoArray.length();i++){
                        JSONObject photoObj= photoArray.getJSONObject(i);
                        String imgUrl=photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to Load Wallpapers....",Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001988949b574e04734836ff91ee4b30488");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModelArrayList.get(position).getCategory();
        getWallpapersByCategories(category);

    }
}