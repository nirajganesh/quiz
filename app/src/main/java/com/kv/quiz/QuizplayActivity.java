package com.kv.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.kv.quiz.Adapter.Quizplay_Adapter;
import com.kv.quiz.modal.Listitem;
import com.kv.quiz.modal.Quizplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class QuizplayActivity extends AppCompatActivity {

    TextView question_no,title_text,attempt_text;
    String timer;
    RadioButton opt1_text,opt2_text,opt3_text,opt4_text;
    LinearLayout preview,next;
    ArrayList<Quizplay> quizlistmodalArrayList=new ArrayList<>();
    int count=0;
    RadioGroup radioGroup;
    EasyDB easyDB;
    Quizplay_Adapter questionlistAdapter;
    Context context=QuizplayActivity.this;
    Integer counter=0;
    RadioButton selectedRadioButton;
    RelativeLayout submit;
    String id,set,question,attempt;
    int right_ans=0,wrong_ans=0;
    String time_taken="0",json;
    ArrayList<Listitem> listitems=new ArrayList<>();
    String total_question,total_time;
    Button yes_btn,no_btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizplay);

        submit=findViewById(R.id.submit_id);
        toolbar=findViewById(R.id.toolbar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        question_no=findViewById(R.id.question_no_id);
        title_text=findViewById(R.id.title_id);
        opt1_text=findViewById(R.id.opt1_id);
        opt2_text=findViewById(R.id.opt2_id);
        opt3_text=findViewById(R.id.opt3_id);
        opt4_text=findViewById(R.id.opt4_id);
        preview=findViewById(R.id.preview_id);
        next=findViewById(R.id.next_id);
        timer=getIntent().getStringExtra("timer");
        radioGroup=findViewById(R.id.radio_group_id);
        attempt_text=findViewById(R.id.attempt_id);


        id=getIntent().getStringExtra("id");
        set=getIntent().getStringExtra("test");
        question=getIntent().getStringExtra("question");
        attempt=getIntent().getStringExtra("attempt");

        attempt_text.setText(attempt+" Attempt");



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(QuizplayActivity.this);
                View bottomview= LayoutInflater.from(QuizplayActivity.this).inflate(R.layout.back_dailog,((Activity)QuizplayActivity.this).findViewById(R.id.relative_id));
                dialog.setContentView(bottomview);

                yes_btn=bottomview.findViewById(R.id.yes_id);
                no_btn=bottomview.findViewById(R.id.no_id);
                no_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuizplayActivity.this.finish();
                    }
                });
                dialog.show();
            }
        });


        create_easydb();
        easyDB.deleteAllDataFromTable();
        category_quiz();


         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {
                 int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                 if(selectedRadioButtonId!=-1)
                 {
                     next.setVisibility(View.VISIBLE);
                 }
                 else
                 {
                     next.setVisibility(View.INVISIBLE);
                 }
             }
         });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizlistmodalArrayList.size()-1>=count)
                {
                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (selectedRadioButtonId != -1) {
                        selectedRadioButton = findViewById(selectedRadioButtonId);
                        String selectedRbText = selectedRadioButton.getText().toString();
                        if(quizlistmodalArrayList.get(count).getAns().equals(selectedRbText))
                        {
                            add_data(count+1,selectedRbText);
                            radioGroup.clearCheck();
                            check_ans();
                            listdata();
                            store_data();
                        }
                        else
                        {
                            Toast.makeText(context,"Your answer is wrong",Toast.LENGTH_LONG).show();
                            QuizplayActivity.this.finish();
                            Intent intent=new Intent(context,QuizplayActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("test",set);
                            intent.putExtra("question",question);
                            intent.putExtra("attempt",String.valueOf(Integer.valueOf(attempt)+1));
                            startActivity(intent);
                        }

                    }
                }
//                Intent intent=new Intent(QuizplayActivity.this,ResultActivity.class);
//                startActivity(intent);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.GONE);
                if(quizlistmodalArrayList.size()-2>=count)
                {
                    if(quizlistmodalArrayList.size()-2==count)
                    {
                        submit.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        submit.setVisibility(View.INVISIBLE);
                    }
                    next_list();
                }
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count!=0)
                {
                    if(quizlistmodalArrayList.size()==count)
                    {
                        submit.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        submit.setVisibility(View.INVISIBLE);
                    }
                    preview_list();
                }
            }
        });
