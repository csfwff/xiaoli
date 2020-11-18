package com.xiamo.xiaoli

import android.graphics.PixelFormat
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceView
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamo.xiaoli.other.GdxAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AndroidApplication() {

    var weekIndex = 0
    lateinit var mGdxAdapter: GdxAdapter
    lateinit var mGdxView: View
    val mHandler: Handler = Handler { false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weekRecy.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val weeks = listOf(
            "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
        )
        weekRecy.adapter = WeekAdapter(weeks)

        timeTv.typeface = Typeface.createFromAsset(assets, "fonts/digital.ttf")

        mHandler.postDelayed(TimeRunnable(), 0)

        initGDX()

    }



    private fun initGDX(){
        val cfg = AndroidApplicationConfiguration()
        cfg.useTextureView = false
        cfg.useImmersiveMode = true
        cfg.a = 8
        cfg.b = cfg.a
        cfg.g = cfg.b
        cfg.r = cfg.g
        mGdxAdapter = GdxAdapter()
        mGdxView = initializeForView(mGdxAdapter, cfg)

        (mGdxView as SurfaceView).holder.setFormat(PixelFormat.TRANSLUCENT)
        (mGdxView as SurfaceView).setZOrderOnTop(true)

        gdxFl.addView(mGdxView)
    }


    inner class TimeRunnable : Runnable {
        override fun run() {
            val calendar = Calendar.getInstance()
            timeTv.text = SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(calendar.time)
            val nowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
            if (weekIndex != nowWeek) {
                weekIndex = nowWeek
                weekRecy.adapter?.notifyDataSetChanged()
            }
            mHandler.postDelayed(this, 1000)
        }
    }

    inner class WeekAdapter(private val datas: List<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_week, datas) {
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.setText(R.id.weekTv, item)
                .setTextColor(
                    R.id.weekTv,
                    mContext.resources.getColor(if (helper.adapterPosition == weekIndex) R.color.textColorClockBlack else R.color.textColorClockGray)
                )
        }

    }


}