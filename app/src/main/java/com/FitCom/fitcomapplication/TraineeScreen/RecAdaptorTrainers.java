package com.FitCom.fitcomapplication.TraineeScreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import java.util.ArrayList;

public class RecAdaptorTrainers extends RecyclerView.Adapter<RecAdaptorTrainers.TrainerHolder> {

    private ArrayList<String> names;
    private ArrayList<String> emails;
    private View view;

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
        i.putExtra(Intent.EXTRA_SUBJECT, view.getContext().getString(R.string.subj));
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
            }
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
