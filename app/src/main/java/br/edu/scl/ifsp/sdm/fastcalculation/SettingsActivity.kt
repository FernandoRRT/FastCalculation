package br.edu.scl.ifsp.sdm.fastcalculation

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    //view binding
    private val activitySettingsBinding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_settings) change this line
        setContentView(activitySettingsBinding.root)

        setSupportActionBar(activitySettingsBinding.gameTbIn.gameTbIn)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.subtitle = getString(R.string.settings)

// listener do botão.
        activitySettingsBinding.apply {
            letsGoBt.setOnClickListener {
                // vou passar para dentro do construtor de settings
                val settings = Settings(
                    playerNameEditText.text.toString(),
                    (roundSp.selectedView as TextView).text.toString().toInt(),
                    roundIntervalRg.run {
                        findViewById<RadioButton>(checkedRadioButtonId).text.toString()
                            .toLong() * 1000L
                    }
                )
//objeto do tipo intent. Quero dizer que estou abrindo uma nova tela. Não esquecer de importar a classe android.classs.intent
//Essa primeira linha é para abrir a tela do jogo
                val gameActivityIntent = Intent(this@SettingsActivity, GameActivity::class.java)
                /* Passei o objeto settings para a tela do jogo. Todas elas começam com uma chave name e uma chave valor NewKotlin file / Object.
                    Dentro dele criei um object pra passar essas informações. Um singleton para garantir que somente uma instância seja criada.
                    https://medium.com/@ZahraHeydari/singleton-pattern-in-kotlin-b09380c53b14 */
                gameActivityIntent.putExtra(EXTRA_SETTINGS, settings)
// Função que chama a próxima tela
                startActivity(gameActivityIntent)
// matei a tela para ela não ter os botões de voltar
                finish()
            }
        }

    }
}