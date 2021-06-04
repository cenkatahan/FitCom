package com.FitCom.fitcomapplication.BlogScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import java.util.ArrayList;

@SuppressWarnings({"ALL", "FieldMayBeFinal", "CanBeFinal"})
public class RecAdaptorBlog extends RecyclerView.Adapter<RecAdaptorBlog.PlaceHolderBlog> {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> titles;

    public RecAdaptorBlog(ArrayList<String> titles) {
        this.titles = titles;
    }

    @NonNull
    @Override
    public PlaceHolderBlog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rec_row_blog, parent, false);
        return new PlaceHolderBlog(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolderBlog holder, int position) {
        holder.title.setText(titles.get(position));
        holder.itemView.setOnClickListener(v -> {
            ArticleListFragmentDirections.ActionArticleListFragmentToArticleDetailFragment navigation = ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailFragment(position);
            navigation.setArticleId(position);
            Navigation.findNavController(v).navigate(navigation);
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @SuppressWarnings("CanBeFinal")
    public class PlaceHolderBlog extends RecyclerView.ViewHolder{

        private TextView title;

        public PlaceHolderBlog(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rec_blog_title);
        }
    }
}
