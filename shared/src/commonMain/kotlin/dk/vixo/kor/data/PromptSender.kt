package dk.vixo.kor.data

import java.io.IOException
import java.util.concurrent.TimeUnit

private const val PROMPT_TIMEOUT = 30L

class PromptSender {

    @Suppress("SwallowedException")
    fun runCommand(vararg args: String): Boolean = try {
        val processBuilder = ProcessBuilder()
        val process = processBuilder.command(*args)
            .redirectErrorStream(true)
            .start()

        val finished = process.waitFor(PROMPT_TIMEOUT, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            return false
        }
        process.exitValue() == 0
    } catch (e: IOException) {
        return false
    }

    @Suppress("SwallowedException")
    fun sendAndGetTermination(vararg args: String): Boolean = try {
        val processBuilder = ProcessBuilder()
        val process = processBuilder.command("claude", *args)
            .redirectErrorStream(true)
            .start()

        val finished = process.waitFor(PROMPT_TIMEOUT, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            return false
        }
        process.exitValue() == 0
    } catch (e: IOException) {
        return false
    }

    @Suppress("SwallowedException")
    fun send(prompt: String): String? = try {
        val processBuilder = ProcessBuilder()
        val process = processBuilder.command("claude", "-p", prompt, "--output-format", "json")
            .redirectErrorStream(false)
            .start()

        val finished = process.waitFor(PROMPT_TIMEOUT, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            return null
        }

        return process.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        null
    }
}
