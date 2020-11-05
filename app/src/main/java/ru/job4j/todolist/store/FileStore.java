package ru.job4j.todolist.store;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import ru.job4j.todolist.model.Plan;

public class FileStore implements IStore {
    private static IStore instance;
    private int counter = 0;
    private Context context;
    private FileStore(Context c) {
        this.context = c;
    }
    public static IStore getInstance(Context c) {
        if (instance == null) {
            instance = new FileStore(c);
        }
        return instance;
    }
    @Override
    public void add(Plan plan) {
        File file = new File(context.getFilesDir(), (counter++) + ".txt");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            out.println(plan.getId());
            out.println(plan.getText());
            out.println(plan.getCreated());
            out.println(plan.isMark());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void edit(int index, Plan plan) {
        delete(index);
        File file = new File(context.getFilesDir(), index + ".txt");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            int id = plan.getId();
            String text = plan.getText();
            long created = plan.getCreated();
            boolean mark = plan.isMark();
            out.println(id);
            out.println(text);
            out.println(created);
            out.println(mark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void delete(int index) {
        File file = new File(context.getFilesDir(), index + ".txt");
        try {
            Files.delete(file.toPath());
        } catch (NoSuchFileException e) {
            System.err.format("%s no such " + "file or directory%n", file.toPath());
        } catch (DirectoryNotEmptyException e) {
            System.err.format("%s not empty%n", file.toPath());
        } catch (IOException e) {
            System.err.print(e.toString());
        }
    }
    @Override
    public int size() {
        return context.getFilesDir().listFiles().length;
    }
    @Override
    public Plan get(int index) {
        Plan plan = new Plan(0, null, false, 0);
        File file = new File(context.getFilesDir(), index + ".txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            plan.setId(Integer.parseInt(in.readLine()));
            plan.setText(in.readLine());
            plan.setCreated(Long.parseLong(in.readLine()));
            plan.setMark(Boolean.parseBoolean(in.readLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
    }
}
