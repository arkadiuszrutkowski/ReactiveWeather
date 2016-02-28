package com.reactiveweather.ui;

import com.reactiveweather.ui.base.BasePresenter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresenterCache {
    private final Map<Class<?>, BasePresenter<?>> presenterCache = new LinkedHashMap<>();

    public void save(Class<?> viewType, BasePresenter<?> presenter) {
        presenterCache.put(viewType, presenter);
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePresenter<?>> T restore(Class<?> viewType) {
        return (T) presenterCache.remove(viewType);
    }
}
