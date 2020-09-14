package com.example.mob1032_assignment.custom_list_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob1032_assignment.R;
import com.example.mob1032_assignment.datamodel.ClassData;
import com.example.mob1032_assignment.datamodel.StudentData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ClassListView extends ArrayAdapter<ClassData> {

    private Context context ;
    private int resource;
    private List<ClassData> arrClass;

    public ClassListView(@NonNull Context context, int resource, List<ClassData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrClass = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_custom_listview_class, parent, false);
        //ánh xạ
        TextView txtClassID = convertView.findViewById(R.id.txtClassID);
        TextView txtClassName = convertView.findViewById(R.id.txtClassName);

        //Tạo đối tượng kiểu <ClassData>
        ClassData classData = arrClass.get(position);
        txtClassID.setText(classData.getId());
        txtClassName.setText(classData.getName());
        return convertView;
    }
}
