package com.yfbx.widgets.fragment

import android.os.Bundle
import android.view.View
import com.yfbx.widgets.R
import com.yfbx.widgets.util.dp
import com.yfbx.widgets.util.setCorner
import kotlinx.android.synthetic.main.frag_rolll_recycler.*
import kotlinx.android.synthetic.main.item_carousel.view.*
import java.util.*

/**
 * Author: Edward
 * Date: 2018/12/10
 * Description:
 */


class RollRecyclerFrag : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycler.setCorner(6f)
        val data = ArrayList<String>()
        data.add("【日程】1-XXX执法，李某，2018-10-10")
        data.add("【日程】2-YYY执法，王某，2018-10-10")
        data.add("【日程】3-ZZZ执法，张某，2018-10-10")

        recycler.setAdapter(R.layout.item_carousel, data) { itemView, item ->
            itemView.notice_txt.text = item
        }

        recycler.start()
    }

    override fun getLayout(): Int {
        return R.layout.frag_rolll_recycler
    }

}
