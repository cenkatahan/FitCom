package com.FitCom.fitcomapplication.TraineeScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class RecAdaptorTrainers extends RecyclerView.Adapter<RecAdaptorTrainers.TrainerHolder> {

    private ArrayList<String> names;
    private ArrayList<String> emails;
    private View view;
    private FirebaseFirestore firebaseFirestore;

    public RecAdaptorTrainers(ArrayList<String> names, ArrayList<String> emails) {
        this.names = names;
        this.emails = emails;
    }

    @NonNull
    @Override
    public TrainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.rec_row_trainers, parent, false);
        return new TrainerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerHolder holder, int position) {
        holder.rec_trainer_name.setText(names.get(position));
        holder.rec_trainer_email.setText(emails.get(position));
        holder.itemView.setOnClickListener(v -> {
            clickToSendEMail(v,position);
        });
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public void clickToSendEMail(View view, int position){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emails.get(position)});
        i.putExtra(Intent.EXTRA_SUBJECT, "Mail Subject");
        i.putExtra(Intent.EXTRA_TEXT   , "message");
        i.setPackage("com.google.android.gm");
        ActivityManager am = (ActivityManager) view.getContext().getSystemService(Activity.ACTIVITY_SERVICE);

        try {
            if (i.resolveActivity(view.getContext().getPackageManager()) != null) {
                if(am != null){
                    am.killBackgroundProcesses("com.google.android.gm");
                    i.removeExtra(Intent.EXTRA_EMAIL);
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emails.get(position)});
                    view.getContext().startActivity(i);
                }
                view.getContext().startActivity(i);
            }else {
                i.setType("text/plain");
                i.setData(Uri.parse("mailto:"));
                i.removeExtra(Intent.EXTRA_EMAIL);
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emails.get(position)});
                i.putExtra(Intent.EXTRA_SUBJECT, "Mail Subject");
                i.putExtra(Intent.EXTRA_TEXT   , "massage");
                i.setPackage("com.google.android.gm");
                //ActivityManager am = (ActivityManager) view.getContext().getSystemService(Activity.ACTIVITY_SERVICE);
                //am.killBackgroundProcesses("com.google.android.gm");
                view.getContext().startActivity(i);
            }
            //System.out.println("ADAPTER CLICKED");
            } catch (android.content.ActivityNotFoundException ex) {}
    }

    public class TrainerHolder extends RecyclerView.ViewHolder{

        private TextView rec_trainer_name, rec_trainer_email;

        public TrainerHolder(@NonNull View itemView) {
            super(itemView);

            rec_trainer_email = itemView.findViewById(R.id.rec_trainer_email);
            rec_trainer_name = itemView.findViewById(R.id.rec_trainer_name);
        }
    }
}
