package xyz.sxsong.paperplane

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import javax.inject.Inject

enum class SortMethod {
    TITLE,
    DATE,
    AUTHOR,
    DATE_ADDED
}

class PreferenceManager @Inject constructor(context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setSortMethod(method: String) {
        val sortMethod = stringToSortMethod(method)
        this.setSortMethod(sortMethod)
    }

    fun setSortMethod(method: SortMethod) {
        val methodString = sortMethodToString(method)
        val editor = sharedPreferences.edit()
        editor.putString("sort_method", methodString)
        editor.apply()
    }

    fun sortMethodToString(method: SortMethod): String {
        return when (method) {
            SortMethod.TITLE -> "TITLE"
            SortMethod.DATE -> "DATE"
            SortMethod.AUTHOR -> "AUTHOR"
            SortMethod.DATE_ADDED -> "DATE_ADDED"
        }
    }

    fun stringToSortMethod(methodString: String): SortMethod {
        return when (methodString) {
            "TITLE" -> SortMethod.TITLE
            "DATE" -> SortMethod.DATE
            "AUTHOR" -> SortMethod.AUTHOR
            "DATE_ADDED" -> SortMethod.DATE_ADDED
            else -> SortMethod.TITLE
        }
    }

    fun getSortMethod(): SortMethod {
        val methodString = sharedPreferences.getString("sort_method", "TITLE") ?: "TITLE"

        return stringToSortMethod(methodString)
    }

    fun isSortedAscendingly(): Boolean {
        /* We will define ascending as going from smallest to largest. As such, A-Z will be defined
        * as ascending and  Z-A will be descending.
        * Similarly, Ascending will refer to older dates to newer ones.
        * Ascending will be represented as an up arrow.*/
        val direction = sharedPreferences.getString("SORT_DIRECTION", "DEFAULT")
        return when (direction) {
            SORT_METHOD_ASCENDING -> true
            SORT_METHOD_DESCENDING -> false
            else -> true // ascending will be default.
        }
    }

    fun setSortDirection(direction: String) {
        if (direction != SORT_METHOD_DESCENDING && direction != SORT_METHOD_ASCENDING) {
            Log.e(
                "zotero",
                "got request to change sort method to $direction which is not understood"
            )
            return
        }
        val editor = sharedPreferences.edit()
        editor.putString("SORT_DIRECTION", direction)
        editor.apply()
    }

    fun getIsShowingOnlyPdfs(): Boolean {
        return sharedPreferences.getBoolean("is_showing_only_with_pdfs", false)
    }

    fun getIsShowingOnlyNotes(): Boolean {
        return sharedPreferences.getBoolean("is_showing_only_with_notes", false)
    }

    fun setIsShowingOnlyPdfs(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_showing_only_with_pdfs", value)
        editor.apply()
    }

    fun setIsShowingOnlyNotes(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_showing_only_with_notes", value)
        editor.apply()
    }

    fun setWebDAVAuthentication(address: String, username: String, password: String, allowInsecureSSL: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("use_webdav", true)
        editor.putString("webdav_address", address)
        editor.putString("webdav_username", username)
        editor.putString("webdav_password", password)
        editor.putBoolean("webdav_allowInsecureSSL", allowInsecureSSL)

        editor.apply()
    }

    fun destroyWebDAVAuthentication() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("use_webdav", false)
        editor.putString("webdav_address", "")
        editor.putString("webdav_username", "")
        editor.putString("webdav_password", "")
        editor.putBoolean("webdav_allowInsecureSSL", false)

        editor.apply()
    }

    fun getWebDAVUsername(): String {
        return sharedPreferences.getString("webdav_username", "") ?: ""
    }

    fun getWebDAVPassword(): String {
        return sharedPreferences.getString("webdav_password", "") ?: ""
    }

    fun getWebDAVAddress(): String {
        return sharedPreferences.getString("webdav_address", "") ?: ""
    }

    fun isWebDAVEnabled(): Boolean {
        return sharedPreferences.getBoolean("use_webdav", false)
    }

    fun setWebDAVEnabled(state: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean("use_webdav", state)
            commit()
        }
    }

    fun isInsecureSSLAllowed(): Boolean {
        return sharedPreferences.getBoolean("webdav_allowInsecureSSL", false)
    }

    fun isWebDAVEnabledForGroups(): Boolean {
        return sharedPreferences.getBoolean("use_webdav_shared_libraries", false)
    }

    fun setCustomAttachmentStorage(data: String) {
        val editor = sharedPreferences.edit()
        editor.putString("custom_attachment_storage_location", data)
        editor.apply()
    }

    fun getCustomAttachmentStorageLocation(): String {
        return sharedPreferences.getString("custom_attachment_storage_location", "") ?: ""
    }

    fun getStorageMode(): String {
        return sharedPreferences.getString("attachment_sync_location", "") ?: ""
    }

    fun useExternalCache() {
        /*Sets the attachment storage location as being external cache.
        * For use in case of errors related to the custom location. */
        val editor = sharedPreferences.edit()
        editor.putString("attachment_sync_location", "EXTERNAL_CACHE")
        editor.putString("custom_attachment_storage_location", "")
        editor.apply()
    }

    fun isAttachmentUploadingEnabled(): Boolean {
        return sharedPreferences.getBoolean("attachments_uploading_enabled", true)
    }

    fun firstRunForVersion28(): Boolean {
        /*check to see if this is the first time the user is opening on version 2.1c (25)*/
        val firstRun = sharedPreferences.getBoolean("firstrun_version28", true)
        if (firstRun) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstrun_version28", false)
            editor.apply()
        }
        return firstRun
    }

    fun firstRunForVersion42(): Boolean {
        /*check to see if this is the first time the user is opening on version 2.9 (42)*/
        val firstRun = sharedPreferences.getBoolean("firstrun_version42", true)
        if (firstRun) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("firstrun_version42", false)
            editor.apply()
        }
        return firstRun
    }

    fun shouldLiveSearch(): Boolean {
        /* This preference defines whether library search should update as the user types.
        * This may be problematic for devices with a slow refresh rate or slow IO. */
        return sharedPreferences.getBoolean("should_live_search", true)
    }

    fun hasShownCustomStorageWarning(): Boolean {
        return sharedPreferences.getBoolean("has_shown_custom_storage_warning", false)
    }

    fun setShownCustomStorageWarning(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("has_shown_custom_storage_warning", true)
        editor.apply()
    }

    fun shouldCheckMd5SumBeforeOpening(): Boolean{
        return sharedPreferences.getBoolean("check_md5sum_before_attachment_open", false)
    }

    companion object {
        val SORT_METHOD_ASCENDING = "ASCENDING"
        val SORT_METHOD_DESCENDING = "DESCENDING"
    }

}