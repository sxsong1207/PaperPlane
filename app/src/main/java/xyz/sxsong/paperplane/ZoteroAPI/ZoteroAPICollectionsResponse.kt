package xyz.sxsong.paperplane.ZoteroAPI

import xyz.sxsong.paperplane.ZoteroAPI.Model.CollectionPOJO

data class ZoteroAPICollectionsResponse(
    val isCached: Boolean,
    val collections: List<CollectionPOJO>,
    val totalResults: Int
)