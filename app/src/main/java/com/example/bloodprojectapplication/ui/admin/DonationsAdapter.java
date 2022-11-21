package com.example.bloodprojectapplication.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodprojectapplication.R;
import com.example.bloodprojectapplication.model.Notification;

import java.util.List;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.Viewholder> {

    Context context;
    List<Notification> notifications;

    public DonationsAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public DonationsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_donation_item, parent, false);
        return new DonationsAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationsAdapter.Viewholder holder, int position) {

        Notification notification = notifications.get(position);
        String message;
        if (!notification.getRecipient().equals("empty")) {
            message = notification.getUserName() + ", of blood group "
                    + notification.getBloodGroup() + " donated to " + notification.getRecipient()
                    + ".";
        } else {
            message = notification.getUserName() + ", of blood group "
                    + notification.getBloodGroup() + " wants to donate blood.";
        }


        holder.message.setText(message);
        holder.timeStamp.setText(notification.getTimeStamp());
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView timeStamp;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notification_message);
            timeStamp = itemView.findViewById(R.id.notification_timestamp);
        }
    }
}