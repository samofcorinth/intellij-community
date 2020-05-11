package com.jetbrains.kotlin.structuralsearch

class KotlinSSFunctionTest : KotlinSSTest() {
    override fun getBasePath() = "function"

    fun testFun() { doTest("fun a() { '_* }") }

    fun testFunLocal() { doTest("fun b() { '_* }") }

    fun testFunParam() { doTest("fun '_(b: Int, c: String) { '_* }") }

    fun testFunSingleParam() { doTest("fun '_('_ : '_) { '_* }") }

    fun testFunTypeParam() { doTest("fun<T, R> '_(a: T, b: R, c: T) { '_* }") }

    fun testFunReturnType() { doTest("fun '_(b: Int): Int { return b }") }

    fun testFunBlockBody() {
        doTest(
            """
            fun '_() {
                println()
            }
            """
        )
    }

    fun testFunPublicModifier() { doTest("public fun '_() { }") }

    fun testFunInternalModifier() { doTest("internal fun '_() { }") }

    fun testFunPrivateModifier() { doTest("private fun '_() { }") }

    fun testMethod() { doTest("fun a() { }") }

    fun testMethodProtectedModifier() { doTest("protected fun '_() { }") }

    fun testFunReceiverType() {
        doTest(
            "fun <'_T, '_E, '_R> '_name('_f : '_T.('_E) -> '_R) : ('_T, '_E) -> '_R = { '_t, '_e -> '_t.'_f('_e) }"
        )
    }
}