package ru.job4j.todolist;

import android.content.Intent;
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
import android.widget.TextView;
import java.util.Date;
import java.util.List;

public class ListPlansActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private int position;
    private int recyclerState = 0;
    private PlanStore ps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_plans);
        Intent intent = getIntent();
        position = intent.getIntExtra("category_position", 0);
        ps = PlanStoreStore.get(position);
        llm = new LinearLayoutManager(getApplicationContext());
        this.recycler = findViewById(R.id.list_plans_recycler);
        this.recycler.setLayoutManager(llm);
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
    }@Override
    protected void onResume() {
        super.onResume();
        loadItems();
        recycler.scrollToPosition(recyclerState);
    }
    private void loadItems() {
        recycler.setAdapter(new ListPlansAdapter(ps.getPlans()));
    }
    static class ListPlansHolder extends RecyclerView.ViewHolder {
        ListPlansHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class ListPlansAdapter extends RecyclerView.Adapter<ListPlansHolder> {
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
                    intent.putExtra("category_position", position);
                    intent.putExtra("position", title.getId());
                    startActivity(intent);
                });
            }
            cb.setChecked(plan.isMark());
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> plan.setMark(isChecked));
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
            case R.id.menu_add_plan: {
                Date date = new Date();
                Plan plan = new Plan("", false, date.getTime());
                PlanStoreStore.get(position).add(plan);
                Intent intent = new Intent(this, PlanActivity.class);
                intent.putExtra("category_position", position);
                intent.putExtra("is_it_new_plan", true);
                startActivity(intent);
                return true;
            }
            case R.id.menu_delete_plans: {
                Intent intent = new Intent(this,
                        DeletePlansDialogActivator.class);
                intent.putExtra("category_position", position);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
