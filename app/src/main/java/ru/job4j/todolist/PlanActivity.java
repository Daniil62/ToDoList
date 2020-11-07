package ru.job4j.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import ru.job4j.todolist.model.Plan;
import ru.job4j.todolist.store.ToDoListBaseHelper;

public class PlanActivity extends AppCompatActivity
        implements SaveChangesDialogFragment.SaveChangesListener,
        DeleteThisPlanDialogFragment.DeleteThisPlanDialogListener {
    private TextView createDate;
    private EditText et;
    private Plan plan;
    private int categoryPosition;
    private int plan_id;
    private ToDoListBaseHelper helper;
    private boolean isItNewPlan;
    private Date date;
    private ImageView photo;
    private final static int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        et = findViewById(R.id.plan_editText_field);
        Button clear = findViewById(R.id.plan_button_clear);
        Button back = findViewById(R.id.plan_button_back);
        Button save = findViewById(R.id.plan_button_save);
        createDate = findViewById(R.id.creation_plan_date_textView);
        photo = findViewById(R.id.plan_photo);
        Intent intent = Objects.requireNonNull(getIntent());
        categoryPosition = intent.getIntExtra("category_position", 0);
        plan_id = intent.getIntExtra("plan_id", 0);
        isItNewPlan = intent.getBooleanExtra("is_it_new_plan", false);
        helper = new ToDoListBaseHelper(this);
        date = new Date();
        clear.setOnClickListener(v -> et.setText(""));
        back.setOnClickListener(v -> buttonBackClick());
        save.setOnClickListener(v -> buttonSave());
        photo.setOnClickListener(v -> {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent1.resolveActivity(this.getPackageManager()) != null) {
                startActivityForResult(intent1, REQUEST_IMAGE_CAPTURE);
            }
        });
        loadIt();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE || requestCode == RESULT_OK) {
            Bundle extras = Objects.requireNonNull(data).getExtras();
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(extras).get("data");
            photo.setImageBitmap(bitmap);
        }
    }
    private void loadIt() {
        if (helper.size() > 0 && !isItNewPlan) {
            plan = helper.getPlan(categoryPosition, plan_id);
        } else {
            plan = new Plan(plan_id,"", false, date.getTime());
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        createDate.setText(sdf.format(plan.getCreated()));
        et.setText(plan.getText());
        et.setSelection(et.getText().length());
    }
    private void buttonSave() {
        String text = et.getText().toString();
        if (!text.equals("")) {
            if (isItNewPlan) {
                plan.setText(text);
                helper.setPlan(categoryPosition, plan);
            } else {
                helper.editPlan(categoryPosition, plan_id, text);
            }
            finish();
        }
    }
    private void buttonBackClick() {
        String existing = plan.getText();
        if (!existing.equals(et.getText().toString())
                && !et.getText().toString().equals("")) {
            DialogFragment dialog = new SaveChangesDialogFragment();
            dialog.show(getSupportFragmentManager(), "save_changes_dialog");
        } else {
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        buttonBackClick();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plan, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_plan_delete: {
                DialogFragment dialog = new DeleteThisPlanDialogFragment();
                dialog.show(getSupportFragmentManager(), "delete_this_plan_dialog");
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    @Override
    public void positiveSaveChangesClick(SaveChangesDialogFragment object) {
        buttonSave();
    }
    @Override
    public void negativeSaveChangesClick(SaveChangesDialogFragment object) {
        finish();
    }
    @Override
    public void positiveDeleteThisPlanClick(DeleteThisPlanDialogFragment object) {
        helper.deleteThisPlan(plan_id);
        finish();
    }
    @Override
    public void negativeDeleteThisPlanClick(DeleteThisPlanDialogFragment object) {
    }
}
