package com.example.mob1032_assignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob1032_assignment.dao.ClassDataDAO;
import com.example.mob1032_assignment.dao.StudentDataDAO;
import com.example.mob1032_assignment.R;
import com.example.mob1032_assignment.custom_list_view.StudentListView;
import com.example.mob1032_assignment.datamodel.ClassData;
import com.example.mob1032_assignment.datamodel.StudentData;

import java.util.ArrayList;
import java.util.List;

public class StudentManagement extends AppCompatActivity {

    private List<StudentData> studentDataList = null;
    private List<ClassData> classDataList = null;
    private ListView lstStudent = null;
    private EditText etStudentID = null;
    private EditText etStudentName = null;
    private Spinner spDay = null;
    private Spinner spMonth = null;
    private Spinner spYear = null;
    private Spinner spStudentClass = null;
    private Button btnAddStudent = null;
    private TextView txtIDDialog = null;
    private EditText etNameDialog = null;
    private Spinner spDayDialog = null;
    private Spinner spMonthDialog = null;
    private Spinner spYearDialog = null;
    private Spinner spClassDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management);
        mapping();
//        testCustomAdapter();
        getData();
        btnAddStudentOnClick();
        displayData();
        displayClassToSpinner(spStudentClass);
        displayDateToSpinner(spDay,spMonth, spYear);
        itemListViewOnClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.itemClassManagement:
            {
                Intent intent = new Intent(StudentManagement.this, ClassManagement.class);
                startActivity(intent);
                return true;
            }

            case R.id.itemExit:
            {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            default: return super.onOptionsItemSelected(item);
        }


    }

    private void mapping()
    {
        lstStudent = findViewById(R.id.lstStudent);
        etStudentID = findViewById(R.id.etStudentID);
        etStudentName = findViewById(R.id.etStudentName);
        spDay = findViewById(R.id.spDay);
        spMonth = findViewById(R.id.spMonth);
        spYear = findViewById(R.id.spYear);
        spStudentClass = findViewById(R.id.spStudentClass);
        btnAddStudent = findViewById(R.id.btnAddStudent);

    }

