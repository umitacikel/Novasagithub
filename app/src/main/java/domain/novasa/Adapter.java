package domain.novasa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;



public class Adapter extends RecyclerView.Adapter<Adapter.ObjtessHolder>{

    private Context context;
    private List<Objectss> list;

    public Adapter(Context context, List<Objectss> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ObjtessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_layout, null);
        ObjtessHolder objholder = new ObjtessHolder(view);
        return  objholder;
    }

    @Override
    public void onBindViewHolder(ObjtessHolder holder, int position) {
        Objectss objectss = list.get(position);

        holder.textid.setText(objectss.getId());
        holder.textproduct.setText(objectss.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ObjtessHolder extends RecyclerView.ViewHolder{
        TextView textid, textproduct;

        public ObjtessHolder(View itemView) {
            super(itemView);

            textid = (TextView) itemView.findViewById(R.id.textid);
            textproduct = (TextView) itemView.findViewById(R.id.textproduct);
        }
    }
}
