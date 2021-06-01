package cn.boardour.app;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.boardour.base.adapter.XQuickAdapter;
import cn.boardour.base.adapter.XQuickViewHolder;
import cn.boardour.base.ui.AppFragment;
import cn.boardour.base.utils.LG;
import cn.boardour.base.utils.TSUtils;

public class TestFragment extends AppFragment {
    private RecyclerView recyclerView;
    private XQuickAdapter<String> adapter;
    private TextView tvDelete;
    boolean isUpward = false;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_test;
    }

    @Override
    public void initViews(View view) {
        tvDelete = findViewById(R.id.tv_delete);
        tvDelete.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getAppActivity(), 3));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add("item:" + i);
        }
        adapter = new XQuickAdapter<String>(getAppActivity(), data, R.layout.item_recycler_view) {
            @Override
            protected void convert(XQuickViewHolder holder, String item, int position) {
                holder.setText(R.id.tv, item);
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TSUtils.show(item);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
                // 发生拽托时，回调该方法，返回true、false决定是否可以拽托
                return target.getAdapterPosition() != adapter.getDataSize() - 1;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                // 位置交换
                Collections.swap(adapter.getData(), fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);

                return true;
            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (tvDelete.getTop() - viewHolder.itemView.getTop() < dY + viewHolder.itemView.getHeight() + tvDelete.getHeight() / 2) {
                    tvDelete.setText("松开手删除");
                    if (tvDelete.getVisibility() == View.INVISIBLE) {
                        if (isUpward) {
                            isUpward = false;
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            deleteItem(viewHolder.getAdapterPosition());
                            return;
                        }
                    }
                } else {
                    tvDelete.setText("拖动到此处删除");
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                isUpward = true;
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setAlpha(0.5f);
                    viewHolder.itemView.setScaleX(1.05f);
                    viewHolder.itemView.setScaleY(1.05f);
                    tvDelete.setVisibility(View.VISIBLE);
                } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    tvDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
                viewHolder.itemView.setScaleX(1.0f);
                viewHolder.itemView.setScaleY(1.0f);
                isUpward = false;
            }
        });
        // 关联
        helper.attachToRecyclerView(recyclerView);
    }

    private void deleteItem(int index) {
        adapter.getData().remove(index);
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void initNet() {

    }


    @Override
    public void fragmentVisible(boolean isVisibleToUser) {
        LG.e("fragmentVisible  " + isVisibleToUser);
    }


}
