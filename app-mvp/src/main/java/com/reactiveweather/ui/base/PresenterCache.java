package com.reactiveweather.ui.base;

import android.os.Bundle;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

class PresenterCache {
    private static final String BUNDLE_PRESENTER_ID = "BUNDLE_PRESENTER_ID";
    private final Map<Long, BasePresenter<?>> presenterCache = new LinkedHashMap<>();
    private final AtomicLong presenterId = new AtomicLong();

    public static PresenterCache getCache() {
        return InstanceHolder.INSTANCE;
    }

    public void save(BasePresenter<?> presenter, Bundle outState) {
        long id = presenterId.getAndIncrement();
        outState.putLong(BUNDLE_PRESENTER_ID, id);
        presenterCache.put(id, presenter);
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePresenter<?>> T restore(Bundle savedState) {
        long id = savedState.getLong(BUNDLE_PRESENTER_ID);
        return (T) presenterCache.remove(id);
    }

    private static final class InstanceHolder {
        private static final PresenterCache INSTANCE = new PresenterCache();
    }
}
