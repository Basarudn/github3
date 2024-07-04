package com.example.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.ui.list.UserAdapter


class FollowerFragment : Fragment() {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowViewModel by viewModels()

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username : String) = FollowerFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_USERNAME, username)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME) ?: return

        val adapter = UserAdapter()
        binding.rvFollow.layoutManager = LinearLayoutManager(context)
        binding.rvFollow.adapter = adapter
        viewModel.getFollowers(username)
        viewModel.followers.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
            Log.d("FollowerFragment","users: ${users}")
        }

        viewModel.isLoadingFollowers.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}