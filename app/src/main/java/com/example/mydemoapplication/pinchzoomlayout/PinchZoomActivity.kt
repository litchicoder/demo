package com.example.mydemoapplication.pinchzoomlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydemoapplication.R

class PinchZoomActivity : AppCompatActivity() {

    private var recyclerView: ZoomingRecyclerView? = null
    private var layoutManager: GridLayoutManager? = null
    private var adapter: GridAdapter? = null
    private var itemAnimator: ZoomItemAnimator? = null
    private var itemAnimator1: ZoomItemAnimator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinch_zoom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        recyclerView = findViewById(R.id.recycler_view) as ZoomingRecyclerView
        layoutManager = GridLayoutManager(this, 3)
        recyclerView?.setLayoutManager(layoutManager)

        adapter = GridAdapter()
        adapter?.setHasStableIds(true)
        recyclerView?.setAdapter(adapter)

        itemAnimator = ZoomItemAnimator()
        recyclerView?.let {
            itemAnimator?.setup(it)
        }

    }
}