package xyz.sxsong.paperplane.ZoteroAPI

import java.util.concurrent.Future

interface ZoteroAPIDownloadAttachmentListener {
    fun onProgressUpdate(progress: Long, total: Long)
    fun onNetworkFailure()
    fun onComplete()
    fun onFailure(message: String = "")
    fun receiveTask(task: Future<Unit>)
    fun receiveMetadata(mtime: Long, metadataHash: String)
}