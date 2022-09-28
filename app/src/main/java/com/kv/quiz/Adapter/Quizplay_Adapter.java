package com.kv.quiz.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kv.quiz.R;
import com.kv.quiz.modal.Quizplay;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class Quizplay_Adapter extends RecyclerView.Adapter<Quizplay_Adapter.ViewHolder> {

    Context context;
    ArrayList<Quizplay> questionlistmodalArrayList=new ArrayList<>();
    RadioGroup radioGroup;
    EasyDB easyDB;

    public Quizplay_Adapter(Context context, ArrayList<Quizplay> questionlistmodalArrayList,RadioGroup radioGroup) {
        this.context = context;
        this.questionlistmodalArrayList = questionlistmodalArrayList;
        this.radioGroup=radioGroup;
        easyDB = EasyDB.init(context, "Quizlist") // TEST is the name of the DATABASE
                .setTableName("questionlist")  // You can ignore this line if you want
                .addColumn(new Column("Itemid", new String[]{"text", "unique"})) // Contrains like "text", "unique", "not null" are not case sensitive
                .addColumn(new Column("Itemans", new String[]{"text"}))
                .addColumn(new Column("Itemselect", new String[]{"text"}))
                .addColumn(new Column("Itemnumber", new String[]{"text"}))
                .doneTableColumn();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.question_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(String.valueOf(questionlistmodalArrayList.get(position).getCounter()));
        retrieved_data(holder,position);
    }

    @Override
    public int getItemCount() {
        return questionlistmodalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        ImageView circle_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question_id);
            circle_view=itemView.findViewById(R.id.image_circle_id);
        }
    }
    private void retrieved_data(ViewHolder holder,int position)
    {
        Cursor res = easyDB.getAllData();
        Integer value=questionlistmodalArrayList.get(position).getCounter();
        while (res.moveToNext()) {
            String item_id = res.getString(1);
            Integer item_int=Integer.parseInt(item_id);

            if(item_int.equals(value))
            {
                holder.circle_view.setBackgroundResource(R.drawable.answer_circle_design);
            }
        }
    }
}
