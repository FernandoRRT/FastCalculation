package br.edu.scl.ifsp.sdm.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.edu.scl.ifsp.sdm.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.scl.ifsp.sdm.fastcalculation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var fragmentGameBinding: FragmentGameBinding

    private lateinit var settings: Settings
    private lateinit var calculationGame: CalculationGame
    private var currentRound: CalculationGame.Round? = null
    private var startRoundTime = 0L
    private var totalGameTime = 0L
    private var hits = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS) ?: Settings()
        }
        calculationGame = CalculationGame(settings.rounds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)

        val onClickListener = View.OnClickListener {
            val value = (it as Button).text.toString().toInt()
            if (value == currentRound?.answer) {
                totalGameTime += System.currentTimeMillis() - startRoundTime
                hits++
            } else {
                totalGameTime += settings.roundInterval
                hits--
            }
            play()
        }
        fragmentGameBinding.apply {
            alternativeOneBt.setOnClickListener(onClickListener)
            alternativeTwoBt.setOnClickListener(onClickListener)
            alternativeThreeBt.setOnClickListener(onClickListener)
        }

        play()

        return fragmentGameBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(settings: Settings) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }

    private fun play() {
        currentRound = calculationGame.nextRound()
        if (currentRound != null) {
            fragmentGameBinding.apply {
                "Round: ${currentRound!!.round}/${settings.rounds}".also {
                    roundTv.text = it
                }
                questionTv.text = currentRound!!.question
                alternativeOneBt.text = currentRound!!.alt1.toString()
                alternativeOneBt.text = currentRound!!.alt2.toString()
                alternativeOneBt.text = currentRound!!.alt3.toString()
            }
            startRoundTime = System.currentTimeMillis()
        } else {
            with(fragmentGameBinding) {
                roundTv.text = getString(R.string.points)
                val points = hits * 10f / (totalGameTime / 1000L)
                "%.1f".format(points).also {
                    questionTv.text = it
                }
                alternativeOneBt.visibility = View.GONE
                alternativeTwoBt.visibility = View.GONE
                alternativeThreeBt.visibility = View.GONE
                //parei em 03:00:00
            }
        }
    }
}