package org.arslansah.android.fragments;

import android.view.View;

public interface HasFab {
    View getFab();
    void showFab();
    void hideFab();
    boolean isScrolling();
}
