package dk.vixo.kor.data.agent

import com.pty4j.PtyProcess
import com.pty4j.PtyProcessBuilder
import dk.vixo.kor.domain.agent.AgentSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.sequences.forEach

class ClaudeAgentSession(
    override val id: String,
    override val name: String,
    override val workingDirectory: String,
) : AgentSession {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var pty: PtyProcess? = null

    private val _output = MutableSharedFlow<String>()
    override val output: Flow<String> = _output.asSharedFlow()

    override fun start() {
        try {
            val process = PtyProcessBuilder()
                .setCommand(arrayOf("/bin/zsh", "-i", "-c", "claude"))
                .setEnvironment(System.getenv() + mapOf("TERM" to "xterm-256color"))
                .setDirectory(workingDirectory)
                .start()
            pty = process

            scope.launch {
                process.inputStream.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        _output.emit(line)
                    }
                }
            }
        } catch (io: IOException) {
            println("PTY failed to start: ${io.message}")
        }
    }

    override fun stop() {
        pty?.destroy()
        pty = null
        scope.cancel()
    }
}
