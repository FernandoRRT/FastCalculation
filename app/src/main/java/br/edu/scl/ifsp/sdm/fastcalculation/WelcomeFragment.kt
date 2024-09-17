package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var fragmentWelcomeBinding: FragmentWelcomeBinding
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS) ?: Settings()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        //como passar valores para o fragment. Mas é melhor que eu primeiro crie o string template para depois criar a alteração utilizando o settings text
        "${getString(R.string.welcome)}, ${settings.playerName}".also {
            fragmentWelcomeBinding.welcomeTv.text = it
            }
        fragmentWelcomeBinding.playBt.setOnClickListener {
            //aqui eu chamo a função que está no GameActivity
            (context as OnPlayGame).onPlayGame()
        }
        return fragmentWelcomeBinding.root
    }

    companion object {
        @JvmStatic
        //Determina aqui quais serão os parâmetros que a função newInstance irá receber
        fun newInstance(settings: Settings) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.restartGameMi).isVisible = false
    }
}