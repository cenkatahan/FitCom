package com.FitCom.fitcomapplication.ExerciseScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecAdaptorPrograms extends RecyclerView.Adapter<RecAdaptorPrograms.ProgramPlaceHolder> {

    ArrayList<String> programTitles;
    private ArrayList<String> programImgs;

    public RecAdaptorPrograms(ArrayList<String> programTitles, ArrayList<String> programImgs) {
        this.programTitles = programTitles;
        this.programImgs = programImgs;
    }

    @NonNull
    @Override
    public ProgramPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_row_programs, parent, false);
        return new ProgramPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramPlaceHolder holder, int position) {

        holder.programTitle.setText(programTitles.get(position));
        Picasso.get().load(programImgs.get(position)).into(holder.programImg);
        holder.itemView.setOnClickListener(v -> {
            ProgramListFragmentDirections.ActionProgramListFragmentToProgramDetailFragment navDir = ProgramListFragmentDirections.actionProgramListFragmentToProgramDetailFragment(position);
            navDir.setProgramId(position);
            Navigation.findNavController(v).navigate(navDir);
        });
    }

    @Override
    public int getItemCount() {
        return programTitles.size();
    }

    class ProgramPlaceHolder extends RecyclerView.ViewHolder{

        private TextView programTitle;
        private ImageView programImg;

        public ProgramPlaceHolder(@NonNull View itemView) {
            super(itemView);
            programTitle = itemView.findViewById(R.id.row_prg_title);
            programImg = itemView.findViewById(R.id.row_prg_img);
        }
    }
}
