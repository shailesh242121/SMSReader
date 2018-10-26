package com.smsreader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smsreader.R;
import com.smsreader.model.SMSModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.ViewHolder> {
    private List<SMSModel> list = new ArrayList<>();

    public SMSAdapter(List<SMSModel> smsList) {
        list = smsList;
    }

    @NonNull
    @Override
    public SMSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sms, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMSAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setItem(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sender)
        TextView tvSender;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_message)
        TextView tvMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setItem(int position) {
            SMSModel model = list.get(position);
            tvSender.setText(model.getSender());
            tvTime.setText(model.getTime());
            tvMessage.setText(model.getMessage());

        }
    }
}
