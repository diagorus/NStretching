package com.diagorus.nstretching.shared.util.textToSpeech

class SelfReference<T> private constructor(initializer: SelfReference<T>.() -> T) {

    val self: T by lazy {
        inner ?: throw IllegalStateException("Do not use `self` until `initializer` finishes.")
    }

    private val inner = initializer()

    companion object {
        fun <T> create(initializer: SelfReference<T>.() -> T): T {
            return SelfReference(initializer).self
        }
    }
}