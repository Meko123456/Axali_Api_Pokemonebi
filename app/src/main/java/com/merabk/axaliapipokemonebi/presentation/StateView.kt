package com.merabk.axaliapipokemonebi.presentation

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.StyleableRes
import com.merabk.axaliapipokemonebi.R
import com.merabk.axaliapipokemonebi.util.forEachChild
import com.merabk.axaliapipokemonebi.util.inflate

class StateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {
    var contentView: View?
        private set
    var loadingView: View?
        private set
    var errorView: View?
        private set
    var noDataView: View?
        private set

    @IdRes
    private val contentViewId: Int

    @IdRes
    private val loadingViewId: Int

    @IdRes
    private val errorViewId: Int

    @IdRes
    private val noDataViewId: Int

    private var showContentBehindLoader = false

    private val applyAlphaAnimationToLoadingView: Boolean

    private val infiniteAlphaAnim =
        AnimationUtils.loadAnimation(context, R.anim.infinite_alpha)


    init {
        //        layoutTransition = LayoutTransition().apply {
        //            disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        //            disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
        //            disableTransitionType(LayoutTransition.CHANGING)
        //            setDuration(600)
        //        }
        val array = context.obtainStyledAttributes(attrs, R.styleable.StateView)
        contentViewId = array.getResourceId(R.styleable.StateView_contentViewId, 0)
        loadingViewId = array.getResourceId(R.styleable.StateView_loadingViewId, 0)
        errorViewId = array.getResourceId(R.styleable.StateView_errorViewId, 0)
        noDataViewId = array.getResourceId(R.styleable.StateView_noDataViewId, 0)
        applyAlphaAnimationToLoadingView =
            array.getBoolean(R.styleable.StateView_applyAlphaAnimationToLoadingView, false)


        contentView = inflateAndAdd(array, R.styleable.StateView_contentLayout, View.VISIBLE)
        loadingView = inflateAndAdd(array, R.styleable.StateView_loadingLayout)
        errorView = inflateAndAdd(array, R.styleable.StateView_errorLayout)
        noDataView = inflateAndAdd(array, R.styleable.StateView_noDataLayout)
        showContentBehindLoader =
            array.getBoolean(R.styleable.StateView_showContentBehindLoadingLayout, false)

        array.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (contentView == null) {
            contentView = findViewById(contentViewId)
        }
        if (loadingView == null) {
            loadingView = findViewById(loadingViewId)
        }
        if (errorView == null) {
            errorView = findViewById(errorViewId)
        }
        if (noDataView == null) {
            noDataView = findViewById(noDataViewId)
        }
        showContent()
    }


    private fun inflateAndAdd(
        array: TypedArray,
        @StyleableRes index: Int,
        defaultVisibility: Int = View.GONE
    ): View? {
        val layoutId = array.getResourceId(index, 0)
        return if (layoutId != 0) {
            inflate(layoutId).apply {
                visibility = defaultVisibility
            }.also {
                addView(it)
            }
        } else {
            null
        }
    }

    fun showContent() {
        showOnly(contentView)
    }

    fun showLoading() {
        showOnly(
            loadingView,
            contentView.takeIf { showContentBehindLoader }
        )
    }

    fun showError() {
        showOnly(errorView)
    }

    fun showNoData() {
        showOnly(noDataView)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != View.VISIBLE) {
            loadingView?.clearAnimation()
        } else if (loadingView?.visibility == View.VISIBLE && applyAlphaAnimationToLoadingView) {
            loadingView?.startAnimation(infiniteAlphaAnim)
        }
    }
    //
    //    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
    //        val view = when (child) {
    //            is SwipeRefreshLayout,
    //            is ScrollingView,
    //            is ScrollView -> {
    //                child
    //            }
    //            else -> {
    //                NestedScrollView(context).apply {
    //                    addView(
    //                        child,
    //                        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    //                    )
    //                }
    //            }
    //        }
    //        super.addView(view, index, params)
    //    }

    private fun showOnly(vararg views: View?) {
        val filtered = views.filterNotNull().takeIf { it.isNotEmpty() } ?: return
        forEachChild {
            it.visibility = if (it in filtered) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        if (applyAlphaAnimationToLoadingView) {
            if (loadingView?.visibility == View.VISIBLE) {
                loadingView?.startAnimation(infiniteAlphaAnim)
            } else {
                loadingView?.clearAnimation()
            }
        }
    }

}