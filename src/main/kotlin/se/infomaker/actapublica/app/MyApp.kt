package se.infomaker.actapublica.app

import se.infomaker.actapublica.view.MainView
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {

    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            MyApp.launchApp(args)
        }
        @JvmStatic
        fun launchApp(args: Array<String>) {
            launch<MyApp>(args)
        }
    }

}