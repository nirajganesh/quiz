package com.kv.quiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kv.quiz.QuizplayActivity;
import com.kv.quiz.R;
import com.kv.quiz.modal.Categories_list;

import java.util.ArrayList;

public class Categories_list_Adapter extends RecyclerView.Adapter<Categories_list_Adapter.Viewholder> {

    Context context;
    ArrayList<Categories_list> categories_lists=new ArrayList<>();

    public Categories_list_Adapter(Context context, ArrayList<Categories_list> categories_lists) {
        this.context = context;
        this.categories_lists = categories_lists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.quiz_listview,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.title.setText(categories_lists.get(position).getName());
        holder.text_time.setText(categories_lists.get(position).getTime()+" Min.");
        holder.text_quiz.setText(categories_lists.get(position).getQuestion()+" Ques.");
        holder.start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, QuizplayActivity.class);
                intent.putExtra("id",categories_lists.get(position).getId());
                intent.putExtra("test",categories_lists.get(position).getName());
                intent.putExtra("question",categories_lists.get(position).getQuestion());
                intent.putExtra("attempt","1");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories_lists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title,price_status,text_quiz,text_time;
        Button start_btn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_id);
            price_status=itemView.findViewById(R.id.price_id);
            text_quiz=itemView.findViewById(R.id.text_ques_id);
            text_time=itemView.findViewById(R.id.time_taken_id);
            start_btn=itemView.findViewById(R.id.start_id);
        }
    }
}
