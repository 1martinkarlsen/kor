package dk.vixo.kor.domain.agent

import com.jediterm.terminal.TtyConnector

interface PtyConnectable {
    fun createTtyConnector(): TtyConnector
}