//        summary_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                summary_question_list();
//            }
//        });

    }

    private void category_quiz()
    {
        total_question=question;
        total_time="4:30";
        if(set.equals("Math test 1"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","What is 2 + 7?","3","9","5","4","9",1));
            quizlistmodalArrayList.add(new Quizplay("2","What is 2 X 5","9","11","10","12","10",2));
            quizlistmodalArrayList.add(new Quizplay("3","What is 7 - 3","4","3","2","1","4",3));
            quizlistmodalArrayList.add(new Quizplay("4","What is 5 + 4","9","8","7","6","9",4));
        }
        if(set.equals("Math test 2"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","What is 10 + 5?","10","11","12","15","15",1));
            quizlistmodalArrayList.add(new Quizplay("2","What is 3 X 2?","6","5","4","3","6",2));
            quizlistmodalArrayList.add(new Quizplay("3","What is 7 - 3?","4","3","2","1","4",3));
            quizlistmodalArrayList.add(new Quizplay("4","What is 5 + 4?","9","8","7","6","9",4));
        }
        if(set.equals("English test 1"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","In follwing option check the vowel?","B","C","D","E","E",1));
            quizlistmodalArrayList.add(new Quizplay("2","In follwing option check the consonants?","A","B","E","O","B",2));
            quizlistmodalArrayList.add(new Quizplay("3","Synonyms of happy?","sad","enjoy","break","hard","enjoy",3));
            quizlistmodalArrayList.add(new Quizplay("4","Synonyms of famous?","fail","popular","sad","happy","popular",4));
        }
        if(set.equals("English test 2"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","In follwing option check the vowel?","B","C","D","E","E",1));
            quizlistmodalArrayList.add(new Quizplay("2","In follwing option check the consonants?","A","B","E","O","B",2));
            quizlistmodalArrayList.add(new Quizplay("3","Synonyms of happy?","sad","enjoy","break","hard","enjoy",3));
            quizlistmodalArrayList.add(new Quizplay("4","Synonyms of famous?","fail","popular","sad","happy","popular",4));
        }
        if(set.equals("Physics test 1"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","Ohm is a unit of measuring _________?","Resistance","Voltage","Current","None of the above","Resistance",1));
            quizlistmodalArrayList.add(new Quizplay("2","Which among the following temperature scale is based upon absolute zero?","Celsius","Fahrenheit","Kelvin","Rankine","Kelvin",2));
            quizlistmodalArrayList.add(new Quizplay("3","Which among the following provides potential energy to an object?"," Its momentum","It’s position","It’s acceleration","It’s shape","It’s position",3));
            quizlistmodalArrayList.add(new Quizplay("4","On heating a pure silicon circular disc with a circular hole at the centre, the diameter of the hole:?","will expand","will contract","will remain constant","may expand or contract","will contract",4));
        }
        if(set.equals("Physics test 2"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","Ohm is a unit of measuring _________?","Resistance","Voltage","Current","None of the above","Resistance",1));
            quizlistmodalArrayList.add(new Quizplay("2","Which among the following temperature scale is based upon absolute zero?","Celsius","Fahrenheit","Kelvin","Rankine","Kelvin",2));
            quizlistmodalArrayList.add(new Quizplay("3","Which among the following provides potential energy to an object?"," Its momentum","It’s position","It’s acceleration","It’s shape","It’s position",3));
            quizlistmodalArrayList.add(new Quizplay("4","On heating a pure silicon circular disc with a circular hole at the centre, the diameter of the hole:?","will expand","will contract","will remain constant","may expand or contract","will contract",4));
        }
        if(set.equals("Computer test 1"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","The capability to perform multiple tasks simultaneously is termed as ....?","Diligence","Versatility","Reliability","All of the above","Versatility",1));
            quizlistmodalArrayList.add(new Quizplay("2","Second generation computers were based on ....?","IC","Vacuum tube","transister","None of the above","transister",2));
            quizlistmodalArrayList.add(new Quizplay("3","Third generation computers were based on ......?","IC","Vacuum tube","transister","None of the above","IC",3));
            quizlistmodalArrayList.add(new Quizplay("4","Father of modern computer?","Charles Babbage","Alan Turing","Ted Hoff","None of the above","Alan Turing",4));
        }
        if(set.equals("Computer test 2"))
        {
            quizlistmodalArrayList.add(new Quizplay("1","The capability to perform multiple tasks simultaneously is termed as ....?","Diligence","Versatility","Reliability","All of the above","Versatility",1));
            quizlistmodalArrayList.add(new Quizplay("2","Second generation computers were based on ....?","IC","Vacuum tube","transister","None of the above","transister",2));
            quizlistmodalArrayList.add(new Quizplay("3","Third generation computers were based on ......?","IC","Vacuum tube","transister","None of the above","IC",3));
            quizlistmodalArrayList.add(new Quizplay("4","Father of modern computer?","Charles Babbage","Alan Turing","Ted Hoff","None of the above","Alan Turing",4));
        }
        question_no.setText("Que."+String.valueOf(count+1));
        title_text.setText(quizlistmodalArrayList.get(count).getQuestion());
        opt1_text.setText(quizlistmodalArrayList.get(count).getOpt1());
        opt2_text.setText(quizlistmodalArrayList.get(count).getOpt2());
        opt3_text.setText(quizlistmodalArrayList.get(count).getOpt3());
        opt4_text.setText(quizlistmodalArrayList.get(count).getOpt4());

        counter=counter+1;
        questionlistAdapter=new Quizplay_Adapter(context,quizlistmodalArrayList,radioGroup);
        retrieve_data();
    }

    private void retrieve_data()
    {
        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            String item_id = res.getString(1);
            String question_string[]=question_no.getText().toString().split("Que.");
            if(item_id.equals(quizlistmodalArrayList.get(Integer.parseInt(question_string[1])-1).getId())) {
                Integer ID = res.getInt(0);
                if(opt1_text.getText().equals(res.getString(3)))
                {
                    opt1_text.setChecked(true);
                }
                if(opt2_text.getText().equals(res.getString(3)))
                {
                    opt2_text.setChecked(true);
                }
                if(opt3_text.getText().equals(res.getString(3)))
                {
                    opt3_text.setChecked(true);
                }
                if(opt4_text.getText().equals(res.getString(3)))
                {
                    opt4_text.setChecked(true);
                }
            }
        }
    }
    public void store_data()
    {
        Intent intent=new Intent(QuizplayActivity.this,ResultActivity.class);
        intent.putExtra("total_question",total_question);
        intent.putExtra("attempt",attempt);
        startActivity(intent);
    }
    private void next_list()
    {
        if(quizlistmodalArrayList.size()-1>count)
        {
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedRbText = selectedRadioButton.getText().toString();
                if(quizlistmodalArrayList.get(count).getAns().equals(selectedRbText))
                {
                    add_data(count+1,selectedRbText);
                    radioGroup.clearCheck();
                }
                else
                {
                    Toast.makeText(context,"Your answer is wrong",Toast.LENGTH_LONG).show();
                    QuizplayActivity.this.finish();
                    Intent intent=new Intent(context,QuizplayActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("test",set);
                    intent.putExtra("question",question);
                    intent.putExtra("attempt",String.valueOf(Integer.valueOf(attempt)+1));
                    startActivity(intent);
                }

            } else {
            }
            count=count+1;
            question_no.setText("Que."+String.valueOf(count+1));
            title_text.setText(quizlistmodalArrayList.get(count).getQuestion());
            opt1_text.setText(quizlistmodalArrayList.get(count).getOpt1());
            opt2_text.setText(quizlistmodalArrayList.get(count).getOpt2());
            opt3_text.setText(quizlistmodalArrayList.get(count).getOpt3());
            opt4_text.setText(quizlistmodalArrayList.get(count).getOpt4());
            retrieve_data();
        }
    }
    private void preview_list()
    {
        if(count!=0)
        {
            count=count-1;
            question_no.setText("Que."+String.valueOf(count+1));
            title_text.setText(quizlistmodalArrayList.get(count).getQuestion());
            opt1_text.setText(quizlistmodalArrayList.get(count).getOpt1());
            opt2_text.setText(quizlistmodalArrayList.get(count).getOpt2());
            opt3_text.setText(quizlistmodalArrayList.get(count).getOpt3());
            opt4_text.setText(quizlistmodalArrayList.get(count).getOpt4());
            radioGroup.clearCheck();
            retrieve_data();
        }
    }
    public void create_easydb() {
        easyDB = EasyDB.init(QuizplayActivity.this, "Quizlist") // TEST is the name of the DATABASE
                .setTableName("questionlist")  // You can ignore this line if you want
                .addColumn(new Column("Itemid", new String[]{"text","unique"})) // Contrains like "text", "unique", "not null" are not case sensitive
                .addColumn(new Column("Itemans", new String[]{"text"}))
                .addColumn(new Column("Itemselect", new String[]{"text"}))
                .addColumn(new Column("Itemnumber", new String[]{"text"}))
                .doneTableColumn();
    }
    public void check_ans()
    {
        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            String ans = res.getString(2);
            String select_ans = res.getString(3);
            if(ans.equals(select_ans))
            {
                right_ans=right_ans+1;
            }
            else
            {
                wrong_ans=wrong_ans+1;
            }
        }
    }
    public void summary_question_list()
    {
        RecyclerView recyclerView;
        BottomSheetDialog dialog=new BottomSheetDialog(QuizplayActivity.this);
        View bottomview= LayoutInflater.from(QuizplayActivity.this).inflate(R.layout.summary_question,((Activity)QuizplayActivity.this).findViewById(R.id.relative_id));
        recyclerView=bottomview.findViewById(R.id.recyclerview_question_id);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
       // questionlistAdapter=new QuestionlistAdapter(context,quizlistmodalArrayList,radioGroup);
       // recyclerView.setAdapter(questionlistAdapter);
        dialog.setContentView(bottomview);
        dialog.show();
    }

    private void add_data(int count, String selectradioid)
    {
        Cursor res = easyDB.getAllData();
        Toast.makeText(context,String.valueOf(res.getCount()),Toast.LENGTH_LONG).show();
        if(res.getCount()==0)
        {
            Toast.makeText(context,String.valueOf(count),Toast.LENGTH_LONG).show();
            boolean done = easyDB.addData("Itemid", quizlistmodalArrayList.get(count-1).getId())
                    .addData("Itemans", quizlistmodalArrayList.get(count-1).getAns())
                    .addData("Itemselect",selectradioid)
                    .addData("Itemnumber",quizlistmodalArrayList.get(count-1).getCounter())
                    .doneDataAdding();
        }
        else
        {
            while (res.moveToNext())
            {
                String item_id = res.getString(1);
                int row_id = res.getInt(0);
                if(item_id.equals(String.valueOf(count)))
                {
                    boolean updated = easyDB.updateData(3, selectradioid)
                            .rowID(row_id);
                }
                else
                {
                    boolean done = easyDB.addData("Itemid", quizlistmodalArrayList.get(count-1).getId())
                            .addData("Itemans", quizlistmodalArrayList.get(count-1).getAns())
                            .addData("Itemselect",selectradioid)
                            .addData("Itemnumber",quizlistmodalArrayList.get(count-1).getCounter())
                            .doneDataAdding();
                }
            }
        }
    }
    public void listdata()
    {
        Cursor res = easyDB.getAllData();
        while (res.moveToNext())
        {
            listitems.add(new Listitem(String.valueOf(res.getInt(1)),res.getString(2),res.getString(3),res.getColumnName(4)));
        }
        json=new Gson().toJson(listitems);
    }

    @Override
    public void onBackPressed() {
        Dialog dialog=new Dialog(QuizplayActivity.this);
        View bottomview= LayoutInflater.from(QuizplayActivity.this).inflate(R.layout.back_dailog,((Activity)QuizplayActivity.this).findViewById(R.id.relative_id));
        dialog.setContentView(bottomview);

        yes_btn=bottomview.findViewById(R.id.yes_id);
        no_btn=bottomview.findViewById(R.id.no_id);
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizplayActivity.this.finish();
            }
        });
        dialog.show();
        //super.onBackPressed();
    }



}