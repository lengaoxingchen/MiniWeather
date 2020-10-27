package cn.testrunner.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CityFragmentPagerAdapter(fm: FragmentManager, private var fragmentList: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {


    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }


    //表示ViewPager包含的页数,当ViewPager的页数发生变化时,必须要重写两个函数
    var childCount: Int = 0

    override fun notifyDataSetChanged() {
        this.childCount = count
        super.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        if (childCount > 0) {
            childCount--
            return POSITION_NONE
        }
        return super.getItemPosition(`object`)
    }

}