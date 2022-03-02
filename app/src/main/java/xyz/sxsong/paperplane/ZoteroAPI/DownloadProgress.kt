package xyz.sxsong.paperplane.ZoteroAPI

data class DownloadProgress(
    val progress: Long,
    val total: Long,
    val mtime: Long = -1,
    val metadataHash: String = ""
)