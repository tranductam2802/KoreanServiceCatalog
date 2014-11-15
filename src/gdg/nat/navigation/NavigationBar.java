package gdg.nat.navigation;

import gdg.nat.base.BaseActivity;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.config.Config;
import gdg.nat.util.GdgLog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigationBar extends RelativeLayout implements IOnNavigation {
	private final String TAG = "TrackingNavigationBar";
	public static final int TYPE_DEFAULT = 0;

	private final boolean IS_LOCK_MEASURE = true;

	private BaseActivity activity;
	private LayoutInflater layoutInflater;
	private int currentResourceId = 0;

	private RelativeLayout viewHead;
	private LinearLayout viewLeft, viewCenter, viewRight;
	private TextView txtTitle;

	private String oldTitle = "";
	private float oldTitleWidth = 0;

	public NavigationBar(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		layoutInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		layoutInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (Config.IS_TRACKING_USER) {
			Log.i(TAG, "onLayout:\nChanged = " + changed + "\nLeft = " + l
					+ "\nRight = " + r + "\nTop = " + t + "\nBottom = " + b);
		}
		super.onLayout(changed, l, t, r, b);
		measureView();
	}

	public void init(BaseActivity baseActivity) {
		this.activity = baseActivity;
	}

	public void terminate() {
		this.activity = null;
	}

	/** Sync view for navigation bar and notify data GUI changed */
	private void syncBar(BaseFragment activePage) {
		if (activePage == null)
			return;

		int type = getViewType(activePage);
		switch (type) {
			case TYPE_DEFAULT:
				setupDefaultView(activePage);
				break;
		}
	}

	private int getViewType(BaseFragment activePage) {
		if (activePage == null) {
			return TYPE_DEFAULT;
		} else {
			return TYPE_DEFAULT;
		}
	}

	/** Setup view */
	private void setupDefaultView(final BaseFragment activePage) {
		int resId = R.layout.navibar_default;
		updateResourceId(resId);

		// TODO:
		if (activePage == null)
			return;

		// Get view instance
		final TextView txtLeft = (TextView) findViewById(R.id.btn_left);
		final TextView txtRight = (TextView) findViewById(R.id.btn_right);
		final EditText edtSearch = (EditText) findViewById(R.id.search_bar);

		// Setup view GUI
		if (activity.getNavigationManager().isEmpty()
				&& edtSearch.getVisibility() != View.VISIBLE) {
			txtLeft.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home,
					0, 0, 0);
		} else {
			txtLeft.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back,
					0, 0, 0);
		}
		if (activePage.getTitleResource() == BaseFragment.RESOURCE_EMPTY) {
			txtTitle.setText(activePage.getTitle());
		} else {
			txtTitle.setText(activePage.getTitleResource());
		}
		txtRight.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search,
				0, 0, 0);

		// Setup view listener
		if (activePage instanceof INaviDefaultViewListener) {
			txtLeft.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (edtSearch.getVisibility() == View.VISIBLE) {
						if (activity.getNavigationManager().isEmpty()) {
							txtLeft.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.ic_home, 0, 0, 0);
						} else {
							txtLeft.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.ic_back, 0, 0, 0);
						}
						txtTitle.setVisibility(View.VISIBLE);
						edtSearch.setVisibility(View.GONE);
						measureView();
					} else {
						((INaviDefaultViewListener) activePage).onGoBack();
					}
				}
			});

			txtRight.setOnClickListener(new OnClickListener() {
				@SuppressWarnings("unused")
				@Override
				public void onClick(View v) {
					if (edtSearch.getVisibility() == View.VISIBLE) {
						String keyword = edtSearch.getText().toString();
						if (keyword.length() > 0) {
							((INaviDefaultViewListener) activePage)
									.onSearch(keyword);
						}
					} else {
						txtLeft.setCompoundDrawablesWithIntrinsicBounds(
								R.drawable.ic_back, 0, 0, 0);
						txtTitle.setVisibility(View.GONE);
						edtSearch.setVisibility(View.VISIBLE);
						if (!IS_LOCK_MEASURE
								&& viewCenter.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
							LayoutParams layoutParams = (LayoutParams) viewCenter
									.getLayoutParams();
							layoutParams
									.addRule(RelativeLayout.CENTER_VERTICAL);
							layoutParams.addRule(
									RelativeLayout.CENTER_HORIZONTAL, -1);
							layoutParams.addRule(RelativeLayout.RIGHT_OF,
									viewLeft.getId());
							layoutParams.addRule(RelativeLayout.LEFT_OF,
									viewRight.getId());
							viewCenter.setLayoutParams(layoutParams);
						}
					}
				}
			});
			edtSearch
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView v, int actionId,
								KeyEvent event) {
							if (actionId == EditorInfo.IME_ACTION_SEARCH) {
								String keyword = v.getText().toString();
								if (keyword.length() > 0) {
									((INaviDefaultViewListener) activePage)
											.onSearch(keyword);
								}
								return true;
							}
							return false;
						}
					});
		}
	}

	private void updateResourceId(int resId) {
		if (resId != currentResourceId) {
			removeAllViews();
			layoutInflater.inflate(resId, this);
			currentResourceId = resId;
			initView();
		}
	}

	/** Find and initial default view */
	private void initView() {
		viewHead = (RelativeLayout) findViewById(R.id.navibar_head);
		viewLeft = (LinearLayout) findViewById(R.id.navibar_left_group);
		viewCenter = (LinearLayout) findViewById(R.id.navibar_center_group);
		viewRight = (LinearLayout) findViewById(R.id.navibar_right_group);
		txtTitle = (TextView) findViewById(R.id.title);
	}

	public void setTitle(int resId) {
		if (txtTitle == null)
			return;
		txtTitle.setText(resId);
		measureView();
	}

	public void setTitle(String title) {
		if (txtTitle == null)
			return;
		txtTitle.setText(title);
		measureView();
	}

	// Measure center text view in navigation bar
	@SuppressWarnings("unused")
	private void measureView() {
		if (!IS_LOCK_MEASURE && txtTitle != null && viewLeft != null
				&& viewRight != null && txtTitle.getText().length() > 0
				&& viewCenter != null && viewHead != null) {
			/*
			 * Get width of view group left and right. The double size of bigger
			 * view group is the space center text view can not display. In this
			 * case, we do not get the layout margin of view groups.
			 */
			float vgpWidth = viewLeft.getWidth();
			if (vgpWidth < viewRight.getWidth())
				vgpWidth = viewRight.getWidth();

			String title = txtTitle.getText().toString();
			if (vgpWidth == oldTitleWidth && oldTitle.equals(title)) {
				return;
			} else {
				oldTitleWidth = vgpWidth;
				oldTitle = title;
			}

			vgpWidth *= 2;
			vgpWidth = this.getWidth() - vgpWidth;

			// Get title and calculate
			float titleWidth = 0;
			if (txtTitle.getVisibility() == VISIBLE) {
				titleWidth = txtTitle.getPaint().measureText(title);
			}

			if (viewCenter.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
				LayoutParams layoutParams = (LayoutParams) viewCenter
						.getLayoutParams();
				layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
				if (vgpWidth > titleWidth) {
					layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);
					layoutParams.addRule(RelativeLayout.LEFT_OF, 0);
				} else {
					layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, -1);
					layoutParams.addRule(RelativeLayout.RIGHT_OF,
							viewLeft.getId());
					layoutParams.addRule(RelativeLayout.LEFT_OF,
							viewRight.getId());
				}
				viewCenter.setLayoutParams(layoutParams);
			}
			GdgLog.e(TAG, "End of calculate center title " + title + " | "
					+ vgpWidth + " | " + titleWidth);
		}
	}

	@Override
	public void onPageChanged(BaseFragment activePage) {
		syncBar(activePage);
	}
}