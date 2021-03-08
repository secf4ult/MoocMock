//package com.example.moocmock.ui.category;
//
//import android.animation.ValueAnimator;
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.database.DataSetObserver;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.GradientDrawable;
//import android.graphics.drawable.LayerDrawable;
//import android.graphics.drawable.RippleDrawable;
//import android.os.Build;
//import android.text.Layout;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.view.accessibility.AccessibilityEvent;
//import android.view.accessibility.AccessibilityNodeInfo;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import androidx.annotation.ColorInt;
//import androidx.annotation.Dimension;
//import androidx.annotation.DrawableRes;
//import androidx.annotation.IntDef;
//import androidx.annotation.LayoutRes;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RestrictTo;
//import androidx.annotation.StringRes;
//import androidx.appcompat.content.res.AppCompatResources;
//import androidx.appcompat.widget.TooltipCompat;
//import androidx.core.graphics.drawable.DrawableCompat;
//import androidx.core.util.Pools;
//import androidx.core.view.AccessibilityDelegateCompat;
//import androidx.core.view.MarginLayoutParamsCompat;
//import androidx.core.view.PointerIconCompat;
//import androidx.core.view.ViewCompat;
//import androidx.core.widget.TextViewCompat;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.material.badge.BadgeDrawable;
//import com.google.android.material.badge.BadgeUtils;
//import com.google.android.material.internal.ThemeEnforcement;
//import com.google.android.material.internal.ViewUtils;
//import com.google.android.material.resources.MaterialResources;
//import com.google.android.material.ripple.RippleUtils;
//import com.google.android.material.shape.MaterialShapeDrawable;
//import com.google.android.material.tabs.TabItem;
//import com.google.android.material.tabs.TabLayout;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class VerticalTabLayout extends ScrollView {
//    @Dimension(
//            unit = 0
//    )
//    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
//    @Dimension(
//            unit = 0
//    )
//    static final int DEFAULT_GAP_TEXT_ICON = 8;
//    @Dimension(
//            unit = 0
//    )
//    private static final int DEFAULT_HEIGHT = 48;
//    @Dimension(
//            unit = 0
//    )
//    private static final int TAB_MIN_WIDTH_MARGIN = 56;
//    @Dimension(
//            unit = 0
//    )
//    private static final int MIN_INDICATOR_WIDTH = 24;
//    @Dimension(
//            unit = 0
//    )
//    static final int FIXED_WRAP_GUTTER_MIN = 16;
//    private static final int INVALID_WIDTH = -1;
//    private static final int ANIMATION_DURATION = 300;
//    private static final Pools.Pool<VerticalTabLayout.Tab> tabPool = new Pools.SynchronizedPool(16);
//    public static final int MODE_SCROLLABLE = 0;
//    public static final int MODE_FIXED = 1;
//    public static final int MODE_AUTO = 2;
//    public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
//    public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
//    public static final int GRAVITY_FILL = 0;
//    public static final int GRAVITY_CENTER = 1;
//    public static final int INDICATOR_GRAVITY_BOTTOM = 0;
//    public static final int INDICATOR_GRAVITY_CENTER = 1;
//    public static final int INDICATOR_GRAVITY_TOP = 2;
//    public static final int INDICATOR_GRAVITY_STRETCH = 3;
//
//    private final ArrayList<VerticalTabLayout.Tab> tabs;
//    @Nullable
//    private VerticalTabLayout.Tab selectedTab;
//    private final RectF tabViewContentBounds;
//    @NonNull
//    private final VerticalTabLayout.SlidingTabIndicator slidingTabIndicator;
//    int tabPaddingStart;
//    int tabPaddingTop;
//    int tabPaddingEnd;
//    int tabPaddingBottom;
//    int tabTextAppearance;
//    ColorStateList tabTextColors;
//    ColorStateList tabIconTint;
//    ColorStateList tabRippleColorStateList;
//    @Nullable
//    Drawable tabSelectedIndicator;
//    android.graphics.PorterDuff.Mode tabIconTintMode;
//    float tabTextSize;
//    float tabTextMultiLineSize;
//    final int tabBackgroundResId;
//    int tabMaxWidth;
//    private final int requestedTabMinWidth;
//    private final int requestedTabMaxWidth;
//    private final int scrollableTabMinWidth;
//    private int contentInsetStart;
//    int tabGravity;
//    int tabIndicatorAnimationDuration;
//    int tabIndicatorGravity;
//    int mode;
//    boolean inlineLabel;
//    boolean tabIndicatorFullWidth;
//    boolean unboundedRipple;
//    @Nullable
//    private VerticalTabLayout.OnTabSelectedListener selectedListener;
//    private final ArrayList<VerticalTabLayout.OnTabSelectedListener> selectedListeners;
//    @Nullable
//    private VerticalTabLayout.OnTabSelectedListener currentVpSelectedListener;
//    private ValueAnimator scrollAnimator;
//    @Nullable
//    ViewPager viewPager;
//    @Nullable
//    private PagerAdapter pagerAdapter;
//    private DataSetObserver pagerAdapterObserver;
//    private VerticalTabLayout.TabLayoutOnPageChangeListener pageChangeListener;
//    private VerticalTabLayout.AdapterChangeListener adapterChangeListener;
//    private boolean setupViewPagerImplicitly;
//    private final Pools.Pool<VerticalTabLayout.TabView> tabViewPool;
//
//
//    public VerticalTabLayout(@NonNull Context context) {
//        this(context, (AttributeSet) null);
//    }
//
//    public VerticalTabLayout(@NonNull Context context,
//                             @Nullable AttributeSet attrs) {
//        this(context, attrs, attr.tabStyle);
//    }
//
//    public VerticalTabLayout(@NonNull Context context,
//                             @Nullable AttributeSet attrs,
//                             int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.tabs = new ArrayList();
//        this.tabViewContentBounds = new RectF();
//        this.tabMaxWidth = 2147483647;
//        this.selectedListeners = new ArrayList();
//        this.tabViewPool = new Pools.SimplePool(12);
//        this.setHorizontalScrollBarEnabled(false);
//        this.slidingTabIndicator = new VerticalTabLayout.SlidingTabIndicator(context);
//        super.addView(this.slidingTabIndicator, 0, new LayoutParams(-2, -1));
//        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, styleable.TabLayout, defStyleAttr, style.Widget_Design_TabLayout, new int[]{styleable.TabLayout_tabTextAppearance});
//        if (this.getBackground() instanceof ColorDrawable) {
//            ColorDrawable background = (ColorDrawable) this.getBackground();
//            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
//            materialShapeDrawable.setFillColor(ColorStateList.valueOf(background.getColor()));
//            materialShapeDrawable.initializeElevationOverlay(context);
//            materialShapeDrawable.setElevation(ViewCompat.getElevation(this));
//            ViewCompat.setBackground(this, materialShapeDrawable);
//        }
//
//        this.slidingTabIndicator.setSelectedIndicatorHeight(a.getDimensionPixelSize(styleable.TabLayout_tabIndicatorHeight, -1));
//        this.slidingTabIndicator.setSelectedIndicatorColor(a.getColor(styleable.TabLayout_tabIndicatorColor, 0));
//        this.setSelectedTabIndicator(MaterialResources.getDrawable(context, a, styleable.TabLayout_tabIndicator));
//        this.setSelectedTabIndicatorGravity(a.getInt(styleable.TabLayout_tabIndicatorGravity, 0));
//        this.setTabIndicatorFullWidth(a.getBoolean(styleable.TabLayout_tabIndicatorFullWidth, true));
//        this.tabPaddingStart = this.tabPaddingTop = this.tabPaddingEnd = this.tabPaddingBottom = a.getDimensionPixelSize(styleable.TabLayout_tabPadding, 0);
//        this.tabPaddingStart = a.getDimensionPixelSize(styleable.TabLayout_tabPaddingStart, this.tabPaddingStart);
//        this.tabPaddingTop = a.getDimensionPixelSize(styleable.TabLayout_tabPaddingTop, this.tabPaddingTop);
//        this.tabPaddingEnd = a.getDimensionPixelSize(styleable.TabLayout_tabPaddingEnd, this.tabPaddingEnd);
//        this.tabPaddingBottom = a.getDimensionPixelSize(styleable.TabLayout_tabPaddingBottom, this.tabPaddingBottom);
//        this.tabTextAppearance = a.getResourceId(styleable.TabLayout_tabTextAppearance, style.TextAppearance_Design_Tab);
//        TypedArray ta = context.obtainStyledAttributes(this.tabTextAppearance, androidx.appcompat.R.styleable.TextAppearance);
//
//        try {
//            this.tabTextSize = (float) ta.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
//            this.tabTextColors = MaterialResources.getColorStateList(context, ta, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
//        } finally {
//            ta.recycle();
//        }
//
//        if (a.hasValue(styleable.TabLayout_tabTextColor)) {
//            this.tabTextColors = MaterialResources.getColorStateList(context, a, styleable.TabLayout_tabTextColor);
//        }
//
//        if (a.hasValue(styleable.TabLayout_tabSelectedTextColor)) {
//            int selected = a.getColor(styleable.TabLayout_tabSelectedTextColor, 0);
//            this.tabTextColors = createColorStateList(this.tabTextColors.getDefaultColor(), selected);
//        }
//
//        this.tabIconTint = MaterialResources.getColorStateList(context, a, styleable.TabLayout_tabIconTint);
//        this.tabIconTintMode = ViewUtils.parseTintMode(a.getInt(styleable.TabLayout_tabIconTintMode, -1), (android.graphics.PorterDuff.Mode) null);
//        this.tabRippleColorStateList = MaterialResources.getColorStateList(context, a, styleable.TabLayout_tabRippleColor);
//        this.tabIndicatorAnimationDuration = a.getInt(styleable.TabLayout_tabIndicatorAnimationDuration, 300);
//        this.requestedTabMinWidth = a.getDimensionPixelSize(styleable.TabLayout_tabMinWidth, -1);
//        this.requestedTabMaxWidth = a.getDimensionPixelSize(styleable.TabLayout_tabMaxWidth, -1);
//        this.tabBackgroundResId = a.getResourceId(styleable.TabLayout_tabBackground, 0);
//        this.contentInsetStart = a.getDimensionPixelSize(styleable.TabLayout_tabContentStart, 0);
//        this.mode = a.getInt(styleable.TabLayout_tabMode, 1);
//        this.tabGravity = a.getInt(styleable.TabLayout_tabGravity, 0);
//        this.inlineLabel = a.getBoolean(styleable.TabLayout_tabInlineLabel, false);
//        this.unboundedRipple = a.getBoolean(styleable.TabLayout_tabUnboundedRipple, false);
//        a.recycle();
//        Resources res = this.getResources();
//        this.tabTextMultiLineSize = (float) res.getDimensionPixelSize(dimen.design_tab_text_size_2line);
//        this.scrollableTabMinWidth = res.getDimensionPixelSize(dimen.design_tab_scrollable_min_width);
//        this.applyModeAndGravity();
//    }
//
//    public void setSelectedTabIndicatorColor(@ColorInt int color) {
//        this.slidingTabIndicator.setSelectedIndicatorColor(color);
//    }
//
//    public void setScrollPosition(int position,
//                                  float positionOffset,
//                                  boolean updateSelectedText) {
//        this.setScrollPosition(position, positionOffset, updateSelectedText, true);
//    }
//
//    public void setScrollPosition(int position,
//                                  float positionOffset,
//                                  boolean updateSelectedText,
//                                  boolean updateIndicatorPosition) {
//        int roundedPosition = Math.round((float) position + positionOffset);
//        if (roundedPosition >= 0 && roundedPosition < this.slidingTabIndicator.getChildCount()) {
//            if (updateIndicatorPosition) {
//                this.slidingTabIndicator.setIndicatorPositionFromTabPosition(position, positionOffset);
//            }
//
//            if (this.scrollAnimator != null && this.scrollAnimator.isRunning()) {
//                this.scrollAnimator.cancel();
//            }
//
//            this.scrollTo(this.calculateScrollXForTab(position, positionOffset), 0);
//            if (updateSelectedText) {
//                this.setSelectedTabView(roundedPosition);
//            }
//
//        }
//    }
//
//    public void addTab(@NonNull VerticalTabLayout.Tab tab) {
//        this.addTab(tab, this.tabs.isEmpty());
//    }
//
//    public void addTab(@NonNull VerticalTabLayout.Tab tab,
//                       int position) {
//        this.addTab(tab, position, this.tabs.isEmpty());
//    }
//
//    public void addTab(@NonNull VerticalTabLayout.Tab tab,
//                       boolean setSelected) {
//        this.addTab(tab, this.tabs.size(), setSelected);
//    }
//
//    public void addTab(@NonNull VerticalTabLayout.Tab tab,
//                       int position,
//                       boolean setSelected) {
//        if (tab.parent != this) {
//            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
//        } else {
//            this.configureTab(tab, position);
//            this.addTabView(tab);
//            if (setSelected) {
//                tab.select();
//            }
//        }
//    }
//
//    private void addTabFromItemView(@NonNull TabItem item) {
//        VerticalTabLayout.Tab tab = this.newTab();
//        if (item.text != null) {
//            tab.setText(item.text);
//        }
//
//        if (item.icon != null) {
//            tab.setIcon(item.icon);
//        }
//
//        if (item.customLayout != 0) {
//            tab.setCustomView(item.customLayout);
//        }
//
//        if (!TextUtils.isEmpty(item.getContentDescription())) {
//            tab.setContentDescription(item.getContentDescription());
//        }
//
//        this.addTab(tab);
//    }
//
//    public void addOnTabSelectedListener(@NonNull VerticalTabLayout.OnTabSelectedListener listener) {
//        if (!this.selectedListeners.contains(listener)) {
//            this.selectedListeners.add(listener);
//        }
//    }
//
//    public void removeOnTabSelectedListener(@Nullable VerticalTabLayout.OnTabSelectedListener listener) {
//        this.selectedListeners.remove(listener);
//    }
//
//    @NonNull
//    public VerticalTabLayout.Tab newTab() {
//        VerticalTabLayout.Tab tab = this.createTabFromPool();
//        tab.parent = this;
//        tab.view = this.createTabView(tab);
//        return tab;
//    }
//
//    protected VerticalTabLayout.Tab createTabFromPool() {
//        VerticalTabLayout.Tab tab = (VerticalTabLayout.Tab) tabPool.acquire();
//        if (tab == null) {
//            tab = new VerticalTabLayout.Tab();
//        }
//
//        return tab;
//    }
//
//    protected boolean releaseFromTabPool(VerticalTabLayout.Tab tab) {
//        return tabPool.release(tab);
//    }
//
//    public int getTabCount() {
//        return this.tabs.size();
//    }
//
//    @Nullable
//    public VerticalTabLayout.Tab getTabAt(int index) {
//        return index >= 0 && index < this.getTabCount() ? (VerticalTabLayout.Tab) this.tabs.get(index) : null;
//    }
//
//    public int getSelectedTabPosition() {
//        return this.selectedTab != null ? this.selectedTab.getPosition() : -1;
//    }
//
//    public void removeTab(@NonNull VerticalTabLayout.Tab tab) {
//        if (tab.parent != this) {
//            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
//        } else {
//            this.removeTabAt(tab.getPosition());
//        }
//    }
//
//    public void removeTabAt(int position) {
//        int selectedTabPosition = this.selectedTab != null ? this.selectedTab.getPosition() : 0;
//        this.removeTabViewAt(position);
//        // remove tab
//        VerticalTabLayout.Tab removedTab = (VerticalTabLayout.Tab) this.tabs.remove(position);
//        if (removedTab != null) {
//            removedTab.reset();
//            this.releaseFromTabPool(removedTab);
//        }
//
//        int newTabCount = this.tabs.size();
//        // reset all remaining tab's position
//        for (int i = position; i < newTabCount; ++i) {
//            ((VerticalTabLayout.Tab) this.tabs.get(i)).setPosition(i);
//        }
//        // reselect tab
//        if (selectedTabPosition == position) {
//            this.selectTab(this.tabs.isEmpty() ? null : (VerticalTabLayout.Tab) this.tabs.get(Math.max(0, position - 1)));
//        }
//    }
//
//    public void removeAllTabs() {
//        for (int i = this.slidingTabIndicator.getChildCount() - 1; i >= 0; --i) {
//            this.removeTabViewAt(i);
//        }
//
//        Iterator i = this.tabs.iterator();
//
//        while (i.hasNext()) {
//            TabLayout.Tab tab = (TabLayout.Tab) i.next();
//            i.remove();
//            tab.reset();
//            this.releaseFromTabPool(tab);
//        }
//
//        this.selectedTab = null;
//    }
//
//    public void clearOnTabSelectedListeners() {
//        this.selectedListeners.clear();
//    }
//
//    public class TabLayoutOnPageChangeListener {
//    }
//
//    public class AdapterChangeListener {
//    }
//
//    public final class TabView extends LinearLayout {
//        private TabLayout.Tab tab;
//        private TextView textView;
//        private ImageView iconView;
//        @Nullable
//        private View badgeAnchorView;
//        @Nullable
//        private BadgeDrawable badgeDrawable;
//        @Nullable
//        private View customView;
//        @Nullable
//        private TextView customTextView;
//        @Nullable
//        private ImageView customIconView;
//        @Nullable
//        private Drawable baseBackgroundDrawable;
//        private int defaultMaxLines = 2;
//
//        public TabView(@NonNull Context context) {
//            super(context);
//            this.updateBackgroundDrawable(context);
//            ViewCompat.setPaddingRelative(this, TabLayout.this.tabPaddingStart, TabLayout.this.tabPaddingTop, TabLayout.this.tabPaddingEnd, TabLayout.this.tabPaddingBottom);
//            this.setGravity(17);
//            this.setOrientation(TabLayout.this.inlineLabel ? 0 : 1);
//            this.setClickable(true);
//            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
//            ViewCompat.setAccessibilityDelegate(this, (AccessibilityDelegateCompat) null);
//        }
//
//        private void updateBackgroundDrawable(Context context) {
//            if (TabLayout.this.tabBackgroundResId != 0) {
//                this.baseBackgroundDrawable = AppCompatResources.getDrawable(context, TabLayout.this.tabBackgroundResId);
//                if (this.baseBackgroundDrawable != null && this.baseBackgroundDrawable.isStateful()) {
//                    this.baseBackgroundDrawable.setState(this.getDrawableState());
//                }
//            } else {
//                this.baseBackgroundDrawable = null;
//            }
//
//            Drawable contentDrawable = new GradientDrawable();
//            ((GradientDrawable) contentDrawable).setColor(0);
//            Object background;
//            if (TabLayout.this.tabRippleColorStateList != null) {
//                GradientDrawable maskDrawable = new GradientDrawable();
//                maskDrawable.setCornerRadius(1.0E-5F);
//                maskDrawable.setColor(-1);
//                ColorStateList rippleColor = RippleUtils.convertToRippleDrawableColor(TabLayout.this.tabRippleColorStateList);
//                if (Build.VERSION.SDK_INT >= 21) {
//                    background = new RippleDrawable(rippleColor, TabLayout.this.unboundedRipple ? null : contentDrawable, TabLayout.this.unboundedRipple ? null : maskDrawable);
//                } else {
//                    Drawable rippleDrawable = DrawableCompat.wrap(maskDrawable);
//                    DrawableCompat.setTintList(rippleDrawable, rippleColor);
//                    background = new LayerDrawable(new Drawable[]{contentDrawable, rippleDrawable});
//                }
//            } else {
//                background = contentDrawable;
//            }
//
//            ViewCompat.setBackground(this, (Drawable) background);
//            TabLayout.this.invalidate();
//        }
//
//        private void drawBackground(@NonNull Canvas canvas) {
//            if (this.baseBackgroundDrawable != null) {
//                this.baseBackgroundDrawable.setBounds(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
//                this.baseBackgroundDrawable.draw(canvas);
//            }
//
//        }
//
//        protected void drawableStateChanged() {
//            super.drawableStateChanged();
//            boolean changed = false;
//            int[] state = this.getDrawableState();
//            if (this.baseBackgroundDrawable != null && this.baseBackgroundDrawable.isStateful()) {
//                changed |= this.baseBackgroundDrawable.setState(state);
//            }
//
//            if (changed) {
//                this.invalidate();
//                TabLayout.this.invalidate();
//            }
//
//        }
//
//        public boolean performClick() {
//            boolean handled = super.performClick();
//            if (this.tab != null) {
//                if (!handled) {
//                    this.playSoundEffect(0);
//                }
//
//                this.tab.select();
//                return true;
//            } else {
//                return handled;
//            }
//        }
//
//        public void setSelected(boolean selected) {
//            boolean changed = this.isSelected() != selected;
//            super.setSelected(selected);
//            if (changed && selected && Build.VERSION.SDK_INT < 16) {
//                this.sendAccessibilityEvent(4);
//            }
//
//            if (this.textView != null) {
//                this.textView.setSelected(selected);
//            }
//
//            if (this.iconView != null) {
//                this.iconView.setSelected(selected);
//            }
//
//            if (this.customView != null) {
//                this.customView.setSelected(selected);
//            }
//
//        }
//
//        public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
//            super.onInitializeAccessibilityEvent(event);
//            event.setClassName(androidx.appcompat.app.ActionBar.Tab.class.getName());
//        }
//
//        @TargetApi(14)
//        public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
//            super.onInitializeAccessibilityNodeInfo(info);
//            info.setClassName(androidx.appcompat.app.ActionBar.Tab.class.getName());
//            if (this.badgeDrawable != null && this.badgeDrawable.isVisible()) {
//                CharSequence customContentDescription = this.getContentDescription();
//                info.setContentDescription(customContentDescription + ", " + this.badgeDrawable.getContentDescription());
//            }
//
//        }
//
//        public void onMeasure(int origWidthMeasureSpec, int origHeightMeasureSpec) {
//            int specWidthSize = MeasureSpec.getSize(origWidthMeasureSpec);
//            int specWidthMode = MeasureSpec.getMode(origWidthMeasureSpec);
//            int maxWidth = TabLayout.this.getTabMaxWidth();
//            int widthMeasureSpec;
//            if (maxWidth <= 0 || specWidthMode != 0 && specWidthSize <= maxWidth) {
//                widthMeasureSpec = origWidthMeasureSpec;
//            } else {
//                widthMeasureSpec = MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, -2147483648);
//            }
//
//            super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
//            if (this.textView != null) {
//                float textSize = TabLayout.this.tabTextSize;
//                int maxLines = this.defaultMaxLines;
//                if (this.iconView != null && this.iconView.getVisibility() == 0) {
//                    maxLines = 1;
//                } else if (this.textView != null && this.textView.getLineCount() > 1) {
//                    textSize = TabLayout.this.tabTextMultiLineSize;
//                }
//
//                float curTextSize = this.textView.getTextSize();
//                int curLineCount = this.textView.getLineCount();
//                int curMaxLines = TextViewCompat.getMaxLines(this.textView);
//                if (textSize != curTextSize || curMaxLines >= 0 && maxLines != curMaxLines) {
//                    boolean updateTextView = true;
//                    if (TabLayout.this.mode == 1 && textSize > curTextSize && curLineCount == 1) {
//                        Layout layout = this.textView.getLayout();
//                        if (layout == null || this.approximateLineWidth(layout, 0, textSize) > (float) (this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight())) {
//                            updateTextView = false;
//                        }
//                    }
//
//                    if (updateTextView) {
//                        this.textView.setTextSize(0, textSize);
//                        this.textView.setMaxLines(maxLines);
//                        super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
//                    }
//                }
//            }
//
//        }
//
//        void setTab(@Nullable TabLayout.Tab tab) {
//            if (tab != this.tab) {
//                this.tab = tab;
//                this.update();
//            }
//
//        }
//
//        void reset() {
//            this.setTab((TabLayout.Tab) null);
//            this.setSelected(false);
//        }
//
//        final void update() {
//            TabLayout.Tab tab = this.tab;
//            View custom = tab != null ? tab.getCustomView() : null;
//            if (custom != null) {
//                ViewParent customParent = custom.getParent();
//                if (customParent != this) {
//                    if (customParent != null) {
//                        ((ViewGroup) customParent).removeView(custom);
//                    }
//
//                    this.addView(custom);
//                }
//
//                this.customView = custom;
//                if (this.textView != null) {
//                    this.textView.setVisibility(8);
//                }
//
//                if (this.iconView != null) {
//                    this.iconView.setVisibility(8);
//                    this.iconView.setImageDrawable((Drawable) null);
//                }
//
//                this.customTextView = (TextView) custom.findViewById(16908308);
//                if (this.customTextView != null) {
//                    this.defaultMaxLines = TextViewCompat.getMaxLines(this.customTextView);
//                }
//
//                this.customIconView = (ImageView) custom.findViewById(16908294);
//            } else {
//                if (this.customView != null) {
//                    this.removeView(this.customView);
//                    this.customView = null;
//                }
//
//                this.customTextView = null;
//                this.customIconView = null;
//            }
//
//            if (this.customView == null) {
//                if (this.iconView == null) {
//                    this.inflateAndAddDefaultIconView();
//                }
//
//                Drawable icon = tab != null && tab.getIcon() != null ? DrawableCompat.wrap(tab.getIcon()).mutate() : null;
//                if (icon != null) {
//                    DrawableCompat.setTintList(icon, TabLayout.this.tabIconTint);
//                    if (TabLayout.this.tabIconTintMode != null) {
//                        DrawableCompat.setTintMode(icon, TabLayout.this.tabIconTintMode);
//                    }
//                }
//
//                if (this.textView == null) {
//                    this.inflateAndAddDefaultTextView();
//                    this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
//                }
//
//                TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
//                if (TabLayout.this.tabTextColors != null) {
//                    this.textView.setTextColor(TabLayout.this.tabTextColors);
//                }
//
//                this.updateTextAndIcon(this.textView, this.iconView);
//                this.tryUpdateBadgeAnchor();
//                this.addOnLayoutChangeListener(this.iconView);
//                this.addOnLayoutChangeListener(this.textView);
//            } else if (this.customTextView != null || this.customIconView != null) {
//                this.updateTextAndIcon(this.customTextView, this.customIconView);
//            }
//
//            if (tab != null && !TextUtils.isEmpty(tab.contentDesc)) {
//                this.setContentDescription(tab.contentDesc);
//            }
//
//            this.setSelected(tab != null && tab.isSelected());
//        }
//
//        private void inflateAndAddDefaultIconView() {
//            ViewGroup iconViewParent = this;
//            if (BadgeUtils.USE_COMPAT_PARENT) {
//                iconViewParent = this.createPreApi18BadgeAnchorRoot();
//                this.addView((View) iconViewParent, 0);
//            }
//
//            this.iconView = (ImageView) LayoutInflater.from(this.getContext()).inflate(layout.design_layout_tab_icon, (ViewGroup) iconViewParent, false);
//            ((ViewGroup) iconViewParent).addView(this.iconView, 0);
//        }
//
//        private void inflateAndAddDefaultTextView() {
//            ViewGroup textViewParent = this;
//            if (BadgeUtils.USE_COMPAT_PARENT) {
//                textViewParent = this.createPreApi18BadgeAnchorRoot();
//                this.addView((View) textViewParent);
//            }
//
//            this.textView = (TextView) LayoutInflater.from(this.getContext()).inflate(layout.design_layout_tab_text, (ViewGroup) textViewParent, false);
//            ((ViewGroup) textViewParent).addView(this.textView);
//        }
//
//        @NonNull
//        private FrameLayout createPreApi18BadgeAnchorRoot() {
//            FrameLayout frameLayout = new FrameLayout(this.getContext());
//            LayoutParams layoutparams = new LayoutParams(-2, -2);
//            frameLayout.setLayoutParams(layoutparams);
//            return frameLayout;
//        }
//
//        @NonNull
//        private BadgeDrawable getOrCreateBadge() {
//            if (this.badgeDrawable == null) {
//                this.badgeDrawable = BadgeDrawable.create(this.getContext());
//            }
//
//            this.tryUpdateBadgeAnchor();
//            if (this.badgeDrawable == null) {
//                throw new IllegalStateException("Unable to create badge");
//            } else {
//                return this.badgeDrawable;
//            }
//        }
//
//        @Nullable
//        private BadgeDrawable getBadge() {
//            return this.badgeDrawable;
//        }
//
//        private void removeBadge() {
//            if (this.badgeAnchorView != null) {
//                this.tryRemoveBadgeFromAnchor();
//            }
//
//            this.badgeDrawable = null;
//        }
//
//        private void addOnLayoutChangeListener(@Nullable final View view) {
//            if (view != null) {
//                view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                        if (view.getVisibility() == 0) {
//                            TabLayout.TabView.this.tryUpdateBadgeDrawableBounds(view);
//                        }
//
//                    }
//                });
//            }
//        }
//
//        private void tryUpdateBadgeAnchor() {
//            if (this.hasBadgeDrawable()) {
//                if (this.customView != null) {
//                    this.tryRemoveBadgeFromAnchor();
//                } else if (this.iconView != null && this.tab != null && this.tab.getIcon() != null) {
//                    if (this.badgeAnchorView != this.iconView) {
//                        this.tryRemoveBadgeFromAnchor();
//                        this.tryAttachBadgeToAnchor(this.iconView);
//                    } else {
//                        this.tryUpdateBadgeDrawableBounds(this.iconView);
//                    }
//                } else if (this.textView != null && this.tab != null && this.tab.getTabLabelVisibility() == 1) {
//                    if (this.badgeAnchorView != this.textView) {
//                        this.tryRemoveBadgeFromAnchor();
//                        this.tryAttachBadgeToAnchor(this.textView);
//                    } else {
//                        this.tryUpdateBadgeDrawableBounds(this.textView);
//                    }
//                } else {
//                    this.tryRemoveBadgeFromAnchor();
//                }
//
//            }
//        }
//
//        private void tryAttachBadgeToAnchor(@Nullable View anchorView) {
//            if (this.hasBadgeDrawable()) {
//                if (anchorView != null) {
//                    this.setClipChildren(false);
//                    this.setClipToPadding(false);
//                    BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, this.getCustomParentForBadge(anchorView));
//                    this.badgeAnchorView = anchorView;
//                }
//
//            }
//        }
//
//        private void tryRemoveBadgeFromAnchor() {
//            if (this.hasBadgeDrawable()) {
//                if (this.badgeAnchorView != null) {
//                    this.setClipChildren(true);
//                    this.setClipToPadding(true);
//                    BadgeUtils.detachBadgeDrawable(this.badgeDrawable, this.badgeAnchorView, this.getCustomParentForBadge(this.badgeAnchorView));
//                    this.badgeAnchorView = null;
//                }
//
//            }
//        }
//
//        final void updateOrientation() {
//            this.setOrientation(TabLayout.this.inlineLabel ? 0 : 1);
//            if (this.customTextView == null && this.customIconView == null) {
//                this.updateTextAndIcon(this.textView, this.iconView);
//            } else {
//                this.updateTextAndIcon(this.customTextView, this.customIconView);
//            }
//
//        }
//
//        private void updateTextAndIcon(@Nullable TextView textView, @Nullable ImageView iconView) {
//            Drawable icon = this.tab != null && this.tab.getIcon() != null ? DrawableCompat.wrap(this.tab.getIcon()).mutate() : null;
//            CharSequence text = this.tab != null ? this.tab.getText() : null;
//            if (iconView != null) {
//                if (icon != null) {
//                    iconView.setImageDrawable(icon);
//                    iconView.setVisibility(0);
//                    this.setVisibility(0);
//                } else {
//                    iconView.setVisibility(8);
//                    iconView.setImageDrawable((Drawable) null);
//                }
//            }
//
//            boolean hasText = !TextUtils.isEmpty(text);
//            if (textView != null) {
//                if (hasText) {
//                    textView.setText(text);
//                    if (this.tab.labelVisibilityMode == 1) {
//                        textView.setVisibility(0);
//                    } else {
//                        textView.setVisibility(8);
//                    }
//
//                    this.setVisibility(0);
//                } else {
//                    textView.setVisibility(8);
//                    textView.setText((CharSequence) null);
//                }
//            }
//
//            if (iconView != null) {
//                MarginLayoutParams lp = (MarginLayoutParams) iconView.getLayoutParams();
//                int iconMargin = 0;
//                if (hasText && iconView.getVisibility() == 0) {
//                    iconMargin = (int) ViewUtils.dpToPx(this.getContext(), 8);
//                }
//
//                if (TabLayout.this.inlineLabel) {
//                    if (iconMargin != MarginLayoutParamsCompat.getMarginEnd(lp)) {
//                        MarginLayoutParamsCompat.setMarginEnd(lp, iconMargin);
//                        lp.bottomMargin = 0;
//                        iconView.setLayoutParams(lp);
//                        iconView.requestLayout();
//                    }
//                } else if (iconMargin != lp.bottomMargin) {
//                    lp.bottomMargin = iconMargin;
//                    MarginLayoutParamsCompat.setMarginEnd(lp, 0);
//                    iconView.setLayoutParams(lp);
//                    iconView.requestLayout();
//                }
//            }
//
//            CharSequence contentDesc = this.tab != null ? this.tab.contentDesc : null;
//            TooltipCompat.setTooltipText(this, hasText ? null : contentDesc);
//        }
//
//        private void tryUpdateBadgeDrawableBounds(@NonNull View anchor) {
//            if (this.hasBadgeDrawable() && anchor == this.badgeAnchorView) {
//                BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchor, this.getCustomParentForBadge(anchor));
//            }
//
//        }
//
//        private boolean hasBadgeDrawable() {
//            return this.badgeDrawable != null;
//        }
//
//        @Nullable
//        private FrameLayout getCustomParentForBadge(@NonNull View anchor) {
//            if (anchor != this.iconView && anchor != this.textView) {
//                return null;
//            } else {
//                return BadgeUtils.USE_COMPAT_PARENT ? (FrameLayout) anchor.getParent() : null;
//            }
//        }
//
//        private int getContentWidth() {
//            boolean initialized = false;
//            int left = 0;
//            int right = 0;
//            View[] var4 = new View[]{this.textView, this.iconView, this.customView};
//            int var5 = var4.length;
//
//            for (int var6 = 0; var6 < var5; ++var6) {
//                View view = var4[var6];
//                if (view != null && view.getVisibility() == 0) {
//                    left = initialized ? Math.min(left, view.getLeft()) : view.getLeft();
//                    right = initialized ? Math.max(right, view.getRight()) : view.getRight();
//                    initialized = true;
//                }
//            }
//
//            return right - left;
//        }
//
//        @Nullable
//        public TabLayout.Tab getTab() {
//            return this.tab;
//        }
//
//        private float approximateLineWidth(@NonNull Layout layout, int line, float textSize) {
//            return layout.getLineWidth(line) * (textSize / layout.getPaint().getTextSize());
//        }
//    }
//
//    public class SlidingTabIndicator extends LinearLayout {
//        private int selectedIndicatorHeight;
//        @NonNull
//        private final Paint selectedIndicatorPaint;
//        @NonNull
//        private final GradientDrawable defaultSelectionIndicator;
//        int selectedPosition = -1;
//        float selectionOffset;
//        private int layoutDirection = -1;
//        private int indicatorLeft = -1;
//        private int indicatorRight = -1;
//        private ValueAnimator indicatorAnimator;
//
//        public SlidingTabIndicator(Context context) {
//            super(context);
//            this.setWillNotDraw(false);
//            this.selectedIndicatorPaint = new Paint();
//            this.defaultSelectionIndicator = new GradientDrawable();
//        }
//
//        void setSelectedIndicatorColor(int color) {
//            if (this.selectedIndicatorPaint.getColor() != color) {
//                this.selectedIndicatorPaint.setColor(color);
//                // ?
//                ViewCompat.postInvalidateOnAnimation(this);
//            }
//        }
//
//        void setSelectedIndicatorHeight(int height) {
//            if (this.selectedIndicatorHeight != height) {
//                this.selectedIndicatorHeight = height;
//                ViewCompat.postInvalidateOnAnimation(this);
//            }
//        }
//
//        boolean childrenNeedLayout() {
//            int i = 0;
//            for (int z = this.getChildCount(); i < z; ++i) {
//                View child = this.getChildAt(i);
//                if (child.getWidth() <= 0) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
//            if (this.indicatorAnimator != null && this.indicatorAnimator.isRunning()) {
//                this.indicatorAnimator.cancel();
//            }
//
//            this.selectedPosition = position;
//            this.selectionOffset = positionOffset;
//            this.updateIndicatorPosition();
//        }
//
//        float getIndicatorPosition() {
//            return (float) this.selectedPosition + this.selectionOffset;
//        }
//
//        public void onRtlPropertiesChanged(int layoutDirection) {
//            super.onRtlPropertiesChanged(layoutDirection);
//            if (Build.VERSION.SDK_INT < 23 && this.layoutDirection != layoutDirection) {
//                this.requestLayout();
//                this.layoutDirection = layoutDirection;
//            }
//        }
//
//        // TODO
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
//                if (VerticalTabLayout.this.tabGravity == 1 || VerticalTabLayout.this.mode == 2) {
//                    int count = this.getChildCount();
//                    int largestTabHeight = 0;
//                    int gutter = 0;
//
//                    for (int z = count; gutter < z; ++gutter) {
//                        View child = this.getChildAt(gutter);
//                        if (child.getVisibility() == VISIBLE) {
//                            largestTabHeight = Math.max(largestTabHeight, child.getMeasuredHeight());
//                        }
//                    }
//
//                    if (largestTabHeight <= 0) {
//                        return;
//                    }
//
//                    // TODO
//                    gutter = (int) com.example.moocmock.ViewUtils.dpToPx(this.getContext(), 16);
//                    boolean remeasure = false;
//                    if (largestTabHeight * count > this.getMeasuredWidth() - gutter * 2) {
//                        VerticalTabLayout.this.tabGravity = 0;
//                        VerticalTabLayout.this.updateTabViews(false);
//                        remeasure = true;
//                    } else {
//                        for (int i = 0; i < count; ++i) {
//                            LinearLayout.LayoutParams lp = (LayoutParams) this.getChildAt(i).getLayoutParams();
//                            if (lp.width != largestTabHeight || lp.weight != 0.0F) {
//                                lp.width = largestTabHeight;
//                                lp.weight = 0.0F;
//                                remeasure = true;
//                            }
//                        }
//                    }
//
//                    if (remeasure) {
//                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//                    }
//                }
//            }
//        }
//
//        @Override
//        protected void onLayout(boolean changed, int l, int t, int r, int b) {
//            super.onLayout(changed, l, t, r, b);
//            if (this.indicatorAnimator != null && this.indicatorAnimator.isRunning()) {
//                this.indicatorAnimator.cancel();
//                long duration = this.indicatorAnimator.getDuration();
//                this.animateIndicatorToPosition(this.selectedPosition, Math.round((1.0F - this.indicatorAnimator.getAnimatedFraction()) * (float) duration));
//            } else {
//                this.updateIndicatorPosition();
//            }
//        }
//
//        private void updateIndicatorPosition() {
//            View selectedTitle = this.getChildAt(this.selectedPosition);
//            int left;
//            int right;
//            if (selectedTitle != null && selectedTitle.getWidth() > 0) {
//                left = selectedTitle.getLeft();
//                right = selectedTitle.getRight();
//                if (!VerticalTabLayout.this.tabIndicatorFullWidth && selectedTitle instanceof VerticalTabLayout.TabView) {
//                    this.calculateTabViewContentBounds((TabLayout));
//                }
//            }
//        }
//    }
//
//    public class Tab {
//        public static final int INVALID_POSITION = -1;
//        @Nullable
//        private Object tag;
//        @Nullable
//        private Drawable icon;
//        @Nullable
//        private CharSequence text;
//        @Nullable
//        private CharSequence contentDesc;
//        private int position = -1;
//        @Nullable
//        private View customView;
//        @VerticalTabLayout.LabelVisibility
//        private int labelVisibilityMode = TAB_LABEL_VISIBILITY_LABELED;
//        @Nullable
//        public VerticalTabLayout parent;
//        @Nullable
//        public VerticalTabLayout.TabView view;
//
//        public Tab() {
//        }
//
//        @Nullable
//        public Object getTag() {
//            return this.tag;
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setTag(@Nullable Object tag) {
//            this.tag = tag;
//            return this;
//        }
//
//        @Nullable
//        public View getCustomView() {
//            return customView;
//        }
//
//        public VerticalTabLayout.Tab setCustomView(@Nullable View view) {
//            this.customView = customView;
//            this.updateView();
//            return this;
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setCustomView(@LayoutRes int resId) {
//            LayoutInflater inflater = LayoutInflater.from(this.view.getContext());
//            return this.setCustomView(inflater.inflate(resId, this.view, false));
//        }
//
//        @Nullable
//        public Drawable getIcon() {
//            return icon;
//        }
//
//        public VerticalTabLayout.Tab setIcon(@Nullable Drawable icon) {
//            this.icon = icon;
//            // ?
//            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
//                this.parent.updateTabViews(true);
//            }
//
//            this.updateView();
//            // ?
//            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
//                this.view.invalidate();
//            }
//            return this;
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setIcon(@DrawableRes int resId) {
//            if (this.parent == null) {
//                throw new IllegalArgumentException("Tab not attached to a TabLayout");
//            } else {
//                return this.setIcon(AppCompatResources.getDrawable((this.parent.getContext()), resId));
//            }
//        }
//
//        public int getPosition() {
//            return position;
//        }
//
//        void setPosition(int position) {
//            this.position = position;
//        }
//
//        @Nullable
//        public CharSequence getText() {
//            return text;
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setText(@Nullable CharSequence text) {
//            if (TextUtils.isEmpty(this.contentDesc) && !TextUtils.isEmpty(text)) {
//                this.view.setContentDescription(text);
//            }
//            this.text = text;
//            this.updateView();
//            return this;
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setText(@StringRes int resId) {
//            if (this.parent == null) {
//                throw new IllegalArgumentException("Tab not attached to a TabLayout");
//            } else {
//                return this.setText(this.parent.getResources().getText(resId));
//            }
//        }
//
//        @NonNull
//        public BadgeDrawable getOrCreatedBadge() {
//            return this.view.getOrCreatedBadge();
//        }
//
//        public void removeBadge() {
//            this.view.removeBadge();
//        }
//
//        @Nullable
//        public BadgeDrawable getBadge() {
//            return this.view.getBadge();
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setTabLabelVisibility(@VerticalTabLayout.LabelVisibility int mode) {
//            this.labelVisibilityMode = mode;
//            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
//                this.parent.updateTabViews(true);
//            }
//
//            this.updateView();
//            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
//                this.view.invalidate();
//            }
//
//            return this;
//        }
//
//        @VerticalTabLayout.LabelVisibility
//        public int getTabLabelVisibility() {
//            return this.labelVisibilityMode;
//        }
//
//        public void select() {
//            if (this.parent == null) {
//                throw new IllegalArgumentException("Tab not attached to a TabLayout");
//            } else {
//                this.parent.selectedTab(this);
//            }
//        }
//
//        public boolean isSelected() {
//            if (this.parent == null) {
//                throw new IllegalArgumentException("Tab not attached to a TabLayout");
//            } else {
//                return this.parent.getSelectedTabPosition() == this.position;
//            }
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setContentDescription(@StringRes int resId) {
//            if (this.parent == null) {
//                throw new IllegalArgumentException("Tab not attached to a TabLayout");
//            } else {
//                return this.setContentDescription(this.parent.getResources().getText(resId));
//            }
//        }
//
//        @NonNull
//        public VerticalTabLayout.Tab setContentDescription(@Nullable CharSequence contentDesc) {
//            this.contentDesc = contentDesc;
//            this.updateView();
//            return this;
//        }
//
//        void updateView() {
//            if (this.view != null) {
//                this.view.update();
//            }
//        }
//
//        void reset() {
//            this.parent = null;
//            this.view = null;
//            this.tag = null;
//            this.icon = null;
//            this.text = null;
//            this.contentDesc = null;
//            this.position = -1;
//            this.customView = null;
//        }
//    }
//
//    public interface OnTabSelectedListener<T extends VerticalTabLayout.Tab> {
//        void onTabSelected(T var1);
//
//        void onTabUnselected(T var1);
//
//        void onTabReselected(T var1);
//    }
//
//    @IntDef({INDICATOR_GRAVITY_BOTTOM, INDICATOR_GRAVITY_CENTER, INDICATOR_GRAVITY_TOP, INDICATOR_GRAVITY_STRETCH})
//    @Retention(RetentionPolicy.SOURCE)
//    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
//    public @interface TabIndicatorGravity {
//    }
//
//    @IntDef(flag = true, value = {GRAVITY_FILL, GRAVITY_CENTER})
//    @Retention(RetentionPolicy.SOURCE)
//    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
//    public @interface TabGravity {
//    }
//
//    @IntDef({TAB_LABEL_VISIBILITY_LABELED, TAB_LABEL_VISIBILITY_UNLABELED})
//    public @interface LabelVisibility {
//    }
//
//    @IntDef({MODE_SCROLLABLE, MODE_AUTO, MODE_FIXED})
//    @Retention(RetentionPolicy.SOURCE)
//    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
//    public @interface Mode {
//    }
//}
