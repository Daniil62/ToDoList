package ru.job4j.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PlanActivity extends AppCompatActivity implements SaveChangesPlanWhenBackDialog
        .SaveChangesPlanWhenBackListener, DeleteThisPlanDialog.DeleteThisPlanDialogListener {
    private TextView createDate;
    private EditText et;
    private int position;
    private int categoryPosition;
    private PlanStore ps;
    private boolean isItNewPlan;
    private Date date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);
        et = findViewById(R.id.plan_editText_field);
        Button clear = findViewById(R.id.plan_button_clear);
        Button back = findViewById(R.id.plan_button_back);
        Button save = findViewById(R.id.plan_button_save);
        createDate = findViewById(R.id.creation_plan_date_textView);
        Intent intent = Objects.requireNonNull(getIntent());
        categoryPosition = intent.getIntExtra("category_position",
                PlanStoreStore.size() - 1);
        ps = PlanStoreStore.get(categoryPosition);
        position = intent.getIntExtra("position", ps.size() - 1);
        isItNewPlan = intent.getBooleanExtra("is_it_new_plan", false);
        this.date = new Date();
        creationDateInicializing();
        et.setText(PlanStoreStore.get(categoryPosition).get(position).getText());
        et.setSelection(et.getText().length());
        clear.setOnClickListener(v -> et.setText(""));
        back.setOnClickListener(this::buttonBackClick);
        save.setOnClickListener(v -> {
            String text = et.getText().toString();
            if (!text.equals("")) {
                Plan plan = new Plan(text, false, date.getTime());
                PlanStoreStore.get(categoryPosition).set(position, plan);
                this.onBackPressed();
            }
        });
    }
    private void creationDateInicializing() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        if (isItNewPlan) {
            createDate.setText(sdf.format(date.getTime()));
        } else {
            createDate.setText(sdf.format(PlanStoreStore
                    .get(categoryPosition).get(position).getCreated()));
        }
    }
    private void buttonBackClick(View view) {
        if (isItNewPlan && et.getText().toString().equals("")) {
            PlanStoreStore.get(categoryPosition).getPlans().remove(position);
            this.onBackPressed();
        }
        else if (!isItNewPlan) {
            String existing = PlanStoreStore.get(categoryPosition)
                    .getPlans().get(position).getText();
            if (!existing.equals(et.getText().toString())
                    && !et.getText().toString().equals("")) {
                DialogFragment dialog = new SaveChangesPlanWhenBackDialog();
                dialog.show(getSupportFragmentManager(), "save_changes_dialog");
            } else {
                this.onBackPressed();
            }
        } else {
            DialogFragment dialog = new SaveChangesPlanWhenBackDialog();
            dialog.show(getSupportFragmentManager(), "save_changes_dialog");
        }
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
                DialogFragment dialog = new DeleteThisPlanDialog();
                dialog.show(getSupportFragmentManager(), "delete_this_plan_dialog");
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    @Override
    public void positiveSaveChangesWhenBackClick(SaveChangesPlanWhenBackDialog object) {
        String text = et.getText().toString();
        if (!text.equals("")) {
            Plan plan = new Plan(text, false, date.getTime());
            PlanStoreStore.get(categoryPosition).set(position, plan);
        }
        this.onBackPressed();
    }
    @Override
    public void negativeSaveChangesWhenBackClick(SaveChangesPlanWhenBackDialog object) {
        if (isItNewPlan) {
            PlanStoreStore.get(categoryPosition).getPlans().remove(position);
        }
        this.onBackPressed();
    }
    @Override
    public void positiveDeleteThisPlanClick(DeleteThisPlanDialog object) {
        List<Plan> temp = new ArrayList<>();
        for (int i = 0; i < ps.size(); i++) {
            if (i == position) {
                continue;
            } else {
                temp.add(ps.get(i));
            }
        }
        ps.clear();
        ps.addAll(temp);
        this.onBackPressed();
    }
    @Override
    public void negativeDeleteThisPlanClick(DeleteThisPlanDialog object) {
    }
}
