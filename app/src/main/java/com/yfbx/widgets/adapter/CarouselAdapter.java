package com.yfbx.widgets.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfbx.widgets.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Edward
 * Date:2018/10/22
 * Description:
 */

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.Holder> implements View.OnClickListener {


    private List<String> data;
    private OnItemClickListener listener;

    public CarouselAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        view.setOnClickListener(this);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int newPos = position % data.size();

        holder.noticeTxt.setText(data.get(newPos));

        holder.itemView.setTag(newPos);
    }

    @Override
    public int getItemCount() {
        return data == null || data.size() == 0 ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int position = (int) view.getTag();
            listener.onItemClick(position, data.get(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, String item);
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.notice_txt)
        TextView noticeTxt;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
