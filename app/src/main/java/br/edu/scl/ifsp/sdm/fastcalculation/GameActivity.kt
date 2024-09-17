package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val activityGameBinding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }
// lateinit var significa que a variável será inicializada depois.
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activityGameBinding.root)
//se ficar algo zoado aqui, voltar aula em: 1:27:41
        setSupportActionBar(activityGameBinding.gameTbIn.gameTbIn)
        supportActionBar?.apply {
            subtitle = getString(R.string.app_name)
            subtitle = getString(R.string.game)
        }
// aqui recebo os parâmetros da tela de settings
        settings = intent.getParcelableExtra<Settings>(EXTRA_SETTINGS) ?: Settings()
        Toast.makeText(this, settings.toString(), Toast.LENGTH_SHORT).show()
    }
}