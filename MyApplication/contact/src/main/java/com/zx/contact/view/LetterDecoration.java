package com.zx.contact.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.zx.contact.entity.User;

import java.util.List;

/**
 * 介绍：分类、悬停的Decoration
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/7.
 */

public class LetterDecoration extends RecyclerView.ItemDecoration {
    private List<User> mUsers;
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect


    private int mTitleHeight;//title的高
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF999999");


    public LetterDecoration(Context context, List<User> users) {
        mUsers = users;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int titleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(titleFontSize);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //pos为1，size为1，1>0? true
            if (mUsers == null || mUsers.isEmpty() || position > mUsers.size() - 1 || position < 0) {
                continue;//越界
            }
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);
                } else if (null != mUsers.get(position).getLable() &&
                        !mUsers.get(position).getLable().equals(mUsers.get(position - 1).getLable())) {
                    //其他的通过判断
                    //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    drawTitleArea(c, left, right, child, params, position);
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c        画布
     * @param left     左边尺寸
     * @param right    右面尺寸
     * @param child    子视图
     * @param params   参数
     * @param position 位置
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.setColor(Color.BLACK);
        mPaint.getTextBounds(mUsers.get(position).getLable(), 0, mUsers.get(position).getLable().length(), mBounds);
        c.drawText(mUsers.get(position).getLable(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super里会先设置0 0 0 0
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (mUsers == null || mUsers.isEmpty() || position > mUsers.size() - 1) {//pos为1，size为1，1>0? true
            return;//越界
        }
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else if (null != mUsers.get(position).getLable() &&
                    !mUsers.get(position).getLable().equals(mUsers.get(position - 1).getLable())) {
                //其他的通过判断
                //不为空 且跟前一个lable不一样了，说明是新的分类，也要title
                outRect.set(0, mTitleHeight, 0, 0);
            } else outRect.set(0, 2, 0, 0);
        }
    }
}