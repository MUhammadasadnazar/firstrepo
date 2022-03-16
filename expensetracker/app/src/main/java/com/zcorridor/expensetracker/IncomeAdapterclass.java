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

public class IncomeAdapterclass extends RecyclerView.Adapter<IncomeAdapterclass.ViewHolderClass> {
    Context context;
    ArrayList<Incomemodalclass> listobj;

    public IncomeAdapterclass(Context context, ArrayList<Incomemodalclass> listobj) {
        this.context = context;
        this.listobj = listobj;
    }

    @NonNull
    @Override
    public IncomeAdapterclass.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.incomerows,viewGroup,false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapterclass.ViewHolderClass viewHolderClass, int i) {
        Incomemodalclass incomemodalclass = listobj.get(i);
        DecimalFormat decim = new DecimalFormat("#,###.##");
        viewHolderClass.tvincomedescription.setText(incomemodalclass.getIncomedescription());
        viewHolderClass.tvincomerupees.setText(decim.format(incomemodalclass.getIncomeamount())+"");
        viewHolderClass.tvincomefirstchar.setText(incomemodalclass.incomedescription.charAt(0)+"");

    }

    @Override
    public int getItemCount() {
       return listobj.size();
     }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView tvincomedescription,tvincomerupees,tvincomefirstchar;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvincomedescription =itemView.findViewById(R.id.incomedescriptionn);
            tvincomerupees = itemView.findViewById(R.id.incomerupeess);
            tvincomefirstchar=itemView.findViewById(R.id.tvincomefirstchr);
        }
    }
}
