package id.sch.smanpesanggaran.kurikulum

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.sch.smanpesanggaran.kurikulum.databinding.ActivityMainBinding
import id.sch.smanpesanggaran.kurikulum.databinding.LayoutErrorBinding
import id.sch.smanpesanggaran.kurikulum.ui.InfoDialogFragment
import id.sch.smanpesanggaran.kurikulum.utils.NetworkUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var errorBinding: LayoutErrorBinding
    private lateinit var networkUtils: NetworkUtils
    
    private var isErrorShowing = false

    companion object {
        private const val BASE_URL = "https://kurikulum.smanpesanggaran.sch.id/"
        private const val TIMEOUT_DURATION = 30000L // 30 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize error binding
        errorBinding = LayoutErrorBinding.bind(binding.layoutError.root)
        
        // Initialize network utils
        networkUtils = NetworkUtils(this)
        
        setupToolbar()
        setupWebView()
        setupSwipeRefresh()
        setupErrorPage()
        setupBackNavigation()
        observeNetworkStatus()
        
        // Initial load
        loadWebPage()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = getString(R.string.toolbar_title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                showInfoDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            settings.apply {
                // Enable JavaScript for modern web features
                javaScriptEnabled = true
                
                // Enable DOM Storage for web apps
                domStorageEnabled = true
                
                // Enable zoom controls
                builtInZoomControls = true
                displayZoomControls = false
                
                // Cache settings
                cacheMode = WebSettings.LOAD_DEFAULT
                
                // Enable mixed content (if needed)
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                
                // Allow file access
                allowFileAccess = true
                
                // Set user agent
                userAgentString = "$userAgentString PortalKurikulum/1.0"
            }
            
            // Set WebViewClient to handle navigation
            webViewClient = CustomWebViewClient()
            
            // Set WebChromeClient for progress
            webChromeClient = CustomWebChromeClient()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            // Set refresh colors
            setColorSchemeResources(
                R.color.accent,
                R.color.primary,
                R.color.primary_dark
            )
            
            // Set background color
            setProgressBackgroundColorSchemeResource(R.color.surface)
            
            // Set refresh listener
            setOnRefreshListener {
                refreshPage()
            }
        }
    }

    private fun setupErrorPage() {
        errorBinding.btnRetry.setOnClickListener {
            if (networkUtils.isNetworkAvailable()) {
                hideErrorPage()
                loadWebPage()
            } else {
                // Show toast or snackbar that network is still unavailable
                showNoNetworkMessage()
            }
        }
    }

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    isErrorShowing -> {
                        // Show exit confirmation
                        showExitConfirmation()
                    }
                    binding.webView.canGoBack() -> {
                        binding.webView.goBack()
                    }
                    else -> {
                        showExitConfirmation()
                    }
                }
            }
        })
    }

    private fun observeNetworkStatus() {
        networkUtils.observeNetworkStatus()
            .onEach { isConnected ->
                if (isConnected && isErrorShowing) {
                    // Network restored, offer to retry
                    hideErrorPage()
                    loadWebPage()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun loadWebPage() {
        if (networkUtils.isNetworkAvailable()) {
            binding.webView.loadUrl(BASE_URL)
        } else {
            showErrorPage()
        }
    }

    private fun refreshPage() {
        if (networkUtils.isNetworkAvailable()) {
            hideErrorPage()
            binding.webView.reload()
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            showErrorPage()
        }
    }

    private fun showErrorPage() {
        isErrorShowing = true
        binding.swipeRefreshLayout.isVisible = false
        binding.layoutError.root.isVisible = true
        binding.progressBar.isVisible = false
    }

    private fun hideErrorPage() {
        isErrorShowing = false
        binding.layoutError.root.isVisible = false
        binding.swipeRefreshLayout.isVisible = true
    }

    private fun showInfoDialog() {
        InfoDialogFragment.newInstance()
            .show(supportFragmentManager, InfoDialogFragment.TAG)
    }

    private fun showExitConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.exit_confirm_title)
            .setMessage(R.string.exit_confirm_message)
            .setPositiveButton(R.string.exit_confirm_yes) { _, _ ->
                finish()
            }
            .setNegativeButton(R.string.exit_confirm_no, null)
            .show()
    }

    private fun showNoNetworkMessage() {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            R.string.error_message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun updateNavigationIcon() {
        if (binding.webView.canGoBack()) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationIcon(R.drawable.ic_back)
            binding.toolbar.setNavigationOnClickListener {
                binding.webView.goBack()
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            binding.toolbar.navigationIcon = null
        }
    }

    // Custom WebViewClient
    inner class CustomWebViewClient : WebViewClient() {
        
        private var hasError = false

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            // Keep navigation within the app
            val url = request?.url?.toString() ?: return false
            
            // Allow navigation within the same domain
            if (url.contains("kurikulum.smanpesanggaran.sch.id")) {
                return false // Let WebView handle it
            }
            
            // For external links, you could open in browser
            // For now, keep everything in WebView
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            hasError = false
            binding.progressBar.isVisible = true
            binding.swipeRefreshLayout.isEnabled = false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.isVisible = false
            binding.swipeRefreshLayout.isRefreshing = false
            binding.swipeRefreshLayout.isEnabled = true
            
            if (!hasError) {
                hideErrorPage()
            }
            
            updateNavigationIcon()
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            
            // Only handle main frame errors
            if (request?.isForMainFrame == true) {
                hasError = true
                binding.swipeRefreshLayout.isRefreshing = false
                showErrorPage()
            }
        }

        @android.annotation.SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(
            view: WebView?,
            handler: android.webkit.SslErrorHandler?,
            error: android.net.http.SslError?
        ) {
            // For trusted school domain, proceed despite SSL errors
            // This is needed for old API devices with outdated CA certificates
            val url = error?.url ?: ""
            if (url.contains("smanpesanggaran.sch.id")) {
                handler?.proceed()
            } else {
                super.onReceivedSslError(view, handler, error)
            }
        }
    }

    // Custom WebChromeClient for progress
    inner class CustomWebChromeClient : WebChromeClient() {
        
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            
            binding.progressBar.apply {
                isVisible = newProgress < 100
                progress = newProgress
            }
        }
    }

    override fun onDestroy() {
        binding.webView.apply {
            stopLoading()
            destroy()
        }
        super.onDestroy()
    }
}
