package com.example.WMS;

import android.graphics.Canvas;

import android.view.ViewGroup;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.FileAlreadyExistsException;
/**
 * 没用到的接口 
 * */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter mAdapter;


    /**
     * 当前滑动距离
     */
    private float scrollDistance = 0;

    private boolean isNeedRecover = true;

    private boolean isCanScrollLeft = false;
    private boolean isCanScrollRight = false;


    /**
     * 删除按钮宽度
     */
    private int deleteBtnWidth = 0;


    public MyItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        //设置滑动删除最大距离，1.5代表是itemview宽度的1.5倍,目的是不让它删除
        return 1.5f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        //设置滑动速度，目的是不让它进入onSwiped
        return defaultValue * 100;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //mAdapter.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (deleteBtnWidth <= 0)
                deleteBtnWidth = getSlideLimitation(viewHolder);

            int currentScroll = viewHolder.itemView.getScrollX();
            if (dX < 0 && isCanScrollLeft && currentScroll <= deleteBtnWidth) {
                dX = Math.abs(dX) <= deleteBtnWidth ? dX : -deleteBtnWidth;
                if (!isNeedRecover) {
                    int newScroll = deleteBtnWidth + (int) dX;
                    newScroll = newScroll <= currentScroll ? currentScroll : newScroll;
                    viewHolder.itemView.scrollTo(newScroll, 0);
                } else {
                    viewHolder.itemView.scrollTo(-(int) dX, 0);
                    scrollDistance = dX;
                }

            } else if (dX > 0 && isCanScrollLeft) {
                //可以左滑的情况下往右滑，恢复item位置
                viewHolder.itemView.scrollTo(0, 0);
                scrollDistance = 0;

            } else if (dX > 0 && isCanScrollRight && currentScroll >= 0) {
                if (!isNeedRecover) {
                    dX = Math.abs(dX) <= Math.abs(currentScroll) ? dX : currentScroll;
                    viewHolder.itemView.scrollTo((int) dX, 0);
                } else {
                    dX = Math.abs(dX) <= deleteBtnWidth ? dX : deleteBtnWidth;
                    viewHolder.itemView.scrollTo(deleteBtnWidth - (int) dX, 0);
                    scrollDistance = dX;
                }
            } else if (dX < 0 && isCanScrollRight) {
                //可以右滑的情况下往左滑，恢复item位置
                viewHolder.itemView.scrollTo(deleteBtnWidth, 0);
                scrollDistance = deleteBtnWidth;
            }

        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    /**
     * 获取删除按钮的宽度
     */

    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //ACTION_DOWN首先会调用这个，然后再调用onChildDraw
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemTouchHelperViewHolder.onItemSelect();
                isNeedRecover = true;
                scrollDistance = 0;
                isCanScrollLeft = false;
                isCanScrollRight = false;
                if (viewHolder.itemView.getScrollX() > 0)
                    isCanScrollRight = true;
                else
                    isCanScrollLeft = true;

            }
        } else {
            //ACTION_UP会首先进入这里，然后再执行recover animation
            if (Math.abs(scrollDistance) >= deleteBtnWidth / 2) {
                isNeedRecover = false;
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //滑动结束后触发
        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            ItemTouchHelperViewHolder itemTouchHelperViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemTouchHelperViewHolder.onItemClear();

            if (viewHolder.itemView.getScrollX() >= deleteBtnWidth / 2)
                viewHolder.itemView.scrollTo(deleteBtnWidth, 0);
            else
                viewHolder.itemView.scrollTo(0, 0);

        }
    }

    public interface ItemTouchHelperViewHolder {
        void onItemSelect();

        void onItemClear();
    }

    public interface ItemTouchHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDelete(int position);

        void onItemChange(int lastSelectPos);

        int getLastSelectItem();
    }
}
