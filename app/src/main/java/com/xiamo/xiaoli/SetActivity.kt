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
import com.xiamo.xiaoli.utils.SpineDataUtils
import kotlinx.android.synthetic.main.activity_set.*
import org.greenrobot.eventbus.EventBus

class SetActivity : AppCompatActivity() {

    var spineDatas:MutableList<SpineData> = ArrayList()
    private var spineAdapter:SpineAdapter  ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        spineDatas = SpineDataUtils.getSpinList()

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


}