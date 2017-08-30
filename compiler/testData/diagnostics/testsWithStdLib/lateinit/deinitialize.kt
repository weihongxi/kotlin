// !DIAGNOSTICS: -UNUSED_VARIABLE -NOTHING_TO_INLINE
// !API_VERSION: 1.2
// FILE: test.kt

interface Base {
    var x: String
}

open class Foo : Base {
    override lateinit var x: String
    private lateinit var y: String

    var nonLateInit: Int = 1

    fun ok() {
        this::x.deinitialize()

        val otherInstance = Foo()
        otherInstance::x.deinitialize()
        Foo()::y.deinitialize()

        val inLambda = { this::x.deinitialize() }
    }

    fun onLiteral() {
        val p = this::x
        p.<!LATEINIT_INTRINSIC_CALL_ON_NON_LITERAL!>deinitialize<!>()
    }

    fun onNonLateinit() {
        this::nonLateInit.<!LATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT!>deinitialize<!>()
    }

    inline fun inlineFun() {
        this::x.<!LATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION!>deinitialize<!>()

        object {
            val z = this@Foo::x.deinitialize()
        }
    }
}

fun onNonAccessible() {
    Foo()::x.<!LATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY!>deinitialize<!>()
}
