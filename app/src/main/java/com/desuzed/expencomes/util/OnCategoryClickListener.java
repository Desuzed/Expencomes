package com.desuzed.expencomes.util;

import com.desuzed.expencomes.model.Category;

public interface OnCategoryClickListener {
    void onCategoryClick (Category category);
    void onDeleteMenuOptionsClick (Category category);
    void onEditMenuOptionsClick (Category category);
}
