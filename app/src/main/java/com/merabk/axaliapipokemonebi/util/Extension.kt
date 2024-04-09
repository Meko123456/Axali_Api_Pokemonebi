package com.merabk.axaliapipokemonebi.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

fun ImageView.loadImageWithGlide(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .circleCrop()
        .into(this)

}

fun <T> Fragment.collectFlow(flow: Flow<T>, onCollect: suspend (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(onCollect)
        }
    }

inline fun AttributeSet?.obtainStyledAttributes(
    context: Context,
    styleable: IntArray,
    block: TypedArray.() -> Unit
) {
    if (this == null) {
        return
    }

    val typedArray = context.obtainStyledAttributes(this, styleable, 0, 0)
    try {
        block(typedArray)
    } finally {
        typedArray.recycle()
    }
}

inline fun <T, R> callAndMap(
    serviceCall: () -> Response<T>,
    mapper: (T) -> R
): Result<R> {
    return try {
        val response = serviceCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val mappedResult = mapper(body)
            Result.success(mappedResult)
        } else {
            Result.failure(Exception("Network call failed with code: ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context)
        .inflate(layoutRes, this, attachToRoot)
}

inline fun ViewGroup.forEachChild(action: (View) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildAt(i))
    }
}

inline fun <T> Result<T>.mapError(transformer: (exception: Throwable) -> Throwable): Result<T> {
    return when {
        isSuccess -> Result.success(getOrThrow())
        else -> Result.failure(transformer(exceptionOrNull()!!))
    }
}
