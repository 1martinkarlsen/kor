package dk.vixo.kor.ui

import dk.vixo.kor.ui.loading.LoadingComponent
import dk.vixo.kor.ui.login.LoginComponent

sealed class Component {
    class Loading(component: LoadingComponent) : Component()
    object Home : Component()
    class Login(val component: LoginComponent) : Component()
}