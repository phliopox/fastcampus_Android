package com.ian.fastcam.chap8

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ian.fastcam.R
import com.ian.fastcam.databinding.Chap8FrameBinding

class FrameFragment : Fragment() {
    private lateinit var binding: Chap8FrameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap8FrameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val images = (it.getStringArray("images")?: emptyArray()).map {
                 uriString ->
                    FrameItem(Uri.parse(uriString))
            }.toList()

            val frameAdapter = FrameAdapter(images)
            binding.viewPager.adapter = frameAdapter

            TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager
            ){
                tab,position ->
                binding.viewPager.currentItem = tab.position
            }.attach()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        /*binding.toolbar.apply{
            title = "나만의 앨범"
           // (activity as AppCompatActivity).setSupportActionBar(this)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId){
                        androidx.appcompat.R.id.home->{
                            findNavController().navigateUp()
                        }
                    }
                    return true
                }

            })

        }*/
    }


}