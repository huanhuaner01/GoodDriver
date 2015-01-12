package com.huishen_app.zc.ui.fragment;

import com.huishen_app.zc.ui.base.BaseActivity;

/**
 * 带有标题的listFragment
 * @author zhanghuan
 *
 */
public class TitleListFragment extends BaseFragment {
    private String title ;
    private String url ;
    private int backmodel ;
    
	public TitleListFragment(BaseActivity father,String title , String url ,int backmodel) {
		super(father);
		this.title = title ;
		this.url = url ;
		this.backmodel = backmodel ;
	}

	public TitleListFragment(BaseActivity father, Object tag,String title , String url ,int bakmodel) {
		super(father, tag);
		this.title = title ;
		this.url = url ;
		this.backmodel = backmodel ;
	}

}
