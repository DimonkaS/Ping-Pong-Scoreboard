package com.example.pingpongscoreboard

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.pingpongscoreboard.databinding.FragmentInitializationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [initializationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class initializationFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentInitializationBinding>(inflater,R.layout.fragment_initialization,container,false)
        val spinner: Spinner = binding.setsSpinner
        context?.let  {
        ArrayAdapter.createFromResource(it, R.array.sets_array,R.layout.spinner_item).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
            }
        }
        val playerSpinner: Spinner = binding.playerSpinner
        context?.let  {
            ArrayAdapter.createFromResource(it, R.array.players,R.layout.spinner_item).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                playerSpinner.adapter = adapter
            }
        }
        binding.continueButton.setOnClickListener { view: View ->
            val servesPP = binding.editTextServesPerPlayer.text.toString()
            val playerName1 = binding.editTextTextPersonName.text.toString()
            val playerName2 = binding.editTextTextPersonName2.text.toString()
            val setPoints : String? = binding.editTextSetPoints.text.toString()
            val sets : String? = spinner.selectedItem.toString()
            val firstServe : String? = playerSpinner.selectedItem.toString()
            val directions = initializationFragmentDirections.actionInitializationFragmentToGameFragment2(playerName1,playerName2,setPoints,sets,firstServe,servesPP)
            view.findNavController().navigate(directions)}
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment initializationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            initializationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}