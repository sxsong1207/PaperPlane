package xyz.sxsong.paperplane.LibraryActivity.WebDAV

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import xyz.sxsong.paperplane.PreferenceManager
import xyz.sxsong.paperplane.R
import xyz.sxsong.paperplane.ZoteroAPI.Webdav
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.Locale

class WebDAVSetup : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_dav_setup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferenceManager = PreferenceManager(this)

        val username_editText = findViewById<EditText>(R.id.editText_username)
        val password_editText = findViewById<EditText>(R.id.editText_password)
        val address_editText = findViewById<EditText>(R.id.editText_address)
        val allowInsecureSSLCheckbox = findViewById<CheckBox>(R.id.checkBox_allow_insecure_ssl)

        username_editText.setText(preferenceManager.getWebDAVUsername())
        password_editText.setText(preferenceManager.getWebDAVPassword())
        address_editText.setText(preferenceManager.getWebDAVAddress())
        allowInsecureSSLCheckbox.isChecked = preferenceManager.isInsecureSSLAllowed()

        val submitButton = findViewById<Button>(R.id.btn_submit)
        val cancelButton = findViewById<Button>(R.id.btn_cancel)

        if (address_editText.text.toString() != "") {
            Toast.makeText(this, "WebDAV is already configured.", Toast.LENGTH_SHORT).show()
        }

        submitButton.setOnClickListener {
            val address = formatAddress(address_editText.text.toString())
            address_editText.setText(address) // update the address box with https:// if user forgot.
            val username = username_editText.text.toString()
            val password = password_editText.text.toString()
            if (address == "") {
                destroyWebDAVAuthentication()
            } else {
                makeConnection(address, username, password)
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    fun formatAddress(address: String): String {
        var mAddress = address.trim()
        if (mAddress == "") {
            return ""
        }
        mAddress = if (!mAddress.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
            "https://" + mAddress
        } else {
            mAddress.trim()
        }
        mAddress = if (mAddress.endsWith("/zotero")) {
            mAddress
        } else {
            if (mAddress.endsWith("/")) { // so we don't get server.com//zotero
                mAddress + "zotero"
            } else {
                mAddress + "/zotero"
            }
        }
        return mAddress
    }

    fun allowInsecureSSL(): Boolean {
        return findViewById<CheckBox>(R.id.checkBox_allow_insecure_ssl).isChecked
    }

    fun makeConnection(address: String, username: String, password: String) {
        val webDav = Webdav(address, username, password, allowInsecureSSL())
        startProgressDialog()
        Completable.fromAction {
            var status = false // default to false incase we get an exception
            var hadAuthenticationError = false
            var errorMessage = "unset"
            status = webDav.testConnection()
            if (status == false) {
                throw Exception("Unspecified error.")
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    setWebDAVAuthentication(address, username, password)
                }

                override fun onError(e: Throwable) {
                    notifyFailed("Error setting up webdav, message: $e")
                }

            })
    }

    var progressDialog: ProgressDialog? = null
    fun startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        progressDialog?.setTitle("Connecting to WebDAV Server")
        progressDialog?.setMessage("We are testing your connection to the WebDAV Server.")
        progressDialog?.isIndeterminate = true
        progressDialog?.show()
    }

    fun hideProgressDialog() {
        progressDialog?.hide()
        progressDialog = null
    }

    fun notifyFailed(message: String = "") {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Error connecting to webDAV server")
        if (message == "") {
            alertDialog.setMessage(
                "There was an error connecting to the webDAV server." +
                        "Please verify the address, username and password."
            )
        } else {
            alertDialog.setMessage(message)
        }
        alertDialog.setPositiveButton("Ok") { _, _ -> {} }
        alertDialog.show()
    }

    fun setWebDAVAuthentication(address: String, username: String, password: String) {
        preferenceManager.setWebDAVAuthentication(address, username, password, allowInsecureSSL())
        preferenceManager.setWebDAVEnabled(true)
        Toast.makeText(this, "Successfully added WebDAV.", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    fun destroyWebDAVAuthentication() {
        preferenceManager.destroyWebDAVAuthentication()
        preferenceManager.setWebDAVEnabled(false)
        this.finish()
    }
}
