package com.identicum.http

object Config {
    fun get(key: String): String? {
        val confValue: String? = System.getProperties().getProperty(key)
        if (confValue != null ) {
            return confValue
        }
        return System.getenv(key)
    }
}