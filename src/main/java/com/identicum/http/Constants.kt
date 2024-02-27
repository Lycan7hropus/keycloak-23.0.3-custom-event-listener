package com.identicum.http

public object Constants {
     val apiMaxConnections: Int = Config.get("apiMaxConnections")?.toInt() ?: 10
     val apiConnectTimeout: Long = Config.get("apiConnectTimeout")?.toLong() ?: 2000
     val apiSocketTimeout: Long = Config.get("apiSocketTimeout")?.toLong() ?: 5000
}