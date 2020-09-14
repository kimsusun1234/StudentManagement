package com.example.mob1032_assignment.ultilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mob1032_assignment.dao.ClassDataDAO;
import com.example.mob1032_assignment.dao.StudentDataDAO;

public class ManagementDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "StudentsDatabase.db"; //Thêm static để các hằng số được khởi tạo đầu tiên trên hệ thống
    public static final int DATABASE_VERSION = 1;                       //không có static thì các hằng số này chỉ tồn tại sau khi khởi tạo đối tượng, nghĩa là sau khi chạy hàm khởi tạo


    //Vì đã có hằng số tên, version của db nên không cần truyền vào hàm tạo
    //chỉ cần đưa vào super
    public ManagementDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override //onCreate được chạy khi database được tạo ra, tham số truyền vào là Database khởi tạo trong activity liên quan
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassDataDAO.CREATE_CLASSES_TABLE);
        db.execSQL(StudentDataDAO.CREATE_STUDENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
