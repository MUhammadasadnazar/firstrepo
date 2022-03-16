package com.zcorridor.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Targetadapter extends RecyclerView.Adapter<Targetadapter.ViewHolder> {
    Context context;
    ArrayList<Targetmodalclass> list;

    public Targetadapter(Context context, ArrayList<Targetmodalclass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.targetrows,viewGroup,false);
        return new ViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final int x=i;
        DecimalFormat decimalFormat=new DecimalFormat("#,###.##");
        Targetmodalclass object = list.get(i);
        viewHolder.tvamntsaved.setText(decimalFormat.format(object.getAmountspent())+"");
        viewHolder.tvtargetremaining.setText(decimalFormat.format(object.getTargetamount()-object.getAmountspent())+"");
        viewHolder.tvdes.setText(object.getTargetdes()+"");
        viewHolder.tvtargettotalamnt.setText(decimalFormat.format(object.getTargetamount())+"");
        viewHolder.seekBar.setMax(object.targetamount);
        viewHolder.seekBar.setProgress(object.amountspent);
         viewHolder.imageView.setImageURI(Uri.parse(new File(object.getImagepath()).toString()));
//                imageView2.setImageURI(Uri.parse(new File(data1).toString()));


    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        SeekBar seekBar;
        TextView tvdes,tvamntsaved,tvtargetremaining,tvtargettotalamnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.ivtargetphoto);
            tvdes=itemView.findViewById(R.id.tvtargetdes);
            tvamntsaved=itemView.findViewById(R.id.tvtargetsaved);
            tvtargetremaining=itemView.findViewById(R.id.tvtargetremaining);
            tvtargettotalamnt=itemView.findViewById(R.id.tvtarettotalamnt);
            seekBar = itemView.findViewById(R.id.sekbar);
            seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(context,Showtargetdescription.class);
            intent.putExtra("des",list.get(getAdapterPosition()).getTargetdes());
            intent.putExtra("savedamnt",list.get(getAdapterPosition()).getAmountspent());
              intent.putExtra("total",list.get(getAdapterPosition()).getTargetamount());
             intent.putExtra("imgpath",list.get(getAdapterPosition()).getImagepath());
             intent.putExtra("days",list.get(getAdapterPosition()).getDays());
              context.startActivity(intent);
        }
    }
}
