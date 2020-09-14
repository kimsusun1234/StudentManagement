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
import com.example.mob1032_assignment.datamodel.StudentData;

import java.util.ArrayList;
import java.util.List;

public class StudentListView extends ArrayAdapter<StudentData>
{
    private Context context;
    private int resource;
    private List<StudentData> arrStudent;

    public StudentListView(@NonNull Context context, int resource, @NonNull List<StudentData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrStudent = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_custom_listview_student, parent,false);

        TextView txtID = convertView.findViewById(R.id.txtStudentID);
        TextView txtName = convertView.findViewById(R.id.txtStudentName);
        TextView txtBirth = convertView.findViewById(R.id.txtBirth);
        TextView txtClass = convertView.findViewById(R.id.txtStudentClass);

        StudentData studentData = arrStudent.get(position);

        txtID.setText(studentData.getId());
        txtName.setText(studentData.getName());
        txtBirth.setText(studentData.getDateOfBirth());
        txtClass.setText(studentData.getClassID());

        return convertView;
    }
}
