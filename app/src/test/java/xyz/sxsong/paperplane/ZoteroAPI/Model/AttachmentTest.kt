package xyz.sxsong.paperplane.ZoteroAPI.Model

import org.junit.Assert.*
import org.junit.Test

class AttachmentTest {
    @Test
    fun testNewTemplate() {
        val jsonObject = AttachmentPOJO.getNewAttachmentTemplate("title","filename", "parentKey")
        assertEquals(jsonObject.toString(), json)
    }

    val json = """[{"itemType":"attachment","parentItem":"parentKey","linkMode":"imported_url","title":"title","accessDate":"","url":"","note":"","tags":[],"relations":{},"contentType":"application/pdf","charset":"","filename":"filename","md5":null,"mtime":null}]"""
}