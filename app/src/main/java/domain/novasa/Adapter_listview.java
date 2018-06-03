package domain.novasa;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;



public class Adapter_listview extends ArrayAdapter<Objectss> {

    private Context context;
    private List<Objectss> object;

    public Adapter_listview(Context context, int resources, List<Objectss> objects)
    {
        super(context, resources, objects);
        this.context = context;
        this.object = objects;
    }



    @Override
    public View getView(int position, View conertview,  ViewGroup parent){
        Objectss obj = object.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listviewlay, null);

        TextView textid = (TextView) view.findViewById(R.id.listviewid);
        textid.setText(obj.getId());
        TextView textname = (TextView) view.findViewById(R.id.listviewname);
        textname.setText(obj.getName());

        return view;
    }

}
