package se.infomaker.actapublica.view

import java.io.File

class AppPaths
{

    fun isMacOSX(): Boolean
    {
        return "Mac OS X" == System.getProperty("os.name")
    }

    fun isWindows(): Boolean
    {
        return "Windows" == System.getProperty("os.name")
    }

    fun isLinux(): Boolean
    {
        return "Linux" == System.getProperty("os.name")
    }

    fun getSettingsDir(): File
    {
        val userHome = System.getProperty("user.home")
        if (isMacOSX()) {
            return createDir(userHome, listOf("Library", "Preferences", "Gravmaskin"))
        }
        if (isWindows()) {
            return createDir(userHome, listOf("AppData", "Roaming", "Gravmaskin"))
        }
        return createDir(userHome, listOf(".Gravmaskin"))
    }

    fun createDir(dir: String, subDirs: List<String>): File
    {
        var file = File(dir)
        for (subDir in subDirs) {
            file = File(file, subDir)
        }
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }
}