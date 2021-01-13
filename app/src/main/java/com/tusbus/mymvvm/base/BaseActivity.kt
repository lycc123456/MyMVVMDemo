package com.tusbus.mymvvm.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tusbus.mymvvm.R
import com.tusbus.mymvvm.manager.ActivityStackManager

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseActivity : AppCompatActivity() {
    inline fun <reified VM : ViewModel> viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStackManager.getInstance().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityStackManager.getInstance().removeActivity(this)
    }

    private fun initStatusColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.always_white_text)
        }
        if (ColorUtils.calculateLuminance(getColor(R.color.always_white_text)) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // 跟随系统
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

}