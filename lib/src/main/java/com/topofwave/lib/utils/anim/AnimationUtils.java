package com.topofwave.lib.utils.anim;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * @describe 位移动画管理工具类.
 * @author adison
 * @date: 2014-10-22 下午3:09:49 <br/>
 */
public final class AnimationUtils {

    private static final int BARS_ANIMATION_DURATION = 150;

    private static final int ANIMATION_DURATION = 350;

    private Animation mTopBarShowAnimation = null;

    private Animation mTopBarHideAnimation = null;

    private Animation mBottomBarShowAnimation = null;

    private Animation mBottomBarHideAnimation = null;

    private Animation mPreviousTabViewShowAnimation = null;

    private Animation mPreviousTabViewHideAnimation = null;

    private Animation mNextTabViewShowAnimation = null;

    private Animation mNextTabViewHideAnimation = null;

    private Animation mInFromRightAnimation;

    private Animation mOutToLeftAnimation;

    private Animation mInFromLeftAnimation;

    private Animation mOutToRightAnimation;

    /**
     * 单例
     */
    private static class AnimationManagerHolder {

        private static final AnimationUtils INSTANCE = new AnimationUtils();
    }

    /**
     * 获取唯一实例
     * @return
     */
    public static AnimationUtils getInstance() {
        return AnimationManagerHolder.INSTANCE;
    }

    private AnimationUtils() {

        mTopBarShowAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                - 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mTopBarShowAnimation.setDuration(BARS_ANIMATION_DURATION);

        mTopBarHideAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, - 1.0f);

        mTopBarHideAnimation.setDuration(BARS_ANIMATION_DURATION);

        mBottomBarShowAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mBottomBarShowAnimation.setDuration(BARS_ANIMATION_DURATION);

        mBottomBarHideAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        mBottomBarHideAnimation.setDuration(BARS_ANIMATION_DURATION);

        mPreviousTabViewShowAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, - 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mPreviousTabViewShowAnimation.setDuration(BARS_ANIMATION_DURATION);

        mPreviousTabViewHideAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, - 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mPreviousTabViewHideAnimation.setDuration(BARS_ANIMATION_DURATION);

        mNextTabViewShowAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mNextTabViewShowAnimation.setDuration(BARS_ANIMATION_DURATION);

        mNextTabViewHideAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        mNextTabViewHideAnimation.setDuration(BARS_ANIMATION_DURATION);

        mInFromRightAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_PARENT, + 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        mInFromRightAnimation.setDuration(ANIMATION_DURATION);
        mInFromRightAnimation.setInterpolator(new AccelerateInterpolator());

        mOutToLeftAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, - 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        mOutToLeftAnimation.setDuration(ANIMATION_DURATION);
        mOutToLeftAnimation.setInterpolator(new AccelerateInterpolator());

        mInFromLeftAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_PARENT, - 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        mInFromLeftAnimation.setDuration(ANIMATION_DURATION);
        mInFromLeftAnimation.setInterpolator(new AccelerateInterpolator());

        mOutToRightAnimation =
            new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, + 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);

        mOutToRightAnimation.setDuration(ANIMATION_DURATION);
        mOutToRightAnimation.setInterpolator(new AccelerateInterpolator());
    }

    /**
     * tobar 展示动画
     * @return
     */
    public Animation getTopBarShowAnimation() {
        return mTopBarShowAnimation;
    }

    /**
     * tobar 隐藏动画
     * @return
     */
    public Animation getTopBarHideAnimation() {
        return mTopBarHideAnimation;
    }

    /**
     * BottomBar 展示动画
     * @return
     */
    public Animation getBottomBarShowAnimation() {
        return mBottomBarShowAnimation;
    }

    /**
     * BottomBar 隐藏动画
     * @return
     */
    public Animation getBottomBarHideAnimation() {
        return mBottomBarHideAnimation;
    }

    /**
     * PreviousTab 展示动画
     * @return
     */
    public Animation getPreviousTabViewShowAnimation() {
        return mPreviousTabViewShowAnimation;
    }

    /**
     * PreviousTab 隐藏动画
     * @return
     */
    public Animation getPreviousTabViewHideAnimation() {
        return mPreviousTabViewHideAnimation;
    }

    /**
     * NextTab 展示动画
     * @return
     */
    public Animation getNextTabViewShowAnimation() {
        return mNextTabViewShowAnimation;
    }

    /**
     * NextTab 隐藏动画
     * @return
     */
    public Animation getNextTabViewHideAnimation() {
        return mNextTabViewHideAnimation;
    }

    /**
     * 右进动画
     * @return
     */
    public Animation getInFromRightAnimation() {
        return mInFromRightAnimation;
    }

    /**
     * 左出动画
     * @return
     */
    public Animation getOutToLeftAnimation() {
        return mOutToLeftAnimation;
    }

    /**
     * 左进动画
     * @return
     */
    public Animation getInFromLeftAnimation() {
        return mInFromLeftAnimation;
    }

    /**
     * 右出动画
     * @return
     */
    public Animation getOutToRightAnimation() {
        return mOutToRightAnimation;
    }

}
