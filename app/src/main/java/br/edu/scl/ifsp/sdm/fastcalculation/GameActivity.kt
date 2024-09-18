package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.ActivityGameBinding
// ver se não tem que criar aqui a outra fragment para o resultado
class GameActivity : AppCompatActivity(), OnPlayGame  {
    private val activityGameBinding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityGameBinding.root)

        setSupportActionBar(activityGameBinding.gameTbIn.gameTbIn)
        supportActionBar?.apply {
            subtitle = getString(R.string.app_name)
            subtitle = getString(R.string.game)
        }
// aqui recebo os parâmetros da tela de settings
        settings = intent.getParcelableExtra<Settings>(EXTRA_SETTINGS) ?: Settings()

        supportFragmentManager.beginTransaction().replace(R.id.gameFl, WelcomeFragment.newInstance(settings)).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.restartGameMi -> {
                onPlayGame()
                true
            }
            R.id.exitMi -> {
                finish()
                true
            }
            else -> {
                false
            }
        }
    }
// aqui é chamado o fragmento do jogo que vai substituir o fragmento de boas vindas. ELa será chamada pela welcome fragment
    override fun onPlayGame() {
        supportFragmentManager.beginTransaction().replace(R.id.gameFl, GameFragment.newInstance(settings)).commit()
    }
}