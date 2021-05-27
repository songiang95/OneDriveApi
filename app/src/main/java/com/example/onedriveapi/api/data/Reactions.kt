package com.example.onedriveapi.api.data

import com.google.gson.annotations.SerializedName

class Reactions {
    var commentCount = 0
}

class Application {
    var displayName: String? = null
    var id: String? = null
}

class Device {
    var id: String? = null
}

class User {
    var displayName: String? = null
    var id: String? = null
}

class CreatedBy {
    var application: Application? = null
    var device: Device? = null
    var user: User? = null
}

class LastModifiedBy {
    var application: Application? = null
    var device: Device? = null
    var user: User? = null
}

class ParentReference {
    var driveId: String? = null
    var driveType: String? = null
    var id: String? = null
    var path: String? = null
}

class FileSystemInfo {
    var createdDateTime: String? = null
    var lastModifiedDateTime: String? = null
}

class View {
    var viewType: String? = null
    var sortBy: String? = null
    var sortOrder: String? = null
}

class Folder {
    var childCount = 0
    var view: View? = null
}

class SpecialFolder {
    var name: String? = null
}

class Hashes {
    var quickXorHash: String? = null
    var sha1Hash: String? = null
    var sha256Hash: String? = null
}

class File {
    var mimeType: String? = null
    var hashes: Hashes? = null
}

class Value {
    var createdDateTime: String? = null
    var cTag: String? = null
    var eTag: String? = null
    var id: String? = null
    var lastModifiedDateTime: String? = null
    var name: String? = null
    var size = 0
    var webUrl: String? = null
    var reactions: Reactions? = null
    var createdBy: CreatedBy? = null
    var lastModifiedBy: LastModifiedBy? = null
    var parentReference: ParentReference? = null
    var fileSystemInfo: FileSystemInfo? = null
    var folder: Folder? = null
    var specialFolder: SpecialFolder? = null

    @SerializedName("@microsoft.graph.downloadUrl")
    var microsoftGraphDownloadUrl: String? = null
    var file: File? = null
}

class Root {
    @SerializedName("@odata.context")
    var odataContext: String? = null

    @SerializedName("@odata.count")
    var odataCount = 0
    var value: List<Value>? = null
}