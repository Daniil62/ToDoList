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
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OrderDialogFragment.OrderDialogListener {
    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private int recyclerState = 0;
    private String sequence = "";
    private String order;
    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        llm = new LinearLayoutManager(getApplicationContext());
        recycler = findViewById(R.id.activity_main_recycler);
        recycler.setLayoutManager(llm);
        EditText editText = findViewById(R.id.main_activity_editText);
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
        recycler.setAdapter(new MainFragmentAdapter(helper.getCategories(sequence, order)));
    }
    static class ListHolder extends RecyclerView.ViewHolder {
        ListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class MainFragmentAdapter extends RecyclerView.Adapter<ListHolder> {
        private ToDoListBaseHelper helper;
        private final List<PlanStore> list;
        MainFragmentAdapter(List<PlanStore> list) {
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
        public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.list_module, viewGroup, false);
            helper = new ToDoListBaseHelper(MainActivity.this);
            return new ListHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MainActivity.ListHolder holder, int i) {
            final PlanStore ps = this.list.get(i);
            TextView title = holder.itemView.findViewById(R.id.list_module_textView);
            CheckBox cb = holder.itemView.findViewById(R.id.list_module_checkBox);
            ImageButton edit = holder.itemView.findViewById(R.id.list_module_button_edit);
            int id = ps.getId();
            if (title != null) {
                title.setId(id);
                title.setText(ps.getTitle());
                title.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this,
                            ListPlansActivity.class);
                    intent.putExtra("category_position", title.getId());
                    startActivity(intent);
                });
            }
            cb.setChecked(ps.isMark());
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                ps.setMark(isChecked);
                helper.markCategory(id, isChecked);
            });
            edit.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this,
                        AddCategoryDialogActivator.class);
                if (title != null) {
                    intent.putExtra("editable_position", id);
                }
                startActivity(intent);
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
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_order_dialog_call: {
                DialogFragment dialog = new OrderDialogFragment();
                dialog.show(getSupportFragmentManager(), "category_order_dialog");
                return true;
            }
            case R.id.menu_add_item: {
                Intent intent = new Intent(MainActivity.this,
                        AddCategoryDialogActivator.class);
                intent.putExtra("variant_for_add", true);
                startActivity(intent);
                return true;
            }
            case R.id.menu_delete_items: {
                Intent intent = new Intent(MainActivity.this,
                        DeleteCategoryDialogActivator.class);
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
        order = ToDoListDBschema.CategoryTable.Cols.TITLE;
        loadItems();
    }
    @Override
    public void negativeOrderClick(OrderDialogFragment object) {
        order = "_id";
        loadItems();
    }
}
