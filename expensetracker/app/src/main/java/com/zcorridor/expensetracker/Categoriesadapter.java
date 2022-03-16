package com.zcorridor.expensetracker;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Categoriesadapter extends RecyclerView.Adapter<Categoriesadapter.Viewholderclass> {
    ArrayList<Categoriesmodalclass> listobj;
    Context context;
    Intent intent;
    public Categoriesadapter(ArrayList<Categoriesmodalclass> listobj, Context context) {
        this.listobj = listobj;
        this.context = context;

    }
  public Categoriesadapter() {
    }
    @NonNull
    @Override
    public Viewholderclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoriesrows, viewGroup,false);
        return new Viewholderclass(view);
    }

//    @Override
    public void onBindViewHolder(@NonNull Viewholderclass viewHolder, int i) {
        Categoriesmodalclass expensesmodalclass = listobj.get(i);
        DecimalFormat decimalFormat=new DecimalFormat("#,###.##");
        viewHolder.tvbudgetplanned.setText(decimalFormat.format(expensesmodalclass.getBudgetplanned())+"");
             viewHolder.tvcategory.setText(expensesmodalclass.getCategory());
            viewHolder.tvbudgetspent.setText(decimalFormat.format(expensesmodalclass.getBudgetspent())+"");
            viewHolder.tvremaining.setText(decimalFormat.format(expensesmodalclass.budgetplanned-expensesmodalclass.getBudgetspent())+"");
            viewHolder.seekBar.setMax(expensesmodalclass.getBudgetplanned());
            viewHolder.seekBar.setProgress(expensesmodalclass.getBudgetspent());
            viewHolder.seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
     }
    @Override
    public int getItemCount() {
        return listobj.size();
    }


    public class Viewholderclass extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvbudgetspent,tvbudgetplanned,tvremaining,tvcategory;
        SeekBar seekBar;

        public Viewholderclass(@NonNull View itemView) {
            super(itemView);
            tvbudgetspent =  itemView.findViewById(R.id.tvbudgetspent);
            tvbudgetplanned =  itemView.findViewById(R.id.tvbudgetplanned);
            tvremaining =   itemView.findViewById(R.id.tvremaining);
            tvcategory=itemView.findViewById(R.id.tvlistcategory);
            seekBar = itemView.findViewById(R.id.sbcategories);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String cat = listobj.get(getLayoutPosition()).getCategory();
            int planed = listobj.get(+getLayoutPosition()).getBudgetplanned();
             Intent intent= new Intent(context, Expenselist.class);
            intent.putExtra("catname",cat);
            intent.putExtra("bgtplaned",planed);
             context.startActivity(intent);
        }
    }
}
