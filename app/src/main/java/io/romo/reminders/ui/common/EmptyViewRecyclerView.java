/*
 * Copyright 2017 Juan Romo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.romo.reminders.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmptyViewRecyclerView extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyView();
        }
    };

    public EmptyViewRecyclerView(Context context) {
        super(context);
    }

    public EmptyViewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyViewRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
    
    @Override
    public void setAdapter(Adapter adapter) {
        
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(dataObserver);
        }
        
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
        }
        
        super.setAdapter(adapter);
        
        updateEmptyView();
    }
    
    private void updateEmptyView() {
        if (emptyView != null && getAdapter() != null) {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            setVisibility(showEmptyView ? GONE : VISIBLE);
        }
    }
}
