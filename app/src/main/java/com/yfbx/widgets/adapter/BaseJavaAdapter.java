package com.yfbx.widgets.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Edward
 * Date: 2019/3/13
 * Description:
 */
public abstract class BaseJavaAdapter<T> extends RecyclerView.Adapter<BaseJavaAdapter.XViewHolder> implements View.OnClickListener {

    protected List<T> data;
    private OnItemClickListener listener;

    public BaseJavaAdapter(List<T> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @LayoutRes
    public abstract int getItemLayout();

    @NonNull
    @Override
    public XViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        view.setOnClickListener(this);
        return new XViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseJavaAdapter.XViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int position = (int) view.getTag();
            listener.onItemClick(position, data.get(position));
        }
    }


    public interface OnItemClickListener {

        <T> void onItemClick(int position, T item);
    }

    class XViewHolder extends RecyclerView.ViewHolder {

        public XViewHolder(View itemView) {
            super(itemView);
        }
    }
}
