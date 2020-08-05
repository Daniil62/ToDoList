package ru.job4j.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LinearLayoutManager llm;
    private int recyclerState = 0;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        llm = new LinearLayoutManager(getApplicationContext());
        recycler = findViewById(R.id.activity_main_recycler);
        recycler.setLayoutManager(llm);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences != null) {
            restoreMemory();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        saveMemory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
        recycler.scrollToPosition(recyclerState);
    }
    private void loadItems() {
        recycler.setAdapter(new MainFragmentAdapter(PlanStoreStore.getPlanStores()));
    }
    static class ListHolder extends RecyclerView.ViewHolder {
        ListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class MainFragmentAdapter extends RecyclerView.Adapter<ListHolder> {
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
            return new ListHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MainActivity.ListHolder holder, int i) {
            final PlanStore ps = this.list.get(i);
            TextView title = holder.itemView.findViewById(R.id.list_module_textView);
            CheckBox cb = holder.itemView.findViewById(R.id.list_module_checkBox);
            ImageButton edit = holder.itemView.findViewById(R.id.list_module_button_edit);
            if (title != null) {
                title.setId(i);
                title.setText(ps.getTitle());
                title.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this,
                            ListPlansActivity.class);
                    intent.putExtra("category_position", title.getId());
                    startActivity(intent);
                });
            }
            cb.setChecked(ps.isMark());
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> ps.setMark(isChecked));
            edit.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this,
                        AddCategoryDialogActivator.class);
                if (title != null) {
                    intent.putExtra("editable_position",
                            Objects.requireNonNull(title).getId());
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
    private void saveMemory() {
        int storeStoreSize = PlanStoreStore.size();
        editor.putInt("plan_store_store_size", storeStoreSize).apply();
        for (int i = 0; i < storeStoreSize; i++) {
            PlanStore ps = PlanStoreStore.get(i);
            int storeSize = ps.size();
            editor.putInt("plan_store_size" + i, storeSize).apply();
            editor.putString("plan_store_title" + i, ps.getTitle()).apply();
            editor.putBoolean("plan_store_mark" + i, ps.isMark()).apply();
            for (int j = 0; j < storeSize; j++) {
                editor.putString("plan_text" + i + j, ps.get(j).getText()).apply();
                editor.putBoolean("plan_mark" + i + j, ps.get(j).isMark()).apply();
                editor.putLong("plan_created" + i + j, ps.get(j).getCreated());
            }
        }
        PlanStoreStore.clear();
    }
    private void restoreMemory() {
        int storeStoreSize = preferences.getInt("plan_store_store_size", 0);
        for (int i = 0; i < storeStoreSize; i++) {
            int storeSize = preferences.getInt("plan_store_size" + i, 0);
            PlanStore ps = new PlanStore(preferences
                    .getString("plan_store_title" + i, ""),
                    preferences.getBoolean("plan_store_mark" + i, false));
            for (int j = 0; j < storeSize; j++) {
                Plan plan = new Plan(preferences.getString("plan_text" + i + j, ""),
                        preferences.getBoolean("plan_mark" + i + j, false),
                        preferences.getLong("plan_created" + i + j, 0));
                ps.add(plan);
            }
            PlanStoreStore.add(ps);
        }
    }
}
