package com.xiamo.xiaoli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamo.xiaoli.bean.Event
import com.xiamo.xiaoli.bean.SpineData
import com.xiamo.xiaoli.other.*
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus

class SetActivity : AppCompatActivity() {

    var spineDatas:MutableList<SpineData> = ArrayList()
    private var spineAdapter:SpineAdapter  ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        initData()

        spineAdapter = SpineAdapter(spineDatas)
        spineRecy.layoutManager = GridLayoutManager(this,4)
        spineRecy.adapter = spineAdapter

        spineAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            SpinePath = spineDatas[position].name
            SpineBg = spineDatas[position].bgRes
            EventBus.getDefault().post(Event(0,""))
            finish()
        }
    }

    private inner class SpineAdapter(@Nullable data:MutableList<SpineData>):
            BaseQuickAdapter<SpineData,BaseViewHolder>(R.layout.item_spine,data){
        override fun convert(helper: BaseViewHolder, item: SpineData) {
            var imgView = helper.getView<ImageView>(R.id.itemImg)
            GlideApp.with(mContext).load(item.imgRes).into(imgView)
        }

    }

    fun initData(){
              spineDatas.add(SpineData("005",R.mipmap.spine_bg_005,R.mipmap.spine_005))
              spineDatas.add(SpineData("006",R.mipmap.spine_bg_006,R.mipmap.spine_006))
              spineDatas.add(SpineData("008",R.mipmap.spine_bg_008,R.mipmap.spine_008))
              spineDatas.add(SpineData("011",R.mipmap.spine_bg_011,R.mipmap.spine_011))
              spineDatas.add(SpineData("012",R.mipmap.spine_bg_012,R.mipmap.spine_012))
              spineDatas.add(SpineData("013",R.mipmap.spine_bg_013,R.mipmap.spine_013))
              spineDatas.add(SpineData("014",R.mipmap.spine_bg_014,R.mipmap.spine_014))
              spineDatas.add(SpineData("015",R.mipmap.spine_bg_015,R.mipmap.spine_015))
              spineDatas.add(SpineData("020",R.mipmap.spine_bg_020,R.mipmap.spine_020))
              spineDatas.add(SpineData("021",R.mipmap.spine_bg_021,R.mipmap.spine_021))
              spineDatas.add(SpineData("022",R.mipmap.spine_bg_022,R.mipmap.spine_022))
              spineDatas.add(SpineData("024",R.mipmap.spine_bg_024,R.mipmap.spine_024))
              spineDatas.add(SpineData("026",R.mipmap.spine_bg_026,R.mipmap.spine_026))
              spineDatas.add(SpineData("027",R.mipmap.spine_bg_027,R.mipmap.spine_027))
              spineDatas.add(SpineData("029",R.mipmap.spine_bg_029,R.mipmap.spine_029))
              spineDatas.add(SpineData("030",R.mipmap.spine_bg_030,R.mipmap.spine_030))
              spineDatas.add(SpineData("031",R.mipmap.spine_bg_031,R.mipmap.spine_031))
              spineDatas.add(SpineData("032",R.mipmap.spine_bg_032,R.mipmap.spine_032))
              spineDatas.add(SpineData("034",R.mipmap.spine_bg_034,R.mipmap.spine_034))
              spineDatas.add(SpineData("035",R.mipmap.spine_bg_035,R.mipmap.spine_035))
              spineDatas.add(SpineData("036",R.mipmap.spine_bg_036,R.mipmap.spine_036))
              spineDatas.add(SpineData("037",R.mipmap.spine_bg_037,R.mipmap.spine_037))
              spineDatas.add(SpineData("038",R.mipmap.spine_bg_038,R.mipmap.spine_038))
              spineDatas.add(SpineData("039",R.mipmap.spine_bg_039,R.mipmap.spine_039))
              spineDatas.add(SpineData("046",R.mipmap.spine_bg_046,R.mipmap.spine_046))
              spineDatas.add(SpineData("048",R.mipmap.spine_bg_048,R.mipmap.spine_048))
              spineDatas.add(SpineData("051",R.mipmap.spine_bg_051,R.mipmap.spine_051))
              spineDatas.add(SpineData("054",R.mipmap.spine_bg_054,R.mipmap.spine_054))
              spineDatas.add(SpineData("060",R.mipmap.spine_bg_060,R.mipmap.spine_060))
              spineDatas.add(SpineData("061",R.mipmap.spine_bg_061,R.mipmap.spine_061))
              spineDatas.add(SpineData("062",R.mipmap.spine_bg_062,R.mipmap.spine_062))
              spineDatas.add(SpineData("063",R.mipmap.spine_bg_063,R.mipmap.spine_063))
    }
}