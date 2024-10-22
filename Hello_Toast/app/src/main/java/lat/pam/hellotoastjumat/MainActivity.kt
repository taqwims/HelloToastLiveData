package lat.pam.hellotoastjumat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    private var mCount = 0

    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonSwitchPage = findViewById<Button>(R.id.button_pindah)
        val buttonBrowser = findViewById<Button>(R.id.button_browser)

        if( savedInstanceState != null){
            mCount = savedInstanceState.getInt("COUNT_KEY",0)
            mShowCount.text =  mCount.toString()
        }

        // Create the observer which updates the UI.
        val nameObserver = Observer<Int> { newName ->
            // Update the UI, in this case, a TextView.
            mShowCount.text = newName.toString()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentName.observe(this, nameObserver)

        buttonCountUp.setOnClickListener(View.OnClickListener {
            mCount = mCount + 1
            if (mShowCount != null)
                mShowCount.text = mCount.toString()
                model.currentName.setValue(mCount)
        })

        buttonToast.setOnClickListener(View.OnClickListener {
            val tulisan: String = mShowCount?.text.toString()
            val toast: Toast = Toast.makeText(this, "Angka yang dimunculkan "+tulisan, Toast.LENGTH_LONG)
            toast.show()
        })

        buttonSwitchPage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        })

        buttonBrowser.setOnClickListener(View.OnClickListener {
            val intentbrowse = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intentbrowse)
        })

//       button.setOnClickListener {
//          val anotherName = "John Doe"
//          model.currentName.setValue(anotherName)
//}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("COUNT_KEY", mCount)
    }


}