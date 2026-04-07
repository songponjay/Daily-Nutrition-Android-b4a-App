package b4a.DailyNutrition;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.DailyNutrition", "b4a.DailyNutrition.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.DailyNutrition", "b4a.DailyNutrition.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.DailyNutrition.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _kcal = 0;
public static int _agecal = 0;
public static String _sexcal = "";
public static String _idnutri1 = "";
public static String _idnutri = "";
public static int _caldateeat = 0;
public Object _eatid = null;
public static int _totalrows = 0;
public static int _menutorepast = 0;
public static String _copyrepast = "";
public static String _reupdate = "";
public static String _datamode = "";
public b4a.DailyNutrition.table _table1 = null;
public Object _idname = null;
public static int _idname1 = 0;
public static String _idtype = "";
public static String _idrepast = "";
public static int _idmat = 0;
public static int _idmenu = 0;
public anywheresoftware.b4a.sql.SQL _dbsql = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtwhight = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnickname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtheight = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhbd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmaterialtype = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetail = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenufood = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnuser = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbh1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbhdtf = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbhdtmn = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbacktype = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfoodtype = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsavetype = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshowtype = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaddfood = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmnmaterial = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scvmngmaterial = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbhmate = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbmenun = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate3 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate4 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate6 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate7 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate8 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate9 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate10 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate11 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate12 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate13 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate14 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate15 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate16 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate17 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate18 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate19 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate20 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate21 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate22 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate23 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate24 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnconmaterial = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaddmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncalmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndiet = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsavemenu = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenuback = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmenup1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmenup2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmenup3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaddmatmenu = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnconmenu = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvmatmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnblackmngmenu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneatback = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnutriback = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiom = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiow = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsavenmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmonthre = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnokmre = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtone = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txttwo = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtthree = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfour = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfive = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsix = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtseven = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbone = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbtwo = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbthree = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbfour = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbfive = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbsix = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _cbseven = null;
public anywheresoftware.b4a.objects.LabelWrapper _test = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrepast = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmonthreend = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrepastback = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmnrepast = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnblackhome1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncopyre = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnviewrepast = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnblackhome2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncopyrepast = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spshowdate = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvshowre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblidname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnamere = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnaddeat = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndieteat = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvfoodinday = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnshowre = null;
public anywheresoftware.b4a.objects.PanelWrapper _pneat = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncopyselect = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmate25 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnconeat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrepastdiet = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblselectday = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblselectday1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnu1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnu2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnu3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncondate = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnewname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblintro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblintro1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnintro = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreintro = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btngoshowname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmngmaterial = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndelmaterial = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbackuser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblshowreintro1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmngmaterial1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmenu2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleatintro = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spselectday = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spselectday2 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spselectday3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmidre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmidre1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbhometoeatre = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldiet1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldietintro1 = null;
  public Object[] GetGlobals() {
		return new Object[] {"Activity",mostCurrent._activity,"agecal",_agecal,"btnaddeat",mostCurrent._btnaddeat,"btnaddfood",mostCurrent._btnaddfood,"btnaddmatmenu",mostCurrent._btnaddmatmenu,"btnaddmenu",mostCurrent._btnaddmenu,"btnbacktype",mostCurrent._btnbacktype,"btnbackuser",mostCurrent._btnbackuser,"btnbh1",mostCurrent._btnbh1,"btnbhdtf",mostCurrent._btnbhdtf,"btnbhdtmn",mostCurrent._btnbhdtmn,"btnbhmate",mostCurrent._btnbhmate,"btnbhometoeatre",mostCurrent._btnbhometoeatre,"btnblackhome1",mostCurrent._btnblackhome1,"btnblackhome2",mostCurrent._btnblackhome2,"btnblackmngmenu",mostCurrent._btnblackmngmenu,"btnbmenun",mostCurrent._btnbmenun,"btncalmenu",mostCurrent._btncalmenu,"btncondate",mostCurrent._btncondate,"btnconeat",mostCurrent._btnconeat,"btnconmaterial",mostCurrent._btnconmaterial,"btnconmenu",mostCurrent._btnconmenu,"btncopyre",mostCurrent._btncopyre,"btncopyrepast",mostCurrent._btncopyrepast,"btncopyselect",mostCurrent._btncopyselect,"btndelmaterial",mostCurrent._btndelmaterial,"btndetail",mostCurrent._btndetail,"btndiet",mostCurrent._btndiet,"btndieteat",mostCurrent._btndieteat,"btndmenu",mostCurrent._btndmenu,"btneatback",mostCurrent._btneatback,"btngoshowname",mostCurrent._btngoshowname,"btnintro",mostCurrent._btnintro,"btnmaterialtype",mostCurrent._btnmaterialtype,"btnmenuback",mostCurrent._btnmenuback,"btnmenufood",mostCurrent._btnmenufood,"btnmnmaterial",mostCurrent._btnmnmaterial,"btnmnrepast",mostCurrent._btnmnrepast,"btnnewname",mostCurrent._btnnewname,"btnnutriback",mostCurrent._btnnutriback,"btnokmre",mostCurrent._btnokmre,"btnrepastback",mostCurrent._btnrepastback,"btnsavemenu",mostCurrent._btnsavemenu,"btnsavenmenu",mostCurrent._btnsavenmenu,"btnsavetype",mostCurrent._btnsavetype,"btnshowtype",mostCurrent._btnshowtype,"btnuser",mostCurrent._btnuser,"btnviewrepast",mostCurrent._btnviewrepast,"caldateeat",_caldateeat,"cbfive",mostCurrent._cbfive,"cbfour",mostCurrent._cbfour,"cbone",mostCurrent._cbone,"cbseven",mostCurrent._cbseven,"cbsix",mostCurrent._cbsix,"cbthree",mostCurrent._cbthree,"cbtwo",mostCurrent._cbtwo,"copyrepast",mostCurrent._copyrepast,"dataMode",mostCurrent._datamode,"dbSQL",mostCurrent._dbsql,"eatid",mostCurrent._eatid,"idmat",_idmat,"idmenu",_idmenu,"idname",mostCurrent._idname,"idname1",_idname1,"idnutri",mostCurrent._idnutri,"idnutri1",mostCurrent._idnutri1,"idrepast",mostCurrent._idrepast,"idtype",mostCurrent._idtype,"kcal",_kcal,"Label2",mostCurrent._label2,"lblDiet1",mostCurrent._lbldiet1,"lbldietintro1",mostCurrent._lbldietintro1,"lbleatintro",mostCurrent._lbleatintro,"lblhbd",mostCurrent._lblhbd,"lblidname",mostCurrent._lblidname,"lblintro",mostCurrent._lblintro,"lblintro1",mostCurrent._lblintro1,"lblmenu2",mostCurrent._lblmenu2,"lblmidre",mostCurrent._lblmidre,"lblmidre1",mostCurrent._lblmidre1,"lblmngmaterial",mostCurrent._lblmngmaterial,"lblmngmaterial1",mostCurrent._lblmngmaterial1,"lblmonthre",mostCurrent._lblmonthre,"lblmonthreend",mostCurrent._lblmonthreend,"lblnamere",mostCurrent._lblnamere,"lblnu1",mostCurrent._lblnu1,"lblnu2",mostCurrent._lblnu2,"lblnu3",mostCurrent._lblnu3,"lblreintro",mostCurrent._lblreintro,"lblrepast",mostCurrent._lblrepast,"lblrepastdiet",mostCurrent._lblrepastdiet,"lblselectday",mostCurrent._lblselectday,"lblselectday1",mostCurrent._lblselectday1,"lblshowreintro1",mostCurrent._lblshowreintro1,"ListView1",mostCurrent._listview1,"lvfoodinday",mostCurrent._lvfoodinday,"lvmatmenu",mostCurrent._lvmatmenu,"lvname",mostCurrent._lvname,"lvshowre",mostCurrent._lvshowre,"menutorepast",_menutorepast,"Panel1",mostCurrent._panel1,"Panel2",mostCurrent._panel2,"pneat",mostCurrent._pneat,"Pnre",mostCurrent._pnre,"pnshowre",mostCurrent._pnshowre,"RadioM",mostCurrent._radiom,"RadioW",mostCurrent._radiow,"reUpdate",mostCurrent._reupdate,"scvmngmaterial",mostCurrent._scvmngmaterial,"sexcal",mostCurrent._sexcal,"spselectday",mostCurrent._spselectday,"spselectday2",mostCurrent._spselectday2,"spselectday3",mostCurrent._spselectday3,"Spshowdate",mostCurrent._spshowdate,"Table1",mostCurrent._table1,"test",mostCurrent._test,"TotalRows",_totalrows,"txtfive",mostCurrent._txtfive,"txtfoodtype",mostCurrent._txtfoodtype,"txtfour",mostCurrent._txtfour,"txtheight",mostCurrent._txtheight,"txtmate1",mostCurrent._txtmate1,"txtmate10",mostCurrent._txtmate10,"txtmate11",mostCurrent._txtmate11,"txtmate12",mostCurrent._txtmate12,"txtmate13",mostCurrent._txtmate13,"txtmate14",mostCurrent._txtmate14,"txtmate15",mostCurrent._txtmate15,"txtmate16",mostCurrent._txtmate16,"txtmate17",mostCurrent._txtmate17,"txtmate18",mostCurrent._txtmate18,"txtmate19",mostCurrent._txtmate19,"txtmate2",mostCurrent._txtmate2,"txtmate20",mostCurrent._txtmate20,"txtmate21",mostCurrent._txtmate21,"txtmate22",mostCurrent._txtmate22,"txtmate23",mostCurrent._txtmate23,"txtmate24",mostCurrent._txtmate24,"txtmate25",mostCurrent._txtmate25,"txtmate3",mostCurrent._txtmate3,"txtmate4",mostCurrent._txtmate4,"txtmate5",mostCurrent._txtmate5,"txtmate6",mostCurrent._txtmate6,"txtmate7",mostCurrent._txtmate7,"txtmate8",mostCurrent._txtmate8,"txtmate9",mostCurrent._txtmate9,"txtmenu",mostCurrent._txtmenu,"txtmenup1",mostCurrent._txtmenup1,"txtmenup2",mostCurrent._txtmenup2,"txtmenup3",mostCurrent._txtmenup3,"txtname",mostCurrent._txtname,"txtnickname",mostCurrent._txtnickname,"txtone",mostCurrent._txtone,"txtseven",mostCurrent._txtseven,"txtsix",mostCurrent._txtsix,"txtthree",mostCurrent._txtthree,"txttwo",mostCurrent._txttwo,"txtwhight",mostCurrent._txtwhight};
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}

public static void killProgram() {
     {
            Activity __a = null;
            if (main.previousOne != null) {
				__a = main.previousOne.get();
			}
            else {
                BA ba = main.mostCurrent.processBA.sharedProcessBA.activityBA.get();
                if (ba != null) __a = ba.activity;
            }
            if (__a != null)
				__a.finish();}

}
public static String  _activity_create(boolean _firsttime) throws Exception{
try {
		Debug.PushSubsStack("Activity_Create (main) ","main",0,mostCurrent.activityBA,mostCurrent,185);
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 185;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 188;BA.debugLine="If FirstTime =True Then";
Debug.ShouldStop(134217728);
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 189;BA.debugLine="If File.Exists(File.DirInternal,\"food.db\") = Fa";
Debug.ShouldStop(268435456);
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"food.db")==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 190;BA.debugLine="File.Copy(File.DirAssets,\"food.db\",File.DirInte";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"food.db",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"food.db");
 };
 };
 BA.debugLineNum = 193;BA.debugLine="If dbSQL.IsInitialized = False Then";
Debug.ShouldStop(1);
if (mostCurrent._dbsql.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 194;BA.debugLine="dbSQL.Initialize(File.DirInternal, \"food.db\", T";
Debug.ShouldStop(2);
Debug.DebugWarningEngine.CheckInitialize(mostCurrent._dbsql);mostCurrent._dbsql.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"food.db",anywheresoftware.b4a.keywords.Common.True);
 };
 BA.debugLineNum = 197;BA.debugLine="Activity.LoadLayout(\"showname\")";
Debug.ShouldStop(16);
mostCurrent._activity.LoadLayout("showname",mostCurrent.activityBA);
 BA.debugLineNum = 198;BA.debugLine="showname";
