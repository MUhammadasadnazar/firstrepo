package com.zcorridor.expensetracker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Transactionadapter extends RecyclerView.Adapter<Transactionadapter.ViewHolder> {
    Context context;
    ArrayList<TransactionModalClass> list;

    public Transactionadapter(Context context, ArrayList<TransactionModalClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Transactionadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.transactionrows,viewGroup,false);
        return new Transactionadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Transactionadapter.ViewHolder viewHolder, int i) {
        TransactionModalClass transactionModalClass = list.get(i);
        viewHolder.tvtype.setText(transactionModalClass.getTranstype()+"");
        if (transactionModalClass.getTranstype().equals("Added")){
             viewHolder.imageView.setImageResource(R.drawable.ic_baseline_trending_down_24);
        }
        if (transactionModalClass.getTranstype().equals("Withdraw")){
             viewHolder.imageView.setImageResource(R.drawable.ic_baseline_trending_up_24);
        }
         viewHolder.tvname.setText(transactionModalClass.getGoalname()+"");
        viewHolder.tvamnt.setText(transactionModalClass.getTransamnt()+"");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MMM/yyyy");
         Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(transactionModalClass.getDate()));
        simpleDateFormat.format(calendar.getTime());
        try {
            viewHolder.tvdate.setText(simpleDateFormat.format(calendar.getTime())+"");

        }
        catch (Exception e){
            viewHolder.tvdate.setText(e.getMessage()+"");

        }

        //         DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
 //        viewHolder.tvtransfchar.setText(viewHolder.tvname.getText().charAt(0)+"");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvamnt,tvtype,tvname,tvdate,tvtransfchar;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvamnt = itemView.findViewById(R.id.tvgoalamntrc);
            tvdate = itemView.findViewById(R.id.tvgoaldaterc);
            tvname = itemView.findViewById(R.id.tvgoalnamerc);
            tvtype = itemView.findViewById(R.id.tvgoaltyperc);
//            tvtransfchar = itemView.findViewById(R.id.tvttransfirstchr);
            imageView=itemView.findViewById(R.id.ivtrans);
        }
    }
}
