package com.test.view.wl

import com.test.worker.Message
import com.test.worker.addMessageHandler
import com.test.worker.post
import com.test.worker.wl.WLWorker
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

private fun main() {
    val body = document.body!!

    val view = View()
    body.appendChild(view)

    fun log(tag: String, data: Any?) {
        val span = document.createElement("span")
        span.textContent = "$tag: $data"
        view.appendChild(span)
    }

    val worker = WLWorker()
    worker.addMessageHandler {
        log("W[$type]", data)
    }
    worker.post(Message("START WL!"))

    fun testBytes() {
        window.fetch("https://httpbin.org/get")
            .then { it.text() }
            .then { it }
            .then { data ->
                log("DATA", data)
                worker.post(Message(data))
            }
    }

    view.addEventListener("click", {
        testBytes()
    })

    testBytes()
}

fun View(): HTMLElement {
    val container = document.createElement("div")
        .unsafeCast<HTMLDivElement>()

    container.style.apply {
        width = "100%"
        height = "100%"
        display = "grid"
        backgroundColor = "pink"
    }

    container.textContent = "View WL"

    return container
}