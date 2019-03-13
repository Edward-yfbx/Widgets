package com.yfbx.widgets.adapter;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.yfbx.widgets.R;

import java.util.List;

/**
 * Author:Edward
 * Date:2018/10/22
 * Description:
 */

public class CarouselAdapter extends BaseJavaAdapter<String> {

    public CarouselAdapter(List<String> data) {
        super(data);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_carousel;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseJavaAdapter.XViewHolder holder, int position) {
        int newPos = position % data.size();
        holder.itemView.setTag(newPos);

        TextView textView = holder.itemView.findViewById(R.id.notice_txt);
        textView.setText(data.get(newPos));
    }


    @Override
    public int getItemCount() {
        return data == null || data.size() == 0 ? 0 : Integer.MAX_VALUE;
    }
}
