package com.jaqen.base.ui

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

/**
 * @author chenp
 * @version 2017-04-13 10:02
 * 基础操作
 */
open class BaseActivity : AppCompatActivity(){

    protected fun initToolbar(toolbar: Toolbar, backEnable: Boolean = false, title: String = ""){
        setSupportActionBar(toolbar)
        setTitle(title)

        if (backEnable)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
                it.setHomeButtonEnabled(true)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}