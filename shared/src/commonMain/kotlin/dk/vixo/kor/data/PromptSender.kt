package dk.vixo.kor.data

import java.io.IOException
import java.util.concurrent.TimeUnit

private const val PROMPT_TIMEOUT = 5L

class PromptSender {

    val processBuilder = ProcessBuilder()

    suspend fun runCommand(vararg args: String): Boolean = try {
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

    suspend fun sendAndGetTermination(vararg args: String): Boolean = try {
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

    suspend fun send(prompt: String): String? {
        val process = processBuilder.command("claude", "-p", prompt, "--output-format", "json")
            .redirectErrorStream(false)
            .start()

        val finished = process.waitFor(PROMPT_TIMEOUT, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            return null
        }

        return process.inputStream.bufferedReader().readText()
    }
}