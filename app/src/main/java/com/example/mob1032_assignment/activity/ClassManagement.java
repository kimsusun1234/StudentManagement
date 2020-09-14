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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mob1032_assignment.dao.ClassDataDAO;
import com.example.mob1032_assignment.R;
import com.example.mob1032_assignment.custom_list_view.ClassListView;
import com.example.mob1032_assignment.datamodel.ClassData;

import java.util.ArrayList;
import java.util.List;

public class ClassManagement extends AppCompatActivity {

    private Button btnAddClass = null;
    private EditText etClassID = null;
    private EditText etClassName = null;
    private ListView lstClasses = null;
    private EditText etClassNameDialog = null;
    private List<ClassData> dataList = new ArrayList<>();
    AlertDialog alertDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_management);
        mapping();
        getData();
        btnAddClassOnClick();
        displayClassList();
        itemClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itemStudentManagment:
            {
                Intent intent = new Intent(ClassManagement.this, StudentManagement.class);
                startActivity(intent);
                return true;
            }

            case R.id.itemExit:
            {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }

    }

    private void mapping()
    {
        btnAddClass = findViewById(R.id.btnAddClass);
        etClassID = findViewById(R.id.etClassID);
        etClassName = findViewById(R.id.etClassName);
        lstClasses = findViewById(R.id.lstClass);
    }

    private void btnAddClassOnClick()
    {
        if (btnAddClass == null)
        {
            mapping();
        }
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm())
                {
                    ClassDataDAO classDAO = new ClassDataDAO(ClassManagement.this);
                    long recordOrder = classDAO.insert(etClassID.getText().toString(), etClassName.getText().toString());
                    Toast.makeText(ClassManagement.this, "Added succesfully. Record order: " + recordOrder , Toast.LENGTH_LONG).show();
                    etClassID.setText("");
                    etClassName.setText("");
                    dataList.clear();
                    getData();
                }
            }
        });
    }

    private void getData()
    {
        ClassDataDAO classDao = new ClassDataDAO(ClassManagement.this);
        dataList = classDao.selectAll();
    }

    private boolean validForm()
    {
        if (etClassID.getText().toString().equals(""))
        {
            Toast.makeText(ClassManagement.this, "Please enter the ID!", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            for (int i = 0; i < dataList.size(); i++)
            {
                if (etClassID.getText().toString().equalsIgnoreCase(dataList.get(i).getId()))
                {
                    Toast.makeText(ClassManagement.this, "ID has already exist", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            //Phải "thoát" khỏi vòng for được thì mới đi xuống câu if bên dưới
            if (etClassName.getText().toString().equals(""))
            {
                Toast.makeText(ClassManagement.this, "Please, enter name of the class", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void displayClassList()
    {
        if (lstClasses == null)
        {
            mapping();
        }
        ClassListView customAdapter = new ClassListView(ClassManagement.this, R.layout.layout_custom_listview_class, dataList);
        lstClasses.setAdapter(customAdapter);
    }

    private void itemClick()
    {
        lstClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialogCreating(position);
            }
        });
    }

    private void dialogCreating(int position)
    {
        //lấy ID hiện tại của record
        final String CURRENT_ID = dataList.get(position).getId();

        //đối tượng builder giúp xây dựng dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassManagement.this);
        //đối tượng inflatter giúp "đẩy" các thành phần custom layout vào dialog
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_class_dialog, null));
        //R.string.update_button là hằng số được lưu trong file xml trong R.values

        builder.setPositiveButton(R.string.update_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClassDataDAO classDataDAO = new ClassDataDAO(ClassManagement.this);
                if(!etClassNameDialog.getText().toString().equals(""))
                {
                    classDataDAO.update(CURRENT_ID, etClassNameDialog.getText().toString());
                    Toast.makeText(ClassManagement.this, "Update Complete", Toast.LENGTH_SHORT).show();
                    dataList.clear();
                    getData();
                    displayClassList();
                    alertDialog.cancel();
                }
                else
                {
                    Toast.makeText(ClassManagement.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.delete_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassManagement.this);
                builder.setTitle(R.string.delete_confirm);
                builder.setMessage(R.string.delete_message);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClassDataDAO classDataDAO = new ClassDataDAO(ClassManagement.this);
                        classDataDAO.delete(CURRENT_ID);
                        Toast.makeText(ClassManagement.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        dataList.clear();
                        getData();
                        displayClassList();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
         alertDialog = builder.create();
        alertDialog.show();

        Button btnUpdate = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        etClassNameDialog = alertDialog.findViewById(R.id.etClassNameDialog);
        etClassNameDialog.setText(dataList.get(position).getName());
    }

}
