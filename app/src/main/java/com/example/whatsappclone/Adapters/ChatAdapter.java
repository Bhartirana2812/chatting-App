package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    int Sender_View_Type =1;
    int reciever_View_Type =2;

    public ChatAdapter(ArrayList<MessageModel> messageModel, Context context) {
        this.messageModels = messageModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==Sender_View_Type)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sendersample,parent,false);
            return  new SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.samplereciever,parent,false);
            return  new RecieverViewHolder(view);


        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return Sender_View_Type;
        }
        else{
            return reciever_View_Type;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel =messageModels.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).sendertext.setText(messageModel.getMessage());
        }
        else{
            ((RecieverViewHolder)holder).recievertext.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView recievertime, recievertext;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recievertext = itemView.findViewById(R.id.recievertext);
            recievertime = itemView.findViewById(R.id.recievertime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sendertime, sendertext;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sendertext = itemView.findViewById(R.id.sendertext);
            sendertime = itemView.findViewById(R.id.sendertime);

        }

    }
}
