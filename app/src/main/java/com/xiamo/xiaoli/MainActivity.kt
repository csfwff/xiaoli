package com.xiamo.xiaoli

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamo.xiaoli.bean.Event
import com.xiamo.xiaoli.other.GdxAdapter
import com.xiamo.xiaoli.other.SpineBg
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AndroidApplication(),View.OnClickListener{

    var weekIndex = 0
    lateinit var mGdxAdapter: GdxAdapter
    lateinit var mGdxView: View
    val mHandler: Handler = Handler { false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        GlideApp.with(this).load(SpineBg).into(spineBgImg)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        weekRecy.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val weeks = listOf(
            "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
        )
        weekRecy.adapter = WeekAdapter(weeks)

        timeTv.typeface = Typeface.createFromAsset(assets, "fonts/digital.ttf")

        mHandler.postDelayed(TimeRunnable(), 0)

        initGDX()

        setBtn.setOnClickListener(this)

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Event) {
        if(event.id==0){
            gdxFl.removeAllViews()
            GlideApp.with(this).load(SpineBg).into(spineBgImg)
            initGDX()
        }
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
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            var batteryImgRes = R.mipmap.battery_4
            when{
                battery in 50..75 -> batteryImgRes = R.mipmap.battery_3
                battery in 30..50 -> batteryImgRes = R.mipmap.battery_2
                battery in 10..30 -> batteryImgRes = R.mipmap.battery_1
                battery in 0..10 -> batteryImgRes = R.mipmap.battery_0
            }
            GlideApp.with(context).load(batteryImgRes).into(batteryImg)

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

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.setBtn-> startActivity<SetActivity>()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}