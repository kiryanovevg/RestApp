package com.evgeniy.restapp.UI.MainActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.evgeniy.restapp.R
import com.evgeniy.restapp.UI.ConversationScreen.ConversationFragment
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (VKAccessToken.currentToken() == null) {
            login()
        } else {
            if (savedInstanceState == null) showFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        showFragment()
                    }
                    override fun onError(error: VKError) {
                        if (VKAccessToken.currentToken() == null) {
                            login()
                        }
                    }
                })) {

            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun login() {
        VKSdk.login(this, "messages")
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, ConversationFragment())
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
            }

            VKSdk.logout()
            login()
        }

        return super.onOptionsItemSelected(item)
    }
}
