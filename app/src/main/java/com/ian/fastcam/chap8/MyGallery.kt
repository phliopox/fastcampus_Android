package com.ian.fastcam.chap8

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ian.fastcam.R
import com.ian.fastcam.TAG
import com.ian.fastcam.databinding.Chap8Binding

class MyGallery : Fragment() {
    private lateinit var binding: Chap8Binding
    private lateinit var imageAdapter: ImageAdapter
    //복수의 권한이 필요한 경우 RequestMultiplePermissions() 후 launch(배열) 로 전달
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                loadImages() // 여기서 요청할경우 권한 동의 후 바로 파일접근으로 넘어갈 수 있다.ㅊ
            }
        }
    private val requestImageUriLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ uriList->
            updateImages(uriList)
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap8Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadImageBtn.setOnClickListener {
            checkPermission()
        }
        initRecyclerView()

        binding.naviFrameFragmentBtn.setOnClickListener {
            val images = imageAdapter.currentList.filterIsInstance<ImageItems.Image>()
                .map { it.uri.toString() }.toTypedArray()
            findNavController().navigate(R.id.action_myGallery_to_frameFragment, bundleOf("images" to images))
        }

    binding.addAction.setOnClickListener {
        checkPermission()
    }
    /*    (activity as MenuHost).addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.chap8_main_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.action_add->{
                        checkPermission()
                        true
                    }
                    else -> false
                }

            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)*/
    }


    private fun checkPermission() {
        when {
           /* ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadImages()
                권한 허용한 경우는 launcher에서 처리
            }*/
            //첫 권한 요청 거절한 후 재 요청 -> 안내 문구를 보내야하는 경우
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showPermissionInfoDialog()
            }
            // 권한을 아직 허용한 적이 없고, 안내문구를 보내야하는 시점도 아닐 경우
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        // activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setMessage("이미지를 가져오기 위해서, 외부 저장소 읽기 권한이 필요합니다.")
            setNegativeButton("취소", null)
            setPositiveButton("동의") { _, _ -> requestReadExternalStorage() }
        }.show()
    }

    private fun requestReadExternalStorage() {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

    }
    private fun loadImages(){
        //모든 이미지타입
        requestImageUriLauncher.launch("image/*")
    }
    private fun updateImages(uriList : List<Uri>){
        Log.d(TAG, "MyGallery - updateImages: $uriList");
        val images = uriList.map{ImageItems.Image(it)}
        val updatedImage = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImage)
    }

    private fun initRecyclerView(){
        imageAdapter = ImageAdapter(object :ItemClickListener{
            override fun onLoadMoreClick() {
                checkPermission()
            }
        })
        binding.imageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context,2)
        }
    }

}