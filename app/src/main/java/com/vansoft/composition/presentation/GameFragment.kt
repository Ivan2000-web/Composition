package com.vansoft.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.vansoft.composition.R
import com.vansoft.composition.databinding.FragmentGameBinding
import com.vansoft.composition.domain.entity.GameResult
import com.vansoft.composition.domain.entity.GameSettings
import com.vansoft.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    //Добавляем ViewModel к фрагменту
    private lateinit var viewModel: GameViewModel

    private val gameOptions by lazy {
        mutableListOf<Button>().apply {
            add(binding.gameBtn1)
            add(binding.gameBtn2)
            add(binding.gameBtn3)
            add(binding.gameBtn4)
            add(binding.gameBtn5)
            add(binding.gameBtn6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragment == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
        viewModel.startGame(level)
    }

    private fun setClickListenersToOptions(){
        for(gameOption in gameOptions){
            gameOption.setOnClickListener{
                viewModel.chooseAnswer(gameOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        //Инициализируем ViewModel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]

        viewModel.question.observe(viewLifecycleOwner){
            binding.gameSum.text = it.sum.toString()
            binding.gameNum1.text = it.visibleNumber.toString()
            for (i in 0 until gameOptions.size){
                gameOptions[i].text = it.options[i].toString()
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it, true) //Для API 24+ (Android 7.0+)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
            binding.gameAnswersProgress.setTextColor(getColorByState(it))
        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.gameTimer.text = it
        }

        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.gameAnswersProgress.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState){
            android.R.color.holo_green_light
        }else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun launchGameFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object{
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL,  level)
                }
            }
        }
    }

}