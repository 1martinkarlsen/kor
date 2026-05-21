package dk.vixo.kor.data.agent

import com.jediterm.pty.PtyProcessTtyConnector
import com.jediterm.terminal.TtyConnector
import com.pty4j.PtyProcess
import com.pty4j.PtyProcessBuilder
import dk.vixo.kor.domain.agent.JvmAgentSession
import java.io.IOException

class ClaudeAgentSession(
    override val id: String,
    override val name: String
) : JvmAgentSession {

    private var pty: PtyProcess? = null

    override fun start() {
        try {
            pty = PtyProcessBuilder()
                .setCommand(arrayOf("/bin/zsh", "-i", "-c", "claude"))
                .setEnvironment(System.getenv() + mapOf("TERM" to "xterm-256color"))
                .start()
        } catch (io: IOException) {
            println("PTY failed to start: ${io.message}")
        }
    }

    override fun createTtyConnector(): TtyConnector {
        return PtyProcessTtyConnector(pty ?: error("Agent not started"), Charsets.UTF_8)
    }

    override fun stop() {
        pty?.destroy()
        pty = null
    }
}
