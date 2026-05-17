package dk.vixo.kor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform