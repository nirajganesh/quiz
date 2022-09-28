package com.kv.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    String total,attempt_text;
    TextView total_text,currect_ans,wrong_ans,attempt;
    Button quiz_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        total_text=findViewById(R.id.total_que_id);
        currect_ans=findViewById(R.id.currect_ans_id);
        wrong_ans=findViewById(R.id.wrong_ans_id);
        quiz_stop=findViewById(R.id.new_quiz_id);
        attempt=findViewById(R.id.attemp_id_1);
        total=getIntent().getStringExtra("total_question");
        attempt_text=getIntent().getStringExtra("attempt");

        total_text.setText(total);
        currect_ans.setText(total);
        wrong_ans.setText("0");
        attempt.setText(attempt_text);

        quiz_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent=new Intent(ResultActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}