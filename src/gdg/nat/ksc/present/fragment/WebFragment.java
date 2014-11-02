package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends BaseFragment{
	private final String TAG = "TrackingWebFragment";
	
	private final String INTENT_LINK = "link";
	
	private WebView webView;
	
	public static WebFragment newInstance(String link){
		WebFragment fragment = new WebFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_LINK, link);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		return inflater.inflate(R.layout.fg_web, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		String link = "";
		if(bundle != null){
			if(bundle.containsKey(INTENT_LINK)){
				link = bundle.getString(INTENT_LINK);
			}
		}
		
		webView = (WebView) view.findViewById(R.id.website);
		if(webView.getVisibility() == View.VISIBLE){ return; }
		webView.setVisibility(View.INVISIBLE);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(false);
		
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress){
				super.onProgressChanged(view, newProgress);
				if(newProgress > 80 && webView.getVisibility() != View.VISIBLE){
					webView.setVisibility(View.VISIBLE);
				}
			}
		});
		webView.loadUrl(link);
	}
	
	@Override
	public String getFragmentTag(){
		return TAG;
	}
}