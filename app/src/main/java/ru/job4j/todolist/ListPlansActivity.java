package ru.job4j.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

public class ListPlansActivity extends AppCompatActivity
        implements OrderDialogFragment.OrderDialogListener {
    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private String sequence = "";
    private String order;
    private int category_position;
    private int recyclerState = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_plans);
        Intent intent = getIntent();
        category_position = intent.getIntExtra("category_position", 0);
        llm = new LinearLayoutManager(getApplicationContext());
        this.recycler = findViewById(R.id.list_plans_recycler);
        this.recycler.setLayoutManager(llm);
        EditText editText = findViewById(R.id.list_plans_editText);
        this.sequence = "";
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                sequence = s.toString();
                loadItems();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("recycler_position", llm.findFirstVisibleItemPosition());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerState = (savedInstanceState.getInt("recycler_position"));
        recycler.scrollToPosition(recyclerState);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (recyclerState == 0) {
            recyclerState = llm.findFirstVisibleItemPosition();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
        recycler.scrollToPosition(recyclerState);
    }
    private void loadItems() {
        ToDoListBaseHelper helper = new ToDoListBaseHelper(this);
        recycler.setAdapter(new ListPlansAdapter(
                helper.getPlansById(category_position, sequence, order)));
    }
    static class ListPlansHolder extends RecyclerView.ViewHolder {
        ListPlansHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class ListPlansAdapter extends RecyclerView.Adapter<ListPlansHolder> {
        private ToDoListBaseHelper helper;
        private List<Plan> list;
        ListPlansAdapter(List<Plan> list) {
            this.list = list;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @Override
        public ListPlansHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.plan_module, viewGroup, false);
            helper = new ToDoListBaseHelper(ListPlansActivity.this);
            return new ListPlansHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ListPlansHolder holder, int i) {
            final Plan plan = this.list.get(i);
            TextView title = holder.itemView.findViewById(R.id.plan_module_textView);
            CheckBox cb = holder.itemView.findViewById(R.id.plan_module_checkBox);
            if (title != null) {
                title.setId(i);
                title.setText(plan.getText());
                title.setOnClickListener(v -> {
                    Intent intent = new Intent(ListPlansActivity.this,
                            PlanActivity.class);
                    intent.putExtra("category_position", category_position);
                    intent.putExtra("plan_id", plan.getId());
                    startActivity(intent);
                });
            }
            cb.setChecked(plan.isMark());
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                plan.setMark(isChecked);
                helper.markPlan(plan.getId(), isChecked);
            });
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_plans, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_order_plan_dialog_call: {
                DialogFragment dialog = new OrderDialogFragment();
                dialog.show(getSupportFragmentManager(), "plan_order_dialog");
                return true;
            }
            case R.id.menu_add_plan: {
                Intent intent = new Intent(this, PlanActivity.class);
                intent.putExtra("category_position", category_position);
                intent.putExtra("is_it_new_plan", true);
                startActivity(intent);
                return true;
            }
            case R.id.menu_delete_plans: {
                Intent intent = new Intent(this,
                        DeletePlansDialogActivator.class);
                intent.putExtra("category_position", category_position);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    @Override
    public void positiveOrderClick(OrderDialogFragment object) {
        order = ToDoListDBschema.PlanTable.Cols.TEXT;
        loadItems();
    }
    @Override
    public void negativeOrderClick(OrderDialogFragment object) {
        order = ToDoListDBschema.PlanTable.Cols.CREATED;
        loadItems();
    }
}
