package eu.tutorials.composematerialdesignsamples.apptorrentmovies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import eu.tutorials.composematerialdesignsamples.R
import eu.tutorials.composematerialdesignsamples.databinding.ActivityTorrentBinding
import java.io.File

class TorrentActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityTorrentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTorrentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        deleteSubFolders(applicationContext.getExternalFilesDir(null)!!.absolutePath)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(mBinding.mainBottomNav, navController)
    }

    private fun deleteSubFolders(path: String) {
        val currentFolder = File(path)
        val files: Array<File> = currentFolder.listFiles() ?: return
        for (f in files) {
            if (f.isDirectory) deleteSubFolders(f.toString())
            f.delete()
        }
    }
}
