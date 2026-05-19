package dk.vixo.kor.data.agent

import com.pty4j.PtyProcess
import com.pty4j.PtyProcessBuilder

class ClaudeAgentSessionImpl(
    override val id: String,
    override val name: String,
    override val workingDirectory: String,

) : ClaudeAgentSession {

    private var pty: PtyProcess? = null

    override fun start() {
        try {
            val process = PtyProcessBuilder()
                .setCommand(arrayOf("/bin/zsh", "-i", "-c", "claude"))
                .setEnvironment(System.getenv() + mapOf("TERM" to "xterm-256color"))
                //.setDirectory(workingDirectory)
                .start()
            pty = process
            Thread {
                process.inputStream.bufferedReader().forEachLine { line ->
                    println("PTY output: $line")
                }
                println("PTY exit code: ${process.exitValue()}")
            }.start()
        } catch (e: Exception) {
            println("PTY failed to start: ${e.message}")
        }
    }

    override fun stop() {
        pty?.destroy()
        pty = null
    }
}
