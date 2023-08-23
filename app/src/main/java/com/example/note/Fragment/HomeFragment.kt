package com.example.note.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note.DataBase.Note
import com.example.note.MainActivity
import com.example.note.R
import com.example.note.adapter.NoteAdapter
import com.example.note.databinding.FragmentHomeBinding
import com.example.note.veiwmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this,viewLifecycleOwner)



        notesViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()
        binding.floatingActionButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter= NoteAdapter()

        binding.RecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner

            ) {

                    note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }

        }

    }

    private fun updateUI(note: List<Note>?) {

        if (note != null) {
            if (note.isNotEmpty()) {
                binding.RecyclerView.visibility = View.VISIBLE
                binding.cardViewnotavalable.visibility = View.GONE

            } else {
                binding.RecyclerView.visibility = View.GONE
                binding.cardViewnotavalable.visibility = View.VISIBLE


            }
        }
    }

    //    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//
//        menu.clear()
//        inflater.inflate(R.menu.home_menu, menu)
////
////        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
////        mMenuSearch.isSubmitButtonEnabled = false
////        mMenuSearch.setOnQueryTextListener(this)
//    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchNote(query)
        return false
    }


    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
            searchNote(newText)
        }
        return true
    }


    @SuppressLint("SuspiciousIndentation")
    private fun searchNote(query: String?) {
        val searchquery = "%$query"
        notesViewModel.searchNote(searchquery).observe(

            this
        ) { list -> noteAdapter.differ.submitList(list) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
onQueryTextChange("")

    return true}
}
