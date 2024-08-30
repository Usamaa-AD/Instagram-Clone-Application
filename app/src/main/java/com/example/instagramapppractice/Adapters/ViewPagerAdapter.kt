package com.example.instagramapppractice.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    val fragmentsList = ArrayList<Fragment>()
    val titleList = ArrayList<String>()

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }
    fun addFragments(fragment: Fragment,title:String){
        fragmentsList.add(fragment)
        titleList.add(title)
    }

}