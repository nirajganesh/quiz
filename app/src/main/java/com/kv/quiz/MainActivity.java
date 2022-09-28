package com.kv.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kv.quiz.Adapter.CategoryquizAdapter;
import com.kv.quiz.modal.Categoryquiz;
import com.kv.quiz.modal.Session;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;

    RecyclerView recyclerView;
    ArrayList<Categoryquiz> categoryquizs=new ArrayList<>();
    CategoryquizAdapter categoriesAdapter;
    Context context=MainActivity.this;
    ShimmerFrameLayout shimmerFrameLayout;
   // SliderView sliderView;
   // SliderLayout sliderLayout;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    String uid;

    int slide_image[] = new int[]{R.drawable.banner_image,R.drawable.banner1};
    int category_image[] = new int[]{R.drawable.math_image_preview,R.drawable.english_image_preview,R.drawable.physics_image_preview,R.drawable.gk_image1_preview};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
     //   getSupportActionBar().setHomeButtonEnabled(true);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString(Session.MY_USERID,null);



        // recylerview data
        recyclerView=findViewById(R.id.recyclerviewid);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // shimmer declaration
        shimmerFrameLayout=findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        // slider declaration
       // sliderView=new SliderView(MainActivity.this);
      //  sliderLayout=findViewById(R.id.imageSlider1);
        category_quiz();


        bottomNavigationView=findViewById(R.id.navigationmenuid);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.profileid)
                {
                    if(uid!=null)
                    {

                    }
                    else
                    {

                    }

                }
                return true;
            }
        });
    }


    private void category_quiz()
    {
        categoryquizs.add(new Categoryquiz(category_image[0],"Mathematics","1"));
        categoryquizs.add(new Categoryquiz(category_image[1],"English","2"));
        categoryquizs.add(new Categoryquiz(category_image[2],"Physics","3"));
        categoryquizs.add(new Categoryquiz(category_image[3],"computer","4"));

        categoriesAdapter=new CategoryquizAdapter(context,categoryquizs);
        recyclerView.setAdapter(categoriesAdapter);
    }
}