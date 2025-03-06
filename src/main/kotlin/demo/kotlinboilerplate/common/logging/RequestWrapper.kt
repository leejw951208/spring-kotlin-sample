package demo.kotlinboilerplate.common.logging

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils
import java.io.ByteArrayInputStream
import java.io.IOException

class RequestWrapper(request: HttpServletRequest): HttpServletRequestWrapper(request) {
    private val cachedInputStream: ByteArray = StreamUtils.copyToByteArray(request.inputStream)

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        val byteArrayInputStream = ByteArrayInputStream(cachedInputStream)
        return object : ServletInputStream() {
            override fun isFinished(): Boolean {
                return try {
                    byteArrayInputStream.available() == 0
                } catch (e: IOException) {
                    e.printStackTrace()
                    false
                }
            }

            override fun isReady(): Boolean = true

            override fun setReadListener(readListener: ReadListener) {
                throw UnsupportedOperationException()
            }

            @Throws(IOException::class)
            override fun read(): Int = byteArrayInputStream.read()
        }
    }
}
