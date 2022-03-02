package xyz.sxsong.paperplane.ZoteroStorage.ZoteroDB

data class ItemsDownloadProgress(
    val libraryVersion: Int,
    val nDownloaded: Int,
    val total: Int //tbh useless
)