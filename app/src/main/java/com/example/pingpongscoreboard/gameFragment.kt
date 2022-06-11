package com.example.pingpongscoreboard

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pingpongscoreboard.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [gameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class gameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    var currentSet : Int = 1
    var playerOnePoints : Int = 0
    var playerTwoPoints : Int = 0
    var playerOneSets : Int = 0
    var playerTwoSets : Int = 0
    var maxPoints : Int = 0
    var serveCounter : Int = 0
    var playerName1 : String = "Player 1"
    var playerName2 : String = "Player 2"
    var isPlayerOnePrio : Boolean = true
    var servesPerPlayer : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,R.layout.fragment_game,container,false)
        val args = gameFragmentArgs.fromBundle(requireArguments())
        Log.i("Player name 1", "'" + args.playerName1 + "'")
        Log.i("Player name 2", "'" + args.playerName2 + "'")
        Log.i("Sets", "'" + args.sets + "'")
        Log.i("Set points", "'" + args.setPoints + "'")
        Log.i("First serve", "'" + args.firstServe + "'")
        if (args.playerName1 == "") playerName1 = "Player 1" else playerName1 = args.playerName1
        if (args.playerName2 == "") playerName2 = "Player 2" else playerName2 = args.playerName2
        if (args.setPoints == "") maxPoints = 11 else maxPoints = args.setPoints!!.toInt()
        if (args.servesPP == "" || args.servesPP == "0") servesPerPlayer = 2 else servesPerPlayer = args.servesPP.toInt()
        isPlayerOnePrio =  if (args.firstServe == "Player 1") true else false
        binding.textViewPlayerName1.setText(playerName1)
        binding.textViewPlayerName2.setText(playerName2)
        binding.setsTextView.setText("Set " + currentSet)
        binding.setPoint1.setText(playerOneSets.toString())
        binding.setPoint2.setText(playerTwoSets.toString())
        binding.textPoint1.setText(playerOnePoints.toString())
        binding.textPoint2.setText(playerTwoPoints.toString())
        binding.textWinner.setText("")
        if (isPlayerOnePrio) binding.textViewPlayerName1.setTextAppearance(R.style.playerTextServer) else binding.textViewPlayerName2.setTextAppearance(R.style.playerTextServer)
        fun swap(){
            if (isPlayerOnePrio) {
                Log.i("Switch", "player 2")
                binding.textViewPlayerName1.setTextAppearance(R.style.playerTextNormal)
                binding.textViewPlayerName2.setTextAppearance(R.style.playerTextServer)
            }
            if (!isPlayerOnePrio){
                Log.i("Switch", "player 1")
                binding.textViewPlayerName2.setTextAppearance(R.style.playerTextNormal)
                binding.textViewPlayerName1.setTextAppearance(R.style.playerTextServer)
            }
            when (isPlayerOnePrio) {
                true -> isPlayerOnePrio = false
                false -> isPlayerOnePrio = true
            }
        }
        binding.plusPoint1.setOnClickListener {
            if (currentSet <= args.sets!!.toInt()) {
                playerOnePoints++
                if (playerOnePoints >= maxPoints-1 && playerTwoPoints >= maxPoints-1){
                    serveCounter = 0
                    servesPerPlayer = 1
                }
                serveCounter++
                Log.i("Serve counter", serveCounter.toString())
                if (serveCounter == servesPerPlayer) {
                    swap()
                    serveCounter = 0
                }
                if ((playerOnePoints == maxPoints && playerTwoPoints <= maxPoints - 2) ||
                    (playerOnePoints - playerTwoPoints == 2 && (playerOnePoints >= maxPoints + 1 || playerTwoPoints >= maxPoints + 1))
                ) {
                    currentSet++
                    playerOneSets++
                    playerOnePoints = 0
                    playerTwoPoints = 0
                    serveCounter = 0
                    if (args.servesPP == "" || args.servesPP == "0") servesPerPlayer = 2 else servesPerPlayer = args.servesPP.toInt()
                    swap()
                    if (currentSet<=args.sets!!.toInt()) binding.setsTextView.setText("Set " + currentSet)
                    binding.setPoint1.setText(playerOneSets.toString())
                    binding.textPoint1.setText(playerOnePoints.toString())
                    binding.textPoint2.setText(playerTwoPoints.toString())
                }
                if (currentSet > args.sets!!.toInt() && playerOneSets > playerTwoSets) binding.textWinner.setText(playerName1 + "\n is the winner")
                binding.textPoint1.setText(playerOnePoints.toString())
            }
        }
        binding.plusPoint2.setOnClickListener {
            if (currentSet <= args.sets!!.toInt()) {
                playerTwoPoints++
                if (playerOnePoints >= maxPoints-1 && playerTwoPoints >= maxPoints-1){
                    serveCounter = 0
                    servesPerPlayer = 1
                }
                serveCounter++
                Log.i("Serve counter", serveCounter.toString())
                if (serveCounter == servesPerPlayer) {
                    swap()
                    serveCounter = 0
                }
                if ((playerTwoPoints == maxPoints && playerOnePoints <= maxPoints-2) ||
                    (playerTwoPoints-playerOnePoints == 2 && (playerOnePoints >= maxPoints+1 || playerTwoPoints >= maxPoints+1))) {
                    currentSet++
                    playerTwoSets++
                    playerOnePoints = 0
                    playerTwoPoints = 0
                    serveCounter = 0
                    if (args.servesPP == "" || args.servesPP == "0") servesPerPlayer = 2 else servesPerPlayer = args.servesPP.toInt()
                    swap()
                    if (currentSet<=args.sets!!.toInt()) binding.setsTextView.setText("Set " + currentSet)
                    binding.setPoint2.setText(playerTwoSets.toString())
                    binding.textPoint1.setText(playerOnePoints.toString())
                    binding.textPoint2.setText(playerTwoPoints.toString())
                }
                binding.textPoint2.setText(playerTwoPoints.toString())
                if (currentSet > args.sets!!.toInt() && playerTwoSets > playerOneSets) binding.textWinner.setText(playerName2 + "\n is the winner")
            }
        }
        binding.minusPoint1.setOnClickListener {
            if (playerOnePoints>0) {
                playerOnePoints--
                binding.textPoint1.setText(playerOnePoints.toString())
                when(serveCounter){
                    1 -> serveCounter--
                    0 -> {serveCounter = 1
                        swap()
                        }
                    }
                }
            }
        binding.minusPoint2.setOnClickListener {
            if (playerTwoPoints>0) {
                playerTwoPoints--
                binding.textPoint2.setText(playerTwoPoints.toString())
                when(serveCounter){
                    1 -> serveCounter--
                    0 -> {serveCounter = 1
                        swap()
                        }
                    }
                }
            }
        binding.imageRestart.setOnClickListener {
            playerOnePoints = 0
            playerTwoPoints = 0
            currentSet = 1
            playerOneSets = 0
            playerTwoSets = 0
            serveCounter = 0
            if (isPlayerOnePrio) {
                Log.i("Switch", "player 2")
                binding.textViewPlayerName1.setTextAppearance(R.style.playerTextNormal)
                binding.textViewPlayerName2.setTextAppearance(R.style.playerTextServer)
            }
            if (!isPlayerOnePrio){
                Log.i("Switch", "player 1")
                binding.textViewPlayerName2.setTextAppearance(R.style.playerTextNormal)
                binding.textViewPlayerName1.setTextAppearance(R.style.playerTextServer)
            }
            when (isPlayerOnePrio) {
                true -> isPlayerOnePrio = false
                false -> isPlayerOnePrio = true
            }
            if (args.servesPP == "" || args.servesPP == "0") servesPerPlayer = 2 else servesPerPlayer = args.servesPP.toInt()
            binding.setsTextView.setText("Set " + currentSet)
            binding.setPoint1.setText(playerOneSets.toString())
            binding.setPoint2.setText(playerTwoSets.toString())
            binding.textPoint1.setText(playerOnePoints.toString())
            binding.textPoint2.setText(playerTwoPoints.toString())
            binding.textWinner.setText("")
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    override fun onPause() {
        super.onPause()
        activity?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment gameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            gameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}