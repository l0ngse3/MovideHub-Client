package com.example.myapplication.Model;

import androidx.fragment.app.Fragment;

import java.util.List;

public class Category {
    private String nameCategory;
    private List<Film> list;
    private Fragment fragment;

    public Category() {
    }

    public Category(String nameCategory, List<Film> list, Fragment fragment) {
        this.nameCategory = nameCategory;
        this.list = list;
        this.fragment = fragment;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Film> getList() {
        return list;
    }

    public void setList(List<Film> list) {
        this.list = list;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
