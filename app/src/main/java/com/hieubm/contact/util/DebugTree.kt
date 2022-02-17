package com.hieubm.contact.util

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format("%s:%s", super.createStackElementTag(element), element.lineNumber)
    }
}