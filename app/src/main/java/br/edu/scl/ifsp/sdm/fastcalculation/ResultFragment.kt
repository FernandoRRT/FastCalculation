package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.scl.ifsp.sdm.fastcalculation.Results.RESULT
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var fragmentResultBinding: FragmentResultBinding
    private var result: Float = 0.0f
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS) ?: Settings()
            result = it.getFloat(RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false)
        with(fragmentResultBinding) {
            //tive que fazer essa checagem antes de exibir o resultado, pois se o usuario clicasse que nem um louco o resultado dava infinito, o app crasha
            //isso deve-se a um bug na contegem do tempo do jogo. Nem sempre ele iniciava os milissegundos.
            //Não consegui implementar essa coreeção na outra tela. Acho que o erro está no handler
            val displayResult = if (result.isFinite()) result else 0.0f
            "%.1f".format(displayResult).also {
                resultTv.text = it
            }
        }
        fragmentResultBinding.restartBt.setOnClickListener {
            //resolvi passar através de fragments a resposta também
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.gameFl, GameFragment.newInstance(settings))
                ?.commit()
        }
        return fragmentResultBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(result: Float, settings: Settings) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putFloat(RESULT, result)
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }
}