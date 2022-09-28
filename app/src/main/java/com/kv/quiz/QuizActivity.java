package com.kv.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kv.quiz.Adapter.Categories_list_Adapter;
import com.kv.quiz.Adapter.CategoryquizAdapter;
import com.kv.quiz.modal.Categories_list;
import com.kv.quiz.modal.Categoryquiz;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Categories_list> category_list=new ArrayList<>();
    Categories_list_Adapter categories_list_Adapter;
    Context context=QuizActivity.this;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences sharedPreferences;
    String uid;
    String cat_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        cat_id=getIntent().getStringExtra("id");
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerviewid);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        quiz_list();
    }

    public void quiz_list()
    {
        if(cat_id.equals("1"))
        {
           category_list.add(new Categories_list("1","4","4:30","Math test 1"));
           category_list.add(new Categories_list("2","4","5:30","Math test 2"));
           categories_list_Adapter=new Categories_list_Adapter(context,category_list);
           recyclerView.setAdapter(categories_list_Adapter);
        }
        if(cat_id.equals("2"))
        {
            category_list.add(new Categories_list("1","4","4:30","Englishtest 1"));
            category_list.add(new Categories_list("2","4","5:30","English test 2"));
            categories_list_Adapter=new Categories_list_Adapter(context,category_list);
            recyclerView.setAdapter(categories_list_Adapter);
        }
        if(cat_id.equals("3"))
        {
            category_list.add(new Categories_list("1","4","4:30","Physics test 1"));
            category_list.add(new Categories_list("2","4","5:30","Physics test 2"));
            categories_list_Adapter=new Categories_list_Adapter(context,category_list);
            recyclerView.setAdapter(categories_list_Adapter);
        }
        if(cat_id.equals("4"))
        {
            category_list.add(new Categories_list("1","4","4:30","Computer test 1"));
            category_list.add(new Categories_list("2","4","5:30","Computer test 2"));
            categories_list_Adapter=new Categories_list_Adapter(context,category_list);
            recyclerView.setAdapter(categories_list_Adapter);
        }
    }

}