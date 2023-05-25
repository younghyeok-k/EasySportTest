package com.example.test


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.test.adapter.CustomAdapter
import com.example.test.adapter.SportListAdapter
import com.example.test.adapter.SportViewPagerAdapter
import com.example.test.api.SportService
import com.example.test.application.SharedManager
import com.example.test.dto.SportDto
import com.example.test.model.SportModel
import com.example.test.model.loginPost
import com.example.test.viewmodel.MainViewModel
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.houseViewPager)
    }
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }
    private val currentLocationButton: LocationButtonView by lazy {
        findViewById(R.id.currentLocationButton)
    }
    private val bottomSheetTitleTextView: TextView by lazy {
        findViewById(R.id.bottomSheetTitleTextView)
    }

    private val viewPagerAdapter = SportViewPagerAdapter(itemClicked = {
        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "[지금 이 가격에 예약하세요!!] ${it.title} ${it.price} 사진보기 : ${it.imgUrl}"
                )
                type = "text/plain"
            }
        startActivity(Intent.createChooser(intent, null))
    })

    private val recyclerAdapter = SportListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        viewPager.adapter = viewPagerAdapter
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selectedHouseModel.lat, selectedHouseModel.lng))
                        .animate(CameraAnimation.Easing)


                naverMap.moveCamera(cameraUpdate)
            }
        })
        val drawer_navi = findViewById<DrawerLayout>(R.id.layout_drawer)
        val menu_navi = findViewById<ImageView>(R.id.menu_navi)
        val naviview = findViewById<NavigationView>(R.id.naviview)
        val nav_header_view = naviview.getHeaderView(0)
        val close = nav_header_view.findViewById<ImageView>(R.id.close)
        val username = nav_header_view.findViewById<TextView>(R.id.username_header)
        val logout = findViewById<TextView>(R.id.logout)

        username.refreshDrawableState()

        naviview.setNavigationItemSelectedListener(this)
        menu_navi.setOnClickListener {
            drawer_navi.openDrawer(GravityCompat.START)

        }

        close.setOnClickListener {
            onBackPressed()
        }

        logout.setOnClickListener {
            sharedManager.clear()
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

//        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        val rv = findViewById<RecyclerView>(R.id.rv)
//        viewModel.liveloginList.observe(this, {
//
//            viewModel.getall()
//            viewModel.liveallList.observe(this, Observer{
//                val customAdapter =
//                    CustomAdapter(it as ArrayList<loginPost> /* = java.util.ArrayList<com.example.retrofitactivity.model.Post> */)
//                CustomAdapter(it as ArrayList<Data> /* = java.util.ArrayList<com.example.retrofitactivity.model.Post> */)
//                rv.adapter = customAdapter
//                rv.layoutManager = LinearLayoutManager(this)
//
//            }
    }

    override fun onMapReady(map: NaverMap) {


        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0


        naverMap.cameraPosition = CameraPosition(
            LatLng(35.180277, 128.091565), // 대상 지점
            16.0, // 줌 레벨
        )

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(35.180277, 128.091565))
        naverMap.moveCamera(cameraUpdate)

        val uiSetting = naverMap.uiSettings
//        val lv = findViewById<LocationButtonView>(R.id.location)
        uiSetting.isLocationButtonEnabled = false


        currentLocationButton.map = naverMap

        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow



        getSportListFromAPI()

    }

    private fun getSportListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SportService::class.java).also {
            it.getSportList()
                .enqueue(object : Callback<SportDto> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<SportDto>, response: Response<SportDto>) {
                        if (response.isSuccessful.not()) {
                            // 실패 처리에 대한 구현
                            return
                        }
                        response.body()?.let { dto ->
                            updateMarker(dto.items)
                            viewPagerAdapter.submitList(dto.items)
                            recyclerAdapter.submitList(dto.items) // 새 리스트로 갱신
                            Log.d("retrofit", "통신 성공")
                            bottomSheetTitleTextView.text = "${dto.items.size}개의 시설"
                        }
                    }

                    override fun onFailure(call: Call<SportDto>, t: Throwable) {
                        // 실패 처리에 대한 구현
                        Log.d("retrofit", "통신 실패")
                        Toast.makeText(this@MainActivity, "실패", Toast.LENGTH_SHORT)
                    }
                })
        }
    }

    private fun updateMarker(Sports: List<SportModel>) {
        Sports.forEach { Sports ->
            val marker = Marker()

            marker.position = LatLng(Sports.lat, Sports.lng)
            marker.onClickListener = this

            marker.map = naverMap
            marker.tag = Sports.id
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.GREEN
            marker.width = 50
            marker.height = 80
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onClick(overly: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.id == overly.tag
        }
        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {


        }
        val drawer_navi = findViewById<DrawerLayout>(R.id.layout_drawer)
        drawer_navi.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        val drawer_navi = findViewById<DrawerLayout>(R.id.layout_drawer)
        if (drawer_navi.isDrawerOpen(GravityCompat.START)) {
            drawer_navi.closeDrawers()
        } else {
            super.onBackPressed() //일반 백버튼 기능 실행
        }

    }

}
