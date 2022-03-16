package com.zcorridor.expensetracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Expenseadapter extends RecyclerView.Adapter<Expenseadapter.ViewHolderclass> {
    ArrayList<Expensemodalclass> listobj;
    Context context;

    public Expenseadapter(ArrayList<Expensemodalclass> listobj, Context context) {
        this.listobj = listobj;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolderclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.expensrows,viewGroup,false);
        return new ViewHolderclass(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderclass viewHolderclass, int i) {
        DecimalFormat decimalFormat=new DecimalFormat("#,###.##");
        Expensemodalclass exaddmodalclass = listobj.get(i);
         viewHolderclass.tvexdes.setText(exaddmodalclass.getDescription());
         viewHolderclass.tvexamount.setText(decimalFormat.format(exaddmodalclass.getAmount())+"");
        viewHolderclass.tvexdate.setText(exaddmodalclass.getDate());
        viewHolderclass.tvfirstchar.setText(exaddmodalclass.getDescription().charAt(0)+"");
    }

    @Override
    public int getItemCount() {
        return listobj.size();
    }


    public class ViewHolderclass extends RecyclerView.ViewHolder {
        TextView tvexdes,tvexamount,tvexdate,tvfirstchar;

        public ViewHolderclass(@NonNull View itemView) {
            super(itemView);
            tvexdes = itemView.findViewById(R.id.tvexpensedes);
            tvexamount = itemView.findViewById(R.id.tvexpenseamount);
            tvexdate = itemView.findViewById(R.id.tvexpensdate);
            tvfirstchar = itemView.findViewById(R.id.tvfirstchr);
        }
    }
}
