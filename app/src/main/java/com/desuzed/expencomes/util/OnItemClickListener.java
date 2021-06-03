package com.desuzed.expencomes.util;

import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.Item;

public interface OnItemClickListener {
    void onItemClick (Item item);
    void onDeleteMenuOptionsClick (Item item);
    void onEditMenuOptionsClick (Item item);
}