/*    private void testCustomAdapter()
    {
        StudentData studentData = new StudentData();
        ArrayList<StudentData> arrStudent = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            studentData.setId("TE " + i);
            studentData.setName("ABCBCA " + i);
            studentData.setDateOfBirth("DD/MM/YYYY " + i);
            studentData.setClassID("ABDBDA " + i);
            arrStudent.add(studentData);
        }

        StudentListView ls = new StudentListView(StudentManagement.this, R.layout.layout_custom_listview_student, arrStudent);
        lstStudent.setAdapter(ls);
    }*/

    private void btnAddStudentOnClick()
    {

        if (btnAddStudent == null)
        {
            mapping();
        }
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validForm())
                {
                    StudentDataDAO studentDataDAO = new StudentDataDAO(StudentManagement.this);
                    String date = spYear.getSelectedItem().toString() +
                            "/" + spMonth.getSelectedItem().toString() +
                            "/" + spDay.getSelectedItem().toString();
                    long recordOrder = studentDataDAO.insert(etStudentID.getText().toString(),
                            etStudentName.getText().toString(),
                            date,
                            spStudentClass.getSelectedItem().toString());
                    Toast.makeText(StudentManagement.this, "Added Successfully! Record Order: " + recordOrder, Toast.LENGTH_SHORT).show();

                    etStudentID.setText("");
                    etStudentName.setText("");
                    spStudentClass.setSelection(0, true);

                    studentDataList.clear();
                    classDataList.clear();
                    getData();
                    displayData();
                }
            }
        });
    }

    private void getData()
    {
        StudentDataDAO studentDataDAO = new StudentDataDAO(StudentManagement.this);
        ClassDataDAO classDataDAO = new ClassDataDAO(StudentManagement.this);
        studentDataList = studentDataDAO.selectAll();
        classDataList = classDataDAO.selectAll();
        if (classDataList.size() == 0)
        {
            Toast.makeText(this, "There's no class existed. Add classes first!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentManagement.this, ClassManagement.class);
            startActivity(intent);
        }
    }

    private void displayData()
    {
        StudentListView adapter = new StudentListView(StudentManagement.this, R.layout.layout_custom_listview_student, studentDataList);
        lstStudent.setAdapter(adapter);
    }


    //Có tham số để dùng cho Spinner trong Dialog nữa
    private void displayClassToSpinner(Spinner studentClass)
    {
        if (studentClass == null)
        {
            mapping();
        }
        List<String> classArr = new ArrayList<>();
        for (int i = 0; i < classDataList.size(); i++)
        {
            classArr.add(classDataList.get(i).getId());
        }
        ArrayAdapter<ClassData> adapter = new ArrayAdapter(StudentManagement.this, android.R.layout.simple_spinner_item, classArr);
        studentClass.setAdapter(adapter);
    }

    private void displayDateToSpinner(Spinner day, Spinner month, Spinner year)
    {
        if (day == null || month == null || year == null)
        {
            mapping();
        }
        List<String> DayArr = new ArrayList<>();
        List<String> MonthArr = new ArrayList<>();
        List<String> YearArr = new ArrayList<>();

        for (int i = 1950; i <= 2050; i++)
        {
            YearArr.add("" + i);
        }
        year.setAdapter(new ArrayAdapter(StudentManagement.this, android.R.layout.simple_spinner_item, YearArr));

        for (int i = 1; i <= 12; i++)
        {
            if (i < 10)
            {
                MonthArr.add("0" + i);
            }
            else
            {
                MonthArr.add("" + i);
            }

        }
        month.setAdapter(new ArrayAdapter(StudentManagement.this, android.R.layout.simple_spinner_item, MonthArr));

        for (int i = 1; i <= 31; i++)
        {
            if (i<10)
            {
                DayArr.add("0" + i);
            }
            else
            {
                DayArr.add("" + i);
            }

        }
        day.setAdapter(new ArrayAdapter(StudentManagement.this, android.R.layout.simple_spinner_item, DayArr));
    }


    private boolean validForm()
    {
        if (etStudentID.getText().toString().equals(""))
        {
            Toast.makeText(StudentManagement.this, "Please enter ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 0; i < studentDataList.size(); i++)
        {
            if(etStudentID.getText().toString().equalsIgnoreCase(studentDataList.get(i).getId()))
            {
                Toast.makeText(StudentManagement.this, "ID has already exited!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (etStudentName.getText().toString().equals(""))
        {
            Toast.makeText(StudentManagement.this, "Please enter name of the Student", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void itemListViewOnClick()
    {

        if (lstStudent == null)
        {
            mapping();
        }
        lstStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String CURRENT_ID = studentDataList.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentManagement.this);

                LayoutInflater inflater = getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.layout_student_dialog, null));


                builder.setPositiveButton(R.string.update_button, null);

                builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNeutralButton(R.string.delete_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(StudentManagement.this);

                        confirmBuilder.setTitle(R.string.delete_confirm);
                        confirmBuilder.setMessage(R.string.delete_message);

                        confirmBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StudentDataDAO studentDataDAO = new StudentDataDAO(StudentManagement.this);
                                studentDataDAO.delete(CURRENT_ID);
                                Toast.makeText(StudentManagement.this, "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                                studentDataList.clear();
                                classDataList.clear();
                                getData();
                                displayData();
                            }
                        });

                        confirmBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialogDelete = confirmBuilder.create();
                        alertDialogDelete.show();

                    }
                });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button btnUpdate = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etNameDialog.getText().toString().equals(""))
                        {
                            String date = spYearDialog.getSelectedItem().toString() +
                                    "/" + spMonthDialog.getSelectedItem().toString() +
                                    "/" + spDayDialog.getSelectedItem().toString();

                            StudentDataDAO studentDataDAO = new StudentDataDAO(StudentManagement.this);
                            studentDataDAO.update(CURRENT_ID,
                                    etNameDialog.getText().toString(),
                                    date,
                                    spClassDialog.getSelectedItem().toString());
                            Toast.makeText(StudentManagement.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                            studentDataList.clear();
                            classDataList.clear();
                            getData();
                            displayData();
                            alertDialog.cancel();
                        }
                        else
                        {
                            Toast.makeText(StudentManagement.this, "Please enter the name!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                txtIDDialog = alertDialog.findViewById(R.id.txtIDDialog);
                etNameDialog = alertDialog.findViewById(R.id.etStudentNameDialog);
                spDayDialog = alertDialog.findViewById(R.id.spDayDialog);
                spMonthDialog = alertDialog.findViewById(R.id.spMonthDialog);
                spYearDialog = alertDialog.findViewById(R.id.spYearDialog);
                spClassDialog = alertDialog.findViewById(R.id.spStudentClassDialog);

                txtIDDialog.setText(studentDataList.get(position).getId());
                etNameDialog.setText(studentDataList.get(position).getName());

                displayDateToSpinner(spDayDialog, spMonthDialog, spYearDialog);
                displayClassToSpinner(spClassDialog);

                String dateProcess = studentDataList.get(position).getDateOfBirth();
                dateProcess = dateProcess.substring(8);
                spDayDialog.setSelection(Integer.parseInt(dateProcess) - 1);

                dateProcess = studentDataList.get(position).getDateOfBirth();
                dateProcess = dateProcess.substring(5,7);
                spMonthDialog.setSelection(Integer.parseInt(dateProcess) - 1);

                dateProcess = studentDataList.get(position).getDateOfBirth();
                dateProcess = dateProcess.substring(0,4);
                spYearDialog.setSelection(Integer.parseInt(dateProcess) - 1950);


                for (int i = 0; i < spClassDialog.getAdapter().getCount(); i++)
                {
                    if (studentDataList.get(position).getClassID().equals(spClassDialog.getItemAtPosition(i)))
                    {
                        spClassDialog.setSelection(i);
                        break;
                    }
                }


            }
        });
    }

}