Debug.ShouldStop(32);
_showname();
 BA.debugLineNum = 199;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnaddeat_click() throws Exception{
try {
		Debug.PushSubsStack("btnaddeat_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2039);
 BA.debugLineNum = 2039;BA.debugLine="Sub btnaddeat_Click";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 2040;BA.debugLine="Try";
Debug.ShouldStop(8388608);
try { BA.debugLineNum = 2041;BA.debugLine="If	pneat.Visible=True Then";
Debug.ShouldStop(16777216);
if (mostCurrent._pneat.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 2042;BA.debugLine="pneat.Visible=False";
Debug.ShouldStop(33554432);
mostCurrent._pneat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2043;BA.debugLine="btnconeat.Visible=False";
Debug.ShouldStop(67108864);
mostCurrent._btnconeat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2044;BA.debugLine="btnconeat.Enabled=False";
Debug.ShouldStop(134217728);
mostCurrent._btnconeat.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2045;BA.debugLine="lbleatintro.Visible=True";
Debug.ShouldStop(268435456);
mostCurrent._lbleatintro.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2046;BA.debugLine="lbleatintro.Enabled=True";
Debug.ShouldStop(536870912);
mostCurrent._lbleatintro.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2049;BA.debugLine="btnaddeat.Visible=True";
Debug.ShouldStop(1);
mostCurrent._btnaddeat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2050;BA.debugLine="btnaddeat.Enabled=True";
Debug.ShouldStop(2);
mostCurrent._btnaddeat.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 2052;BA.debugLine="Else If pneat.Visible=False Then";
Debug.ShouldStop(8);
if (mostCurrent._pneat.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 2053;BA.debugLine="pneat.Visible=True";
Debug.ShouldStop(16);
mostCurrent._pneat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2054;BA.debugLine="pneat.Top=btnconeat.Height+btnconeat.top";
Debug.ShouldStop(32);
mostCurrent._pneat.setTop((int) (mostCurrent._btnconeat.getHeight()+mostCurrent._btnconeat.getTop()));
 BA.debugLineNum = 2055;BA.debugLine="pneat.Width=100%x";
Debug.ShouldStop(64);
mostCurrent._pneat.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 2056;BA.debugLine="pneat.Height=100%y-btnconeat.Height";
Debug.ShouldStop(128);
mostCurrent._pneat.setHeight((int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnconeat.getHeight()));
 BA.debugLineNum = 2057;BA.debugLine="showmenuselect";
Debug.ShouldStop(256);
_showmenuselect();
 BA.debugLineNum = 2058;BA.debugLine="Log(\"Loading...\")";
Debug.ShouldStop(512);
anywheresoftware.b4a.keywords.Common.Log("Loading...");
 BA.debugLineNum = 2059;BA.debugLine="btnconeat.Visible=True";
Debug.ShouldStop(1024);
mostCurrent._btnconeat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2060;BA.debugLine="btnconeat.Enabled=True";
Debug.ShouldStop(2048);
mostCurrent._btnconeat.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2063;BA.debugLine="btnaddeat.Visible=False";
Debug.ShouldStop(16384);
mostCurrent._btnaddeat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2064;BA.debugLine="btnaddeat.Enabled=False";
Debug.ShouldStop(32768);
mostCurrent._btnaddeat.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2066;BA.debugLine="lbleatintro.Visible=False";
Debug.ShouldStop(131072);
mostCurrent._lbleatintro.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2067;BA.debugLine="lbleatintro.Enabled=False";
Debug.ShouldStop(262144);
mostCurrent._lbleatintro.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }};
 } 
       catch (Exception e1616) {
			processBA.setLastException(e1616); BA.debugLineNum = 2070;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 2072;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnaddfood_click() throws Exception{
try {
		Debug.PushSubsStack("btnaddfood_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,708);
 BA.debugLineNum = 708;BA.debugLine="Sub btnaddfood_Click 'เพิ่มวัตถุดิบในประเภทที่เลือ";
Debug.ShouldStop(8);
 BA.debugLineNum = 709;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 710;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(32);
mostCurrent._table1._clearall();
 BA.debugLineNum = 711;BA.debugLine="dataMode=\"new\"";
Debug.ShouldStop(64);
mostCurrent._datamode = "new";
 BA.debugLineNum = 712;BA.debugLine="Activity.LoadLayout(\"mngmaterial\")";
Debug.ShouldStop(128);
mostCurrent._activity.LoadLayout("mngmaterial",mostCurrent.activityBA);
 BA.debugLineNum = 713;BA.debugLine="scvmngmaterial.Panel.LoadLayout(\"scollmngmateria";
Debug.ShouldStop(256);
mostCurrent._scvmngmaterial.getPanel().LoadLayout("scollmngmaterial",mostCurrent.activityBA);
 BA.debugLineNum = 714;BA.debugLine="scvmngmaterial.Panel.Height = 1100dip";
Debug.ShouldStop(512);
mostCurrent._scvmngmaterial.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1100)));
 BA.debugLineNum = 715;BA.debugLine="scvmngmaterial.Height = 80%y";
Debug.ShouldStop(1024);
mostCurrent._scvmngmaterial.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 BA.debugLineNum = 716;BA.debugLine="scvmngmaterial.Width = 100%x";
Debug.ShouldStop(2048);
mostCurrent._scvmngmaterial.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 719;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnaddmatmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnaddmatmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1244);
 BA.debugLineNum = 1244;BA.debugLine="Sub btnaddmatmenu_Click	'panel เมนู";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 1245;BA.debugLine="Panel2.Top =0";
Debug.ShouldStop(268435456);
mostCurrent._panel2.setTop((int) (0));
 BA.debugLineNum = 1246;BA.debugLine="Panel2.Left =0";
Debug.ShouldStop(536870912);
mostCurrent._panel2.setLeft((int) (0));
 BA.debugLineNum = 1247;BA.debugLine="Panel2.Height =100%y";
Debug.ShouldStop(1073741824);
mostCurrent._panel2.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 1248;BA.debugLine="Panel2.Width =100%x";
Debug.ShouldStop(-2147483648);
mostCurrent._panel2.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 1249;BA.debugLine="Panel2.Visible=True";
Debug.ShouldStop(1);
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 1250;BA.debugLine="Panel2.BringToFront";
Debug.ShouldStop(2);
mostCurrent._panel2.BringToFront();
 BA.debugLineNum = 1251;BA.debugLine="showselectmat";
Debug.ShouldStop(4);
_showselectmat();
 BA.debugLineNum = 1252;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnaddmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnaddmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1059);
 BA.debugLineNum = 1059;BA.debugLine="Sub btnaddmenu_Click";
Debug.ShouldStop(4);
 BA.debugLineNum = 1060;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(8);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1061;BA.debugLine="Activity.LoadLayout(\"cremenu\")";
Debug.ShouldStop(16);
mostCurrent._activity.LoadLayout("cremenu",mostCurrent.activityBA);
 BA.debugLineNum = 1062;BA.debugLine="dataMode=\"new\"";
Debug.ShouldStop(32);
mostCurrent._datamode = "new";
 BA.debugLineNum = 1063;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbacktype_click() throws Exception{
try {
		Debug.PushSubsStack("btnbacktype_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,645);
 BA.debugLineNum = 645;BA.debugLine="Sub btnbacktype_Click";
Debug.ShouldStop(16);
 BA.debugLineNum = 646;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(32);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 647;BA.debugLine="Activity.LoadLayout(\"foodtype\")";
Debug.ShouldStop(64);
mostCurrent._activity.LoadLayout("foodtype",mostCurrent.activityBA);
 BA.debugLineNum = 648;BA.debugLine="showfoodtype";
Debug.ShouldStop(128);
_showfoodtype();
 BA.debugLineNum = 649;BA.debugLine="End Sub";
Debug.ShouldStop(256);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbackuser_click() throws Exception{
try {
		Debug.PushSubsStack("btnbackuser_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,247);
 BA.debugLineNum = 247;BA.debugLine="Sub btnbackuser_Click		'กลับหน้าแรก";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 249;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16777216);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 250;BA.debugLine="Activity.LoadLayout(\"showname\")";
Debug.ShouldStop(33554432);
mostCurrent._activity.LoadLayout("showname",mostCurrent.activityBA);
 BA.debugLineNum = 251;BA.debugLine="showname";
Debug.ShouldStop(67108864);
_showname();
 BA.debugLineNum = 253;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbh1_click() throws Exception{
try {
		Debug.PushSubsStack("btnbh1_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,630);
 BA.debugLineNum = 630;BA.debugLine="Sub btnbh1_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 631;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(4194304);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 632;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(8388608);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 633;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbhdtf_click() throws Exception{
try {
		Debug.PushSubsStack("btnbhdtf_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,635);
 BA.debugLineNum = 635;BA.debugLine="Sub btnbhdtf_Click";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 636;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(134217728);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 637;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(268435456);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 638;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbhdtmn_click() throws Exception{
try {
		Debug.PushSubsStack("btnbhdtmn_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,640);
 BA.debugLineNum = 640;BA.debugLine="Sub btnbhdtmn_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 641;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 642;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(2);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 643;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbhmate_click() throws Exception{
try {
		Debug.PushSubsStack("btnbhmate_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,962);
 BA.debugLineNum = 962;BA.debugLine="Sub btnbhmate_Click";
Debug.ShouldStop(2);
 BA.debugLineNum = 963;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(4);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 964;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(8);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 965;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbhometoeatre_click() throws Exception{
try {
		Debug.PushSubsStack("btnbhometoeatre_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2476);
 BA.debugLineNum = 2476;BA.debugLine="Sub btnbhometoeatre_Click";
Debug.ShouldStop(2048);
 BA.debugLineNum = 2477;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(4096);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 2478;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(8192);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 2479;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnblackhome1_click() throws Exception{
try {
		Debug.PushSubsStack("btnblackhome1_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2331);
 BA.debugLineNum = 2331;BA.debugLine="Sub btnblackhome1_Click";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 2332;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(134217728);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 2333;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(268435456);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 2335;BA.debugLine="Activity.SetBackgroundImage (Null)";
Debug.ShouldStop(1073741824);
mostCurrent._activity.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 BA.debugLineNum = 2336;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnblackhome2_click() throws Exception{
try {
		Debug.PushSubsStack("btnblackhome2_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1743);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
 BA.debugLineNum = 1743;BA.debugLine="Sub btnblackhome2_Click";
Debug.ShouldStop(16384);
 BA.debugLineNum = 1746;BA.debugLine="If pnshowre.Visible=False Then";
Debug.ShouldStop(131072);
if (mostCurrent._pnshowre.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 1747;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(262144);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1748;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(524288);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1749;BA.debugLine="Activity.LoadLayout(\"mng_repast\")";
Debug.ShouldStop(1048576);
mostCurrent._activity.LoadLayout("mng_repast",mostCurrent.activityBA);
 BA.debugLineNum = 1750;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(2097152);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1751;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4194304);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1752;BA.debugLine="sql=\"select namesuser from recorduser where us";
Debug.ShouldStop(8388608);
_sql = "select namesuser from recorduser where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1753;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(16777216);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1754;BA.debugLine="rs.Position =0";
Debug.ShouldStop(33554432);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1755;BA.debugLine="lblnamere.Text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(67108864);
mostCurrent._lblnamere.setText((Object)(_rs.GetString("namesuser")));
 }else 
{ BA.debugLineNum = 1757;BA.debugLine="Else If pnshowre.Visible=True Then";
Debug.ShouldStop(268435456);
if (mostCurrent._pnshowre.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1758;BA.debugLine="pnshowre.Visible=False";
Debug.ShouldStop(536870912);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1760;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(-2147483648);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1761;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1762;BA.debugLine="Activity.LoadLayout(\"mng_repast\")";
Debug.ShouldStop(2);
mostCurrent._activity.LoadLayout("mng_repast",mostCurrent.activityBA);
 BA.debugLineNum = 1763;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(4);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1764;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1765;BA.debugLine="sql=\"select namesuser from recorduser where us";
Debug.ShouldStop(16);
_sql = "select namesuser from recorduser where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1766;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(32);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1767;BA.debugLine="rs.Position =0";
Debug.ShouldStop(64);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1768;BA.debugLine="lblnamere.Text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(128);
mostCurrent._lblnamere.setText((Object)(_rs.GetString("namesuser")));
 }};
 BA.debugLineNum = 1771;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnblackmngmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnblackmngmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1277);
 BA.debugLineNum = 1277;BA.debugLine="Sub btnblackmngmenu_Click";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 1278;BA.debugLine="Panel2.Visible=False";
Debug.ShouldStop(536870912);
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1279;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(1073741824);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1280;BA.debugLine="showmatmenu";
Debug.ShouldStop(-2147483648);
_showmatmenu();
 BA.debugLineNum = 1281;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnbmenun_click() throws Exception{
try {
		Debug.PushSubsStack("btnbmenun_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1042);
 BA.debugLineNum = 1042;BA.debugLine="Sub btnbmenun_Click  'หน้าชื่อกลับหน้าหลัก *ชั่วคร";
Debug.ShouldStop(131072);
 BA.debugLineNum = 1043;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(262144);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1044;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(524288);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 1045;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btncalmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btncalmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1909);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
String[] _sp = null;
 BA.debugLineNum = 1909;BA.debugLine="Sub btncalmenu_Click 'eatเพิ่มเมนูในมื้ออาหาร";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 1910;BA.debugLine="Try";
Debug.ShouldStop(2097152);
try { BA.debugLineNum = 1911;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(4194304);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1912;BA.debugLine="Activity.LoadLayout(\"eat\")";
Debug.ShouldStop(8388608);
mostCurrent._activity.LoadLayout("eat",mostCurrent.activityBA);
 BA.debugLineNum = 1913;BA.debugLine="Spshowdate.Add(\"\")";
Debug.ShouldStop(16777216);
mostCurrent._spshowdate.Add("");
 BA.debugLineNum = 1914;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(33554432);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1915;BA.debugLine="Dim sql As String";
Debug.ShouldStop(67108864);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1916;BA.debugLine="sql=\"select repast_date from repastname where";
Debug.ShouldStop(134217728);
_sql = "select repast_date from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' group by repast_date order by repast_date ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1917;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(268435456);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1918;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(536870912);
{
final int step1551 = 1;
final int limit1551 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1551 > 0 && _i <= limit1551) || (step1551 < 0 && _i >= limit1551); _i = ((int)(0 + _i + step1551))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1919;BA.debugLine="rs.Position =i";
Debug.ShouldStop(1073741824);
_rs.setPosition(_i);
 BA.debugLineNum = 1921;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(1);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 1922;BA.debugLine="sp=Regex.Split(\"\\-\",rs.GetString(\"repast_da";
Debug.ShouldStop(2);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\-",_rs.GetString("repast_date"));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 1924;BA.debugLine="Spshowdate.Add((sp(2) & \"/\" & sp(1) & \"/\" &";
Debug.ShouldStop(8);
mostCurrent._spshowdate.Add((_sp[(int) (2)]+"/"+_sp[(int) (1)]+"/"+_sp[(int) (0)]));
 }
}Debug.locals.put("i", _i);
;
 } 
       catch (Exception e1558) {
			processBA.setLastException(e1558); BA.debugLineNum = 1928;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 1931;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btncondate_click() throws Exception{
try {
		Debug.PushSubsStack("btncondate_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2374);
 BA.debugLineNum = 2374;BA.debugLine="Sub btncondate_Click";
Debug.ShouldStop(32);
 BA.debugLineNum = 2376;BA.debugLine="shownutri";
Debug.ShouldStop(128);
_shownutri();
 BA.debugLineNum = 2377;BA.debugLine="End Sub";
Debug.ShouldStop(256);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnconeat_click() throws Exception{
try {
		Debug.PushSubsStack("btnconeat_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2234);
int _i = 0;
String _sqlfood = "";
 BA.debugLineNum = 2234;BA.debugLine="Sub btnconeat_Click";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 2235;BA.debugLine="btnconeat.Visible=False";
Debug.ShouldStop(67108864);
mostCurrent._btnconeat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2238;BA.debugLine="Dim i As Int";
Debug.ShouldStop(536870912);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 2239;BA.debugLine="For i = 0 To TotalRows-1";
Debug.ShouldStop(1073741824);
{
final int step1717 = 1;
final int limit1717 = (int) (_totalrows-1);
for (_i = (int) (0); (step1717 > 0 && _i <= limit1717) || (step1717 < 0 && _i >= limit1717); _i = ((int)(0 + _i + step1717))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2240;BA.debugLine="If Table1.GetValue(2,i)=\"X\" Then  'start copy";
Debug.ShouldStop(-2147483648);
if ((mostCurrent._table1._getvalue((int) (2),_i)).equals("X")) { 
 BA.debugLineNum = 2254;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(8192);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2255;BA.debugLine="sqlfood=\"insert into takefood(\"";
Debug.ShouldStop(16384);
_sqlfood = "insert into takefood(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2256;BA.debugLine="sqlfood=sqlfood & \" repastname_id,\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" repastname_id,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2257;BA.debugLine="sqlfood=sqlfood & \" menufood_id,\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" menufood_id,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2258;BA.debugLine="sqlfood=sqlfood & \" takefood_count,\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" takefood_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2259;BA.debugLine="sqlfood=sqlfood & \" takefood_status\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" takefood_status";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2260;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2261;BA.debugLine="sqlfood=sqlfood & \"'\"& eatid &\"',\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+"'"+BA.ObjectToString(mostCurrent._eatid)+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2262;BA.debugLine="sqlfood=sqlfood & \"'\"& Table1.GetValue(0,i";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+"'"+mostCurrent._table1._getvalue((int) (0),_i)+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2263;BA.debugLine="sqlfood=sqlfood & \"'\"& Table1.GetValue(3,i";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+"'"+mostCurrent._table1._getvalue((int) (3),_i)+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2264;BA.debugLine="sqlfood=sqlfood & \"'1'\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+"'1'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2265;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 2266;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(33554432);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2272;BA.debugLine="Msgbox(\"เพิ่มเมนูอาหารในมื้อสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(-2147483648);
anywheresoftware.b4a.keywords.Common.Msgbox("เพิ่มเมนูอาหารในมื้อสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 2273;BA.debugLine="pneat.Visible=False";
Debug.ShouldStop(1);
mostCurrent._pneat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 2274;BA.debugLine="nameeat";
Debug.ShouldStop(2);
_nameeat();
 BA.debugLineNum = 2276;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnconmaterial_click() throws Exception{
try {
		Debug.PushSubsStack("btnconmaterial_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,781);
String _sqlfood = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
 BA.debugLineNum = 781;BA.debugLine="Sub btnconmaterial_Click";
Debug.ShouldStop(4096);
 BA.debugLineNum = 782;BA.debugLine="If txtmate1.Text =\"\" Then";
Debug.ShouldStop(8192);
if ((mostCurrent._txtmate1.getText()).equals("")) { 
 BA.debugLineNum = 783;BA.debugLine="Msgbox(\"กรุณาใส่ชื่อ\",\"แจ้งเตือน\")";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่ชื่อ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 784;BA.debugLine="txtmate1.RequestFocus";
Debug.ShouldStop(32768);
mostCurrent._txtmate1.RequestFocus();
 BA.debugLineNum = 785;BA.debugLine="Return";
Debug.ShouldStop(65536);
if (true) return "";
 }else 
{ BA.debugLineNum = 786;BA.debugLine="else If txtmate2.Text > 1 And txtmate2.Text < 1";
Debug.ShouldStop(131072);
if ((double)(Double.parseDouble(mostCurrent._txtmate2.getText()))>1 && (double)(Double.parseDouble(mostCurrent._txtmate2.getText()))<1) { 
 BA.debugLineNum = 787;BA.debugLine="Msgbox(\"กรุณาใส่ปริมาณ 1 (กรัม)\",\"แจ้งเตือน\")";
Debug.ShouldStop(262144);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่ปริมาณ 1 (กรัม)","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 788;BA.debugLine="txtmate2.RequestFocus";
Debug.ShouldStop(524288);
mostCurrent._txtmate2.RequestFocus();
 BA.debugLineNum = 789;BA.debugLine="Return";
Debug.ShouldStop(1048576);
if (true) return "";
 }else 
{ BA.debugLineNum = 790;BA.debugLine="else If txtmate3.Text =\"\" Then '---";
Debug.ShouldStop(2097152);
if ((mostCurrent._txtmate3.getText()).equals("")) { 
 BA.debugLineNum = 791;BA.debugLine="Msgbox(\"กรุณาใส่พลังงาน(cal)\",\"แจ้งเตือน\")";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่พลังงาน(cal)","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 792;BA.debugLine="txtmate3.RequestFocus";
Debug.ShouldStop(8388608);
mostCurrent._txtmate3.RequestFocus();
 BA.debugLineNum = 793;BA.debugLine="Return";
Debug.ShouldStop(16777216);
if (true) return "";
 }else 
{ BA.debugLineNum = 794;BA.debugLine="else If txtmate4.Text =\"\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._txtmate4.getText()).equals("")) { 
 BA.debugLineNum = 795;BA.debugLine="Msgbox(\"กรุณาใส่โปรตีน\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่โปรตีน","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 796;BA.debugLine="txtmate4.RequestFocus";
Debug.ShouldStop(134217728);
mostCurrent._txtmate4.RequestFocus();
 BA.debugLineNum = 797;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 }else 
{ BA.debugLineNum = 798;BA.debugLine="else If txtmate5.Text =\"\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._txtmate5.getText()).equals("")) { 
 BA.debugLineNum = 799;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินA\",\"แจ้งเตือน\")";
Debug.ShouldStop(1073741824);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินA","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 800;BA.debugLine="txtmate5.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtmate5.RequestFocus();
 BA.debugLineNum = 801;BA.debugLine="Return";
Debug.ShouldStop(1);
if (true) return "";
 }else 
{ BA.debugLineNum = 802;BA.debugLine="else If txtmate6.Text =\"\" Then";
Debug.ShouldStop(2);
if ((mostCurrent._txtmate6.getText()).equals("")) { 
 BA.debugLineNum = 803;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินC\",\"แจ้งเตือน\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินC","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 804;BA.debugLine="txtmate6.RequestFocus";
Debug.ShouldStop(8);
mostCurrent._txtmate6.RequestFocus();
 BA.debugLineNum = 805;BA.debugLine="Return";
Debug.ShouldStop(16);
if (true) return "";
 }else 
{ BA.debugLineNum = 806;BA.debugLine="else If txtmate7.Text =\"\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._txtmate7.getText()).equals("")) { 
 BA.debugLineNum = 807;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินE\",\"แจ้งเตือน\")";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินE","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 808;BA.debugLine="txtmate7.RequestFocus";
Debug.ShouldStop(128);
mostCurrent._txtmate7.RequestFocus();
 BA.debugLineNum = 809;BA.debugLine="Return";
Debug.ShouldStop(256);
if (true) return "";
 }else 
{ BA.debugLineNum = 810;BA.debugLine="else If txtmate8.Text =\"\" Then";
Debug.ShouldStop(512);
if ((mostCurrent._txtmate8.getText()).equals("")) { 
 BA.debugLineNum = 811;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินB1\",\"แจ้งเตือน\")";
Debug.ShouldStop(1024);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินB1","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 812;BA.debugLine="txtmate8.RequestFocus";
Debug.ShouldStop(2048);
mostCurrent._txtmate8.RequestFocus();
 BA.debugLineNum = 813;BA.debugLine="Return";
Debug.ShouldStop(4096);
if (true) return "";
 }else 
{ BA.debugLineNum = 814;BA.debugLine="else If txtmate9.Text =\"\" Then";
Debug.ShouldStop(8192);
if ((mostCurrent._txtmate9.getText()).equals("")) { 
 BA.debugLineNum = 815;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินB2\",\"แจ้งเตือน\")";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินB2","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 816;BA.debugLine="txtmate9.RequestFocus";
Debug.ShouldStop(32768);
mostCurrent._txtmate9.RequestFocus();
 BA.debugLineNum = 817;BA.debugLine="Return";
Debug.ShouldStop(65536);
if (true) return "";
 }else 
{ BA.debugLineNum = 818;BA.debugLine="else If txtmate10.Text =\"\" Then";
Debug.ShouldStop(131072);
if ((mostCurrent._txtmate10.getText()).equals("")) { 
 BA.debugLineNum = 819;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินB3\",\"แจ้งเตือน\")";
Debug.ShouldStop(262144);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินB3","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 820;BA.debugLine="txtmate10.RequestFocus";
Debug.ShouldStop(524288);
mostCurrent._txtmate10.RequestFocus();
 BA.debugLineNum = 821;BA.debugLine="Return";
Debug.ShouldStop(1048576);
if (true) return "";
 }else 
{ BA.debugLineNum = 822;BA.debugLine="else If txtmate11.Text =\"\" Then";
Debug.ShouldStop(2097152);
if ((mostCurrent._txtmate11.getText()).equals("")) { 
 BA.debugLineNum = 823;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินB6\",\"แจ้งเตือน\")";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินB6","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 824;BA.debugLine="txtmate11.RequestFocus";
Debug.ShouldStop(8388608);
mostCurrent._txtmate11.RequestFocus();
 BA.debugLineNum = 825;BA.debugLine="Return";
Debug.ShouldStop(16777216);
if (true) return "";
 }else 
{ BA.debugLineNum = 826;BA.debugLine="else If txtmate12.Text =\"\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._txtmate12.getText()).equals("")) { 
 BA.debugLineNum = 827;BA.debugLine="Msgbox(\"กรุณาใส่วิตามินB12\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่วิตามินB12","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 828;BA.debugLine="txtmate12.RequestFocus";
Debug.ShouldStop(134217728);
mostCurrent._txtmate12.RequestFocus();
 BA.debugLineNum = 829;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 }else 
{ BA.debugLineNum = 830;BA.debugLine="else If txtmate13.Text =\"\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._txtmate13.getText()).equals("")) { 
 BA.debugLineNum = 831;BA.debugLine="Msgbox(\"กรุณาใส่โคลีน\",\"แจ้งเตือน\")";
Debug.ShouldStop(1073741824);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่โคลีน","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 832;BA.debugLine="txtmate13.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtmate13.RequestFocus();
 BA.debugLineNum = 833;BA.debugLine="Return";
Debug.ShouldStop(1);
if (true) return "";
 }else 
{ BA.debugLineNum = 834;BA.debugLine="else If txtmate14.Text =\"\" Then";
Debug.ShouldStop(2);
if ((mostCurrent._txtmate14.getText()).equals("")) { 
 BA.debugLineNum = 835;BA.debugLine="Msgbox(\"กรุณาใส่โซเดียม\",\"แจ้งเตือน\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่โซเดียม","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 836;BA.debugLine="txtmate14.RequestFocus";
Debug.ShouldStop(8);
mostCurrent._txtmate14.RequestFocus();
 BA.debugLineNum = 837;BA.debugLine="Return";
Debug.ShouldStop(16);
if (true) return "";
 }else 
{ BA.debugLineNum = 838;BA.debugLine="else If txtmate15.Text =\"\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._txtmate15.getText()).equals("")) { 
 BA.debugLineNum = 839;BA.debugLine="Msgbox(\"กรุณาใส่โปแตสเซียม\",\"แจ้งเตือน\")";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่โปแตสเซียม","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 840;BA.debugLine="txtmate15.RequestFocus";
Debug.ShouldStop(128);
mostCurrent._txtmate15.RequestFocus();
 BA.debugLineNum = 841;BA.debugLine="Return";
Debug.ShouldStop(256);
if (true) return "";
 }else 
{ BA.debugLineNum = 842;BA.debugLine="else If txtmate16.Text =\"\" Then";
Debug.ShouldStop(512);
if ((mostCurrent._txtmate16.getText()).equals("")) { 
 BA.debugLineNum = 843;BA.debugLine="Msgbox(\"กรุณาใส่แคลเซียม\",\"แจ้งเตือน\")";
Debug.ShouldStop(1024);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่แคลเซียม","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 844;BA.debugLine="txtmate16.RequestFocus";
Debug.ShouldStop(2048);
mostCurrent._txtmate16.RequestFocus();
 BA.debugLineNum = 845;BA.debugLine="Return";
Debug.ShouldStop(4096);
if (true) return "";
 }else 
{ BA.debugLineNum = 846;BA.debugLine="else If txtmate17.Text =\"\" Then";
Debug.ShouldStop(8192);
if ((mostCurrent._txtmate17.getText()).equals("")) { 
 BA.debugLineNum = 847;BA.debugLine="Msgbox(\"กรุณาใส่แมงกานีส\",\"แจ้งเตือน\")";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่แมงกานีส","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 848;BA.debugLine="txtmate17.RequestFocus";
Debug.ShouldStop(32768);
mostCurrent._txtmate17.RequestFocus();
 BA.debugLineNum = 849;BA.debugLine="Return";
Debug.ShouldStop(65536);
if (true) return "";
 }else 
{ BA.debugLineNum = 850;BA.debugLine="else If txtmate18.Text =\"\" Then";
Debug.ShouldStop(131072);
if ((mostCurrent._txtmate18.getText()).equals("")) { 
 BA.debugLineNum = 851;BA.debugLine="Msgbox(\"กรุณาใส่เหล็ก\",\"แจ้งเตือน\")";
Debug.ShouldStop(262144);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่เหล็ก","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 852;BA.debugLine="txtmate18.RequestFocus";
Debug.ShouldStop(524288);
mostCurrent._txtmate18.RequestFocus();
 BA.debugLineNum = 853;BA.debugLine="Return";
Debug.ShouldStop(1048576);
if (true) return "";
 }else 
{ BA.debugLineNum = 854;BA.debugLine="else If txtmate19.Text =\"\" Then";
Debug.ShouldStop(2097152);
if ((mostCurrent._txtmate19.getText()).equals("")) { 
 BA.debugLineNum = 855;BA.debugLine="Msgbox(\"กรุณาใส่สังกะสี\",\"แจ้งเตือน\")";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่สังกะสี","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 856;BA.debugLine="txtmate19.RequestFocus";
Debug.ShouldStop(8388608);
mostCurrent._txtmate19.RequestFocus();
 BA.debugLineNum = 857;BA.debugLine="Return";
Debug.ShouldStop(16777216);
if (true) return "";
 }else 
{ BA.debugLineNum = 858;BA.debugLine="else If txtmate20.Text =\"\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._txtmate20.getText()).equals("")) { 
 BA.debugLineNum = 859;BA.debugLine="Msgbox(\"กรุณาใสซีลีเนียม่\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใสซีลีเนียม่","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 860;BA.debugLine="txtmate20.RequestFocus";
Debug.ShouldStop(134217728);
mostCurrent._txtmate20.RequestFocus();
 BA.debugLineNum = 861;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 }else 
{ BA.debugLineNum = 862;BA.debugLine="else If txtmate21.Text =\"\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._txtmate21.getText()).equals("")) { 
 BA.debugLineNum = 863;BA.debugLine="Msgbox(\"กรุณาใสคาร์โบไฮเดรต\",\"แจ้งเตือน\")";
Debug.ShouldStop(1073741824);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใสคาร์โบไฮเดรต","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 864;BA.debugLine="txtmate21.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtmate21.RequestFocus();
 BA.debugLineNum = 865;BA.debugLine="Return";
Debug.ShouldStop(1);
if (true) return "";
 }else 
{ BA.debugLineNum = 866;BA.debugLine="else If txtmate22.Text =\"\" Then";
Debug.ShouldStop(2);
if ((mostCurrent._txtmate22.getText()).equals("")) { 
 BA.debugLineNum = 867;BA.debugLine="Msgbox(\"กรุณาใสไขมัน่\",\"แจ้งเตือน\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใสไขมัน่","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 868;BA.debugLine="txtmate22.RequestFocus";
Debug.ShouldStop(8);
mostCurrent._txtmate22.RequestFocus();
 BA.debugLineNum = 869;BA.debugLine="Return";
Debug.ShouldStop(16);
if (true) return "";
 }else 
{ BA.debugLineNum = 870;BA.debugLine="else If txtmate23.Text =\"\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._txtmate23.getText()).equals("")) { 
 BA.debugLineNum = 871;BA.debugLine="Msgbox(\"กรุณาใส่น้ำ\",\"แจ้งเตือน\")";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่น้ำ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 872;BA.debugLine="txtmate23.RequestFocus";
Debug.ShouldStop(128);
mostCurrent._txtmate23.RequestFocus();
 BA.debugLineNum = 873;BA.debugLine="Return";
Debug.ShouldStop(256);
if (true) return "";
 }else 
{ BA.debugLineNum = 874;BA.debugLine="else If txtmate24.Text =\"\" Then";
Debug.ShouldStop(512);
if ((mostCurrent._txtmate24.getText()).equals("")) { 
 BA.debugLineNum = 875;BA.debugLine="Msgbox(\"กรุณาใส่่ใยอาหาร\",\"แจ้งเตือน\")";
Debug.ShouldStop(1024);
anywheresoftware.b4a.keywords.Common.Msgbox("กรุณาใส่่ใยอาหาร","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 876;BA.debugLine="txtmate24.RequestFocus";
Debug.ShouldStop(2048);
mostCurrent._txtmate24.RequestFocus();
 BA.debugLineNum = 877;BA.debugLine="Return";
Debug.ShouldStop(4096);
if (true) return "";
 }}}}}}}}}}}}}}}}}}}}}}}};
 BA.debugLineNum = 882;BA.debugLine="Try";
Debug.ShouldStop(131072);
try { BA.debugLineNum = 884;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(524288);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 885;BA.debugLine="If dataMode=\"new\" Then";
Debug.ShouldStop(1048576);
if ((mostCurrent._datamode).equals("new")) { 
 BA.debugLineNum = 886;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามีหรือไม่ใน";
Debug.ShouldStop(2097152);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 887;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4194304);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 888;BA.debugLine="sql=\"select material_name from material wher";
Debug.ShouldStop(8388608);
_sql = "select material_name from material where material_name='"+mostCurrent._txtmate1.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 889;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(16777216);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 890;BA.debugLine="If rs.RowCount>0 Then";
Debug.ShouldStop(33554432);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 891;BA.debugLine="Msgbox(\"มีชื่อย่อนี้แล้วในระบบ\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("มีชื่อย่อนี้แล้วในระบบ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 892;BA.debugLine="Return";
Debug.ShouldStop(134217728);
if (true) return "";
 };
 BA.debugLineNum = 894;BA.debugLine="sqlfood=\"insert into material(\"";
Debug.ShouldStop(536870912);
_sqlfood = "insert into material(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 895;BA.debugLine="sqlfood=sqlfood & \" type_id,\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+" type_id,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 896;BA.debugLine="sqlfood=sqlfood & \" material_name,\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+" material_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 897;BA.debugLine="sqlfood=sqlfood & \" number,\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+" number,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 898;BA.debugLine="sqlfood=sqlfood & \" kcal,\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" kcal,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 899;BA.debugLine="sqlfood=sqlfood & \" prot,\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+" prot,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 900;BA.debugLine="sqlfood=sqlfood & \" vitA,\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+" vitA,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 901;BA.debugLine="sqlfood=sqlfood & \" vitC,\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+" vitC,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 902;BA.debugLine="sqlfood=sqlfood & \" vitE,\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+" vitE,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 903;BA.debugLine="sqlfood=sqlfood & \" vitB1,\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" vitB1,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 904;BA.debugLine="sqlfood=sqlfood & \" vitB2,\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" vitB2,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 905;BA.debugLine="sqlfood=sqlfood & \" vitB3,\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+" vitB3,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 906;BA.debugLine="sqlfood=sqlfood & \" vitB6,\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" vitB6,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 907;BA.debugLine="sqlfood=sqlfood & \" vitB9,\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" vitB9,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 908;BA.debugLine="sqlfood=sqlfood & \" vitB12,\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" vitB12,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 909;BA.debugLine="sqlfood=sqlfood & \" chol,\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" chol,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 910;BA.debugLine="sqlfood=sqlfood & \" sodi,\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+" sodi,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 911;BA.debugLine="sqlfood=sqlfood & \" pota,\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" pota,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 912;BA.debugLine="sqlfood=sqlfood & \" calc,\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" calc,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 913;BA.debugLine="sqlfood=sqlfood & \" magn,\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" magn,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 914;BA.debugLine="sqlfood=sqlfood & \" iron,\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" iron,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 915;BA.debugLine="sqlfood=sqlfood & \" zinc,\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" zinc,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 916;BA.debugLine="sqlfood=sqlfood & \" sele,\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" sele,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 917;BA.debugLine="sqlfood=sqlfood & \" crab,\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+" crab,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 918;BA.debugLine="sqlfood=sqlfood & \" fat,\"";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+" fat,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 919;BA.debugLine="sqlfood=sqlfood & \" h2o,\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" h2o,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 920;BA.debugLine="sqlfood=sqlfood & \" fiber\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+" fiber";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 921;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 922;BA.debugLine="sqlfood=sqlfood & \" '\"& idtype &\"',\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" '"+mostCurrent._idtype+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 923;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate1.text&\"',\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate1.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 924;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate2.text&\"',\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate2.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 925;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate3.text&\"',\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate3.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 926;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate4.text&\"',\"";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate4.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 927;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate5.text&\"',\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate5.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 928;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate6.text&\"',\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate6.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 929;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate7.text&\"',\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate7.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 930;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate8.text&\"',\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate8.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 931;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate9.text&\"',\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate9.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 932;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate10.text&\"',\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate10.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 933;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate11.text&\"',\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate11.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 934;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate12.text&\"',\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate12.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 935;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate13.text&\"',\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate13.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 936;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate14.text&\"',\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate14.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 937;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate15.text&\"',\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate15.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 938;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate16.text&\"',\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate16.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 939;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate17.text&\"',\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate17.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 940;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate18.text&\"',\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate18.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 941;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate19.text&\"',\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate19.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 942;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate20.text&\"',\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate20.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 943;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate21.text&\"',\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate21.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 944;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate22.text&\"',\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate22.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 945;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate23.text&\"',\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate23.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 946;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate24.text&\"',\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate24.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 947;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmate25.text&\"'\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmate25.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 948;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 }else {
 BA.debugLineNum = 950;BA.debugLine="sqlfood=\"update material set \"";
Debug.ShouldStop(2097152);
_sqlfood = "update material set ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 951;BA.debugLine="sqlfood=sqlfood & \" material_name='\" &txt";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" material_name='"+mostCurrent._txtmate1.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 952;BA.debugLine="sqlfood=sqlfood & \" where material_id= '\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+" where material_id= '"+BA.NumberToString(_idmat)+"'";Debug.locals.put("sqlfood", _sqlfood);
 };
 BA.debugLineNum = 954;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(33554432);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 955;BA.debugLine="Msgbox(\"บันทึกวัตถุดิบสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("บันทึกวัตถุดิบสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 956;BA.debugLine="showMaterial";
Debug.ShouldStop(134217728);
_showmaterial();
 } 
       catch (Exception e810) {
			processBA.setLastException(e810); BA.debugLineNum = 958;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 960;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnconmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnconmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1158);
String _sqlfood = "";
 BA.debugLineNum = 1158;BA.debugLine="Sub btnconmenu_Click";
Debug.ShouldStop(32);
 BA.debugLineNum = 1159;BA.debugLine="If  txtmenup3.text=\"\" Then";
Debug.ShouldStop(64);
if ((mostCurrent._txtmenup3.getText()).equals("")) { 
 BA.debugLineNum = 1160;BA.debugLine="Msgbox(\"โปรดใส่ปริมาณ\",\"แจ้งเตือน\")";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ปริมาณ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1161;BA.debugLine="txtmenup3.RequestFocus";
Debug.ShouldStop(256);
mostCurrent._txtmenup3.RequestFocus();
 BA.debugLineNum = 1162;BA.debugLine="Return";
Debug.ShouldStop(512);
if (true) return "";
 };
 BA.debugLineNum = 1173;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(1048576);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1174;BA.debugLine="sqlfood=\"insert into detailmenu(\"";
Debug.ShouldStop(2097152);
_sqlfood = "insert into detailmenu(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1175;BA.debugLine="sqlfood=sqlfood & \" material_id,\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" material_id,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1176;BA.debugLine="sqlfood=sqlfood & \" menufood_id,\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+" menufood_id,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1177;BA.debugLine="sqlfood=sqlfood & \" material_amount\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" material_amount";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1178;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1179;BA.debugLine="sqlfood=sqlfood & \"'\"&txtmenup1.Text&\"',\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+"'"+mostCurrent._txtmenup1.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1180;BA.debugLine="sqlfood=sqlfood & \"'\"&idmenu&\"',\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idmenu)+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1181;BA.debugLine="sqlfood=sqlfood & \"'\"&txtmenup3.text&\"'\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+"'"+mostCurrent._txtmenup3.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1182;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1183;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(1073741824);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 1184;BA.debugLine="showselectmat";
Debug.ShouldStop(-2147483648);
_showselectmat();
 BA.debugLineNum = 1185;BA.debugLine="Msgbox(\"บันทึกสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(1);
anywheresoftware.b4a.keywords.Common.Msgbox("บันทึกสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1186;BA.debugLine="txtmenup1.Text = \"\"";
Debug.ShouldStop(2);
mostCurrent._txtmenup1.setText((Object)(""));
 BA.debugLineNum = 1187;BA.debugLine="txtmenup2.Text = \"\"";
Debug.ShouldStop(4);
mostCurrent._txtmenup2.setText((Object)(""));
 BA.debugLineNum = 1188;BA.debugLine="txtmenup3.Text = \"\"";
Debug.ShouldStop(8);
mostCurrent._txtmenup3.setText((Object)(""));
 BA.debugLineNum = 1191;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btncopyre_click() throws Exception{
try {
		Debug.PushSubsStack("btncopyre_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1691);
 BA.debugLineNum = 1691;BA.debugLine="Sub btncopyre_Click														'+++แสดงวันที่ ที";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 1705;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(256);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1706;BA.debugLine="Activity.LoadLayout(\"showrepast\")";
Debug.ShouldStop(512);
mostCurrent._activity.LoadLayout("showrepast",mostCurrent.activityBA);
 BA.debugLineNum = 1707;BA.debugLine="pnshowre.Visible=False";
Debug.ShouldStop(1024);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1708;BA.debugLine="showrepast";
Debug.ShouldStop(2048);
_showrepast();
 BA.debugLineNum = 1727;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btncopyrepast_click() throws Exception{
try {
		Debug.PushSubsStack("btncopyrepast_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1866);
int _i = 0;
 BA.debugLineNum = 1866;BA.debugLine="Sub btncopyrepast_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 1867;BA.debugLine="Try";
Debug.ShouldStop(1024);
try { BA.debugLineNum = 1868;BA.debugLine="Dim i As Int";
Debug.ShouldStop(2048);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 1869;BA.debugLine="If pnshowre.Visible=False Then";
Debug.ShouldStop(4096);
if (mostCurrent._pnshowre.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 1870;BA.debugLine="pnshowre.Top =lblshowreintro1.Top + lblshowrein";
Debug.ShouldStop(8192);
mostCurrent._pnshowre.setTop((int) (mostCurrent._lblshowreintro1.getTop()+mostCurrent._lblshowreintro1.getHeight()));
 BA.debugLineNum = 1871;BA.debugLine="pnshowre.Height =100%y";
Debug.ShouldStop(16384);
mostCurrent._pnshowre.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 1872;BA.debugLine="pnshowre.Width =100%x";
Debug.ShouldStop(32768);
mostCurrent._pnshowre.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 1873;BA.debugLine="pnshowre.Visible=True";
Debug.ShouldStop(65536);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 1874;BA.debugLine="pnshowre.BringToFront";
Debug.ShouldStop(131072);
mostCurrent._pnshowre.BringToFront();
 BA.debugLineNum = 1877;BA.debugLine="showcopynamedate";
Debug.ShouldStop(1048576);
_showcopynamedate();
 BA.debugLineNum = 1878;BA.debugLine="i = Msgbox2(\"ต้องการคัดลอกมื้ออาหารหรือไม่\",\"";
Debug.ShouldStop(2097152);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("ต้องการคัดลอกมื้ออาหารหรือไม่","แจ้งเตือน","ยกเลิกกลับหน้าก่อน","","ใช่",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 1879;BA.debugLine="If i = -1 Then";
Debug.ShouldStop(4194304);
if (_i==-1) { 
 BA.debugLineNum = 1880;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(8388608);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1881;BA.debugLine="pnshowre.Visible=False";
Debug.ShouldStop(16777216);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1882;BA.debugLine="Return";
Debug.ShouldStop(33554432);
if (true) return "";
 }else 
{ BA.debugLineNum = 1883;BA.debugLine="Else If i=-2 Then";
Debug.ShouldStop(67108864);
if (_i==-2) { 
 BA.debugLineNum = 1884;BA.debugLine="Return";
Debug.ShouldStop(134217728);
if (true) return "";
 }else {
 BA.debugLineNum = 1886;BA.debugLine="Return";
Debug.ShouldStop(536870912);
if (true) return "";
 }};
 }else 
{ BA.debugLineNum = 1889;BA.debugLine="Else If pnshowre.Visible=True Then";
Debug.ShouldStop(1);
if (mostCurrent._pnshowre.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1890;BA.debugLine="pnshowre.Visible=False";
Debug.ShouldStop(2);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1893;BA.debugLine="showrepast";
Debug.ShouldStop(16);
_showrepast();
 }};
 } 
       catch (Exception e1535) {
			processBA.setLastException(e1535); BA.debugLineNum = 1897;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(256);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 1899;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btncopyselect_click() throws Exception{
try {
		Debug.PushSubsStack("btncopyselect_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1802);
int _a = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
int _r = 0;
String _sqlfood = "";
 BA.debugLineNum = 1802;BA.debugLine="Sub btncopyselect_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 1806;BA.debugLine="Dim A As Int";
Debug.ShouldStop(8192);
_a = 0;Debug.locals.put("A", _a);
 BA.debugLineNum = 1807;BA.debugLine="A = Msgbox2(\"การแก้ไขนี้จะทำให้ข้อมูลของมื้ออาหา";
Debug.ShouldStop(16384);
_a = anywheresoftware.b4a.keywords.Common.Msgbox2("การแก้ไขนี้จะทำให้ข้อมูลของมื้ออาหารในชื่อที่เลือกมีการเปลี่ยนแปลง","แจ้งเตือน","ยกเลิก","","แก้ไขทับ",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("A", _a);
 BA.debugLineNum = 1808;BA.debugLine="If A = -1 Then";
Debug.ShouldStop(32768);
if (_a==-1) { 
 BA.debugLineNum = 1809;BA.debugLine="Msgbox(\"ยกเลิกการคัดลอกมื้ออาหาร\",\"แจ้งเตือน\")";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Msgbox("ยกเลิกการคัดลอกมื้ออาหาร","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1810;BA.debugLine="pnshowre.Visible=False";
Debug.ShouldStop(131072);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1811;BA.debugLine="Return";
Debug.ShouldStop(262144);
if (true) return "";
 }else 
{ BA.debugLineNum = 1812;BA.debugLine="Else If A = -2 Then";
Debug.ShouldStop(524288);
if (_a==-2) { 
 BA.debugLineNum = 1814;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(2097152);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1815;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4194304);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1816;BA.debugLine="sql=\"select repast_date,repastname_name,repast";
Debug.ShouldStop(8388608);
_sql = "select repast_date,repastname_name,repast_count from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1817;BA.debugLine="sql=sql & \"order by repast_date\"";
Debug.ShouldStop(16777216);
_sql = _sql+"order by repast_date";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1818;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(33554432);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1819;BA.debugLine="For i = 0 To TotalRows-1";
Debug.ShouldStop(67108864);
{
final int step1474 = 1;
final int limit1474 = (int) (_totalrows-1);
for (_i = (int) (0); (step1474 > 0 && _i <= limit1474) || (step1474 < 0 && _i >= limit1474); _i = ((int)(0 + _i + step1474))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1820;BA.debugLine="If Table1.GetValue(2,i)= \"X\" Then  'start";
Debug.ShouldStop(134217728);
if ((mostCurrent._table1._getvalue((int) (2),_i)).equals("X")) { 
 BA.debugLineNum = 1821;BA.debugLine="For r= 0 To rs.RowCount -1";
Debug.ShouldStop(268435456);
{
final int step1476 = 1;
final int limit1476 = (int) (_rs.getRowCount()-1);
for (_r = (int) (0); (step1476 > 0 && _r <= limit1476) || (step1476 < 0 && _r >= limit1476); _r = ((int)(0 + _r + step1476))) {
Debug.locals.put("r", _r);
 BA.debugLineNum = 1824;BA.debugLine="rs.Position = r";
Debug.ShouldStop(-2147483648);
_rs.setPosition(_r);
 BA.debugLineNum = 1826;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(2);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1827;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(4);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1828;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1829;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1830;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1831;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1832;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1833;BA.debugLine="sqlfood=sqlfood & \"'\"& rs.GetString(\"r";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+"'"+_rs.GetString("repastname_name")+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1834;BA.debugLine="sqlfood=sqlfood & \"'\"& rs.GetString(\"r";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+"'"+_rs.GetString("repast_date")+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1835;BA.debugLine="sqlfood=sqlfood & \"'\"& rs.GetString(\"r";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+"'"+_rs.GetString("repast_count")+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1836;BA.debugLine="sqlfood=sqlfood & \"'\"& Table1.GetValue";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+"'"+mostCurrent._table1._getvalue((int) (0),_i)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1837;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1838;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(8192);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 }
}Debug.locals.put("r", _r);
;
 BA.debugLineNum = 1840;BA.debugLine="Msgbox(\"คัดลอกสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(32768);
anywheresoftware.b4a.keywords.Common.Msgbox("คัดลอกสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 };
 }
}Debug.locals.put("i", _i);
;
 }else {
 BA.debugLineNum = 1846;BA.debugLine="Return";
Debug.ShouldStop(2097152);
if (true) return "";
 }};
 BA.debugLineNum = 1852;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btndiet_click() throws Exception{
try {
		Debug.PushSubsStack("btndiet_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2294);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
String[] _sp = null;
 BA.debugLineNum = 2294;BA.debugLine="Sub btndiet_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 2295;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(4194304);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 2296;BA.debugLine="Activity.LoadLayout(\"nutrient\")";
Debug.ShouldStop(8388608);
mostCurrent._activity.LoadLayout("nutrient",mostCurrent.activityBA);
 BA.debugLineNum = 2297;BA.debugLine="Activity.SetBackgroundImage (LoadBitmap(File.DirA";
Debug.ShouldStop(16777216);
mostCurrent._activity.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"new-im2-1.jpg").getObject()));
 BA.debugLineNum = 2300;BA.debugLine="spselectday.Add(\"\")";
Debug.ShouldStop(134217728);
mostCurrent._spselectday.Add("");
 BA.debugLineNum = 2302;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(536870912);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2303;BA.debugLine="Dim sql As String";
Debug.ShouldStop(1073741824);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2304;BA.debugLine="sql=\"select repast_date from repastname where";
Debug.ShouldStop(-2147483648);
_sql = "select repast_date from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' group by repast_date order by repast_date ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2305;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2306;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(2);
{
final int step1760 = 1;
final int limit1760 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1760 > 0 && _i <= limit1760) || (step1760 < 0 && _i >= limit1760); _i = ((int)(0 + _i + step1760))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2307;BA.debugLine="rs.Position =i";
Debug.ShouldStop(4);
_rs.setPosition(_i);
 BA.debugLineNum = 2309;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(16);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2310;BA.debugLine="sp=Regex.Split(\"\\-\",rs.GetString(\"repast_dat";
Debug.ShouldStop(32);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\-",_rs.GetString("repast_date"));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2311;BA.debugLine="spselectday.Add((sp(2) & \"/\" & sp(1) & \"/\"";
Debug.ShouldStop(64);
mostCurrent._spselectday.Add((_sp[(int) (2)]+"/"+_sp[(int) (1)]+"/"+_sp[(int) (0)]));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2316;BA.debugLine="spselectday2.Add(\"\")";
Debug.ShouldStop(2048);
mostCurrent._spselectday2.Add("");
 BA.debugLineNum = 2317;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(4096);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2318;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8192);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2319;BA.debugLine="sql=\"select repast_date from repastname where";
Debug.ShouldStop(16384);
_sql = "select repast_date from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' group by repast_date order by repast_date ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2320;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(32768);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2321;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(65536);
{
final int step1771 = 1;
final int limit1771 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1771 > 0 && _i <= limit1771) || (step1771 < 0 && _i >= limit1771); _i = ((int)(0 + _i + step1771))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2322;BA.debugLine="rs.Position =i";
Debug.ShouldStop(131072);
_rs.setPosition(_i);
 BA.debugLineNum = 2324;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(524288);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2325;BA.debugLine="sp=Regex.Split(\"\\-\",rs.GetString(\"repast_da";
Debug.ShouldStop(1048576);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\-",_rs.GetString("repast_date"));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2326;BA.debugLine="spselectday2.Add((sp(2) & \"/\" & sp(1) & \"/\"";
Debug.ShouldStop(2097152);
mostCurrent._spselectday2.Add((_sp[(int) (2)]+"/"+_sp[(int) (1)]+"/"+_sp[(int) (0)]));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2329;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btndieteat_click() throws Exception{
try {
		Debug.PushSubsStack("btndieteat_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2115);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
double _x1 = 0;
double _x2 = 0;
int _i = 0;
 BA.debugLineNum = 2115;BA.debugLine="Sub btndieteat_Click";
Debug.ShouldStop(4);
 BA.debugLineNum = 2116;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(8);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 2118;BA.debugLine="Activity.LoadLayout(\"eatinrepast\")";
Debug.ShouldStop(32);
mostCurrent._activity.LoadLayout("eatinrepast",mostCurrent.activityBA);
 BA.debugLineNum = 2120;BA.debugLine="Table1.Initialize(Me, \"Table_eatinre\", 4)";
Debug.ShouldStop(128);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_eatinre",(int) (4));
 BA.debugLineNum = 2121;BA.debugLine="Table1.AddToActivity(Activity,0 ,lbldietintro1";
Debug.ShouldStop(256);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._lbldietintro1.getTop()+mostCurrent._lbldietintro1.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 BA.debugLineNum = 2122;BA.debugLine="Table1.SetHeader(Array As String(\"โภชนาการ\",\"ไ";
Debug.ShouldStop(512);
mostCurrent._table1._setheader(new String[]{"โภชนาการ","ได้รับ(หน่วย)","มาตรฐาน","ผลลัพธ์"});
 BA.debugLineNum = 2123;BA.debugLine="Table1.SetColumnsWidths(Array As Int(23%x,23%x";
Debug.ShouldStop(1024);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (23),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (23),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (24),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)});
 BA.debugLineNum = 2124;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(2048);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2125;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4096);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2126;BA.debugLine="sql= \"SELECT sum(material.kcal * detailmenu.";
Debug.ShouldStop(8192);
_sql = "SELECT sum(material.kcal * detailmenu.material_amount * takefood.takefood_count) As kcal,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2127;BA.debugLine="sql=sql & \" sum(material.prot * detailmenu.ma";
Debug.ShouldStop(16384);
_sql = _sql+" sum(material.prot * detailmenu.material_amount * takefood.takefood_count) As prot,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2128;BA.debugLine="sql=sql & \" sum(material.crab * detailmenu.m";
Debug.ShouldStop(32768);
_sql = _sql+" sum(material.crab * detailmenu.material_amount * takefood.takefood_count) As crab,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2129;BA.debugLine="sql=sql & \" sum(material.fiber * detailmenu.m";
Debug.ShouldStop(65536);
_sql = _sql+" sum(material.fiber * detailmenu.material_amount * takefood.takefood_count) As fiber,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2130;BA.debugLine="sql=sql & \" sum(material.fat * detailmenu.mat";
Debug.ShouldStop(131072);
_sql = _sql+" sum(material.fat * detailmenu.material_amount * takefood.takefood_count) As fat,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2131;BA.debugLine="sql=sql & \" sum(material.iron * detailmenu.ma";
Debug.ShouldStop(262144);
_sql = _sql+" sum(material.iron * detailmenu.material_amount * takefood.takefood_count) As iron,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2132;BA.debugLine="sql=sql & \" sum(material.chol * detailmenu.ma";
Debug.ShouldStop(524288);
_sql = _sql+" sum(material.chol * detailmenu.material_amount * takefood.takefood_count) As chol,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2133;BA.debugLine="sql=sql & \" sum(material.calc * detailmenu.ma";
Debug.ShouldStop(1048576);
_sql = _sql+" sum(material.calc * detailmenu.material_amount * takefood.takefood_count) As calc,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2134;BA.debugLine="sql=sql & \" sum(material.magn * detailmenu.ma";
Debug.ShouldStop(2097152);
_sql = _sql+" sum(material.magn * detailmenu.material_amount * takefood.takefood_count) As magn,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2135;BA.debugLine="sql=sql & \" sum(material.pota * detailmenu.ma";
Debug.ShouldStop(4194304);
_sql = _sql+" sum(material.pota * detailmenu.material_amount * takefood.takefood_count) As pota,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2136;BA.debugLine="sql=sql & \" sum(material.sodi * detailmenu.ma";
Debug.ShouldStop(8388608);
_sql = _sql+" sum(material.sodi * detailmenu.material_amount * takefood.takefood_count) As sodi,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2137;BA.debugLine="sql=sql & \" sum(material.zinc * detailmenu.ma";
Debug.ShouldStop(16777216);
_sql = _sql+" sum(material.zinc * detailmenu.material_amount * takefood.takefood_count) As zinc,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2138;BA.debugLine="sql=sql & \" sum(material.vitA * detailmenu.ma";
Debug.ShouldStop(33554432);
_sql = _sql+" sum(material.vitA * detailmenu.material_amount * takefood.takefood_count) As vitA,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2139;BA.debugLine="sql=sql & \" sum(material.vitB1 * detailmenu.m";
Debug.ShouldStop(67108864);
_sql = _sql+" sum(material.vitB1 * detailmenu.material_amount * takefood.takefood_count) As vitB1,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2140;BA.debugLine="sql=sql & \" sum(material.vitE * detailmenu.ma";
Debug.ShouldStop(134217728);
_sql = _sql+" sum(material.vitE * detailmenu.material_amount * takefood.takefood_count) As vitE,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2141;BA.debugLine="sql=sql & \" sum(material.vitB2 * detailmenu.m";
Debug.ShouldStop(268435456);
_sql = _sql+" sum(material.vitB2 * detailmenu.material_amount * takefood.takefood_count) As vitB2,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2142;BA.debugLine="sql=sql & \" sum(material.vitB3 * detailmenu.m";
Debug.ShouldStop(536870912);
_sql = _sql+" sum(material.vitB3 * detailmenu.material_amount * takefood.takefood_count) As vitB3,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2143;BA.debugLine="sql=sql & \" sum(material.vitB6 * detailmenu.m";
Debug.ShouldStop(1073741824);
_sql = _sql+" sum(material.vitB6 * detailmenu.material_amount * takefood.takefood_count) As vitB6,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2145;BA.debugLine="sql=sql & \" sum(material.vitC * detailmenu.ma";
Debug.ShouldStop(1);
_sql = _sql+" sum(material.vitC * detailmenu.material_amount * takefood.takefood_count) As vitC,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2146;BA.debugLine="sql=sql & \" sum(material.vitB12 * detailmenu.";
Debug.ShouldStop(2);
_sql = _sql+" sum(material.vitB12 * detailmenu.material_amount * takefood.takefood_count) As vitB12,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2147;BA.debugLine="sql=sql & \" sum(material.sele * detailmenu.ma";
Debug.ShouldStop(4);
_sql = _sql+" sum(material.sele * detailmenu.material_amount * takefood.takefood_count) As sele";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2148;BA.debugLine="sql=sql & \" FROM menufood INNER JOIN detailme";
Debug.ShouldStop(8);
_sql = _sql+" FROM menufood INNER JOIN detailmenu on detailmenu.menufood_id=menufood.menufood_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2149;BA.debugLine="sql=sql & \" INNER JOIN material on material.";
Debug.ShouldStop(16);
_sql = _sql+" INNER JOIN material on material.material_id = detailmenu.material_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2150;BA.debugLine="sql=sql & \" INNER JOIN takefood on takefood.m";
Debug.ShouldStop(32);
_sql = _sql+" INNER JOIN takefood on takefood.menufood_id = detailmenu.menufood_id ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2151;BA.debugLine="sql=sql & \" INNER JOIN repastname on repastna";
Debug.ShouldStop(64);
_sql = _sql+" INNER JOIN repastname on repastname.repastname_id = takefood.repastname_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2152;BA.debugLine="sql=sql & \" WHERE repast_date = '\" & idrepast";
Debug.ShouldStop(128);
_sql = _sql+" WHERE repast_date = '"+mostCurrent._idrepast+"' AND user_id = '"+BA.ObjectToString(mostCurrent._idname)+"' and repastname.repastname_id ='"+BA.ObjectToString(mostCurrent._eatid)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2154;BA.debugLine="Dim x1 As Double";
Debug.ShouldStop(512);
_x1 = 0;Debug.locals.put("x1", _x1);
 BA.debugLineNum = 2155;BA.debugLine="Dim x2 As Double";
Debug.ShouldStop(1024);
_x2 = 0;Debug.locals.put("x2", _x2);
 BA.debugLineNum = 2156;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(2048);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2157;BA.debugLine="For i = 0 To rs.ColumnCount -1";
Debug.ShouldStop(4096);
{
final int step1694 = 1;
final int limit1694 = (int) (_rs.getColumnCount()-1);
for (_i = (int) (0); (step1694 > 0 && _i <= limit1694) || (step1694 < 0 && _i >= limit1694); _i = ((int)(0 + _i + step1694))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2158;BA.debugLine="rs.Position =0";
Debug.ShouldStop(8192);
_rs.setPosition((int) (0));
 BA.debugLineNum = 2159;BA.debugLine="x1= getdri(\"thdri_\" & rs.GetColumnName(i))";
Debug.ShouldStop(16384);
_x1 = (double)(Double.parseDouble(_getdri("thdri_"+_rs.GetColumnName(_i))));Debug.locals.put("x1", _x1);
 BA.debugLineNum = 2160;BA.debugLine="x2= rs.GetString2(i) - x1";
Debug.ShouldStop(32768);
_x2 = (double)(Double.parseDouble(_rs.GetString2(_i)))-_x1;Debug.locals.put("x2", _x2);
 BA.debugLineNum = 2162;BA.debugLine="Table1.AddRow(Array As String(rs.GetColumn";
Debug.ShouldStop(131072);
mostCurrent._table1._addrow(new String[]{_rs.GetColumnName(_i),_rs.GetString2(_i),anywheresoftware.b4a.keywords.Common.NumberFormat2(_x1,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),anywheresoftware.b4a.keywords.Common.NumberFormat2(_x2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2164;BA.debugLine="TotalRows=i";
Debug.ShouldStop(524288);
_totalrows = _i;
 BA.debugLineNum = 2198;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btndmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btndmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,650);
 BA.debugLineNum = 650;BA.debugLine="Sub btndmenu_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 651;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1024);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 652;BA.debugLine="Activity.LoadLayout(\"detailmenu\")";
Debug.ShouldStop(2048);
mostCurrent._activity.LoadLayout("detailmenu",mostCurrent.activityBA);
 BA.debugLineNum = 653;BA.debugLine="btnmenufood_Click";
Debug.ShouldStop(4096);
_btnmenufood_click();
 BA.debugLineNum = 655;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btneatback_click() throws Exception{
try {
		Debug.PushSubsStack("btneatback_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1904);
 BA.debugLineNum = 1904;BA.debugLine="Sub btneatback_Click 'หน้ากินกลับหน้าหลัก";
Debug.ShouldStop(32768);
 BA.debugLineNum = 1905;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(65536);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1906;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(131072);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 1907;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btngoshowname_click() throws Exception{
try {
		Debug.PushSubsStack("btngoshowname_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,409);
 BA.debugLineNum = 409;BA.debugLine="Sub btngoshowname_Click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 410;BA.debugLine="If dataMode=\"new\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._datamode).equals("new")) { 
 BA.debugLineNum = 411;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(67108864);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 412;BA.debugLine="Activity.LoadLayout(\"showname\")";
Debug.ShouldStop(134217728);
mostCurrent._activity.LoadLayout("showname",mostCurrent.activityBA);
 BA.debugLineNum = 413;BA.debugLine="showname";
Debug.ShouldStop(268435456);
_showname();
 }else 
{ BA.debugLineNum = 414;BA.debugLine="Else if dataMode=\"Edit\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._datamode).equals("Edit")) { 
 BA.debugLineNum = 415;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1073741824);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 416;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 }else {
 BA.debugLineNum = 418;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(2);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 419;BA.debugLine="Activity.LoadLayout(\"showname\")";
Debug.ShouldStop(4);
mostCurrent._activity.LoadLayout("showname",mostCurrent.activityBA);
 BA.debugLineNum = 420;BA.debugLine="showname";
Debug.ShouldStop(8);
_showname();
 }};
 BA.debugLineNum = 423;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnmaterialtype_click() throws Exception{
try {
		Debug.PushSubsStack("btnmaterialtype_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,468);
 BA.debugLineNum = 468;BA.debugLine="Sub btnmaterialtype_Click";
Debug.ShouldStop(524288);
 BA.debugLineNum = 469;BA.debugLine="showfoodtype 'มาจากSub showfoodtype";
Debug.ShouldStop(1048576);
_showfoodtype();
 BA.debugLineNum = 470;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnmenuback_click() throws Exception{
try {
		Debug.PushSubsStack("btnmenuback_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1130);
 BA.debugLineNum = 1130;BA.debugLine="Sub btnmenuback_Click		'{";
Debug.ShouldStop(512);
 BA.debugLineNum = 1131;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(1024);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1132;BA.debugLine="Activity.LoadLayout(\"detailmenu\")";
Debug.ShouldStop(2048);
mostCurrent._activity.LoadLayout("detailmenu",mostCurrent.activityBA);
 BA.debugLineNum = 1133;BA.debugLine="showmenu";
Debug.ShouldStop(4096);
_showmenu();
 BA.debugLineNum = 1135;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnmenufood_click() throws Exception{
try {
		Debug.PushSubsStack("btnmenufood_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,472);
 BA.debugLineNum = 472;BA.debugLine="Sub btnmenufood_Click";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 473;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16777216);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 474;BA.debugLine="Activity.LoadLayout(\"detailmenu\")";
Debug.ShouldStop(33554432);
mostCurrent._activity.LoadLayout("detailmenu",mostCurrent.activityBA);
 BA.debugLineNum = 475;BA.debugLine="showmenu";
Debug.ShouldStop(67108864);
_showmenu();
 BA.debugLineNum = 476;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnmnmaterial_click() throws Exception{
try {
		Debug.PushSubsStack("btnmnmaterial_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,427);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 427;BA.debugLine="Sub btnmnmaterial_Click			'โชว์วัตถุดิบทั้งหมด";
Debug.ShouldStop(1024);
 BA.debugLineNum = 428;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(2048);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 429;BA.debugLine="Activity.LoadLayout(\"material\")";
Debug.ShouldStop(4096);
mostCurrent._activity.LoadLayout("material",mostCurrent.activityBA);
 BA.debugLineNum = 430;BA.debugLine="Table1.Initialize(Me, \"Table_material\"";
Debug.ShouldStop(8192);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_material",(int) (6));
 BA.debugLineNum = 431;BA.debugLine="Table1.AddToActivity(Activity, 0,btnaddfood.";
Debug.ShouldStop(16384);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._btnaddfood.getTop()+mostCurrent._btnaddfood.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnaddfood.getHeight()));
 BA.debugLineNum = 432;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อว";
Debug.ShouldStop(32768);
mostCurrent._table1._setheader(new String[]{"id","ชื่อวัตถุดิบ","โปรตีน","คาร์โบไฮเดรต","ไขมัน","..."});
 BA.debugLineNum = 433;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0%x,45%";
Debug.ShouldStop(65536);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (45),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA)});
 BA.debugLineNum = 434;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(131072);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 435;BA.debugLine="Dim sql As String";
Debug.ShouldStop(262144);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 436;BA.debugLine="sql=\"select crab,fat,prot,material_name,material";
Debug.ShouldStop(524288);
_sql = "select crab,fat,prot,material_name,material_id from material";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 437;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1048576);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 438;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(2097152);
mostCurrent._table1._clearall();
 BA.debugLineNum = 439;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(4194304);
{
final int step363 = 1;
final int limit363 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step363 > 0 && _i <= limit363) || (step363 < 0 && _i >= limit363); _i = ((int)(0 + _i + step363))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 440;BA.debugLine="rs.Position =i";
Debug.ShouldStop(8388608);
_rs.setPosition(_i);
 BA.debugLineNum = 441;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(\"m";
Debug.ShouldStop(16777216);
mostCurrent._table1._addrow(new String[]{_rs.GetString("material_id"),_rs.GetString("material_name"),_rs.GetString("prot"),_rs.GetString("crab"),_rs.GetString("fat"),"..."});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 445;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnmnrepast_click() throws Exception{
try {
		Debug.PushSubsStack("btnmnrepast_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1047);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
 BA.debugLineNum = 1047;BA.debugLine="Sub btnmnrepast_Click 		'จัดการมื้ออาหาร";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 1048;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(8388608);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1049;BA.debugLine="Activity.LoadLayout(\"mng_repast\")";
Debug.ShouldStop(16777216);
mostCurrent._activity.LoadLayout("mng_repast",mostCurrent.activityBA);
 BA.debugLineNum = 1050;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(33554432);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1051;BA.debugLine="Dim sql As String";
Debug.ShouldStop(67108864);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1052;BA.debugLine="sql=\"select namesuser,user_id from recorduser";
Debug.ShouldStop(134217728);
_sql = "select namesuser,user_id from recorduser where user_id='"+BA.NumberToString(_idname1)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1053;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(268435456);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1054;BA.debugLine="rs.Position =0";
Debug.ShouldStop(536870912);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1056;BA.debugLine="lblnamere.Text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(-2147483648);
mostCurrent._lblnamere.setText((Object)(_rs.GetString("namesuser")));
 BA.debugLineNum = 1057;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnnewname_click() throws Exception{
try {
		Debug.PushSubsStack("btnnewname_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,447);
 BA.debugLineNum = 447;BA.debugLine="Sub btnnewname_Click";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 448;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 449;BA.debugLine="Activity.LoadLayout(\"showname_new\")";
Debug.ShouldStop(1);
mostCurrent._activity.LoadLayout("showname_new",mostCurrent.activityBA);
 BA.debugLineNum = 450;BA.debugLine="dataMode=\"new\"";
Debug.ShouldStop(2);
mostCurrent._datamode = "new";
 BA.debugLineNum = 451;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnnutriback_click() throws Exception{
try {
		Debug.PushSubsStack("btnnutriback_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1283);
 BA.debugLineNum = 1283;BA.debugLine="Sub btnnutriback_Click";
Debug.ShouldStop(4);
 BA.debugLineNum = 1284;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(8);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1285;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(16);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 1286;BA.debugLine="End Sub";
Debug.ShouldStop(32);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnokmre_click() throws Exception{
try {
		Debug.PushSubsStack("btnokmre_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1348);
long _tom = 0L;
int _iday = 0;
String _daysave = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1348;BA.debugLine="Sub btnokmre_Click";
Debug.ShouldStop(8);
 BA.debugLineNum = 1349;BA.debugLine="Try";
Debug.ShouldStop(16);
try { BA.debugLineNum = 1350;BA.debugLine="DateTime.DateFormat = \"yyyy-MM-dd\"";
Debug.ShouldStop(32);
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy-MM-dd");
 BA.debugLineNum = 1351;BA.debugLine="Dim Tom As Long";
Debug.ShouldStop(64);
_tom = 0L;Debug.locals.put("Tom", _tom);
 BA.debugLineNum = 1352;BA.debugLine="Dim iDay As Int";
Debug.ShouldStop(128);
_iday = 0;Debug.locals.put("iDay", _iday);
 BA.debugLineNum = 1353;BA.debugLine="Dim DaySave As String";
Debug.ShouldStop(256);
_daysave = "";Debug.locals.put("DaySave", _daysave);
 BA.debugLineNum = 1355;BA.debugLine="If lblmonthre.Text=\"- เลือก -\" Or lblmonthreend.";
Debug.ShouldStop(1024);
if ((mostCurrent._lblmonthre.getText()).equals("- เลือก -") || (mostCurrent._lblmonthreend.getText()).equals("- เลือก -")) { 
 BA.debugLineNum = 1356;BA.debugLine="Msgbox(\"ตรวจสอบวันที่ในการเพิ่มมื้ออาหาร\",\"แจ้ง";
Debug.ShouldStop(2048);
anywheresoftware.b4a.keywords.Common.Msgbox("ตรวจสอบวันที่ในการเพิ่มมื้ออาหาร","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1357;BA.debugLine="Return";
Debug.ShouldStop(4096);
if (true) return "";
 };
 BA.debugLineNum = 1359;BA.debugLine="reUpdate=\"ALL\"";
Debug.ShouldStop(16384);
mostCurrent._reupdate = "ALL";
 BA.debugLineNum = 1360;BA.debugLine="Dim rs As Cursor			'";
Debug.ShouldStop(32768);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1361;BA.debugLine="Dim sql As String";
Debug.ShouldStop(65536);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1362;BA.debugLine="sql=\"select user_id,repast_date from repastna";
Debug.ShouldStop(131072);
_sql = "select user_id,repast_date from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"' and repast_date between '"+BA.ObjectToString(mostCurrent._lblmonthre.getTag())+"'  And '"+BA.ObjectToString(mostCurrent._lblmonthreend.getTag())+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1363;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(262144);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1364;BA.debugLine="rs.Position=0";
Debug.ShouldStop(524288);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1365;BA.debugLine="If rs.rowcount > 0 Then";
Debug.ShouldStop(1048576);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 1366;BA.debugLine="Dim i As Int";
Debug.ShouldStop(2097152);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 1367;BA.debugLine="i = Msgbox2(\"ในระหว่างวันที่เลือกมีการบันท";
Debug.ShouldStop(4194304);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("ในระหว่างวันที่เลือกมีการบันทึกมื้อในวันที่แล้ว ต้องการ 'แก้ไขทับ' หรือ 'ข้าม' มื้ออาหาร","แจ้งเตือน","แก้ไข","","ข้าม",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 1368;BA.debugLine="If i =-1 Then 			'แก้ไข";
Debug.ShouldStop(8388608);
if (_i==-1) { 
 BA.debugLineNum = 1369;BA.debugLine="reUpdate=\"Y\"";
Debug.ShouldStop(16777216);
mostCurrent._reupdate = "Y";
 }else 
{ BA.debugLineNum = 1370;BA.debugLine="Else If i= -2  Then 	'ข้าม";
Debug.ShouldStop(33554432);
if (_i==-2) { 
 BA.debugLineNum = 1371;BA.debugLine="reUpdate=\"N\"";
Debug.ShouldStop(67108864);
mostCurrent._reupdate = "N";
 }};
 };
 BA.debugLineNum = 1374;BA.debugLine="If  reUpdate=\"Y\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._reupdate).equals("Y")) { 
 BA.debugLineNum = 1375;BA.debugLine="dbSQL.ExecNonQuery(\"delete from rep";
Debug.ShouldStop(1073741824);
mostCurrent._dbsql.ExecNonQuery("delete from repastname where user_id='"+BA.ObjectToString(mostCurrent._idname)+"'and repast_date between '"+BA.ObjectToString(mostCurrent._lblmonthre.getTag())+"'  And '"+BA.ObjectToString(mostCurrent._lblmonthreend.getTag())+"'");
 };
 BA.debugLineNum = 1378;BA.debugLine="Do While  DaySave <>  lblmonthreend.Ta";
Debug.ShouldStop(2);
while ((_daysave).equals(BA.ObjectToString(mostCurrent._lblmonthreend.getTag())) == false) {
 BA.debugLineNum = 1379;BA.debugLine="Tom = DateTime.Add(DateTime.DatePa";
Debug.ShouldStop(4);
_tom = anywheresoftware.b4a.keywords.Common.DateTime.Add(anywheresoftware.b4a.keywords.Common.DateTime.DateParse(BA.ObjectToString(mostCurrent._lblmonthre.getTag())),(int) (0),(int) (0),_iday);Debug.locals.put("Tom", _tom);
 BA.debugLineNum = 1380;BA.debugLine="DaySave=DateTime.Date(Tom)";
Debug.ShouldStop(8);
_daysave = anywheresoftware.b4a.keywords.Common.DateTime.Date(_tom);Debug.locals.put("DaySave", _daysave);
 BA.debugLineNum = 1381;BA.debugLine="If  reUpdate=\"ALL\" Or  reUpdate=\"Y\"";
Debug.ShouldStop(16);
if ((mostCurrent._reupdate).equals("ALL") || (mostCurrent._reupdate).equals("Y")) { 
 BA.debugLineNum = 1382;BA.debugLine="SaveNewALL(DaySave)";
Debug.ShouldStop(32);
_savenewall(_daysave);
 }else {
 BA.debugLineNum = 1384;BA.debugLine="SaveUpdate(DaySave)";
Debug.ShouldStop(128);
_saveupdate(_daysave);
 };
 BA.debugLineNum = 1386;BA.debugLine="iDay=iDay+1";
Debug.ShouldStop(512);
_iday = (int) (_iday+1);Debug.locals.put("iDay", _iday);
 }
;
 BA.debugLineNum = 1390;BA.debugLine="Msgbox(\"เพิ่มมื้ออาหารสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.Msgbox("เพิ่มมื้ออาหารสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1391;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16384);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1392;BA.debugLine="Activity.LoadLayout(\"showrepast\")";
Debug.ShouldStop(32768);
mostCurrent._activity.LoadLayout("showrepast",mostCurrent.activityBA);
 BA.debugLineNum = 1393;BA.debugLine="pnshowre.Visible=True";
Debug.ShouldStop(65536);
mostCurrent._pnshowre.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 1394;BA.debugLine="showrepast";
Debug.ShouldStop(131072);
_showrepast();
 } 
       catch (Exception e1158) {
			processBA.setLastException(e1158); BA.debugLineNum = 1396;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 1399;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnrepastback_click() throws Exception{
try {
		Debug.PushSubsStack("btnrepastback_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1289);
 BA.debugLineNum = 1289;BA.debugLine="Sub btnrepastback_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 1290;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(512);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1291;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(1024);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 1292;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnsavemenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnsavemenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1065);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
String _sqlfood = "";
int _i = 0;
 BA.debugLineNum = 1065;BA.debugLine="Sub btnsavemenu_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 1079;BA.debugLine="Try";
Debug.ShouldStop(4194304);
try { BA.debugLineNum = 1080;BA.debugLine="If txtmenu.text=\"\" Then";
Debug.ShouldStop(8388608);
if ((mostCurrent._txtmenu.getText()).equals("")) { 
 BA.debugLineNum = 1081;BA.debugLine="Msgbox(\"โปรดใส่ชื่อเมนูอาหาร\",\"แจ้งเตือน\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ชื่อเมนูอาหาร","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1082;BA.debugLine="txtmenu.RequestFocus";
Debug.ShouldStop(33554432);
mostCurrent._txtmenu.RequestFocus();
 BA.debugLineNum = 1083;BA.debugLine="Return";
Debug.ShouldStop(67108864);
if (true) return "";
 };
 BA.debugLineNum = 1085;BA.debugLine="If	dataMode=\"new\" Then";
Debug.ShouldStop(268435456);
if ((mostCurrent._datamode).equals("new")) { 
 BA.debugLineNum = 1086;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามีหรือ";
Debug.ShouldStop(536870912);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1087;BA.debugLine="Dim sql As String";
Debug.ShouldStop(1073741824);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1088;BA.debugLine="sql=\"select menufood_name from menufood";
Debug.ShouldStop(-2147483648);
_sql = "select menufood_name from menufood where menufood_name='"+mostCurrent._txtmenu.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1089;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1090;BA.debugLine="If rs.RowCount>0 Then";
Debug.ShouldStop(2);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 1091;BA.debugLine="Msgbox(\"มีรายชื่อนี้แล้วในระบบ\",\"แจ้งเตื";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Msgbox("มีรายชื่อนี้แล้วในระบบ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1092;BA.debugLine="Return";
Debug.ShouldStop(8);
if (true) return "";
 };
 BA.debugLineNum = 1094;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(32);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1095;BA.debugLine="sqlfood=\"insert into menufood(\"";
Debug.ShouldStop(64);
_sqlfood = "insert into menufood(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1097;BA.debugLine="sqlfood=sqlfood & \" menufood_name\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+" menufood_name";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1098;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1100;BA.debugLine="sqlfood=sqlfood & \" '\"&txtmenu.text&\"'\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" '"+mostCurrent._txtmenu.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1101;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1102;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(8192);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 1103;BA.debugLine="Msgbox(\"บันทึกเมนูสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.Msgbox("บันทึกเมนูสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1104;BA.debugLine="btnmenufood_Click";
Debug.ShouldStop(32768);
_btnmenufood_click();
 }else 
{ BA.debugLineNum = 1106;BA.debugLine="Else If dataMode=\"Edit\" Then";
Debug.ShouldStop(131072);
if ((mostCurrent._datamode).equals("Edit")) { 
 BA.debugLineNum = 1108;BA.debugLine="Dim i As Int";
Debug.ShouldStop(524288);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 1109;BA.debugLine="i = Msgbox2(\"ยืนยันการแก้ไขชื่อเมนูหรือไม";
Debug.ShouldStop(1048576);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("ยืนยันการแก้ไขชื่อเมนูหรือไม่","แจ้งเตือน","ใช่","","ยกเลิก",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 1110;BA.debugLine="If i = -1 Then";
Debug.ShouldStop(2097152);
if (_i==-1) { 
 BA.debugLineNum = 1111;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(4194304);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1112;BA.debugLine="sqlfood=\"update   menufood set \"";
Debug.ShouldStop(8388608);
_sqlfood = "update   menufood set ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1113;BA.debugLine="sqlfood=sqlfood & \" menufood_name= '\"&t";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" menufood_name= '"+mostCurrent._txtmenu.getText()+"' ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1114;BA.debugLine="sqlfood=sqlfood & \" where menufood_id='";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" where menufood_id='"+BA.NumberToString(_idmenu)+"' ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1115;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(67108864);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 1116;BA.debugLine="Log(\"success\")";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Log("success");
 }else 
{ BA.debugLineNum = 1117;BA.debugLine="Else If i = -2 Then";
Debug.ShouldStop(268435456);
if (_i==-2) { 
 BA.debugLineNum = 1118;BA.debugLine="Return";
Debug.ShouldStop(536870912);
if (true) return "";
 }};
 }else {
 BA.debugLineNum = 1121;BA.debugLine="Return";
Debug.ShouldStop(1);
if (true) return "";
 }};
 } 
       catch (Exception e948) {
			processBA.setLastException(e948); BA.debugLineNum = 1124;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 1127;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnsavename_click() throws Exception{
try {
		Debug.PushSubsStack("btnsavename_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,297);
String _strsex = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
String _sqlfood = "";
 BA.debugLineNum = 297;BA.debugLine="Sub btnsavename_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 298;BA.debugLine="Try";
Debug.ShouldStop(512);
try { BA.debugLineNum = 300;BA.debugLine="If txtname.text=\"\" Then										'}";
Debug.ShouldStop(2048);
if ((mostCurrent._txtname.getText()).equals("")) { 
 BA.debugLineNum = 301;BA.debugLine="Msgbox(\"โปรดใส่ชื่อผู้ใช้\",\"แจ้งเตือน\")";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ชื่อผู้ใช้","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 302;BA.debugLine="txtname.RequestFocus";
Debug.ShouldStop(8192);
mostCurrent._txtname.RequestFocus();
 BA.debugLineNum = 303;BA.debugLine="Return";
Debug.ShouldStop(16384);
if (true) return "";
 }else 
{ BA.debugLineNum = 304;BA.debugLine="else if  lblhbd.Text = \"เลือกวันที่\" Then";
Debug.ShouldStop(32768);
if ((mostCurrent._lblhbd.getText()).equals("เลือกวันที่")) { 
 BA.debugLineNum = 305;BA.debugLine="Msgbox(\"โปรดเลือก วัน/เดือน/ปี เกิด!\",\"แจ้งเตือน";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดเลือก วัน/เดือน/ปี เกิด!","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 306;BA.debugLine="lblhbd.RequestFocus";
Debug.ShouldStop(131072);
mostCurrent._lblhbd.RequestFocus();
 BA.debugLineNum = 307;BA.debugLine="Return";
Debug.ShouldStop(262144);
if (true) return "";
 }else 
{ BA.debugLineNum = 308;BA.debugLine="else if txtwhight.text=\"\" Then";
Debug.ShouldStop(524288);
if ((mostCurrent._txtwhight.getText()).equals("")) { 
 BA.debugLineNum = 309;BA.debugLine="Msgbox(\"โปรดใส่ น้ำหนัก\",\"แจ้งเตือน\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ น้ำหนัก","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 310;BA.debugLine="txtwhight.RequestFocus";
Debug.ShouldStop(2097152);
mostCurrent._txtwhight.RequestFocus();
 BA.debugLineNum = 311;BA.debugLine="Return";
Debug.ShouldStop(4194304);
if (true) return "";
 }else 
{ BA.debugLineNum = 312;BA.debugLine="else if txtheight.text=\"\" Then";
Debug.ShouldStop(8388608);
if ((mostCurrent._txtheight.getText()).equals("")) { 
 BA.debugLineNum = 313;BA.debugLine="Msgbox(\"โปรดใส่ ส่วนสูง\",\"แจ้งเตือน\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ ส่วนสูง","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 314;BA.debugLine="txtheight.RequestFocus";
Debug.ShouldStop(33554432);
mostCurrent._txtheight.RequestFocus();
 BA.debugLineNum = 315;BA.debugLine="Return";
Debug.ShouldStop(67108864);
if (true) return "";
 }else 
{ BA.debugLineNum = 316;BA.debugLine="else if txtnickname.text=\"\" Then";
Debug.ShouldStop(134217728);
if ((mostCurrent._txtnickname.getText()).equals("")) { 
 BA.debugLineNum = 317;BA.debugLine="Msgbox(\"โปรดใส่ชื่อย่อ\",\"แจ้งเตือน\")";
Debug.ShouldStop(268435456);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ชื่อย่อ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 318;BA.debugLine="txtnickname.RequestFocus";
Debug.ShouldStop(536870912);
mostCurrent._txtnickname.RequestFocus();
 BA.debugLineNum = 319;BA.debugLine="Return";
Debug.ShouldStop(1073741824);
if (true) return "";
 }else {
 }}}}};
 BA.debugLineNum = 324;BA.debugLine="Dim strSex As String";
Debug.ShouldStop(8);
_strsex = "";Debug.locals.put("strSex", _strsex);
 BA.debugLineNum = 325;BA.debugLine="If RadioM.Checked =True Then";
Debug.ShouldStop(16);
if (mostCurrent._radiom.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 326;BA.debugLine="strSex=\"ชาย\"";
Debug.ShouldStop(32);
_strsex = "ชาย";Debug.locals.put("strSex", _strsex);
 }else {
 BA.debugLineNum = 328;BA.debugLine="strSex=\"หญิง\"";
Debug.ShouldStop(128);
_strsex = "หญิง";Debug.locals.put("strSex", _strsex);
 };
 BA.debugLineNum = 342;BA.debugLine="idname = txtname.text";
Debug.ShouldStop(2097152);
mostCurrent._idname = (Object)(mostCurrent._txtname.getText());
 BA.debugLineNum = 343;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามีหรือไม";
Debug.ShouldStop(4194304);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 344;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8388608);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 345;BA.debugLine="sql=\"select usernick from recorduser where n";
Debug.ShouldStop(16777216);
_sql = "select usernick from recorduser where namesuser='"+mostCurrent._txtnickname.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 346;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(33554432);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 347;BA.debugLine="If rs.RowCount>0 Then";
Debug.ShouldStop(67108864);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 348;BA.debugLine="Msgbox(\"มีชื่อย่อ\"+ txtnickname.text +\"นี้แล";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.NumberToString((double)(Double.parseDouble("มีชื่อย่อ"))+(double)(Double.parseDouble(mostCurrent._txtnickname.getText()))+(double)(Double.parseDouble("นี้แล้วในระบบ"))),"แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 349;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 };
 BA.debugLineNum = 352;BA.debugLine="Dim sqlfood As String										'}";
Debug.ShouldStop(-2147483648);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 353;BA.debugLine="If dataMode=\"new\" Then";
Debug.ShouldStop(1);
if ((mostCurrent._datamode).equals("new")) { 
 BA.debugLineNum = 354;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามีหรือไ";
Debug.ShouldStop(2);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 355;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 356;BA.debugLine="sql=\"select namesuser from recorduser whe";
Debug.ShouldStop(8);
_sql = "select namesuser from recorduser where namesuser='"+mostCurrent._txtname.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 357;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(16);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 358;BA.debugLine="If rs.RowCount>0 Then";
Debug.ShouldStop(32);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 359;BA.debugLine="Msgbox(\"มีรายชื่อ\" + txtname.text +\"แล้วใ";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.NumberToString((double)(Double.parseDouble("มีรายชื่อ"))+(double)(Double.parseDouble(mostCurrent._txtname.getText()))+(double)(Double.parseDouble("แล้วในระบบ"))),"แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 360;BA.debugLine="Return";
Debug.ShouldStop(128);
if (true) return "";
 };
 BA.debugLineNum = 363;BA.debugLine="sqlfood=\"insert into recorduser(\"";
Debug.ShouldStop(1024);
_sqlfood = "insert into recorduser(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 364;BA.debugLine="sqlfood=sqlfood & \" namesuser,\"   '1";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" namesuser,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 365;BA.debugLine="sqlfood=sqlfood & \" birthday,\" '2";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" birthday,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 366;BA.debugLine="sqlfood=sqlfood & \" whight,\" '3";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+" whight,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 367;BA.debugLine="sqlfood=sqlfood & \" hight,\" '4";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" hight,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 368;BA.debugLine="sqlfood=sqlfood & \" usernick,\" '5";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" usernick,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 369;BA.debugLine="sqlfood=sqlfood & \" sex\" '6";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" sex";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 370;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 371;BA.debugLine="sqlfood=sqlfood & \" '\"&txtname.text&\"',\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" '"+mostCurrent._txtname.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 372;BA.debugLine="sqlfood=sqlfood & \" '\"& lblhbd.tag &\"'\" '";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" '"+BA.ObjectToString(mostCurrent._lblhbd.getTag())+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 373;BA.debugLine="sqlfood=sqlfood & \" \"&txtwhight.text&\",\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+" "+mostCurrent._txtwhight.getText()+",";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 374;BA.debugLine="sqlfood=sqlfood & \" \"&txtheight.text&\",\"";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+" "+mostCurrent._txtheight.getText()+",";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 375;BA.debugLine="sqlfood=sqlfood & \" '\"&txtnickname.text&\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" '"+mostCurrent._txtnickname.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 377;BA.debugLine="sqlfood=sqlfood & \" '\"& strSex &\"'\" '";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" '"+_strsex+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 378;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 380;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามี";
Debug.ShouldStop(134217728);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 381;BA.debugLine="Dim sql As String";
Debug.ShouldStop(268435456);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 382;BA.debugLine="sql=\"select namesuser,user_id from r";
Debug.ShouldStop(536870912);
_sql = "select namesuser,user_id from recorduser where namesuser='"+mostCurrent._txtname.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 383;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1073741824);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 384;BA.debugLine="rs.Position=0";
Debug.ShouldStop(-2147483648);
_rs.setPosition((int) (0));
 BA.debugLineNum = 385;BA.debugLine="idname = rs.GetString(\"user_id\")";
Debug.ShouldStop(1);
mostCurrent._idname = (Object)(_rs.GetString("user_id"));
 }else 
{ BA.debugLineNum = 388;BA.debugLine="Else If dataMode=\"Edit\" Then";
Debug.ShouldStop(8);
if ((mostCurrent._datamode).equals("Edit")) { 
 BA.debugLineNum = 390;BA.debugLine="sqlfood=\"update recorduser set \"";
Debug.ShouldStop(32);
_sqlfood = "update recorduser set ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 391;BA.debugLine="sqlfood=sqlfood & \" namesuser= '\"&txtname.t";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" namesuser= '"+mostCurrent._txtname.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 392;BA.debugLine="sqlfood=sqlfood & \" birthday= '\"& lblhbd.ta";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" birthday= '"+BA.ObjectToString(mostCurrent._lblhbd.getTag())+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 394;BA.debugLine="sqlfood=sqlfood & \" whight='\" &txtwhight.te";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" whight='"+mostCurrent._txtwhight.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 395;BA.debugLine="sqlfood=sqlfood & \" hight='\" &txtheight.tex";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" hight='"+mostCurrent._txtheight.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 396;BA.debugLine="sqlfood=sqlfood & \" usernick='\" &txtnicknam";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" usernick='"+mostCurrent._txtnickname.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 397;BA.debugLine="sqlfood=sqlfood & \"where user_id='\" &idname";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+"where user_id='"+BA.ObjectToString(mostCurrent._idname)+"'";Debug.locals.put("sqlfood", _sqlfood);
 }};
 BA.debugLineNum = 399;BA.debugLine="dbSQL.ExecNonQuery(sqlfood) 								'} อินพ";
Debug.ShouldStop(16384);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 400;BA.debugLine="Msgbox(\"บันทึกรายชื่อสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(32768);
anywheresoftware.b4a.keywords.Common.Msgbox("บันทึกรายชื่อสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 401;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(65536);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 402;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(131072);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 } 
       catch (Exception e334) {
			processBA.setLastException(e334); BA.debugLineNum = 405;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 407;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnsavenmenu_click() throws Exception{
try {
		Debug.PushSubsStack("btnsavenmenu_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1217);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
 BA.debugLineNum = 1217;BA.debugLine="Sub btnsavenmenu_Click	'ปุ่มบันทึกชื่อเมนูอาหาร";
Debug.ShouldStop(1);
 BA.debugLineNum = 1218;BA.debugLine="dataMode=\"Edit\" 'เก็บค่า edit เพื่อให้รู้ว่านี่";
Debug.ShouldStop(2);
mostCurrent._datamode = "Edit";
 BA.debugLineNum = 1220;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(8);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1221;BA.debugLine="Activity.LoadLayout(\"cremenu\")";
Debug.ShouldStop(16);
mostCurrent._activity.LoadLayout("cremenu",mostCurrent.activityBA);
 BA.debugLineNum = 1222;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(32);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1223;BA.debugLine="Dim sql As String";
Debug.ShouldStop(64);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1224;BA.debugLine="sql=\"select menufood_id,menufood_name from";
Debug.ShouldStop(128);
_sql = "select menufood_id,menufood_name from menufood where menufood_id='"+BA.NumberToString(_idmenu)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1225;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(256);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1226;BA.debugLine="rs.Position =0";
Debug.ShouldStop(512);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1227;BA.debugLine="txtmenu.Text = rs.GetString(\"menufood_";
Debug.ShouldStop(1024);
mostCurrent._txtmenu.setText((Object)(_rs.GetString("menufood_name")));
 BA.debugLineNum = 1242;BA.debugLine="End Sub";
Debug.ShouldStop(33554432);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnsavetype_click() throws Exception{
try {
		Debug.PushSubsStack("btnsavetype_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,663);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
String _sqlfood = "";
 BA.debugLineNum = 663;BA.debugLine="Sub btnsavetype_Click";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 664;BA.debugLine="Try";
Debug.ShouldStop(8388608);
try { BA.debugLineNum = 666;BA.debugLine="If txtfoodtype.text=\"\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._txtfoodtype.getText()).equals("")) { 
 BA.debugLineNum = 667;BA.debugLine="Msgbox(\"โปรดใส่ชื่อประเภทอาหาร\",\"แจ้งเตือน\")";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Msgbox("โปรดใส่ชื่อประเภทอาหาร","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 668;BA.debugLine="txtfoodtype.RequestFocus";
Debug.ShouldStop(134217728);
mostCurrent._txtfoodtype.RequestFocus();
 BA.debugLineNum = 669;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 };
 BA.debugLineNum = 671;BA.debugLine="Dim rs As Cursor		'ค้นหา ชื่อซ้ำ ว่ามีหรือไม่ใ";
Debug.ShouldStop(1073741824);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 672;BA.debugLine="Dim sql As String";
Debug.ShouldStop(-2147483648);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 673;BA.debugLine="sql=\"select type_name from foodtype where type";
Debug.ShouldStop(1);
_sql = "select type_name from foodtype where type_name='"+mostCurrent._txtfoodtype.getText()+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 674;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(2);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 675;BA.debugLine="If rs.RowCount>0 Then";
Debug.ShouldStop(4);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 676;BA.debugLine="Msgbox(\"มีรายชื่อนี้แล้วในระบบ\",\"แจ้งเตือน\")";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.Msgbox("มีรายชื่อนี้แล้วในระบบ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 677;BA.debugLine="Return";
Debug.ShouldStop(16);
if (true) return "";
 };
 BA.debugLineNum = 679;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(64);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 680;BA.debugLine="If dataMode=\"new\"  Then 'ถ้าตัวแปรเป็น new";
Debug.ShouldStop(128);
if ((mostCurrent._datamode).equals("new")) { 
 BA.debugLineNum = 681;BA.debugLine="sqlfood=\"insert into foodtype(\"";
Debug.ShouldStop(256);
_sqlfood = "insert into foodtype(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 682;BA.debugLine="sqlfood=sqlfood & \" type_name\" '1";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" type_name";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 683;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 684;BA.debugLine="sqlfood=sqlfood & \" '\"&txtfoodtype.text&\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" '"+mostCurrent._txtfoodtype.getText()+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 685;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 }else {
 BA.debugLineNum = 687;BA.debugLine="sqlfood=\"update   foodtype set \"";
Debug.ShouldStop(16384);
_sqlfood = "update   foodtype set ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 688;BA.debugLine="sqlfood=sqlfood & \" type_name= '\"&txtfood";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" type_name= '"+mostCurrent._txtfoodtype.getText()+"' ";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 689;BA.debugLine="sqlfood=sqlfood & \" where type_id='\" & id";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" where type_id='"+mostCurrent._idtype+"' ";Debug.locals.put("sqlfood", _sqlfood);
 };
 BA.debugLineNum = 691;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(262144);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 BA.debugLineNum = 692;BA.debugLine="Msgbox(\"บันทึกชื่อประเภทสำเร็จ\",\"แจ้งเตือน";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.Msgbox("บันทึกชื่อประเภทสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 693;BA.debugLine="showfoodtype";
Debug.ShouldStop(1048576);
_showfoodtype();
 } 
       catch (Exception e572) {
			processBA.setLastException(e572); BA.debugLineNum = 697;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 699;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnshowtype_click() throws Exception{
try {
		Debug.PushSubsStack("btnshowtype_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,657);
 BA.debugLineNum = 657;BA.debugLine="Sub btnshowtype_Click";
Debug.ShouldStop(65536);
 BA.debugLineNum = 658;BA.debugLine="dataMode=\"new\" 'เก็บค่า new เพื่อนให้รู้ว่า";
Debug.ShouldStop(131072);
mostCurrent._datamode = "new";
 BA.debugLineNum = 659;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(262144);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 660;BA.debugLine="Activity.LoadLayout(\"foodtype_new\")";
Debug.ShouldStop(524288);
mostCurrent._activity.LoadLayout("foodtype_new",mostCurrent.activityBA);
 BA.debugLineNum = 661;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnuser_click() throws Exception{
try {
		Debug.PushSubsStack("btnuser_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,592);
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
String[] _sp = null;
 BA.debugLineNum = 592;BA.debugLine="Sub btnuser_Click											'ddddddddddddddddddddd";
Debug.ShouldStop(32768);
 BA.debugLineNum = 594;BA.debugLine="Dim i As Int";
Debug.ShouldStop(131072);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 595;BA.debugLine="i = Msgbox2(\"ยืนยันการแก้ไขข้อมูล\", \"แจ้งเตือน\",";
Debug.ShouldStop(262144);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("ยืนยันการแก้ไขข้อมูล","แจ้งเตือน","แก้ไข","","ยกเลิก",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 596;BA.debugLine="If i =-1 Then 'แก้ไข";
Debug.ShouldStop(524288);
if (_i==-1) { 
 }else 
{ BA.debugLineNum = 598;BA.debugLine="Else If i= -2  Then 'ยกเลิก";
Debug.ShouldStop(2097152);
if (_i==-2) { 
 BA.debugLineNum = 599;BA.debugLine="Return";
Debug.ShouldStop(4194304);
if (true) return "";
 }else {
 BA.debugLineNum = 601;BA.debugLine="Return";
Debug.ShouldStop(16777216);
if (true) return "";
 }};
 BA.debugLineNum = 603;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(67108864);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 604;BA.debugLine="Activity.LoadLayout(\"showname_new\")";
Debug.ShouldStop(134217728);
mostCurrent._activity.LoadLayout("showname_new",mostCurrent.activityBA);
 BA.debugLineNum = 605;BA.debugLine="dataMode=\"Edit\"";
Debug.ShouldStop(268435456);
mostCurrent._datamode = "Edit";
 BA.debugLineNum = 606;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(536870912);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 607;BA.debugLine="Dim sql As String";
Debug.ShouldStop(1073741824);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 608;BA.debugLine="sql=\"SELECT user_id,usernick,birthday,sex,name";
Debug.ShouldStop(-2147483648);
_sql = "SELECT user_id,usernick,birthday,sex,namesuser,whight,hight FROM recorduser where user_id='"+BA.ObjectToString(mostCurrent._idname)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 609;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 610;BA.debugLine="rs.Position = 0";
Debug.ShouldStop(2);
_rs.setPosition((int) (0));
 BA.debugLineNum = 611;BA.debugLine="txtname.text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(4);
mostCurrent._txtname.setText((Object)(_rs.GetString("namesuser")));
 BA.debugLineNum = 612;BA.debugLine="lblhbd.text = rs.GetString(\"birthday\")";
Debug.ShouldStop(8);
mostCurrent._lblhbd.setText((Object)(_rs.GetString("birthday")));
 BA.debugLineNum = 614;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(32);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 615;BA.debugLine="sp=Regex.Split(\"\\-\",lblhbd.Text)";
Debug.ShouldStop(64);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\-",mostCurrent._lblhbd.getText());Debug.locals.put("sp", _sp);
 BA.debugLineNum = 616;BA.debugLine="lblhbd.Text = sp(2) &\"/\"& sp(1) &\"/\" & sp(0)";
Debug.ShouldStop(128);
mostCurrent._lblhbd.setText((Object)(_sp[(int) (2)]+"/"+_sp[(int) (1)]+"/"+_sp[(int) (0)]));
 BA.debugLineNum = 617;BA.debugLine="txtwhight.text = rs.GetString(\"whight\")";
Debug.ShouldStop(256);
mostCurrent._txtwhight.setText((Object)(_rs.GetString("whight")));
 BA.debugLineNum = 618;BA.debugLine="txtheight.text = rs.GetString(\"hight\")";
Debug.ShouldStop(512);
mostCurrent._txtheight.setText((Object)(_rs.GetString("hight")));
 BA.debugLineNum = 619;BA.debugLine="txtnickname.text = rs.GetString(\"usernick\")";
Debug.ShouldStop(1024);
mostCurrent._txtnickname.setText((Object)(_rs.GetString("usernick")));
 BA.debugLineNum = 620;BA.debugLine="If rs.GetString(\"sex\") =(\"ชาย\") Then";
Debug.ShouldStop(2048);
if ((_rs.GetString("sex")).equals(("ชาย"))) { 
 BA.debugLineNum = 621;BA.debugLine="RadioM.Checked =True";
Debug.ShouldStop(4096);
mostCurrent._radiom.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 622;BA.debugLine="Else If rs.GetString(\"sex\") =(\"หญิง\") Then";
Debug.ShouldStop(8192);
if ((_rs.GetString("sex")).equals(("หญิง"))) { 
 BA.debugLineNum = 623;BA.debugLine="RadioW.Checked =True";
Debug.ShouldStop(16384);
mostCurrent._radiow.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }};
 BA.debugLineNum = 628;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _getcrab(double _idfood) throws Exception{
try {
		Debug.PushSubsStack("getCrab (main) ","main",0,mostCurrent.activityBA,mostCurrent,524);
String _ss = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
Debug.locals.put("idFood", _idfood);
 BA.debugLineNum = 524;BA.debugLine="Sub getCrab(idFood As Double)					'คาร์โบในเมน";
Debug.ShouldStop(2048);
 BA.debugLineNum = 525;BA.debugLine="Dim ss As String";
Debug.ShouldStop(4096);
_ss = "";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 526;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(8192);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 527;BA.debugLine="ss=\"select sum(material.crab * material_amount) a";
Debug.ShouldStop(16384);
_ss = "select sum(material.crab * material_amount) as totalG from material inner join detailmenu on material.material_id = detailmenu.material_id ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 528;BA.debugLine="ss=ss & \" where detailmenu.menufood_id = '\" & idF";
Debug.ShouldStop(32768);
_ss = _ss+" where detailmenu.menufood_id = '"+BA.NumberToString(_idfood)+"' ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 529;BA.debugLine="rs=dbSQL.ExecQuery(ss)";
Debug.ShouldStop(65536);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_ss)));
 BA.debugLineNum = 531;BA.debugLine="If rs.RowCount >0 Then";
Debug.ShouldStop(262144);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 532;BA.debugLine="rs.Position=0";
Debug.ShouldStop(524288);
_rs.setPosition((int) (0));
 BA.debugLineNum = 533;BA.debugLine="If rs.GetString(\"totalG\") =Null Then";
Debug.ShouldStop(1048576);
if (_rs.GetString("totalG")== null) { 
 BA.debugLineNum = 534;BA.debugLine="Return \"\"";
Debug.ShouldStop(2097152);
if (true) return "";
 }else {
 BA.debugLineNum = 536;BA.debugLine="Return rs.GetString(\"totalG\")";
Debug.ShouldStop(8388608);
if (true) return _rs.GetString("totalG");
 };
 }else {
 BA.debugLineNum = 539;BA.debugLine="Return \"\"";
Debug.ShouldStop(67108864);
if (true) return "";
 };
 BA.debugLineNum = 541;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _getdri(String _dri) throws Exception{
try {
		Debug.PushSubsStack("getdri (main) ","main",0,mostCurrent.activityBA,mostCurrent,2462);
String _ss = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
Debug.locals.put("dri", _dri);
 BA.debugLineNum = 2462;BA.debugLine="Sub getdri(dri As String) As String";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 2464;BA.debugLine="Dim ss As String";
Debug.ShouldStop(-2147483648);
_ss = "";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 2465;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(1);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2466;BA.debugLine="ss=\"select  \" & dri &\" from dri where sex_DRI='\"";
Debug.ShouldStop(2);
_ss = "select  "+_dri+" from dri where sex_DRI='"+mostCurrent._sexcal+"' And age_start <= '"+BA.NumberToString(_agecal)+"' and age_end >= '"+BA.NumberToString(_agecal)+"'";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 2467;BA.debugLine="rs=dbSQL.ExecQuery(ss)";
Debug.ShouldStop(4);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_ss)));
 BA.debugLineNum = 2468;BA.debugLine="If rs.RowCount >0 Then";
Debug.ShouldStop(8);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 2469;BA.debugLine="rs.Position=0";
Debug.ShouldStop(16);
_rs.setPosition((int) (0));
 BA.debugLineNum = 2470;BA.debugLine="Return  rs.GetString2(0)";
Debug.ShouldStop(32);
if (true) return _rs.GetString2((int) (0));
 }else {
 BA.debugLineNum = 2472;BA.debugLine="Return \"\"";
Debug.ShouldStop(128);
if (true) return "";
 };
 BA.debugLineNum = 2474;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _getdriinre(double _dri) throws Exception{
try {
		Debug.PushSubsStack("getdriinre (main) ","main",0,mostCurrent.activityBA,mostCurrent,2202);
String _ss = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
Debug.locals.put("dri", _dri);
 BA.debugLineNum = 2202;BA.debugLine="Sub getdriinre(dri As Double)";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 2205;BA.debugLine="Dim ss As String";
Debug.ShouldStop(268435456);
_ss = "";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 2206;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(536870912);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2207;BA.debugLine="ss=\"select  \" & dri &\" from dri where sex_DRI='\"";
Debug.ShouldStop(1073741824);
_ss = "select  "+BA.NumberToString(_dri)+" from dri where sex_DRI='"+mostCurrent._sexcal+"' And age_start <= '"+BA.NumberToString(_agecal)+"' and age_end >= '"+BA.NumberToString(_agecal)+"'";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 2209;BA.debugLine="rs=dbSQL.ExecQuery(ss)";
Debug.ShouldStop(1);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_ss)));
 BA.debugLineNum = 2210;BA.debugLine="If rs.RowCount >0 Then";
Debug.ShouldStop(2);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 2211;BA.debugLine="rs.Position=0";
Debug.ShouldStop(4);
_rs.setPosition((int) (0));
 BA.debugLineNum = 2212;BA.debugLine="Return  rs.GetString2(0)";
Debug.ShouldStop(8);
if (true) return _rs.GetString2((int) (0));
 };
 BA.debugLineNum = 2215;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _getfat(double _idfood) throws Exception{
try {
		Debug.PushSubsStack("getFat (main) ","main",0,mostCurrent.activityBA,mostCurrent,543);
String _ss = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
Debug.locals.put("idFood", _idfood);
 BA.debugLineNum = 543;BA.debugLine="Sub getFat(idFood As Double)					'ไว้ทำเพิ่ม ไขมัน";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 544;BA.debugLine="Dim ss As String";
Debug.ShouldStop(-2147483648);
_ss = "";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 545;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(1);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 546;BA.debugLine="ss=\"select sum(material.fat * material_amount) as";
Debug.ShouldStop(2);
_ss = "select sum(material.fat * material_amount) as totalG from material inner join detailmenu on material.material_id = detailmenu.material_id ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 547;BA.debugLine="ss=ss & \" where detailmenu.menufood_id = '\" & idF";
Debug.ShouldStop(4);
_ss = _ss+" where detailmenu.menufood_id = '"+BA.NumberToString(_idfood)+"' ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 548;BA.debugLine="rs=dbSQL.ExecQuery(ss)";
Debug.ShouldStop(8);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_ss)));
 BA.debugLineNum = 550;BA.debugLine="If rs.RowCount >0 Then";
Debug.ShouldStop(32);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 551;BA.debugLine="rs.Position=0";
Debug.ShouldStop(64);
_rs.setPosition((int) (0));
 BA.debugLineNum = 552;BA.debugLine="If rs.GetString(\"totalG\") =Null Then";
Debug.ShouldStop(128);
if (_rs.GetString("totalG")== null) { 
 BA.debugLineNum = 553;BA.debugLine="Return \"\"";
Debug.ShouldStop(256);
if (true) return "";
 }else {
 BA.debugLineNum = 555;BA.debugLine="Return rs.GetString(\"totalG\")";
Debug.ShouldStop(1024);
if (true) return _rs.GetString("totalG");
 };
 }else {
 BA.debugLineNum = 558;BA.debugLine="Return \"\"";
Debug.ShouldStop(8192);
if (true) return "";
 };
 BA.debugLineNum = 560;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _getprot(double _idfood) throws Exception{
try {
		Debug.PushSubsStack("getProt (main) ","main",0,mostCurrent.activityBA,mostCurrent,506);
String _ss = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
Debug.locals.put("idFood", _idfood);
 BA.debugLineNum = 506;BA.debugLine="Sub getProt(idFood As Double)					'โปรตีน";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 507;BA.debugLine="Dim ss As String";
Debug.ShouldStop(67108864);
_ss = "";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 508;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(134217728);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 509;BA.debugLine="ss=\"select sum(material.prot * material_amount) a";
Debug.ShouldStop(268435456);
_ss = "select sum(material.prot * material_amount) as totalG from material inner join detailmenu on material.material_id = detailmenu.material_id ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 510;BA.debugLine="ss=ss & \" where detailmenu.menufood_id = '\" & idF";
Debug.ShouldStop(536870912);
_ss = _ss+" where detailmenu.menufood_id = '"+BA.NumberToString(_idfood)+"' ";Debug.locals.put("ss", _ss);
 BA.debugLineNum = 511;BA.debugLine="rs=dbSQL.ExecQuery(ss)";
Debug.ShouldStop(1073741824);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_ss)));
 BA.debugLineNum = 512;BA.debugLine="If rs.RowCount >0 Then";
Debug.ShouldStop(-2147483648);
if (_rs.getRowCount()>0) { 
 BA.debugLineNum = 513;BA.debugLine="rs.Position=0";
Debug.ShouldStop(1);
_rs.setPosition((int) (0));
 BA.debugLineNum = 514;BA.debugLine="If rs.GetString(\"totalG\") =Null Then";
Debug.ShouldStop(2);
if (_rs.GetString("totalG")== null) { 
 BA.debugLineNum = 515;BA.debugLine="Return \"\"";
Debug.ShouldStop(4);
if (true) return "";
 }else {
 BA.debugLineNum = 517;BA.debugLine="Return rs.GetString(\"totalG\")";
Debug.ShouldStop(16);
if (true) return _rs.GetString("totalG");
 };
 }else {
 BA.debugLineNum = 520;BA.debugLine="Return \"\"";
Debug.ShouldStop(128);
if (true) return "";
 };
 BA.debugLineNum = 522;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Dim kcal As Int";
_kcal = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim agecal As Int";
_agecal = 0;
 //BA.debugLineNum = 24;BA.debugLine="Dim sexcal As String";
mostCurrent._sexcal = "";
 //BA.debugLineNum = 25;BA.debugLine="Dim idnutri1 As String";
mostCurrent._idnutri1 = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim idnutri As String";
mostCurrent._idnutri = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim caldateeat As Int		'ทำฟังชันเรียกชื่อ";
_caldateeat = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim eatid As Object";
mostCurrent._eatid = new Object();
 //BA.debugLineNum = 30;BA.debugLine="Dim TotalRows As Int";
_totalrows = 0;
 //BA.debugLineNum = 31;BA.debugLine="Dim menutorepast As Int";
_menutorepast = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim copyrepast As String";
mostCurrent._copyrepast = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim reUpdate As String";
mostCurrent._reupdate = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim dataMode As String  'ตัวแปรเก็บค่า ต้องการ เพ";
mostCurrent._datamode = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim Table1 As Table";
mostCurrent._table1 = new b4a.DailyNutrition.table();
 //BA.debugLineNum = 36;BA.debugLine="Dim idname As Object";
mostCurrent._idname = new Object();
 //BA.debugLineNum = 37;BA.debugLine="Dim idname1 As Int";
_idname1 = 0;
 //BA.debugLineNum = 38;BA.debugLine="Dim idtype As String";
mostCurrent._idtype = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim idrepast As String";
mostCurrent._idrepast = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim idmat As Int";
_idmat = 0;
 //BA.debugLineNum = 42;BA.debugLine="Dim idmenu As Int";
_idmenu = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim dbSQL As SQL";
mostCurrent._dbsql = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 44;BA.debugLine="Private txtname As EditText";
mostCurrent._txtname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private txtwhight As EditText";
mostCurrent._txtwhight = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private txtnickname As EditText";
mostCurrent._txtnickname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private txtheight As EditText";
mostCurrent._txtheight = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lvname As ListView";
mostCurrent._lvname = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblhbd As Label";
mostCurrent._lblhbd = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btnmaterialtype As Button";
mostCurrent._btnmaterialtype = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private btndetail As Button";
mostCurrent._btndetail = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private btnmenufood As Button";
mostCurrent._btnmenufood = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private btnuser As Button";
mostCurrent._btnuser = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btnbh1 As Button";
mostCurrent._btnbh1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private btnbhdtf As Button";
mostCurrent._btnbhdtf = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private btnbhdtmn As Button";
mostCurrent._btnbhdtmn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private btnbacktype As Button";
mostCurrent._btnbacktype = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private txtfoodtype As EditText";
mostCurrent._txtfoodtype = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private btnsavetype As Button";
mostCurrent._btnsavetype = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private btnshowtype As Button";
mostCurrent._btnshowtype = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private btnaddfood As Button";
mostCurrent._btnaddfood = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private btnmnmaterial As Button";
mostCurrent._btnmnmaterial = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private scvmngmaterial As ScrollView";
mostCurrent._scvmngmaterial = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private btnbhmate As Button";
mostCurrent._btnbhmate = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private btnbmenun As Button";
mostCurrent._btnbmenun = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private txtmate1 As EditText";
mostCurrent._txtmate1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private txtmate2 As EditText";
mostCurrent._txtmate2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private txtmate3 As EditText";
mostCurrent._txtmate3 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private txtmate4 As EditText";
mostCurrent._txtmate4 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private txtmate5 As EditText";
mostCurrent._txtmate5 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private txtmate6 As EditText";
mostCurrent._txtmate6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private txtmate7 As EditText";
mostCurrent._txtmate7 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private txtmate8 As EditText";
mostCurrent._txtmate8 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private txtmate9 As EditText";
mostCurrent._txtmate9 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private txtmate10 As EditText";
mostCurrent._txtmate10 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private txtmate11 As EditText";
mostCurrent._txtmate11 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private txtmate12 As EditText";
mostCurrent._txtmate12 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private txtmate13 As EditText";
mostCurrent._txtmate13 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private txtmate14 As EditText";
mostCurrent._txtmate14 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private txtmate15 As EditText";
mostCurrent._txtmate15 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Private txtmate16 As EditText";
mostCurrent._txtmate16 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private txtmate17 As EditText";
mostCurrent._txtmate17 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private txtmate18 As EditText";
mostCurrent._txtmate18 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private txtmate19 As EditText";
mostCurrent._txtmate19 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private txtmate20 As EditText";
mostCurrent._txtmate20 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private txtmate21 As EditText";
mostCurrent._txtmate21 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private txtmate22 As EditText";
mostCurrent._txtmate22 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private txtmate23 As EditText";
mostCurrent._txtmate23 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private txtmate24 As EditText";
mostCurrent._txtmate24 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private btnconmaterial As Button";
mostCurrent._btnconmaterial = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private btnaddmenu As Button";
mostCurrent._btnaddmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private btndmenu As Button";
mostCurrent._btndmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private btncalmenu As Button";
mostCurrent._btncalmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private btndiet As Button";
mostCurrent._btndiet = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private btnsavemenu As Button";
mostCurrent._btnsavemenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private txtmenu As EditText";
mostCurrent._txtmenu = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnmenuback As Button";
mostCurrent._btnmenuback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private txtmenup1 As EditText";
mostCurrent._txtmenup1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private txtmenup2 As EditText";
mostCurrent._txtmenup2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private txtmenup3 As EditText";
mostCurrent._txtmenup3 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Private btnaddmatmenu As Button";
mostCurrent._btnaddmatmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Private btnconmenu As Button";
mostCurrent._btnconmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Private lvmatmenu As ListView";
mostCurrent._lvmatmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 108;BA.debugLine="Private btnblackmngmenu As Button";
mostCurrent._btnblackmngmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Private btneatback As Button";
mostCurrent._btneatback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 110;BA.debugLine="Private btnnutriback As Button";
mostCurrent._btnnutriback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Private RadioM As RadioButton";
mostCurrent._radiom = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Private RadioW As RadioButton";
mostCurrent._radiow = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private btnsavenmenu As Button";
mostCurrent._btnsavenmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 114;BA.debugLine="Private lblmonthre As Label";
mostCurrent._lblmonthre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Private btnokmre As Button";
mostCurrent._btnokmre = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private txtone As EditText";
mostCurrent._txtone = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private txttwo As EditText";
mostCurrent._txttwo = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Private txtthree As EditText";
mostCurrent._txtthree = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private txtfour As EditText";
mostCurrent._txtfour = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Private txtfive As EditText";
mostCurrent._txtfive = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 121;BA.debugLine="Private txtsix As EditText";
mostCurrent._txtsix = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 122;BA.debugLine="Private txtseven As EditText";
mostCurrent._txtseven = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 123;BA.debugLine="Private cbone As CheckBox";
mostCurrent._cbone = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 124;BA.debugLine="Private cbtwo As CheckBox";
mostCurrent._cbtwo = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Private cbthree As CheckBox";
mostCurrent._cbthree = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 126;BA.debugLine="Private cbfour As CheckBox";
mostCurrent._cbfour = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 127;BA.debugLine="Private cbfive As CheckBox";
mostCurrent._cbfive = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Private cbsix As CheckBox";
mostCurrent._cbsix = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private cbseven As CheckBox";
mostCurrent._cbseven = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 130;BA.debugLine="Private test As Label";
mostCurrent._test = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 131;BA.debugLine="Private Pnre As Panel";
mostCurrent._pnre = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Private lblrepast As Label";
mostCurrent._lblrepast = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 133;BA.debugLine="Private lblmonthreend As Label";
mostCurrent._lblmonthreend = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 134;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 135;BA.debugLine="Private btnrepastback As Button";
mostCurrent._btnrepastback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 136;BA.debugLine="Private btnmnrepast As Button";
mostCurrent._btnmnrepast = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 137;BA.debugLine="Private btnblackhome1 As Button";
mostCurrent._btnblackhome1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 138;BA.debugLine="Private btncopyre As Button";
mostCurrent._btncopyre = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Private btnviewrepast As Button";
mostCurrent._btnviewrepast = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 140;BA.debugLine="Private btnblackhome2 As Button";
mostCurrent._btnblackhome2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Private btncopyrepast As Button";
mostCurrent._btncopyrepast = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 142;BA.debugLine="Private Spshowdate As Spinner";
mostCurrent._spshowdate = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private lvshowre As ListView";
mostCurrent._lvshowre = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Private lblidname As Label";
mostCurrent._lblidname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private lblnamere As Label";
mostCurrent._lblnamere = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 146;BA.debugLine="Private btnaddeat As Button";
mostCurrent._btnaddeat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 147;BA.debugLine="Private btndieteat As Button";
mostCurrent._btndieteat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Private lvfoodinday As ListView";
mostCurrent._lvfoodinday = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 149;BA.debugLine="Private pnshowre As Panel";
mostCurrent._pnshowre = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 150;BA.debugLine="Private pneat As Panel";
mostCurrent._pneat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 151;BA.debugLine="Private btncopyselect As Button";
mostCurrent._btncopyselect = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 152;BA.debugLine="Private txtmate25 As EditText";
mostCurrent._txtmate25 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Private btnconeat As Button";
mostCurrent._btnconeat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Private lblrepastdiet As Label";
mostCurrent._lblrepastdiet = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private lblselectday As Label";
mostCurrent._lblselectday = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Private lblselectday1 As Label";
mostCurrent._lblselectday1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private lblnu1 As Label";
mostCurrent._lblnu1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Private lblnu2 As Label";
mostCurrent._lblnu2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Private lblnu3 As Label";
mostCurrent._lblnu3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Private btncondate As Button";
mostCurrent._btncondate = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private btnnewname As Button";
mostCurrent._btnnewname = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 163;BA.debugLine="Private lblintro As Label";
mostCurrent._lblintro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 164;BA.debugLine="Private lblintro1 As Label";
mostCurrent._lblintro1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Private btnintro As Button";
mostCurrent._btnintro = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Private lblreintro As Label";
mostCurrent._lblreintro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 167;BA.debugLine="Private btngoshowname As Button";
mostCurrent._btngoshowname = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 168;BA.debugLine="Private lblmngmaterial As Label";
mostCurrent._lblmngmaterial = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 169;BA.debugLine="Private btndelmaterial As Button";
mostCurrent._btndelmaterial = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 170;BA.debugLine="Private btnbackuser As Button";
mostCurrent._btnbackuser = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Private lblshowreintro1 As Label";
mostCurrent._lblshowreintro1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 172;BA.debugLine="Private lblmngmaterial1 As Label";
mostCurrent._lblmngmaterial1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Private lblmenu2 As Label";
mostCurrent._lblmenu2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Private lbleatintro As Label";
mostCurrent._lbleatintro = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Private spselectday As Spinner";
mostCurrent._spselectday = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 176;BA.debugLine="Private spselectday2 As Spinner";
mostCurrent._spselectday2 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 177;BA.debugLine="Private spselectday3 As Spinner";
mostCurrent._spselectday3 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 178;BA.debugLine="Private lblmidre As Label";
mostCurrent._lblmidre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 179;BA.debugLine="Private lblmidre1 As Label";
mostCurrent._lblmidre1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 180;BA.debugLine="Private btnbhometoeatre As Button";
mostCurrent._btnbhometoeatre = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Private lblDiet1 As Label";
mostCurrent._lbldiet1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Private lbldietintro1 As Label";
mostCurrent._lbldietintro1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _lblhbd_click() throws Exception{
try {
		Debug.PushSubsStack("lblhbd_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,453);
int _ret = 0;
anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dd = null;
 BA.debugLineNum = 453;BA.debugLine="Sub lblhbd_Click";
Debug.ShouldStop(16);
 BA.debugLineNum = 454;BA.debugLine="Dim ret As Int";
Debug.ShouldStop(32);
_ret = 0;Debug.locals.put("ret", _ret);
 BA.debugLineNum = 455;BA.debugLine="Dim Dd As DateDialog													'เลือกวันเกิด";
Debug.ShouldStop(64);
_dd = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();Debug.locals.put("Dd", _dd);
 BA.debugLineNum = 456;BA.debugLine="Dd.Year = DateTime.GetYear(DateTime.Now)";
Debug.ShouldStop(128);
_dd.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 457;BA.debugLine="Dd.Month = DateTime.GetMonth(DateTime.Now)";
Debug.ShouldStop(256);
_dd.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 458;BA.debugLine="Dd.DayOfMonth = DateTime.GetDayOfMonth(DateTime.N";
Debug.ShouldStop(512);
_dd.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 459;BA.debugLine="ret = Dd.Show(\"Set the required date\", \"B4A Date";
Debug.ShouldStop(1024);
_ret = _dd.Show("Set the required date","B4A Date Dialog","No","Yes","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));Debug.locals.put("ret", _ret);
 BA.debugLineNum = 460;BA.debugLine="If ret=-1 Then";
Debug.ShouldStop(2048);
if (_ret==-1) { 
 BA.debugLineNum = 461;BA.debugLine="lblhbd.text = Dd.DayOfMonth & \"/\" & Dd.Month &";
Debug.ShouldStop(4096);
mostCurrent._lblhbd.setText((Object)(BA.NumberToString(_dd.getDayOfMonth())+"/"+BA.NumberToString(_dd.getMonth())+"/"+BA.NumberToString(_dd.getYear())));
 BA.debugLineNum = 462;BA.debugLine="lblhbd.tag =  Dd.Year & \"-\" & Dd.Month & \"-\" &";
Debug.ShouldStop(8192);
mostCurrent._lblhbd.setTag((Object)(BA.NumberToString(_dd.getYear())+"-"+BA.NumberToString(_dd.getMonth())+"-"+BA.NumberToString(_dd.getDayOfMonth())));
 }else 
{ BA.debugLineNum = 463;BA.debugLine="Else If ret=-3 Then";
Debug.ShouldStop(16384);
if (_ret==-3) { 
 BA.debugLineNum = 464;BA.debugLine="Return";
Debug.ShouldStop(32768);
if (true) return "";
 }};
 BA.debugLineNum = 466;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblmonthre_click() throws Exception{
try {
		Debug.PushSubsStack("lblmonthre_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1294);
int _ret = 0;
anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dd = null;
String _a = "";
String _b = "";
 BA.debugLineNum = 1294;BA.debugLine="Sub lblmonthre_Click";
Debug.ShouldStop(8192);
 BA.debugLineNum = 1295;BA.debugLine="Dim ret As Int";
Debug.ShouldStop(16384);
_ret = 0;Debug.locals.put("ret", _ret);
 BA.debugLineNum = 1296;BA.debugLine="Dim Dd As DateDialog													'เลือกวันหน้าจัด";
Debug.ShouldStop(32768);
_dd = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();Debug.locals.put("Dd", _dd);
 BA.debugLineNum = 1297;BA.debugLine="Dd.Year = DateTime.GetYear(DateTime.Now)";
Debug.ShouldStop(65536);
_dd.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1298;BA.debugLine="Dd.Month = DateTime.GetMonth(DateTime.Now)";
Debug.ShouldStop(131072);
_dd.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1299;BA.debugLine="Dd.DayOfMonth = DateTime.GetDayOfMonth(DateTime.N";
Debug.ShouldStop(262144);
_dd.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1300;BA.debugLine="ret = Dd.Show(\"แตะเพื่อเลือกวันที่\", \"ต้้งค่ามื้อ";
Debug.ShouldStop(524288);
_ret = _dd.Show("แตะเพื่อเลือกวันที่","ต้้งค่ามื้ออาหาร","No","Yes","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));Debug.locals.put("ret", _ret);
 BA.debugLineNum = 1301;BA.debugLine="If ret=-3 Then";
Debug.ShouldStop(1048576);
if (_ret==-3) { 
 BA.debugLineNum = 1302;BA.debugLine="lblmonthre.text = Dd.DayOfMonth & \"/\" & Dd.Mon";
Debug.ShouldStop(2097152);
mostCurrent._lblmonthre.setText((Object)(BA.NumberToString(_dd.getDayOfMonth())+"/"+BA.NumberToString(_dd.getMonth())+"/"+BA.NumberToString(_dd.getYear())));
 BA.debugLineNum = 1303;BA.debugLine="Dim a As String";
Debug.ShouldStop(4194304);
_a = "";Debug.locals.put("a", _a);
 BA.debugLineNum = 1304;BA.debugLine="Dim b As String";
Debug.ShouldStop(8388608);
_b = "";Debug.locals.put("b", _b);
 BA.debugLineNum = 1305;BA.debugLine="a=Dd.Month";
Debug.ShouldStop(16777216);
_a = BA.NumberToString(_dd.getMonth());Debug.locals.put("a", _a);
 BA.debugLineNum = 1306;BA.debugLine="b=Dd.DayOfMonth";
Debug.ShouldStop(33554432);
_b = BA.NumberToString(_dd.getDayOfMonth());Debug.locals.put("b", _b);
 BA.debugLineNum = 1307;BA.debugLine="If a.Length =1 Then a=\"0\" & a";
Debug.ShouldStop(67108864);
if (_a.length()==1) { 
_a = "0"+_a;Debug.locals.put("a", _a);};
 BA.debugLineNum = 1308;BA.debugLine="If b.Length=1 Then b=\"0\" & b";
Debug.ShouldStop(134217728);
if (_b.length()==1) { 
_b = "0"+_b;Debug.locals.put("b", _b);};
 BA.debugLineNum = 1309;BA.debugLine="lblmonthre.tag =  Dd.Year & \"-\" & a & \"-\" & b";
Debug.ShouldStop(268435456);
mostCurrent._lblmonthre.setTag((Object)(BA.NumberToString(_dd.getYear())+"-"+_a+"-"+_b));
 }else {
 BA.debugLineNum = 1311;BA.debugLine="Return";
Debug.ShouldStop(1073741824);
if (true) return "";
 };
 BA.debugLineNum = 1314;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblmonthreend_click() throws Exception{
try {
		Debug.PushSubsStack("lblmonthreend_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1315);
int _set = 0;
anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dd = null;
String _a = "";
String _b = "";
 BA.debugLineNum = 1315;BA.debugLine="Sub lblmonthreend_Click			'เลือกวันหน้าจัดการมื้อ";
Debug.ShouldStop(4);
 BA.debugLineNum = 1316;BA.debugLine="Dim set As Int";
Debug.ShouldStop(8);
_set = 0;Debug.locals.put("set", _set);
 BA.debugLineNum = 1317;BA.debugLine="Dim Dd As DateDialog													'เลือกวันหน้าจัด";
Debug.ShouldStop(16);
_dd = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();Debug.locals.put("Dd", _dd);
 BA.debugLineNum = 1318;BA.debugLine="Dd.Year = DateTime.GetYear(DateTime.Now)";
Debug.ShouldStop(32);
_dd.setYear(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1319;BA.debugLine="Dd.Month = DateTime.GetMonth(DateTime.Now)";
Debug.ShouldStop(64);
_dd.setMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1320;BA.debugLine="Dd.DayOfMonth = DateTime.GetDayOfMonth(DateTime.N";
Debug.ShouldStop(128);
_dd.setDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 BA.debugLineNum = 1321;BA.debugLine="set = Dd.Show(\"แตะเพื่อเลือกวันที่\", \"ต้้งค่ามื้อ";
Debug.ShouldStop(256);
_set = _dd.Show("แตะเพื่อเลือกวันที่","ต้้งค่ามื้ออาหาร","No","Yes","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));Debug.locals.put("set", _set);
 BA.debugLineNum = 1322;BA.debugLine="If set=-3 Then";
Debug.ShouldStop(512);
if (_set==-3) { 
 BA.debugLineNum = 1323;BA.debugLine="lblmonthreend.text = Dd.DayOfMonth & \"/\" & Dd.";
Debug.ShouldStop(1024);
mostCurrent._lblmonthreend.setText((Object)(BA.NumberToString(_dd.getDayOfMonth())+"/"+BA.NumberToString(_dd.getMonth())+"/"+BA.NumberToString(_dd.getYear())));
 BA.debugLineNum = 1324;BA.debugLine="Dim a As String";
Debug.ShouldStop(2048);
_a = "";Debug.locals.put("a", _a);
 BA.debugLineNum = 1325;BA.debugLine="Dim b As String";
Debug.ShouldStop(4096);
_b = "";Debug.locals.put("b", _b);
 BA.debugLineNum = 1326;BA.debugLine="a=Dd.Month";
Debug.ShouldStop(8192);
_a = BA.NumberToString(_dd.getMonth());Debug.locals.put("a", _a);
 BA.debugLineNum = 1327;BA.debugLine="b=Dd.DayOfMonth";
Debug.ShouldStop(16384);
_b = BA.NumberToString(_dd.getDayOfMonth());Debug.locals.put("b", _b);
 BA.debugLineNum = 1328;BA.debugLine="If a.Length =1 Then a=\"0\" & a";
Debug.ShouldStop(32768);
if (_a.length()==1) { 
_a = "0"+_a;Debug.locals.put("a", _a);};
 BA.debugLineNum = 1329;BA.debugLine="If b.Length=1 Then b=\"0\" & b";
Debug.ShouldStop(65536);
if (_b.length()==1) { 
_b = "0"+_b;Debug.locals.put("b", _b);};
 BA.debugLineNum = 1330;BA.debugLine="lblmonthreend.tag =  Dd.Year & \"-\" & a & \"-\" &";
Debug.ShouldStop(131072);
mostCurrent._lblmonthreend.setTag((Object)(BA.NumberToString(_dd.getYear())+"-"+_a+"-"+_b));
 }else {
 BA.debugLineNum = 1332;BA.debugLine="Return";
Debug.ShouldStop(524288);
if (true) return "";
 };
 BA.debugLineNum = 1346;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblselectday_click() throws Exception{
try {
		Debug.PushSubsStack("lblselectday_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2340);
 BA.debugLineNum = 2340;BA.debugLine="Sub lblselectday_Click			' เปลี่ยน1เป็น2";
Debug.ShouldStop(8);
 BA.debugLineNum = 2355;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblselectday1_click() throws Exception{
try {
		Debug.PushSubsStack("lblselectday1_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,2357);
 BA.debugLineNum = 2357;BA.debugLine="Sub lblselectday1_Click			' เปลี่ยน1เป็น2";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 2372;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lsfoodtype_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lsfoodtype_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,773);
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 773;BA.debugLine="Sub lsfoodtype_ItemClick (Position As Int, Value A";
Debug.ShouldStop(16);
 BA.debugLineNum = 774;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(32);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 775;BA.debugLine="Activity.LoadLayout(\"typetomate\")";
Debug.ShouldStop(64);
mostCurrent._activity.LoadLayout("typetomate",mostCurrent.activityBA);
 BA.debugLineNum = 776;BA.debugLine="Msgbox(Value,\"test\")";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(_value),"test",mostCurrent.activityBA);
 BA.debugLineNum = 777;BA.debugLine="End Sub";
Debug.ShouldStop(256);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lvfoodinday_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lvfoodinday_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2021);
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 2021;BA.debugLine="Sub lvfoodinday_ItemClick (Position As Int, Value";
Debug.ShouldStop(16);
 BA.debugLineNum = 2037;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lvmatmenu_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lvmatmenu_itemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,1267);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 1267;BA.debugLine="Sub lvmatmenu_itemClick(Position As Int,Value As O";
Debug.ShouldStop(262144);
 BA.debugLineNum = 1268;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(524288);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1269;BA.debugLine="Dim sql As String";
Debug.ShouldStop(1048576);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1270;BA.debugLine="sql=\"select material_name,material_id from";
Debug.ShouldStop(2097152);
_sql = "select material_name,material_id from material where material_id='"+BA.ObjectToString(_value)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1271;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(4194304);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1272;BA.debugLine="rs.Position = 0";
Debug.ShouldStop(8388608);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1273;BA.debugLine="txtmenup1.Text = Value";
Debug.ShouldStop(16777216);
mostCurrent._txtmenup1.setText(_value);
 BA.debugLineNum = 1274;BA.debugLine="txtmenup2.Text = rs.GetString(\"material_name\")";
Debug.ShouldStop(33554432);
mostCurrent._txtmenup2.setText((Object)(_rs.GetString("material_name")));
 BA.debugLineNum = 1275;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lvname_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lvname_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,217);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 217;BA.debugLine="Sub lvname_ItemClick (Position As Int, Value As Ob";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 218;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(33554432);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 219;BA.debugLine="idname = Value";
Debug.ShouldStop(67108864);
mostCurrent._idname = _value;
 BA.debugLineNum = 220;BA.debugLine="idname1 = Value";
Debug.ShouldStop(134217728);
_idname1 = (int)(BA.ObjectToNumber(_value));
 BA.debugLineNum = 221;BA.debugLine="Activity.LoadLayout(\"home\")";
Debug.ShouldStop(268435456);
mostCurrent._activity.LoadLayout("home",mostCurrent.activityBA);
 BA.debugLineNum = 225;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(1);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 226;BA.debugLine="Dim sql As String";
Debug.ShouldStop(2);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 227;BA.debugLine="sql=\"select sex,birthday,user_id,namesuser,jul";
Debug.ShouldStop(4);
_sql = "select sex,birthday,user_id,namesuser,julianday('now') - julianday(birthday) as days from recorduser";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 228;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(8);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 229;BA.debugLine="rs.Position = 0";
Debug.ShouldStop(16);
_rs.setPosition((int) (0));
 BA.debugLineNum = 230;BA.debugLine="agecal = rs.GetString(\"days\")/365";
Debug.ShouldStop(32);
_agecal = (int) ((double)(Double.parseDouble(_rs.GetString("days")))/(double)365);
 BA.debugLineNum = 232;BA.debugLine="If agecal <=8 Then";
Debug.ShouldStop(128);
if (_agecal<=8) { 
 BA.debugLineNum = 233;BA.debugLine="sexcal=\"ร\"";
Debug.ShouldStop(256);
mostCurrent._sexcal = "ร";
 }else {
 BA.debugLineNum = 235;BA.debugLine="If rs.GetString(\"sex\")=\"ชาย\" Then";
Debug.ShouldStop(1024);
if ((_rs.GetString("sex")).equals("ชาย")) { 
 BA.debugLineNum = 236;BA.debugLine="sexcal = \"ช\"";
Debug.ShouldStop(2048);
mostCurrent._sexcal = "ช";
 }else {
 BA.debugLineNum = 238;BA.debugLine="sexcal = \"ญ\"";
Debug.ShouldStop(8192);
mostCurrent._sexcal = "ญ";
 };
 };
 BA.debugLineNum = 245;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lvname_itemlongclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lvname_ItemLongClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,255);
int _i = 0;
String _sql = "";
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String[] _sp = null;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 255;BA.debugLine="Sub lvname_ItemLongClick (Position As Int, Value A";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 256;BA.debugLine="Try";
Debug.ShouldStop(-2147483648);
try { BA.debugLineNum = 257;BA.debugLine="Dim i As Int";
Debug.ShouldStop(1);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 258;BA.debugLine="i = Msgbox2(\"กรุณาเลือก?\", \"แจ้งเตือน\", \"แก้ไข\",";
Debug.ShouldStop(2);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("กรุณาเลือก?","แจ้งเตือน","แก้ไข","","ลบ",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 260;BA.debugLine="If i=-1 Then   'do Yes code";
Debug.ShouldStop(8);
if (_i==-1) { 
 BA.debugLineNum = 261;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 262;BA.debugLine="Activity.LoadLayout(\"showname_new\")";
Debug.ShouldStop(32);
mostCurrent._activity.LoadLayout("showname_new",mostCurrent.activityBA);
 BA.debugLineNum = 263;BA.debugLine="dataMode=\"edit\"";
Debug.ShouldStop(64);
mostCurrent._datamode = "edit";
 BA.debugLineNum = 264;BA.debugLine="Dim sql As String";
Debug.ShouldStop(128);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 265;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(256);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 266;BA.debugLine="sql=\"select namesuser,birthday,whight,hight,";
Debug.ShouldStop(512);
_sql = "select namesuser,birthday,whight,hight,usernick,sex from recorduser where user_id='"+BA.ObjectToString(_value)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 267;BA.debugLine="rs = dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1024);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 268;BA.debugLine="rs.Position=0";
Debug.ShouldStop(2048);
_rs.setPosition((int) (0));
 BA.debugLineNum = 269;BA.debugLine="txtname.Text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(4096);
mostCurrent._txtname.setText((Object)(_rs.GetString("namesuser")));
 BA.debugLineNum = 270;BA.debugLine="lblhbd.Text = rs.GetString(\"birthday\")";
Debug.ShouldStop(8192);
mostCurrent._lblhbd.setText((Object)(_rs.GetString("birthday")));
 BA.debugLineNum = 271;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(16384);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 272;BA.debugLine="sp=Regex.Split(\"\\-\",lblhbd.Text)";
Debug.ShouldStop(32768);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\-",mostCurrent._lblhbd.getText());Debug.locals.put("sp", _sp);
 BA.debugLineNum = 273;BA.debugLine="lblhbd.Text = sp(2) &\"/\"& sp(1) &\"/\"& sp(0)";
Debug.ShouldStop(65536);
mostCurrent._lblhbd.setText((Object)(_sp[(int) (2)]+"/"+_sp[(int) (1)]+"/"+_sp[(int) (0)]));
 BA.debugLineNum = 274;BA.debugLine="txtwhight.Text = rs.GetInt(\"whight\")		'we";
Debug.ShouldStop(131072);
mostCurrent._txtwhight.setText((Object)(_rs.GetInt("whight")));
 BA.debugLineNum = 275;BA.debugLine="txtheight.Text = rs.GetInt(\"hight\")		'hei";
Debug.ShouldStop(262144);
mostCurrent._txtheight.setText((Object)(_rs.GetInt("hight")));
 BA.debugLineNum = 276;BA.debugLine="txtnickname.Text = rs.GetString(\"usernick";
Debug.ShouldStop(524288);
mostCurrent._txtnickname.setText((Object)(_rs.GetString("usernick")));
 BA.debugLineNum = 277;BA.debugLine="If rs.GetString(\"sex\") =(\"ชาย\") Then";
Debug.ShouldStop(1048576);
if ((_rs.GetString("sex")).equals(("ชาย"))) { 
 BA.debugLineNum = 278;BA.debugLine="RadioM.Checked =True";
Debug.ShouldStop(2097152);
mostCurrent._radiom.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 279;BA.debugLine="Else If rs.GetString(\"sex\") =(\"หญิง\") Th";
Debug.ShouldStop(4194304);
if ((_rs.GetString("sex")).equals(("หญิง"))) { 
 BA.debugLineNum = 280;BA.debugLine="RadioW.Checked =True";
Debug.ShouldStop(8388608);
mostCurrent._radiow.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }};
 }else 
{ BA.debugLineNum = 282;BA.debugLine="Else If i=-3 Then";
Debug.ShouldStop(33554432);
if (_i==-3) { 
 BA.debugLineNum = 283;BA.debugLine="Dim sql As String";
Debug.ShouldStop(67108864);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 284;BA.debugLine="sql=\"delete from recorduser where user_id=";
Debug.ShouldStop(134217728);
_sql = "delete from recorduser where user_id='"+BA.ObjectToString(_value)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 285;BA.debugLine="dbSQL.ExecNonQuery(sql)";
Debug.ShouldStop(268435456);
mostCurrent._dbsql.ExecNonQuery(_sql);
 BA.debugLineNum = 287;BA.debugLine="lvname.Clear";
Debug.ShouldStop(1073741824);
mostCurrent._lvname.Clear();
 BA.debugLineNum = 288;BA.debugLine="showname";
Debug.ShouldStop(-2147483648);
_showname();
 }else {
 BA.debugLineNum = 290;BA.debugLine="Return";
Debug.ShouldStop(2);
if (true) return "";
 }};
 } 
       catch (Exception e247) {
			processBA.setLastException(e247); BA.debugLineNum = 293;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(16);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 295;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lvshowre_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("lvshowre_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,1984);
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 1984;BA.debugLine="Sub lvshowre_ItemClick (Position As Int, Value As";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 1988;BA.debugLine="eatid = Value";
Debug.ShouldStop(8);
mostCurrent._eatid = _value;
 BA.debugLineNum = 1989;BA.debugLine="btnaddeat.Visible=True";
Debug.ShouldStop(16);
mostCurrent._btnaddeat.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 1990;BA.debugLine="btnaddeat.Enabled=True";
Debug.ShouldStop(32);
mostCurrent._btnaddeat.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 2003;BA.debugLine="nameeat";
Debug.ShouldStop(262144);
_nameeat();
 BA.debugLineNum = 2008;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _nameeat() throws Exception{
try {
		Debug.PushSubsStack("nameeat (main) ","main",0,mostCurrent.activityBA,mostCurrent,2278);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 2278;BA.debugLine="Sub nameeat";
Debug.ShouldStop(32);
 BA.debugLineNum = 2279;BA.debugLine="lvfoodinday.Clear";
Debug.ShouldStop(64);
mostCurrent._lvfoodinday.Clear();
 BA.debugLineNum = 2280;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(128);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2281;BA.debugLine="Dim sql As String";
Debug.ShouldStop(256);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2282;BA.debugLine="sql=\"select takefood.repastname_id,takefood.t";
Debug.ShouldStop(512);
_sql = "select takefood.repastname_id,takefood.takefood_count,menufood.menufood_id,menufood.menufood_name from takefood ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2283;BA.debugLine="sql=sql & \" inner join menufood on takefood.m";
Debug.ShouldStop(1024);
_sql = _sql+" inner join menufood on takefood.menufood_id = menufood.menufood_id ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2284;BA.debugLine="sql=sql & \" where takefood.repastname_id='\" &";
Debug.ShouldStop(2048);
_sql = _sql+" where takefood.repastname_id='"+BA.ObjectToString(mostCurrent._eatid)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2285;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(4096);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2286;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(8192);
{
final int step1746 = 1;
final int limit1746 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1746 > 0 && _i <= limit1746) || (step1746 < 0 && _i >= limit1746); _i = ((int)(0 + _i + step1746))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2287;BA.debugLine="rs.Position =i";
Debug.ShouldStop(16384);
_rs.setPosition(_i);
 BA.debugLineNum = 2288;BA.debugLine="lvfoodinday.AddSingleLine2(rs.GetString(\"menu";
Debug.ShouldStop(32768);
mostCurrent._lvfoodinday.AddSingleLine2(_rs.GetString("menufood_name"),(Object)(_rs.GetString("repastname_id")));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2290;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
    if (mostCurrent != null && mostCurrent.activityBA != null) {
Debug.StartDebugging(mostCurrent.activityBA, 12298, new int[] {79, 12}, "a5af7b8e-6316-4ae2-9e9e-6c846b5904af");}

    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _savenewall(String _daysave) throws Exception{
try {
		Debug.PushSubsStack("SaveNewALL (main) ","main",0,mostCurrent.activityBA,mostCurrent,1519);
String _sqlfood = "";
Debug.locals.put("DaySave", _daysave);
 BA.debugLineNum = 1519;BA.debugLine="Sub SaveNewALL(DaySave As String)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 1520;BA.debugLine="If cbone.Checked=True Then";
Debug.ShouldStop(32768);
if (mostCurrent._cbone.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1521;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(65536);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1522;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(131072);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1523;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1524;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1525;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1526;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1527;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1528;BA.debugLine="sqlfood=sqlfood & \"'\"&txtone.Text&\"',\" 'เก";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+"'"+mostCurrent._txtone.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1529;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1530;BA.debugLine="sqlfood=sqlfood & \"'1',\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+"'1',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1531;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1532;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1533;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(268435456);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1535;BA.debugLine="If cbtwo.Checked=True Then";
Debug.ShouldStop(1073741824);
if (mostCurrent._cbtwo.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1536;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(-2147483648);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1537;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1538;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1539;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1540;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1541;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1542;BA.debugLine="sqlfood=sqlfood & \"'\"&txttwo.Text&\"',\" 'เก";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+"'"+mostCurrent._txttwo.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1543;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1544;BA.debugLine="sqlfood=sqlfood & \"'2',\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+"'2',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1545;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1546;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1547;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(1024);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1549;BA.debugLine="If cbthree.Checked=True Then";
Debug.ShouldStop(4096);
if (mostCurrent._cbthree.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1550;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(8192);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1551;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1552;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1553;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1554;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1555;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1556;BA.debugLine="sqlfood=sqlfood & \"'\"&txtthree.Text&\"',\" '";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+"'"+mostCurrent._txtthree.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1557;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1558;BA.debugLine="sqlfood=sqlfood & \"'3',\"";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+"'3',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1559;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1560;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1561;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(16777216);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1563;BA.debugLine="If cbfour.Checked=True Then";
Debug.ShouldStop(67108864);
if (mostCurrent._cbfour.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1564;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(134217728);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1565;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1566;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1567;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1568;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1569;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1570;BA.debugLine="sqlfood=sqlfood & \"'\"&txtfour.Text&\"',\" 'เ";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+"'"+mostCurrent._txtfour.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1571;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1572;BA.debugLine="sqlfood=sqlfood & \"'4',\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+"'4',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1573;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1574;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1575;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(64);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1577;BA.debugLine="If cbfive.Checked=True Then";
Debug.ShouldStop(256);
if (mostCurrent._cbfive.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1578;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(512);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1579;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1580;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1581;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1582;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1583;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1584;BA.debugLine="sqlfood=sqlfood & \"'\"&txtfive.Text&\"',\" 'เ";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+"'"+mostCurrent._txtfive.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1585;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1586;BA.debugLine="sqlfood=sqlfood & \"'5',\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+"'5',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1587;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1588;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1589;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(1048576);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1591;BA.debugLine="If cbsix.Checked=True Then";
Debug.ShouldStop(4194304);
if (mostCurrent._cbsix.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1592;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(8388608);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1593;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1594;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1595;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1596;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1597;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1598;BA.debugLine="sqlfood=sqlfood & \"'\"&txtsix.Text&\"',\" 'เก";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+"'"+mostCurrent._txtsix.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1599;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1600;BA.debugLine="sqlfood=sqlfood & \"'6',\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+"'6',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1601;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1602;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1603;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(4);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1605;BA.debugLine="If cbseven.Checked=True Then";
Debug.ShouldStop(16);
if (mostCurrent._cbseven.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1606;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(32);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1607;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1608;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1609;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1610;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1611;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1612;BA.debugLine="sqlfood=sqlfood & \"'\"&txtseven.Text&\"',\" '";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+"'"+mostCurrent._txtseven.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1613;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1614;BA.debugLine="sqlfood=sqlfood & \"'7',\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+"'7',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1615;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1616;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1617;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(65536);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1619;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _saveupdate(String _daysave) throws Exception{
try {
		Debug.PushSubsStack("SaveUpdate (main) ","main",0,mostCurrent.activityBA,mostCurrent,1400);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
String _sqlfood = "";
Debug.locals.put("DaySave", _daysave);
 BA.debugLineNum = 1400;BA.debugLine="Sub SaveUpdate(DaySave As String)				'repastcount";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 1411;BA.debugLine="Dim rs As Cursor			'";
Debug.ShouldStop(4);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1412;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1413;BA.debugLine="sql=\"select user_id,repast_date from repastna";
Debug.ShouldStop(16);
_sql = "select user_id,repast_date from repastname where user_id='"+BA.NumberToString(_idname1)+"' and repast_date = '"+_daysave+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1414;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(32);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1415;BA.debugLine="If rs.rowcount = 0   Then";
Debug.ShouldStop(64);
if (_rs.getRowCount()==0) { 
 BA.debugLineNum = 1416;BA.debugLine="If cbone.Checked=True Then";
Debug.ShouldStop(128);
if (mostCurrent._cbone.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1417;BA.debugLine="Dim sqlfood As String";
Debug.ShouldStop(256);
_sqlfood = "";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1418;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(512);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1419;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1420;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1421;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1422;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1423;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1424;BA.debugLine="sqlfood=sqlfood & \"'\"&txtone.Text&\"',\" '";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+"'"+mostCurrent._txtone.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1425;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1426;BA.debugLine="sqlfood=sqlfood & \"'1',\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+"'1',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1427;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1428;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1429;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(1048576);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1431;BA.debugLine="If cbtwo.Checked=True Then";
Debug.ShouldStop(4194304);
if (mostCurrent._cbtwo.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1432;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(8388608);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1433;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1434;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1435;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1436;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1437;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1438;BA.debugLine="sqlfood=sqlfood & \"'\"&txttwo.Text&\"',\" '";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+"'"+mostCurrent._txttwo.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1439;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1440;BA.debugLine="sqlfood=sqlfood & \"'2',\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+"'2',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1441;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1442;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1443;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(4);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1445;BA.debugLine="If cbthree.Checked=True Then";
Debug.ShouldStop(16);
if (mostCurrent._cbthree.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1446;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(32);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1447;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1448;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1449;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1450;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1451;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1452;BA.debugLine="sqlfood=sqlfood & \"'\"&txtthree.Text&\"',\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+"'"+mostCurrent._txtthree.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1453;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(4096);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1454;BA.debugLine="sqlfood=sqlfood & \"'3',\"";
Debug.ShouldStop(8192);
_sqlfood = _sqlfood+"'3',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1455;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(16384);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1456;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(32768);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1457;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(65536);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1459;BA.debugLine="If cbfour.Checked=True Then";
Debug.ShouldStop(262144);
if (mostCurrent._cbfour.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1460;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(524288);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1461;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1462;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1463;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1464;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1465;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1466;BA.debugLine="sqlfood=sqlfood & \"'\"&txtfour.Text&\"',\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+"'"+mostCurrent._txtfour.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1467;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(67108864);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1468;BA.debugLine="sqlfood=sqlfood & \"'4',\"";
Debug.ShouldStop(134217728);
_sqlfood = _sqlfood+"'4',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1469;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(268435456);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1470;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(536870912);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1471;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(1073741824);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1473;BA.debugLine="If cbfive.Checked=True Then";
Debug.ShouldStop(1);
if (mostCurrent._cbfive.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1474;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(2);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1475;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1476;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1477;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1478;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1479;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1480;BA.debugLine="sqlfood=sqlfood & \"'\"&txtfive.Text&\"',\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+"'"+mostCurrent._txtfive.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1481;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(256);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1482;BA.debugLine="sqlfood=sqlfood & \"'5',\"";
Debug.ShouldStop(512);
_sqlfood = _sqlfood+"'5',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1483;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(1024);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1484;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(2048);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1485;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(4096);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1487;BA.debugLine="If cbsix.Checked=True Then";
Debug.ShouldStop(16384);
if (mostCurrent._cbsix.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1488;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(32768);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1489;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(65536);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1490;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(131072);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1491;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(262144);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1492;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(524288);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1493;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(1048576);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1494;BA.debugLine="sqlfood=sqlfood & \"'\"&txtsix.Text&\"',\" '";
Debug.ShouldStop(2097152);
_sqlfood = _sqlfood+"'"+mostCurrent._txtsix.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1495;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(4194304);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1496;BA.debugLine="sqlfood=sqlfood & \"'6',\"";
Debug.ShouldStop(8388608);
_sqlfood = _sqlfood+"'6',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1497;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(16777216);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1498;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(33554432);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1499;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(67108864);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 BA.debugLineNum = 1501;BA.debugLine="If cbseven.Checked=True Then";
Debug.ShouldStop(268435456);
if (mostCurrent._cbseven.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1502;BA.debugLine="sqlfood=\"insert into repastname(\"";
Debug.ShouldStop(536870912);
_sqlfood = "insert into repastname(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1503;BA.debugLine="sqlfood=sqlfood & \" repastname_name,\"";
Debug.ShouldStop(1073741824);
_sqlfood = _sqlfood+" repastname_name,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1504;BA.debugLine="sqlfood=sqlfood & \" repast_date,\"";
Debug.ShouldStop(-2147483648);
_sqlfood = _sqlfood+" repast_date,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1505;BA.debugLine="sqlfood=sqlfood & \" repast_count,\"";
Debug.ShouldStop(1);
_sqlfood = _sqlfood+" repast_count,";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1506;BA.debugLine="sqlfood=sqlfood & \" user_id\"";
Debug.ShouldStop(2);
_sqlfood = _sqlfood+" user_id";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1507;BA.debugLine="sqlfood=sqlfood & \" )values(\"";
Debug.ShouldStop(4);
_sqlfood = _sqlfood+" )values(";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1508;BA.debugLine="sqlfood=sqlfood & \"'\"&txtseven.Text&\"',\"";
Debug.ShouldStop(8);
_sqlfood = _sqlfood+"'"+mostCurrent._txtseven.getText()+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1509;BA.debugLine="sqlfood=sqlfood & \"'\"& DaySave &\"',\"";
Debug.ShouldStop(16);
_sqlfood = _sqlfood+"'"+_daysave+"',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1510;BA.debugLine="sqlfood=sqlfood & \"'7',\"";
Debug.ShouldStop(32);
_sqlfood = _sqlfood+"'7',";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1511;BA.debugLine="sqlfood=sqlfood & \"'\"&idname1&\"'\"";
Debug.ShouldStop(64);
_sqlfood = _sqlfood+"'"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1512;BA.debugLine="sqlfood=sqlfood & \" )\"";
Debug.ShouldStop(128);
_sqlfood = _sqlfood+" )";Debug.locals.put("sqlfood", _sqlfood);
 BA.debugLineNum = 1513;BA.debugLine="dbSQL.ExecNonQuery(sqlfood)";
Debug.ShouldStop(256);
mostCurrent._dbsql.ExecNonQuery(_sqlfood);
 };
 };
 BA.debugLineNum = 1517;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showcopynamedate() throws Exception{
try {
		Debug.PushSubsStack("showcopynamedate (main) ","main",0,mostCurrent.activityBA,mostCurrent,1773);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1773;BA.debugLine="Sub showcopynamedate";
Debug.ShouldStop(4096);
 BA.debugLineNum = 1774;BA.debugLine="TotalRows=0";
Debug.ShouldStop(8192);
_totalrows = (int) (0);
 BA.debugLineNum = 1775;BA.debugLine="Table1.Initialize(Me, \"Table_copynamedate\", 3)";
Debug.ShouldStop(16384);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_copynamedate",(int) (3));
 BA.debugLineNum = 1776;BA.debugLine="Table1.AddToActivity(pnshowre, 0,lblshowreintro";
Debug.ShouldStop(32768);
mostCurrent._table1._addtoactivity((anywheresoftware.b4a.objects.ActivityWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ActivityWrapper(), (anywheresoftware.b4a.BALayout)(mostCurrent._pnshowre.getObject())),(int) (0),(int) (mostCurrent._lblshowreintro1.getTop()+mostCurrent._lblshowreintro1.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btncopyrepast.getHeight()));
 BA.debugLineNum = 1777;BA.debugLine="Table1.SetHeader(Array As String(\"รหัส\",\"ชื่อผู";
Debug.ShouldStop(65536);
mostCurrent._table1._setheader(new String[]{"รหัส","ชื่อผู้ใช้งาน","เลือกหลายชื่อ"});
 BA.debugLineNum = 1778;BA.debugLine="Table1.SetColumnsWidths(Array As Int(40%x,40%x,";
Debug.ShouldStop(131072);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA)});
 BA.debugLineNum = 1779;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(262144);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1780;BA.debugLine="Dim sql As String";
Debug.ShouldStop(524288);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1781;BA.debugLine="sql=\"select user_id,namesuser,usernick from r";
Debug.ShouldStop(1048576);
_sql = "select user_id,namesuser,usernick from recorduser";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1782;BA.debugLine="sql=sql & \" where user_id not in( select user";
Debug.ShouldStop(2097152);
_sql = _sql+" where user_id not in( select user_id from  repastname where  user_id='"+BA.ObjectToString(mostCurrent._idname)+"' )";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1783;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(4194304);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1784;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(8388608);
{
final int step1455 = 1;
final int limit1455 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1455 > 0 && _i <= limit1455) || (step1455 < 0 && _i >= limit1455); _i = ((int)(0 + _i + step1455))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1785;BA.debugLine="rs.Position =i";
Debug.ShouldStop(16777216);
_rs.setPosition(_i);
 BA.debugLineNum = 1786;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(\"u";
Debug.ShouldStop(33554432);
mostCurrent._table1._addrow(new String[]{_rs.GetString("user_id"),_rs.GetString("namesuser"),("...")});
 BA.debugLineNum = 1787;BA.debugLine="TotalRows=TotalRows+1";
Debug.ShouldStop(67108864);
_totalrows = (int) (_totalrows+1);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1789;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showfoodtype() throws Exception{
try {
		Debug.PushSubsStack("showfoodtype (main) ","main",0,mostCurrent.activityBA,mostCurrent,721);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 721;BA.debugLine="Sub showfoodtype";
Debug.ShouldStop(65536);
 BA.debugLineNum = 722;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(131072);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 723;BA.debugLine="Activity.LoadLayout(\"foodtype\")";
Debug.ShouldStop(262144);
mostCurrent._activity.LoadLayout("foodtype",mostCurrent.activityBA);
 BA.debugLineNum = 725;BA.debugLine="Table1.Initialize(Me, \"Table_type\", 4)";
Debug.ShouldStop(1048576);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_type",(int) (4));
 BA.debugLineNum = 726;BA.debugLine="Table1.AddToActivity(Activity, 0,btnshowtype";
Debug.ShouldStop(2097152);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._btnshowtype.getTop()+mostCurrent._btnshowtype.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnshowtype.getHeight()));
 BA.debugLineNum = 727;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อป";
Debug.ShouldStop(4194304);
mostCurrent._table1._setheader(new String[]{"id","ชื่อประเภท","รายการ","แก้ไข"});
 BA.debugLineNum = 728;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0%x,65%";
Debug.ShouldStop(8388608);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA)});
 BA.debugLineNum = 729;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(16777216);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 730;BA.debugLine="Dim sql As String";
Debug.ShouldStop(33554432);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 732;BA.debugLine="sql=\"select foodtype.type_name,foodtype.type_id,";
Debug.ShouldStop(134217728);
_sql = "select foodtype.type_name,foodtype.type_id,count(material.type_id) as total from foodtype left join material on foodtype.type_id=material.type_id group by foodtype.type_name,foodtype.type_id ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 733;BA.debugLine="rs=dbSQL.ExecQuery(sql)						'เรียกข้อมูลในฐานมา";
Debug.ShouldStop(268435456);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 734;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(536870912);
mostCurrent._table1._clearall();
 BA.debugLineNum = 735;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(1073741824);
{
final int step597 = 1;
final int limit597 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step597 > 0 && _i <= limit597) || (step597 < 0 && _i >= limit597); _i = ((int)(0 + _i + step597))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 736;BA.debugLine="rs.Position =i";
Debug.ShouldStop(-2147483648);
_rs.setPosition(_i);
 BA.debugLineNum = 737;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(\"ty";
Debug.ShouldStop(1);
mostCurrent._table1._addrow(new String[]{_rs.GetString("type_id"),_rs.GetString("type_name"),_rs.GetString("total"),"..."});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 739;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showmaterial() throws Exception{
try {
		Debug.PushSubsStack("showMaterial (main) ","main",0,mostCurrent.activityBA,mostCurrent,967);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 967;BA.debugLine="Sub showMaterial 												'{เพิ่มนี้2";
Debug.ShouldStop(64);
 BA.debugLineNum = 968;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(128);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 969;BA.debugLine="Activity.LoadLayout(\"material\")";
Debug.ShouldStop(256);
mostCurrent._activity.LoadLayout("material",mostCurrent.activityBA);
 BA.debugLineNum = 970;BA.debugLine="Table1.Initialize(Me, \"Table_material2";
Debug.ShouldStop(512);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_material2",(int) (6));
 BA.debugLineNum = 971;BA.debugLine="Table1.AddToActivity(Activity, 0,btnaddfood.";
Debug.ShouldStop(1024);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._btnaddfood.getTop()+mostCurrent._btnaddfood.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 972;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อว";
Debug.ShouldStop(2048);
mostCurrent._table1._setheader(new String[]{"id","ชื่อวัตถุดิบ","ปริมาณโปรตีน","ปริมาณคาร์โบไฺฮเดรต","ปริมาณไขมัน","แก้ไข"});
 BA.debugLineNum = 973;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0,50%x,";
Debug.ShouldStop(4096);
mostCurrent._table1._setcolumnswidths(new int[]{(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA)});
 BA.debugLineNum = 974;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(8192);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 975;BA.debugLine="Dim sql As String";
Debug.ShouldStop(16384);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 976;BA.debugLine="sql=\"select crab,fat,prot,material_name,material";
Debug.ShouldStop(32768);
_sql = "select crab,fat,prot,material_name,material_id from material where type_id='"+mostCurrent._idtype+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 977;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(65536);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 978;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(131072);
mostCurrent._table1._clearall();
 BA.debugLineNum = 979;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(262144);
{
final int step829 = 1;
final int limit829 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step829 > 0 && _i <= limit829) || (step829 < 0 && _i >= limit829); _i = ((int)(0 + _i + step829))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 980;BA.debugLine="rs.Position =i";
Debug.ShouldStop(524288);
_rs.setPosition(_i);
 BA.debugLineNum = 981;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(\"m";
Debug.ShouldStop(1048576);
mostCurrent._table1._addrow(new String[]{_rs.GetString("material_id"),_rs.GetString("material_name"),_rs.GetString("prot"),_rs.GetString("crab"),_rs.GetString("fat"),"..."});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 983;BA.debugLine="End Sub															'}เพิ่ม2";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showmatmenu() throws Exception{
try {
		Debug.PushSubsStack("showmatmenu (main) ","main",0,mostCurrent.activityBA,mostCurrent,1136);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1136;BA.debugLine="Sub showmatmenu				'วัตถุดิบในเมนู";
Debug.ShouldStop(32768);
 BA.debugLineNum = 1137;BA.debugLine="Table1.Initialize(Me, \"Table_matmenu\", 3)";
Debug.ShouldStop(65536);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_matmenu",(int) (3));
 BA.debugLineNum = 1138;BA.debugLine="Table1.AddToActivity(Activity,0,btnaddmatmenu.To";
Debug.ShouldStop(131072);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._btnaddmatmenu.getTop()+mostCurrent._btnaddmatmenu.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnaddmatmenu.getHeight()));
 BA.debugLineNum = 1139;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อวัตถุ";
Debug.ShouldStop(262144);
mostCurrent._table1._setheader(new String[]{"id","ชื่อวัตถุดิบ","ปริมาณ(กรัม)"});
 BA.debugLineNum = 1140;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0,60%x,40%x";
Debug.ShouldStop(524288);
mostCurrent._table1._setcolumnswidths(new int[]{(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA)});
 BA.debugLineNum = 1141;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(1048576);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1142;BA.debugLine="Dim sql As String";
Debug.ShouldStop(2097152);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1143;BA.debugLine="sql=\"select detailmenu.material_amount,\"";
Debug.ShouldStop(4194304);
_sql = "select detailmenu.material_amount,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1144;BA.debugLine="sql=sql & \"  detailmenu.material_id,\"";
Debug.ShouldStop(8388608);
_sql = _sql+"  detailmenu.material_id,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1145;BA.debugLine="sql=sql & \"  material.material_name, \"";
Debug.ShouldStop(16777216);
_sql = _sql+"  material.material_name, ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1146;BA.debugLine="sql=sql & \"  detailmenu.menufood_id from detai";
Debug.ShouldStop(33554432);
_sql = _sql+"  detailmenu.menufood_id from detailmenu ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1147;BA.debugLine="sql=sql & \"  inner join material on material.m";
Debug.ShouldStop(67108864);
_sql = _sql+"  inner join material on material.material_id=detailmenu.material_id ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1148;BA.debugLine="sql=sql & \" where menufood_id='\"& idmenu & \"'\"";
Debug.ShouldStop(134217728);
_sql = _sql+" where menufood_id='"+BA.NumberToString(_idmenu)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1150;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(536870912);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1151;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(1073741824);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1152;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(-2147483648);
{
final int step971 = 1;
final int limit971 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step971 > 0 && _i <= limit971) || (step971 < 0 && _i >= limit971); _i = ((int)(0 + _i + step971))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1153;BA.debugLine="rs.Position =i";
Debug.ShouldStop(1);
_rs.setPosition(_i);
 BA.debugLineNum = 1154;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(\"";
Debug.ShouldStop(2);
mostCurrent._table1._addrow(new String[]{_rs.GetString("material_id"),_rs.GetString("material_name"),_rs.GetString("material_amount")});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1157;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showmenu() throws Exception{
try {
		Debug.PushSubsStack("showmenu (main) ","main",0,mostCurrent.activityBA,mostCurrent,487);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 487;BA.debugLine="Sub showmenu";
Debug.ShouldStop(64);
 BA.debugLineNum = 488;BA.debugLine="Table1.Initialize(Me, \"Table_menu\",6)  		'7";
Debug.ShouldStop(128);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_menu",(int) (6));
 BA.debugLineNum = 489;BA.debugLine="Table1.AddToActivity(Activity, 0,btnaddmenu.T";
Debug.ShouldStop(256);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._btnaddmenu.getTop()+mostCurrent._btnaddmenu.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnaddmenu.getHeight()));
 BA.debugLineNum = 490;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อเม";
Debug.ShouldStop(512);
mostCurrent._table1._setheader(new String[]{"id","ชื่อเมนู","Prot","crab","fat","แก้ไข"});
 BA.debugLineNum = 491;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0,50%x,1";
Debug.ShouldStop(1024);
mostCurrent._table1._setcolumnswidths(new int[]{(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (12.5),mostCurrent.activityBA)});
 BA.debugLineNum = 492;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(2048);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 493;BA.debugLine="Dim sql As String";
Debug.ShouldStop(4096);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 494;BA.debugLine="sql=\"select menufood_id,menufood_name from m";
Debug.ShouldStop(8192);
_sql = "select menufood_id,menufood_name from menufood";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 497;BA.debugLine="rs=dbSQL.ExecQuery(sql)						'เรียกข้อมูลในฐ";
Debug.ShouldStop(65536);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 499;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(262144);
{
final int step404 = 1;
final int limit404 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step404 > 0 && _i <= limit404) || (step404 < 0 && _i >= limit404); _i = ((int)(0 + _i + step404))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 500;BA.debugLine="rs.Position =i";
Debug.ShouldStop(524288);
_rs.setPosition(_i);
 BA.debugLineNum = 501;BA.debugLine="Table1.AddRow(Array As String(rs.GetString";
Debug.ShouldStop(1048576);
mostCurrent._table1._addrow(new String[]{_rs.GetString("menufood_id"),_rs.GetString("menufood_name"),_getprot((double)(Double.parseDouble(_rs.GetString("menufood_id")))),_getcrab((double)(Double.parseDouble(_rs.GetString("menufood_id")))),_getfat((double)(Double.parseDouble(_rs.GetString("menufood_id")))),"..."});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 504;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showmenuselect() throws Exception{
try {
		Debug.PushSubsStack("showmenuselect (main) ","main",0,mostCurrent.activityBA,mostCurrent,2073);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 2073;BA.debugLine="Sub showmenuselect											''''''''''''ทำตรงนี้*";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 2074;BA.debugLine="Table1.Initialize(Me, \"Table_eat\", 4)";
Debug.ShouldStop(33554432);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_eat",(int) (4));
 BA.debugLineNum = 2075;BA.debugLine="Table1.AddToActivity(pneat,0,0,100%y,100%x-btn";
Debug.ShouldStop(67108864);
mostCurrent._table1._addtoactivity((anywheresoftware.b4a.objects.ActivityWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ActivityWrapper(), (anywheresoftware.b4a.BALayout)(mostCurrent._pneat.getObject())),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._btnconeat.getHeight()));
 BA.debugLineNum = 2076;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อเมน";
Debug.ShouldStop(134217728);
mostCurrent._table1._setheader(new String[]{"id","ชื่อเมนู","เลือก","จำนวน"});
 BA.debugLineNum = 2077;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0,65%x,20";
Debug.ShouldStop(268435456);
mostCurrent._table1._setcolumnswidths(new int[]{(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (65),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA)});
 BA.debugLineNum = 2078;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(536870912);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2079;BA.debugLine="Dim sql As String";
Debug.ShouldStop(1073741824);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2080;BA.debugLine="sql=\"select menufood_id,menufood_name from m";
Debug.ShouldStop(-2147483648);
_sql = "select menufood_id,menufood_name from menufood";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2081;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2082;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(2);
{
final int step1628 = 1;
final int limit1628 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1628 > 0 && _i <= limit1628) || (step1628 < 0 && _i >= limit1628); _i = ((int)(0 + _i + step1628))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2083;BA.debugLine="rs.Position =i";
Debug.ShouldStop(4);
_rs.setPosition(_i);
 BA.debugLineNum = 2084;BA.debugLine="Table1.AddRow(Array As String(rs.GetInt(\"m";
Debug.ShouldStop(8);
mostCurrent._table1._addrow(new String[]{BA.NumberToString(_rs.GetInt("menufood_id")),_rs.GetString("menufood_name"),("..."),("1")});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2086;BA.debugLine="TotalRows=i";
Debug.ShouldStop(32);
_totalrows = _i;
 BA.debugLineNum = 2087;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showname() throws Exception{
try {
		Debug.PushSubsStack("showname (main) ","main",0,mostCurrent.activityBA,mostCurrent,201);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 201;BA.debugLine="Sub showname";
Debug.ShouldStop(256);
 BA.debugLineNum = 202;BA.debugLine="lvname.Height=100%y-(btnnewname.height)";
Debug.ShouldStop(512);
mostCurrent._lvname.setHeight((int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-(mostCurrent._btnnewname.getHeight())));
 BA.debugLineNum = 203;BA.debugLine="lvname.Width=100%x";
Debug.ShouldStop(1024);
mostCurrent._lvname.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 205;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(4096);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 206;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8192);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 207;BA.debugLine="sql=\"select user_id,namesuser from recorduser\"";
Debug.ShouldStop(16384);
_sql = "select user_id,namesuser from recorduser";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 208;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(32768);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 209;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(65536);
{
final int step180 = 1;
final int limit180 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step180 > 0 && _i <= limit180) || (step180 < 0 && _i >= limit180); _i = ((int)(0 + _i + step180))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 210;BA.debugLine="rs.Position =i";
Debug.ShouldStop(131072);
_rs.setPosition(_i);
 BA.debugLineNum = 211;BA.debugLine="lvname.AddSingleLine2(rs.GetString(\"namesuser\")";
Debug.ShouldStop(262144);
mostCurrent._lvname.AddSingleLine2(_rs.GetString("namesuser"),(Object)(_rs.GetString("user_id")));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 215;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _shownutri() throws Exception{
try {
		Debug.PushSubsStack("shownutri (main) ","main",0,mostCurrent.activityBA,mostCurrent,2380);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
double _x1 = 0;
double _x2 = 0;
int _days = 0;
int _i = 0;
 BA.debugLineNum = 2380;BA.debugLine="Sub	shownutri";
Debug.ShouldStop(2048);
 BA.debugLineNum = 2404;BA.debugLine="Table1.Initialize(Me, \"Table_nutri\", 4)";
Debug.ShouldStop(8);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_nutri",(int) (4));
 BA.debugLineNum = 2405;BA.debugLine="Table1.AddToActivity(Activity,0 ,lblnu2.Top +";
Debug.ShouldStop(16);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._lblnu2.getTop()+mostCurrent._btncondate.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA)-mostCurrent._btncondate.getHeight()));
 BA.debugLineNum = 2406;BA.debugLine="Table1.SetHeader(Array As String(\"โภชนาการ\",\"ไ";
Debug.ShouldStop(32);
mostCurrent._table1._setheader(new String[]{"โภชนาการ","ได้รับ","มาตรฐาน","ผลลัพธ์"});
 BA.debugLineNum = 2407;BA.debugLine="Table1.SetColumnsWidths(Array As Int(23%x,23%x";
Debug.ShouldStop(64);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (23),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (23),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (24),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)});
 BA.debugLineNum = 2408;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(128);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 2409;BA.debugLine="Dim sql As String";
Debug.ShouldStop(256);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2410;BA.debugLine="sql= \"SELECT \"";
Debug.ShouldStop(512);
_sql = "SELECT ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2411;BA.debugLine="sql=sql & \" sum(material.kcal * detailmenu.ma";
Debug.ShouldStop(1024);
_sql = _sql+" sum(material.kcal * detailmenu.material_amount * takefood.takefood_count) As kcal,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2412;BA.debugLine="sql=sql & \" sum(material.prot * detailmenu.ma";
Debug.ShouldStop(2048);
_sql = _sql+" sum(material.prot * detailmenu.material_amount * takefood.takefood_count) As prot,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2413;BA.debugLine="sql=sql & \" sum(material.crab * detailmenu.m";
Debug.ShouldStop(4096);
_sql = _sql+" sum(material.crab * detailmenu.material_amount * takefood.takefood_count) As crab,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2414;BA.debugLine="sql=sql & \" sum(material.fiber * detailmenu.m";
Debug.ShouldStop(8192);
_sql = _sql+" sum(material.fiber * detailmenu.material_amount * takefood.takefood_count) As fiber,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2415;BA.debugLine="sql=sql & \" sum(material.fat * detailmenu.mat";
Debug.ShouldStop(16384);
_sql = _sql+" sum(material.fat * detailmenu.material_amount * takefood.takefood_count) As fat,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2416;BA.debugLine="sql=sql & \" sum(material.iron * detailmenu.ma";
Debug.ShouldStop(32768);
_sql = _sql+" sum(material.iron * detailmenu.material_amount * takefood.takefood_count) As iron,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2417;BA.debugLine="sql=sql & \" sum(material.chol * detailmenu.ma";
Debug.ShouldStop(65536);
_sql = _sql+" sum(material.chol * detailmenu.material_amount * takefood.takefood_count) As chol,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2418;BA.debugLine="sql=sql & \" sum(material.calc * detailmenu.ma";
Debug.ShouldStop(131072);
_sql = _sql+" sum(material.calc * detailmenu.material_amount * takefood.takefood_count) As calc,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2419;BA.debugLine="sql=sql & \" sum(material.magn * detailmenu.ma";
Debug.ShouldStop(262144);
_sql = _sql+" sum(material.magn * detailmenu.material_amount * takefood.takefood_count) As magn,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2420;BA.debugLine="sql=sql & \" sum(material.pota * detailmenu.ma";
Debug.ShouldStop(524288);
_sql = _sql+" sum(material.pota * detailmenu.material_amount * takefood.takefood_count) As pota,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2421;BA.debugLine="sql=sql & \" sum(material.sodi * detailmenu.ma";
Debug.ShouldStop(1048576);
_sql = _sql+" sum(material.sodi * detailmenu.material_amount * takefood.takefood_count) As sodi,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2422;BA.debugLine="sql=sql & \" sum(material.zinc * detailmenu.ma";
Debug.ShouldStop(2097152);
_sql = _sql+" sum(material.zinc * detailmenu.material_amount * takefood.takefood_count) As zinc,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2423;BA.debugLine="sql=sql & \" sum(material.vitA * detailmenu.ma";
Debug.ShouldStop(4194304);
_sql = _sql+" sum(material.vitA * detailmenu.material_amount * takefood.takefood_count) As vitA,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2424;BA.debugLine="sql=sql & \" sum(material.vitB1 * detailmenu.m";
Debug.ShouldStop(8388608);
_sql = _sql+" sum(material.vitB1 * detailmenu.material_amount * takefood.takefood_count) As vitB1,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2425;BA.debugLine="sql=sql & \" sum(material.vitE * detailmenu.ma";
Debug.ShouldStop(16777216);
_sql = _sql+" sum(material.vitE * detailmenu.material_amount * takefood.takefood_count) As vitE,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2426;BA.debugLine="sql=sql & \" sum(material.vitB2 * detailmenu.m";
Debug.ShouldStop(33554432);
_sql = _sql+" sum(material.vitB2 * detailmenu.material_amount * takefood.takefood_count) As vitB2,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2427;BA.debugLine="sql=sql & \" sum(material.vitB3 * detailmenu.m";
Debug.ShouldStop(67108864);
_sql = _sql+" sum(material.vitB3 * detailmenu.material_amount * takefood.takefood_count) As vitB3,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2428;BA.debugLine="sql=sql & \" sum(material.vitB6 * detailmenu.m";
Debug.ShouldStop(134217728);
_sql = _sql+" sum(material.vitB6 * detailmenu.material_amount * takefood.takefood_count) As vitB6,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2430;BA.debugLine="sql=sql & \" sum(material.vitC * detailmenu.ma";
Debug.ShouldStop(536870912);
_sql = _sql+" sum(material.vitC * detailmenu.material_amount * takefood.takefood_count) As vitC,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2431;BA.debugLine="sql=sql & \" sum(material.vitB12 * detailmenu.";
Debug.ShouldStop(1073741824);
_sql = _sql+" sum(material.vitB12 * detailmenu.material_amount * takefood.takefood_count) As vitB12,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2432;BA.debugLine="sql=sql & \" sum(material.sele * detailmenu.ma";
Debug.ShouldStop(-2147483648);
_sql = _sql+" sum(material.sele * detailmenu.material_amount * takefood.takefood_count) As sele,";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2434;BA.debugLine="sql=sql & \" julianday('\" & lblselectday1.tag";
Debug.ShouldStop(2);
_sql = _sql+" julianday('"+BA.ObjectToString(mostCurrent._lblselectday1.getTag())+"') - julianday('"+BA.ObjectToString(mostCurrent._lblselectday.getTag())+"') +1 as days ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2436;BA.debugLine="sql=sql & \" FROM menufood INNER JOIN detailme";
Debug.ShouldStop(8);
_sql = _sql+" FROM menufood INNER JOIN detailmenu on detailmenu.menufood_id=menufood.menufood_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2437;BA.debugLine="sql=sql & \" INNER JOIN material on material.";
Debug.ShouldStop(16);
_sql = _sql+" INNER JOIN material on material.material_id = detailmenu.material_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2438;BA.debugLine="sql=sql & \" INNER JOIN takefood on takefood.m";
Debug.ShouldStop(32);
_sql = _sql+" INNER JOIN takefood on takefood.menufood_id = detailmenu.menufood_id INNER JOIN repastname on repastname.repastname_id = takefood.repastname_id";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2439;BA.debugLine="sql=sql & \" WHERE repast_date between '\" & lb";
Debug.ShouldStop(64);
_sql = _sql+" WHERE repast_date between '"+BA.ObjectToString(mostCurrent._lblselectday.getTag())+"' and '"+BA.ObjectToString(mostCurrent._lblselectday1.getTag())+"' AND user_id = '"+BA.ObjectToString(mostCurrent._idname)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 2441;BA.debugLine="If lblselectday.tag = \"\" Or lblselectday1.";
Debug.ShouldStop(256);
if ((mostCurrent._lblselectday.getTag()).equals((Object)("")) || (mostCurrent._lblselectday1.getTag()).equals((Object)(""))) { 
 BA.debugLineNum = 2442;BA.debugLine="Msgbox(\"แจ้งเตือน\",\"กรุณาเลือกวันที่\")";
Debug.ShouldStop(512);
anywheresoftware.b4a.keywords.Common.Msgbox("แจ้งเตือน","กรุณาเลือกวันที่",mostCurrent.activityBA);
 BA.debugLineNum = 2443;BA.debugLine="Return";
Debug.ShouldStop(1024);
if (true) return "";
 }else {
 };
 BA.debugLineNum = 2446;BA.debugLine="Dim x1 As Double";
Debug.ShouldStop(8192);
_x1 = 0;Debug.locals.put("x1", _x1);
 BA.debugLineNum = 2447;BA.debugLine="Dim x2 As Double";
Debug.ShouldStop(16384);
_x2 = 0;Debug.locals.put("x2", _x2);
 BA.debugLineNum = 2448;BA.debugLine="Dim days As Int";
Debug.ShouldStop(32768);
_days = 0;Debug.locals.put("days", _days);
 BA.debugLineNum = 2449;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(65536);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 2450;BA.debugLine="For i = 0 To rs.ColumnCount -2";
Debug.ShouldStop(131072);
{
final int step1833 = 1;
final int limit1833 = (int) (_rs.getColumnCount()-2);
for (_i = (int) (0); (step1833 > 0 && _i <= limit1833) || (step1833 < 0 && _i >= limit1833); _i = ((int)(0 + _i + step1833))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 2451;BA.debugLine="rs.Position =0";
Debug.ShouldStop(262144);
_rs.setPosition((int) (0));
 BA.debugLineNum = 2452;BA.debugLine="days= rs.GetString(\"days\")";
Debug.ShouldStop(524288);
_days = (int)(Double.parseDouble(_rs.GetString("days")));Debug.locals.put("days", _days);
 BA.debugLineNum = 2454;BA.debugLine="x1 = getdri(\"thdri_\" & rs.GetColumnName(i))";
Debug.ShouldStop(2097152);
_x1 = (double)(Double.parseDouble(_getdri("thdri_"+_rs.GetColumnName(_i))))*_days;Debug.locals.put("x1", _x1);
 BA.debugLineNum = 2455;BA.debugLine="x2 = rs.GetString2(i) -  x1";
Debug.ShouldStop(4194304);
_x2 = (double)(Double.parseDouble(_rs.GetString2(_i)))-_x1;Debug.locals.put("x2", _x2);
 BA.debugLineNum = 2457;BA.debugLine="Table1.AddRow(Array As String(rs.GetColumn";
Debug.ShouldStop(16777216);
mostCurrent._table1._addrow(new String[]{_rs.GetColumnName(_i),_rs.GetString2(_i),anywheresoftware.b4a.keywords.Common.NumberFormat2(_x1,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True),anywheresoftware.b4a.keywords.Common.NumberFormat2(_x2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 2459;BA.debugLine="TotalRows=i";
Debug.ShouldStop(67108864);
_totalrows = _i;
 BA.debugLineNum = 2460;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showrepast() throws Exception{
try {
		Debug.PushSubsStack("showrepast (main) ","main",0,mostCurrent.activityBA,mostCurrent,1652);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1652;BA.debugLine="Sub showrepast";
Debug.ShouldStop(524288);
 BA.debugLineNum = 1654;BA.debugLine="Table1.Initialize(Me, \"Table_namedatere\", 4)";
Debug.ShouldStop(2097152);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_namedatere",(int) (4));
 BA.debugLineNum = 1655;BA.debugLine="Table1.AddToActivity(Activity, 0,lblshowreintr";
Debug.ShouldStop(4194304);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._lblshowreintro1.getTop()+mostCurrent._lblshowreintro1.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (98),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (98),mostCurrent.activityBA)-(mostCurrent._btncopyrepast.getHeight()+mostCurrent._btncopyrepast.getHeight())));
 BA.debugLineNum = 1656;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"วันที่\"";
Debug.ShouldStop(8388608);
mostCurrent._table1._setheader(new String[]{"id","วันที่","ชื่อมื้อ","ชื่อ"});
 BA.debugLineNum = 1657;BA.debugLine="Table1.SetColumnsWidths(Array As Int(0,30%x,40";
Debug.ShouldStop(16777216);
mostCurrent._table1._setcolumnswidths(new int[]{(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA)});
 BA.debugLineNum = 1658;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(33554432);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1659;BA.debugLine="Dim sql As String";
Debug.ShouldStop(67108864);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1660;BA.debugLine="sql=\"select repast_date,user_id,repastname_name";
Debug.ShouldStop(134217728);
_sql = "select repast_date,user_id,repastname_name from repastname where user_id='"+BA.NumberToString(_idname1)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1661;BA.debugLine="sql=sql & \"order by repast_date\"";
Debug.ShouldStop(268435456);
_sql = _sql+"order by repast_date";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1662;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(536870912);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1663;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(1073741824);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1664;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(-2147483648);
{
final int step1409 = 1;
final int limit1409 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1409 > 0 && _i <= limit1409) || (step1409 < 0 && _i >= limit1409); _i = ((int)(0 + _i + step1409))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1665;BA.debugLine="rs.Position =i";
Debug.ShouldStop(1);
_rs.setPosition(_i);
 BA.debugLineNum = 1666;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(";
Debug.ShouldStop(2);
mostCurrent._table1._addrow(new String[]{_rs.GetString("user_id"),_rs.GetString("repast_date"),_rs.GetString("repastname_name"),_rs.GetString("user_id")});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1669;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showselectmat() throws Exception{
try {
		Debug.PushSubsStack("showselectmat (main) ","main",0,mostCurrent.activityBA,mostCurrent,1254);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1254;BA.debugLine="Sub showselectmat";
Debug.ShouldStop(32);
 BA.debugLineNum = 1255;BA.debugLine="lvmatmenu.Clear";
Debug.ShouldStop(64);
mostCurrent._lvmatmenu.Clear();
 BA.debugLineNum = 1256;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(128);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1257;BA.debugLine="Dim sql As String";
Debug.ShouldStop(256);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1258;BA.debugLine="sql=\"select material_id, material_name from m";
Debug.ShouldStop(512);
_sql = "select material_id, material_name from material";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1259;BA.debugLine="sql=sql & \" where material_id not in( select";
Debug.ShouldStop(1024);
_sql = _sql+" where material_id not in( select material_id from  detailmenu where  menufood_id='"+BA.NumberToString(_idmenu)+"' )";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1260;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(2048);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1261;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(4096);
{
final int step1047 = 1;
final int limit1047 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1047 > 0 && _i <= limit1047) || (step1047 < 0 && _i >= limit1047); _i = ((int)(0 + _i + step1047))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1262;BA.debugLine="rs.Position =i";
Debug.ShouldStop(8192);
_rs.setPosition(_i);
 BA.debugLineNum = 1263;BA.debugLine="lvmatmenu.AddSingleLine2(rs.GetString(\"materi";
Debug.ShouldStop(16384);
mostCurrent._lvmatmenu.AddSingleLine2(_rs.GetString("material_name"),(Object)(_rs.GetString("material_id")));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1265;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _spselectday_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("spselectday_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2493);
String[] _sp = null;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 2493;BA.debugLine="Sub spselectday_ItemClick (Position As Int, Value";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 2494;BA.debugLine="Try";
Debug.ShouldStop(536870912);
try { BA.debugLineNum = 2495;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(1073741824);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2496;BA.debugLine="sp=Regex.Split(\"\\/\",Value)";
Debug.ShouldStop(-2147483648);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\/",BA.ObjectToString(_value));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2498;BA.debugLine="idnutri = sp(2) &\"-\"&sp(1)&\"-\"&sp(0)";
Debug.ShouldStop(2);
mostCurrent._idnutri = _sp[(int) (2)]+"-"+_sp[(int) (1)]+"-"+_sp[(int) (0)];
 BA.debugLineNum = 2499;BA.debugLine="lblselectday.tag = idnutri";
Debug.ShouldStop(4);
mostCurrent._lblselectday.setTag((Object)(mostCurrent._idnutri));
 } 
       catch (Exception e1874) {
			processBA.setLastException(e1874); BA.debugLineNum = 2504;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 2506;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _spselectday2_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("spselectday2_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2508);
String[] _sp = null;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 2508;BA.debugLine="Sub spselectday2_ItemClick (Position As Int, Value";
Debug.ShouldStop(2048);
 BA.debugLineNum = 2509;BA.debugLine="Try";
Debug.ShouldStop(4096);
try { BA.debugLineNum = 2510;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(8192);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2511;BA.debugLine="sp=Regex.Split(\"\\/\",Value)";
Debug.ShouldStop(16384);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\/",BA.ObjectToString(_value));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 2513;BA.debugLine="idnutri1 = sp(2) &\"-\"&sp(1)&\"-\"&sp(0)";
Debug.ShouldStop(65536);
mostCurrent._idnutri1 = _sp[(int) (2)]+"-"+_sp[(int) (1)]+"-"+_sp[(int) (0)];
 BA.debugLineNum = 2514;BA.debugLine="lblselectday1.tag = idnutri1";
Debug.ShouldStop(131072);
mostCurrent._lblselectday1.setTag((Object)(mostCurrent._idnutri1));
 } 
       catch (Exception e1884) {
			processBA.setLastException(e1884); BA.debugLineNum = 2517;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 2519;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _spselectday3_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("spselectday3_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2217);
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 2217;BA.debugLine="Sub spselectday3_ItemClick (Position As Int, Value";
Debug.ShouldStop(256);
 BA.debugLineNum = 2232;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _spshowdate_itemclick(int _position,Object _value) throws Exception{
try {
		Debug.PushSubsStack("Spshowdate_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,1933);
String[] _sp = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 1933;BA.debugLine="Sub Spshowdate_ItemClick (Position As Int, Value A";
Debug.ShouldStop(4096);
 BA.debugLineNum = 1934;BA.debugLine="Try";
Debug.ShouldStop(8192);
try { BA.debugLineNum = 1935;BA.debugLine="lvfoodinday.Clear";
Debug.ShouldStop(16384);
mostCurrent._lvfoodinday.Clear();
 BA.debugLineNum = 1936;BA.debugLine="lvshowre.Clear";
Debug.ShouldStop(32768);
mostCurrent._lvshowre.Clear();
 BA.debugLineNum = 1937;BA.debugLine="Dim sp() As String";
Debug.ShouldStop(65536);
_sp = new String[(int) (0)];
java.util.Arrays.fill(_sp,"");Debug.locals.put("sp", _sp);
 BA.debugLineNum = 1938;BA.debugLine="sp=Regex.Split(\"\\/\",Value)";
Debug.ShouldStop(131072);
_sp = anywheresoftware.b4a.keywords.Common.Regex.Split("\\/",BA.ObjectToString(_value));Debug.locals.put("sp", _sp);
 BA.debugLineNum = 1940;BA.debugLine="idrepast = sp(2) &\"-\"&sp(1)&\"-\"&sp(0)";
Debug.ShouldStop(524288);
mostCurrent._idrepast = _sp[(int) (2)]+"-"+_sp[(int) (1)]+"-"+_sp[(int) (0)];
 BA.debugLineNum = 1942;BA.debugLine="Msgbox(\"เลือกวันที่ \"& Value &\"\",\"แจ้งเตือน\")";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.Msgbox("เลือกวันที่ "+BA.ObjectToString(_value)+"","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1943;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(4194304);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1944;BA.debugLine="Dim sql As String";
Debug.ShouldStop(8388608);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1945;BA.debugLine="sql=\"select repastname_name,repastname_id,rep";
Debug.ShouldStop(16777216);
_sql = "select repastname_name,repastname_id,repast_count,user_id,repast_date from repastname where  user_id='"+BA.ObjectToString(mostCurrent._idname)+"' and repast_date = '"+mostCurrent._idrepast+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1946;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(33554432);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1947;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(67108864);
{
final int step1573 = 1;
final int limit1573 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1573 > 0 && _i <= limit1573) || (step1573 < 0 && _i >= limit1573); _i = ((int)(0 + _i + step1573))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1948;BA.debugLine="rs.Position =i";
Debug.ShouldStop(134217728);
_rs.setPosition(_i);
 BA.debugLineNum = 1949;BA.debugLine="lvshowre.AddSingleLine2(rs.GetString(\"repastn";
Debug.ShouldStop(268435456);
mostCurrent._lvshowre.AddSingleLine2(_rs.GetString("repastname_name"),(Object)(_rs.GetString("repastname_id")));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1973;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1048576);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1974;BA.debugLine="rs.Position =0";
Debug.ShouldStop(2097152);
_rs.setPosition((int) (0));
 } 
       catch (Exception e1580) {
			processBA.setLastException(e1580); BA.debugLineNum = 1980;BA.debugLine="Msgbox(LastException,\"x\")";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),"x",mostCurrent.activityBA);
 };
 BA.debugLineNum = 1982;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_copynamedate_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_copynamedate_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,1854);
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 1854;BA.debugLine="Sub Table_copynamedate_CellClick (Col As Int, Row";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 1855;BA.debugLine="If Col =2 Then";
Debug.ShouldStop(1073741824);
if (_col==2) { 
 BA.debugLineNum = 1856;BA.debugLine="If Table1.GetValue(2,Row)=\"...\" Then";
Debug.ShouldStop(-2147483648);
if ((mostCurrent._table1._getvalue((int) (2),_row)).equals("...")) { 
 BA.debugLineNum = 1857;BA.debugLine="Table1.SetValue(2,Row,\"X\")";
Debug.ShouldStop(1);
mostCurrent._table1._setvalue((int) (2),_row,"X");
 }else {
 BA.debugLineNum = 1859;BA.debugLine="Table1.SetValue(2,Row,\"...\")";
Debug.ShouldStop(4);
mostCurrent._table1._setvalue((int) (2),_row,"...");
 };
 }else {
 BA.debugLineNum = 1862;BA.debugLine="Return";
Debug.ShouldStop(32);
if (true) return "";
 };
 BA.debugLineNum = 1864;BA.debugLine="End Sub";
Debug.ShouldStop(128);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_eat_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_eat_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2089);
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 2089;BA.debugLine="Sub Table_eat_CellClick (Col As Int, Row As Int)";
Debug.ShouldStop(256);
 BA.debugLineNum = 2090;BA.debugLine="If Col =2 Then";
Debug.ShouldStop(512);
if (_col==2) { 
 BA.debugLineNum = 2091;BA.debugLine="If Table1.GetValue(2,Row)=\"...\" Then";
Debug.ShouldStop(1024);
if ((mostCurrent._table1._getvalue((int) (2),_row)).equals("...")) { 
 BA.debugLineNum = 2092;BA.debugLine="Table1.SetValue(2,Row,\"X\")";
Debug.ShouldStop(2048);
mostCurrent._table1._setvalue((int) (2),_row,"X");
 BA.debugLineNum = 2094;BA.debugLine="Dim Id As InputDialog";
Debug.ShouldStop(8192);
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();Debug.locals.put("Id", _id);
 BA.debugLineNum = 2095;BA.debugLine="Dim ret As Int";
Debug.ShouldStop(16384);
_ret = 0;Debug.locals.put("ret", _ret);
 BA.debugLineNum = 2096;BA.debugLine="Id.Input = Table1.GetValue(3,Row)";
Debug.ShouldStop(32768);
_id.setInput(mostCurrent._table1._getvalue((int) (3),_row));
 BA.debugLineNum = 2097;BA.debugLine="Id.Hint = \"\"";
Debug.ShouldStop(65536);
_id.setHint("");
 BA.debugLineNum = 2098;BA.debugLine="Id.InputType =Id.INPUT_TYPE_DECIMAL_NUM";
Debug.ShouldStop(131072);
_id.setInputType(_id.INPUT_TYPE_DECIMAL_NUMBERS);
 BA.debugLineNum = 2099;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 14";
Debug.ShouldStop(262144);
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 BA.debugLineNum = 2100;BA.debugLine="ret = DialogResponse.CANCEL";
Debug.ShouldStop(524288);
_ret = anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL;Debug.locals.put("ret", _ret);
 BA.debugLineNum = 2101;BA.debugLine="ret = Id.Show(\"จำนวนจาน\",\"แจ้งเตือน\", \"";
Debug.ShouldStop(1048576);
_ret = _id.Show("จำนวนจาน","แจ้งเตือน","ยกเลิก","","ตกลง",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));Debug.locals.put("ret", _ret);
 BA.debugLineNum = 2102;BA.debugLine="If ret=-3 Then";
Debug.ShouldStop(2097152);
if (_ret==-3) { 
 BA.debugLineNum = 2103;BA.debugLine="Return";
Debug.ShouldStop(4194304);
if (true) return "";
 }else {
 BA.debugLineNum = 2105;BA.debugLine="Table1.SetValue(3,Row,Id.Input)";
Debug.ShouldStop(16777216);
mostCurrent._table1._setvalue((int) (3),_row,_id.getInput());
 };
 }else {
 BA.debugLineNum = 2109;BA.debugLine="Table1.SetValue(2,Row,\"...\")";
Debug.ShouldStop(268435456);
mostCurrent._table1._setvalue((int) (2),_row,"...");
 };
 };
 BA.debugLineNum = 2113;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_material2_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_material2_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,985);
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 985;BA.debugLine="Sub Table_material2_CellClick (Col As Int, Row As";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 986;BA.debugLine="If  Col=5 Then";
Debug.ShouldStop(33554432);
if (_col==5) { 
 BA.debugLineNum = 987;BA.debugLine="idmat = Table1.GetValue(0,Row)";
Debug.ShouldStop(67108864);
_idmat = (int)(Double.parseDouble(mostCurrent._table1._getvalue((int) (0),_row)));
 BA.debugLineNum = 988;BA.debugLine="Dim i As Int";
Debug.ShouldStop(134217728);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 989;BA.debugLine="i = Msgbox2(\"กรุณาเลือก?\", \"แจ้งเตือน\", \"แก้ไข\"";
Debug.ShouldStop(268435456);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("กรุณาเลือก?","แจ้งเตือน","แก้ไข","","ลบ",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 990;BA.debugLine="If i=-1 Then";
Debug.ShouldStop(536870912);
if (_i==-1) { 
 BA.debugLineNum = 991;BA.debugLine="dataMode=\"edit\"";
Debug.ShouldStop(1073741824);
mostCurrent._datamode = "edit";
 BA.debugLineNum = 992;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 993;BA.debugLine="Activity.LoadLayout(\"mngmaterial\")";
Debug.ShouldStop(1);
mostCurrent._activity.LoadLayout("mngmaterial",mostCurrent.activityBA);
 BA.debugLineNum = 994;BA.debugLine="scvmngmaterial.Panel.LoadLayout(\"scollmngmate";
Debug.ShouldStop(2);
mostCurrent._scvmngmaterial.getPanel().LoadLayout("scollmngmaterial",mostCurrent.activityBA);
 BA.debugLineNum = 995;BA.debugLine="scvmngmaterial.Panel.Height = 1100dip";
Debug.ShouldStop(4);
mostCurrent._scvmngmaterial.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1100)));
 BA.debugLineNum = 996;BA.debugLine="scvmngmaterial.Height = 80%y";
Debug.ShouldStop(8);
mostCurrent._scvmngmaterial.setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA));
 BA.debugLineNum = 997;BA.debugLine="scvmngmaterial.Width = 100%x";
Debug.ShouldStop(16);
mostCurrent._scvmngmaterial.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA));
 BA.debugLineNum = 998;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(32);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 999;BA.debugLine="Dim sql As String";
Debug.ShouldStop(64);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1000;BA.debugLine="sql=\"select material_name,kcal,prot,vitA,v";
Debug.ShouldStop(128);
_sql = "select material_name,kcal,prot,vitA,vitC,vitE,vitB1,vitB2,vitB3,vitB6,vitB12,chol,sodi,pota,calc,magn,iron,zinc,sele,crab,fat,h2o,fiber,vitB9 from material where material_id='"+BA.NumberToString(_idmat)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1001;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(256);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1002;BA.debugLine="rs.Position =0";
Debug.ShouldStop(512);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1003;BA.debugLine="txtmate1.text = rs.GetString(\"material_";
Debug.ShouldStop(1024);
mostCurrent._txtmate1.setText((Object)(_rs.GetString("material_name")));
 BA.debugLineNum = 1006;BA.debugLine="txtmate3.Text = rs.GetString(\"kcal\")";
Debug.ShouldStop(8192);
mostCurrent._txtmate3.setText((Object)(_rs.GetString("kcal")));
 BA.debugLineNum = 1007;BA.debugLine="txtmate4.Text = rs.GetString(\"prot\")";
Debug.ShouldStop(16384);
mostCurrent._txtmate4.setText((Object)(_rs.GetString("prot")));
 BA.debugLineNum = 1008;BA.debugLine="txtmate5.Text = rs.GetString(\"vitA\")";
Debug.ShouldStop(32768);
mostCurrent._txtmate5.setText((Object)(_rs.GetString("vitA")));
 BA.debugLineNum = 1009;BA.debugLine="txtmate6.Text = rs.GetString(\"vitC\")";
Debug.ShouldStop(65536);
mostCurrent._txtmate6.setText((Object)(_rs.GetString("vitC")));
 BA.debugLineNum = 1010;BA.debugLine="txtmate7.Text = rs.GetString(\"vitE\")";
Debug.ShouldStop(131072);
mostCurrent._txtmate7.setText((Object)(_rs.GetString("vitE")));
 BA.debugLineNum = 1011;BA.debugLine="txtmate8.Text = rs.GetString(\"vitB1\")";
Debug.ShouldStop(262144);
mostCurrent._txtmate8.setText((Object)(_rs.GetString("vitB1")));
 BA.debugLineNum = 1012;BA.debugLine="txtmate9.Text = rs.GetString(\"vitB2\")";
Debug.ShouldStop(524288);
mostCurrent._txtmate9.setText((Object)(_rs.GetString("vitB2")));
 BA.debugLineNum = 1013;BA.debugLine="txtmate10.Text = rs.GetString(\"vitB3\")";
Debug.ShouldStop(1048576);
mostCurrent._txtmate10.setText((Object)(_rs.GetString("vitB3")));
 BA.debugLineNum = 1014;BA.debugLine="txtmate11.Text = rs.GetString(\"vitB6\")";
Debug.ShouldStop(2097152);
mostCurrent._txtmate11.setText((Object)(_rs.GetString("vitB6")));
 BA.debugLineNum = 1015;BA.debugLine="txtmate12.Text = rs.GetString(\"vitB12\")";
Debug.ShouldStop(4194304);
mostCurrent._txtmate12.setText((Object)(_rs.GetString("vitB12")));
 BA.debugLineNum = 1016;BA.debugLine="txtmate13.Text = rs.GetString(\"chol\")";
Debug.ShouldStop(8388608);
mostCurrent._txtmate13.setText((Object)(_rs.GetString("chol")));
 BA.debugLineNum = 1017;BA.debugLine="txtmate14.Text = rs.GetString(\"sodi\")";
Debug.ShouldStop(16777216);
mostCurrent._txtmate14.setText((Object)(_rs.GetString("sodi")));
 BA.debugLineNum = 1018;BA.debugLine="txtmate15.Text = rs.GetString(\"pota\")";
Debug.ShouldStop(33554432);
mostCurrent._txtmate15.setText((Object)(_rs.GetString("pota")));
 BA.debugLineNum = 1019;BA.debugLine="txtmate16.Text = rs.GetString(\"calc\")";
Debug.ShouldStop(67108864);
mostCurrent._txtmate16.setText((Object)(_rs.GetString("calc")));
 BA.debugLineNum = 1020;BA.debugLine="txtmate17.Text = rs.GetString(\"magn\")";
Debug.ShouldStop(134217728);
mostCurrent._txtmate17.setText((Object)(_rs.GetString("magn")));
 BA.debugLineNum = 1021;BA.debugLine="txtmate18.Text = rs.GetString(\"iron\")";
Debug.ShouldStop(268435456);
mostCurrent._txtmate18.setText((Object)(_rs.GetString("iron")));
 BA.debugLineNum = 1022;BA.debugLine="txtmate19.Text = rs.GetString(\"zinc\")";
Debug.ShouldStop(536870912);
mostCurrent._txtmate19.setText((Object)(_rs.GetString("zinc")));
 BA.debugLineNum = 1023;BA.debugLine="txtmate20.Text = rs.GetString(\"sele\")";
Debug.ShouldStop(1073741824);
mostCurrent._txtmate20.setText((Object)(_rs.GetString("sele")));
 BA.debugLineNum = 1024;BA.debugLine="txtmate21.Text = rs.GetString(\"crab\")";
Debug.ShouldStop(-2147483648);
mostCurrent._txtmate21.setText((Object)(_rs.GetString("crab")));
 BA.debugLineNum = 1025;BA.debugLine="txtmate22.Text = rs.GetString(\"fat\")";
Debug.ShouldStop(1);
mostCurrent._txtmate22.setText((Object)(_rs.GetString("fat")));
 BA.debugLineNum = 1026;BA.debugLine="txtmate23.Text = rs.GetString(\"h2o\")";
Debug.ShouldStop(2);
mostCurrent._txtmate23.setText((Object)(_rs.GetString("h2o")));
 BA.debugLineNum = 1027;BA.debugLine="txtmate24.Text = rs.GetString(\"fiber\")";
Debug.ShouldStop(4);
mostCurrent._txtmate24.setText((Object)(_rs.GetString("fiber")));
 BA.debugLineNum = 1028;BA.debugLine="txtmate25.Text = rs.GetString(\"vitB9\")";
Debug.ShouldStop(8);
mostCurrent._txtmate25.setText((Object)(_rs.GetString("vitB9")));
 }else 
{ BA.debugLineNum = 1030;BA.debugLine="Else If i=-2 Then";
Debug.ShouldStop(32);
if (_i==-2) { 
 BA.debugLineNum = 1031;BA.debugLine="Dim sql As String 																			'จัดก";
Debug.ShouldStop(64);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1032;BA.debugLine="sql=\"delete from material where material_i";
Debug.ShouldStop(128);
_sql = "delete from material where material_id='"+BA.NumberToString(_idmat)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1033;BA.debugLine="dbSQL.ExecNonQuery(sql)			'เรียกฐานข้อมูล";
Debug.ShouldStop(256);
mostCurrent._dbsql.ExecNonQuery(_sql);
 BA.debugLineNum = 1034;BA.debugLine="Msgbox(\"ลบวัตถุดิบสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(512);
anywheresoftware.b4a.keywords.Common.Msgbox("ลบวัตถุดิบสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1035;BA.debugLine="showMaterial";
Debug.ShouldStop(1024);
_showmaterial();
 }else {
 BA.debugLineNum = 1037;BA.debugLine="Return";
Debug.ShouldStop(4096);
if (true) return "";
 }};
 };
 BA.debugLineNum = 1040;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_matmenu_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_matmenu_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,1193);
int _i = 0;
String _sql = "";
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 1193;BA.debugLine="Sub Table_matmenu_CellClick (Col As Int, Row As In";
Debug.ShouldStop(256);
 BA.debugLineNum = 1194;BA.debugLine="If Col=1 Then";
Debug.ShouldStop(512);
if (_col==1) { 
 BA.debugLineNum = 1195;BA.debugLine="idmenu = Table1.GetValue(0,Row)";
Debug.ShouldStop(1024);
_idmenu = (int)(Double.parseDouble(mostCurrent._table1._getvalue((int) (0),_row)));
 BA.debugLineNum = 1196;BA.debugLine="showmatmenu";
Debug.ShouldStop(2048);
_showmatmenu();
 }else 
{ BA.debugLineNum = 1199;BA.debugLine="else if Col=2 Then";
Debug.ShouldStop(16384);
if (_col==2) { 
 BA.debugLineNum = 1200;BA.debugLine="Dim i As Int";
Debug.ShouldStop(32768);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 1201;BA.debugLine="i = Msgbox2(\"กรุณาเลือก?\", \"แจ้งเตือน\", \"แก้ไข";
Debug.ShouldStop(65536);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("กรุณาเลือก?","แจ้งเตือน","แก้ไข","","ลบ",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 1202;BA.debugLine="If i=-1 Then   'do Yes code";
Debug.ShouldStop(131072);
if (_i==-1) { 
 BA.debugLineNum = 1203;BA.debugLine="Dim sql As String 																			'จัดการ";
Debug.ShouldStop(262144);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1204;BA.debugLine="sql=\"delete from detailmenu where materia";
Debug.ShouldStop(524288);
_sql = "delete from detailmenu where material_id='"+mostCurrent._table1._getvalue((int) (0),_row)+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1205;BA.debugLine="dbSQL.ExecNonQuery(sql)";
Debug.ShouldStop(1048576);
mostCurrent._dbsql.ExecNonQuery(_sql);
 BA.debugLineNum = 1206;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(2097152);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1207;BA.debugLine="showmatmenu";
Debug.ShouldStop(4194304);
_showmatmenu();
 }else 
{ BA.debugLineNum = 1208;BA.debugLine="Else If i=-2 Then";
Debug.ShouldStop(8388608);
if (_i==-2) { 
 BA.debugLineNum = 1209;BA.debugLine="Return";
Debug.ShouldStop(16777216);
if (true) return "";
 }else {
 BA.debugLineNum = 1211;BA.debugLine="Return";
Debug.ShouldStop(67108864);
if (true) return "";
 }};
 }};
 BA.debugLineNum = 1214;BA.debugLine="Msgbox(\"ลบข้อมูลสำเร็จ\",\"แจ้งเตือน\")";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Msgbox("ลบข้อมูลสำเร็จ","แจ้งเตือน",mostCurrent.activityBA);
 BA.debugLineNum = 1215;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_menu_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_menu_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,562);
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 562;BA.debugLine="Sub Table_menu_CellClick (Col As Int, Row As Int)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 565;BA.debugLine="If Col=1 Then";
Debug.ShouldStop(1048576);
if (_col==1) { 
 BA.debugLineNum = 566;BA.debugLine="Dim i As Int";
Debug.ShouldStop(2097152);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 567;BA.debugLine="i = Msgbox2(\"ยืนยันการแก้ไขข้อมูล\", \"แจ้งเตือน\"";
Debug.ShouldStop(4194304);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("ยืนยันการแก้ไขข้อมูล","แจ้งเตือน","แก้ไข","","ยกเลิก",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 568;BA.debugLine="If i =-1 Then 'แก้ไข";
Debug.ShouldStop(8388608);
if (_i==-1) { 
 }else 
{ BA.debugLineNum = 570;BA.debugLine="Else If i= -2  Then 'ยกเลิก";
Debug.ShouldStop(33554432);
if (_i==-2) { 
 BA.debugLineNum = 571;BA.debugLine="Return";
Debug.ShouldStop(67108864);
if (true) return "";
 }else {
 BA.debugLineNum = 573;BA.debugLine="Return";
Debug.ShouldStop(268435456);
if (true) return "";
 }};
 }else 
{ BA.debugLineNum = 575;BA.debugLine="Else If Col=5 Then";
Debug.ShouldStop(1073741824);
if (_col==5) { 
 BA.debugLineNum = 576;BA.debugLine="idmenu= Table1.GetValue(0,Row)";
Debug.ShouldStop(-2147483648);
_idmenu = (int)(Double.parseDouble(mostCurrent._table1._getvalue((int) (0),_row)));
 BA.debugLineNum = 578;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(2);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 579;BA.debugLine="Activity.LoadLayout(\"mng_menu\")";
Debug.ShouldStop(4);
mostCurrent._activity.LoadLayout("mng_menu",mostCurrent.activityBA);
 BA.debugLineNum = 580;BA.debugLine="showmatmenu";
Debug.ShouldStop(8);
_showmatmenu();
 BA.debugLineNum = 582;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(32);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 583;BA.debugLine="Dim sql As String";
Debug.ShouldStop(64);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 584;BA.debugLine="sql=\"select menufood_id, menufood_name from me";
Debug.ShouldStop(128);
_sql = "select menufood_id, menufood_name from menufood where menufood_id='"+BA.NumberToString(_idmenu)+"' ";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 585;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(256);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 586;BA.debugLine="rs.Position =0";
Debug.ShouldStop(512);
_rs.setPosition((int) (0));
 BA.debugLineNum = 587;BA.debugLine="lblmenu2.Text = rs.GetString(\"menufood_name";
Debug.ShouldStop(1024);
mostCurrent._lblmenu2.setText((Object)(_rs.GetString("menufood_name")));
 }};
 BA.debugLineNum = 590;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_nutri_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_nutri_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,2483);
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 2483;BA.debugLine="Sub Table_nutri_CellClick (Col As Int, Row As Int)";
Debug.ShouldStop(262144);
 BA.debugLineNum = 2484;BA.debugLine="If Col =2 Then";
Debug.ShouldStop(524288);
if (_col==2) { 
 BA.debugLineNum = 2485;BA.debugLine="If Table1.GetValue(2,Row)=\"...\" Then";
Debug.ShouldStop(1048576);
if ((mostCurrent._table1._getvalue((int) (2),_row)).equals("...")) { 
 BA.debugLineNum = 2486;BA.debugLine="Table1.SetValue(2,Row,\"X\")";
Debug.ShouldStop(2097152);
mostCurrent._table1._setvalue((int) (2),_row,"X");
 }else {
 BA.debugLineNum = 2488;BA.debugLine="Table1.SetValue(2,Row,\"...\")";
Debug.ShouldStop(8388608);
mostCurrent._table1._setvalue((int) (2),_row,"...");
 };
 };
 BA.debugLineNum = 2491;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _table_type_cellclick(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("Table_type_CellClick (main) ","main",0,mostCurrent.activityBA,mostCurrent,741);
int _i = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 741;BA.debugLine="Sub Table_type_CellClick (Col As Int, Row As Int)";
Debug.ShouldStop(16);
 BA.debugLineNum = 742;BA.debugLine="If Col=1 Then";
Debug.ShouldStop(32);
if (_col==1) { 
 BA.debugLineNum = 743;BA.debugLine="idtype= Table1.GetValue(0,Row)";
Debug.ShouldStop(64);
mostCurrent._idtype = mostCurrent._table1._getvalue((int) (0),_row);
 BA.debugLineNum = 744;BA.debugLine="showMaterial";
Debug.ShouldStop(128);
_showmaterial();
 }else 
{ BA.debugLineNum = 745;BA.debugLine="else  If Col=3 Then";
Debug.ShouldStop(256);
if (_col==3) { 
 BA.debugLineNum = 746;BA.debugLine="idtype= Table1.GetValue(0,Row)";
Debug.ShouldStop(512);
mostCurrent._idtype = mostCurrent._table1._getvalue((int) (0),_row);
 BA.debugLineNum = 747;BA.debugLine="Dim i As Int";
Debug.ShouldStop(1024);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 748;BA.debugLine="i = Msgbox2(\"กรุณาเลือก?\", \"แจ้งเตือน\", \"แก้ไข";
Debug.ShouldStop(2048);
_i = anywheresoftware.b4a.keywords.Common.Msgbox2("กรุณาเลือก?","แจ้งเตือน","แก้ไข","","ลบ",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);Debug.locals.put("i", _i);
 BA.debugLineNum = 749;BA.debugLine="If i=-1 Then";
Debug.ShouldStop(4096);
if (_i==-1) { 
 BA.debugLineNum = 750;BA.debugLine="dataMode=\"edit\" 'เก็บค่า edit";
Debug.ShouldStop(8192);
mostCurrent._datamode = "edit";
 BA.debugLineNum = 751;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16384);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 752;BA.debugLine="Activity.LoadLayout(\"foodtype_new\")";
Debug.ShouldStop(32768);
mostCurrent._activity.LoadLayout("foodtype_new",mostCurrent.activityBA);
 BA.debugLineNum = 753;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(65536);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 754;BA.debugLine="Dim sql As String";
Debug.ShouldStop(131072);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 755;BA.debugLine="sql=\"select type_name from foodtype  where";
Debug.ShouldStop(262144);
_sql = "select type_name from foodtype  where type_id='"+mostCurrent._idtype+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 756;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(524288);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 757;BA.debugLine="rs.Position =0";
Debug.ShouldStop(1048576);
_rs.setPosition((int) (0));
 BA.debugLineNum = 758;BA.debugLine="txtfoodtype.Text = rs.GetString(\"type_n";
Debug.ShouldStop(2097152);
mostCurrent._txtfoodtype.setText((Object)(_rs.GetString("type_name")));
 }else 
{ BA.debugLineNum = 759;BA.debugLine="Else If i=-2 Then";
Debug.ShouldStop(4194304);
if (_i==-2) { 
 BA.debugLineNum = 760;BA.debugLine="Dim sql As String 																			'จัดก";
Debug.ShouldStop(8388608);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 761;BA.debugLine="sql=\"delete from foodtype where type_id='";
Debug.ShouldStop(16777216);
_sql = "delete from foodtype where type_id='"+mostCurrent._idtype+"'";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 762;BA.debugLine="dbSQL.ExecNonQuery(sql)			'ลบฐานข้อมูล,อ";
Debug.ShouldStop(33554432);
mostCurrent._dbsql.ExecNonQuery(_sql);
 BA.debugLineNum = 763;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(67108864);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 764;BA.debugLine="Activity.LoadLayout(\"foodtype\")";
Debug.ShouldStop(134217728);
mostCurrent._activity.LoadLayout("foodtype",mostCurrent.activityBA);
 BA.debugLineNum = 765;BA.debugLine="showfoodtype";
Debug.ShouldStop(268435456);
_showfoodtype();
 }else {
 BA.debugLineNum = 767;BA.debugLine="Return";
Debug.ShouldStop(1073741824);
if (true) return "";
 }};
 }};
 BA.debugLineNum = 771;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _test_click() throws Exception{
try {
		Debug.PushSubsStack("test_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent,1621);
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
String _sql = "";
int _i = 0;
 BA.debugLineNum = 1621;BA.debugLine="Sub test_Click		'ปุ่มไว้ดูฐานข้อมูล";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 1622;BA.debugLine="If	Pnre.Visible=True Then";
Debug.ShouldStop(2097152);
if (mostCurrent._pnre.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 1623;BA.debugLine="Pnre.Visible=False";
Debug.ShouldStop(4194304);
mostCurrent._pnre.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 1624;BA.debugLine="Table1.ClearAll";
Debug.ShouldStop(8388608);
mostCurrent._table1._clearall();
 BA.debugLineNum = 1625;BA.debugLine="Activity.RemoveAllViews";
Debug.ShouldStop(16777216);
mostCurrent._activity.RemoveAllViews();
 BA.debugLineNum = 1626;BA.debugLine="Activity.LoadLayout(\"mng_repast\")";
Debug.ShouldStop(33554432);
mostCurrent._activity.LoadLayout("mng_repast",mostCurrent.activityBA);
 BA.debugLineNum = 1627;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(67108864);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1628;BA.debugLine="Dim sql As String";
Debug.ShouldStop(134217728);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1629;BA.debugLine="sql=\"select namesuser,user_id from recorduser\"";
Debug.ShouldStop(268435456);
_sql = "select namesuser,user_id from recorduser";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1630;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(536870912);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1631;BA.debugLine="rs.Position =0";
Debug.ShouldStop(1073741824);
_rs.setPosition((int) (0));
 BA.debugLineNum = 1632;BA.debugLine="lblidname.Text = rs.GetInt(\"user_id\")";
Debug.ShouldStop(-2147483648);
mostCurrent._lblidname.setText((Object)(_rs.GetInt("user_id")));
 BA.debugLineNum = 1633;BA.debugLine="lblnamere.Text = rs.GetString(\"namesuser\")";
Debug.ShouldStop(1);
mostCurrent._lblnamere.setText((Object)(_rs.GetString("namesuser")));
 }else 
{ BA.debugLineNum = 1635;BA.debugLine="Else If Pnre.Visible=False Then";
Debug.ShouldStop(4);
if (mostCurrent._pnre.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 1636;BA.debugLine="Table1.Initialize(Me, \"Table_test\", 5)";
Debug.ShouldStop(8);
mostCurrent._table1._initialize(mostCurrent.activityBA,main.getObject(),"Table_test",(int) (5));
 BA.debugLineNum = 1637;BA.debugLine="Table1.AddToActivity(Activity, 0,lblrepast.Top";
Debug.ShouldStop(16);
mostCurrent._table1._addtoactivity(mostCurrent._activity,(int) (0),(int) (mostCurrent._lblrepast.getTop()+mostCurrent._lblrepast.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._lblrepast.getHeight()));
 BA.debugLineNum = 1638;BA.debugLine="Table1.SetHeader(Array As String(\"id\",\"ชื่อมื้";
Debug.ShouldStop(32);
mostCurrent._table1._setheader(new String[]{"id","ชื่อมื้อ","วันที่","จำนวน","ชื่อผู้เพิ่มไปแล้ว"});
 BA.debugLineNum = 1639;BA.debugLine="Table1.SetColumnsWidths(Array As Int(5%x,35%x,";
Debug.ShouldStop(64);
mostCurrent._table1._setcolumnswidths(new int[]{anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA)});
 BA.debugLineNum = 1640;BA.debugLine="Dim rs As Cursor";
Debug.ShouldStop(128);
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("rs", _rs);
 BA.debugLineNum = 1641;BA.debugLine="Dim sql As String";
Debug.ShouldStop(256);
_sql = "";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1642;BA.debugLine="sql=\"select repastname_id,repastname_name,repa";
Debug.ShouldStop(512);
_sql = "select repastname_id,repastname_name,repast_date,repast_count,user_id from repastname order by repast_date";Debug.locals.put("sql", _sql);
 BA.debugLineNum = 1643;BA.debugLine="rs=dbSQL.ExecQuery(sql)";
Debug.ShouldStop(1024);
_rs.setObject((android.database.Cursor)(mostCurrent._dbsql.ExecQuery(_sql)));
 BA.debugLineNum = 1644;BA.debugLine="For i = 0 To rs.RowCount -1";
Debug.ShouldStop(2048);
{
final int step1391 = 1;
final int limit1391 = (int) (_rs.getRowCount()-1);
for (_i = (int) (0); (step1391 > 0 && _i <= limit1391) || (step1391 < 0 && _i >= limit1391); _i = ((int)(0 + _i + step1391))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 1645;BA.debugLine="rs.Position =i";
Debug.ShouldStop(4096);
_rs.setPosition(_i);
 BA.debugLineNum = 1646;BA.debugLine="Table1.AddRow(Array As String(rs.GetString(";
Debug.ShouldStop(8192);
mostCurrent._table1._addrow(new String[]{_rs.GetString("repastname_id"),_rs.GetString("repastname_name"),_rs.GetString("repast_date"),_rs.GetString("repast_count"),_rs.GetString("user_id")});
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 1648;BA.debugLine="Pnre.Visible=True";
Debug.ShouldStop(32768);
mostCurrent._pnre.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }};
 BA.debugLineNum = 1650;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
}
