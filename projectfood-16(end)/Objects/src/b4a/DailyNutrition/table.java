package b4a.DailyNutrition;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class table extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.DailyNutrition.table");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            if (BA.isShellModeRuntimeCheck(ba)) {
			    ba.raiseEvent2(null, true, "CREATE", true, "b4a.DailyNutrition.table",
                    ba);
                return;
		    }
        }
        ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.StringUtils _stringutils1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _sv = null;
public anywheresoftware.b4a.objects.PanelWrapper _header = null;
public Object _callback = null;
public String _event = "";
public int _selectedrow = 0;
public anywheresoftware.b4a.objects.collections.List _data = null;
public anywheresoftware.b4a.objects.collections.List _labelscache = null;
public int _minvisiblerow = 0;
public int _maxvisiblerow = 0;
public boolean _visible = false;
public anywheresoftware.b4a.objects.collections.Map _visiblerows = null;
public int _numberofcolumns = 0;
public int _columnwidth = 0;
public int _rowheight = 0;
public int _headercolor = 0;
public int _tablecolor = 0;
public int _fontcolor = 0;
public int _headerfontcolor = 0;
public float _fontsize = 0f;
public int _alignment = 0;
public Object[] _selecteddrawable = null;
public Object[] _drawable1 = null;
public Object[] _drawable2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelc = null;
public b4a.DailyNutrition.main _main = null;
  public Object[] GetGlobals() {
		return new Object[] {"Alignment",_alignment,"Callback",_callback,"ColumnWidth",_columnwidth,"Data",_data,"Drawable1",_drawable1,"Drawable2",_drawable2,"Event",_event,"FontColor",_fontcolor,"FontSize",_fontsize,"Header",_header,"HeaderColor",_headercolor,"HeaderFontColor",_headerfontcolor,"LabelsCache",_labelscache,"Main",Debug.moduleToString(b4a.DailyNutrition.main.class),"maxVisibleRow",_maxvisiblerow,"minVisibleRow",_minvisiblerow,"NumberOfColumns",_numberofcolumns,"PanelC",_panelc,"RowHeight",_rowheight,"SelectedDrawable",_selecteddrawable,"SelectedRow",_selectedrow,"StringUtils1",_stringutils1,"SV",_sv,"TableColor",_tablecolor,"visible",_visible,"visibleRows",_visiblerows};
}
public static class _rowcol{
public boolean IsInitialized;
public int Row;
public int Col;
public void Initialize() {
IsInitialized = true;
Row = 0;
Col = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _addrow(String[] _values) throws Exception{
try {
		Debug.PushSubsStack("AddRow (table) ","table",1,ba,this,147);
int _lastrow = 0;
Debug.locals.put("Values", _values);
 BA.debugLineNum = 147;BA.debugLine="Public Sub AddRow(Values() As String)";
Debug.ShouldStop(262144);
 BA.debugLineNum = 148;BA.debugLine="If Values.Length <> NumberOfColumns Then";
Debug.ShouldStop(524288);
if (_values.length!=_numberofcolumns) { 
 BA.debugLineNum = 149;BA.debugLine="Log(\"Wrong number of values.\")";
Debug.ShouldStop(1048576);
__c.Log("Wrong number of values.");
 BA.debugLineNum = 150;BA.debugLine="Return";
Debug.ShouldStop(2097152);
if (true) return "";
 };
 BA.debugLineNum = 152;BA.debugLine="Data.Add(Values)";
Debug.ShouldStop(8388608);
_data.Add((Object)(_values));
 BA.debugLineNum = 153;BA.debugLine="Dim lastRow As Int";
Debug.ShouldStop(16777216);
_lastrow = 0;Debug.locals.put("lastRow", _lastrow);
 BA.debugLineNum = 154;BA.debugLine="lastRow = Data.Size - 1";
Debug.ShouldStop(33554432);
_lastrow = (int) (_data.getSize()-1);Debug.locals.put("lastRow", _lastrow);
 BA.debugLineNum = 155;BA.debugLine="If lastRow < (SV.ScrollPosition + SV.Height) / Ro";
Debug.ShouldStop(67108864);
if (_lastrow<(_sv.getScrollPosition()+_sv.getHeight())/(double)_rowheight+1) { 
 BA.debugLineNum = 156;BA.debugLine="ShowRow(lastRow)";
Debug.ShouldStop(134217728);
_showrow(_lastrow);
 };
 BA.debugLineNum = 158;BA.debugLine="SV.Panel.Height = Data.Size * RowHeight";
Debug.ShouldStop(536870912);
_sv.getPanel().setHeight((int) (_data.getSize()*_rowheight));
 BA.debugLineNum = 159;BA.debugLine="End Sub";
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
public String  _addtoactivity(anywheresoftware.b4a.objects.ActivityWrapper _act,int _left,int _top,int _width,int _height) throws Exception{
try {
		Debug.PushSubsStack("AddToActivity (table) ","table",1,ba,this,133);
Debug.locals.put("Act", _act);
Debug.locals.put("Left", _left);
Debug.locals.put("Top", _top);
Debug.locals.put("Width", _width);
Debug.locals.put("Height", _height);
 BA.debugLineNum = 133;BA.debugLine="Public Sub AddToActivity(Act As Activity, Left As";
Debug.ShouldStop(16);
 BA.debugLineNum = 134;BA.debugLine="visible = True";
Debug.ShouldStop(32);
_visible = __c.True;
 BA.debugLineNum = 135;BA.debugLine="Header.Initialize(\"\")";
Debug.ShouldStop(64);
_header.Initialize(ba,"");
 BA.debugLineNum = 136;BA.debugLine="Header.Color = TableColor";
Debug.ShouldStop(128);
_header.setColor(_tablecolor);
 BA.debugLineNum = 137;BA.debugLine="Act.AddView(Header, Left, Top , Width, RowHeight)";
Debug.ShouldStop(256);
_act.AddView((android.view.View)(_header.getObject()),_left,_top,_width,_rowheight);
 BA.debugLineNum = 138;BA.debugLine="Act.AddView(SV, Left, Top + RowHeight, Width, Hei";
Debug.ShouldStop(512);
_act.AddView((android.view.View)(_sv.getObject()),_left,(int) (_top+_rowheight),_width,(int) (_height-_rowheight));
 BA.debugLineNum = 139;BA.debugLine="ColumnWidth = SV.Width / NumberOfColumns";
Debug.ShouldStop(1024);
_columnwidth = (int) (_sv.getWidth()/(double)_numberofcolumns);
 BA.debugLineNum = 141;BA.debugLine="SV_ScrollChanged(0)";
Debug.ShouldStop(4096);
_sv_scrollchanged((int) (0));
 BA.debugLineNum = 143;BA.debugLine="End Sub";
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
public String  _cell_click() throws Exception{
try {
		Debug.PushSubsStack("Cell_Click (table) ","table",1,ba,this,255);
b4a.DailyNutrition.table._rowcol _rc = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
 BA.debugLineNum = 255;BA.debugLine="Private Sub Cell_Click";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 256;BA.debugLine="Dim rc As RowCol";
Debug.ShouldStop(-2147483648);
_rc = new b4a.DailyNutrition.table._rowcol();Debug.locals.put("rc", _rc);
 BA.debugLineNum = 257;BA.debugLine="Dim l As Label";
Debug.ShouldStop(1);
_l = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("l", _l);
 BA.debugLineNum = 258;BA.debugLine="l = Sender";
Debug.ShouldStop(2);
_l.setObject((android.widget.TextView)(__c.Sender(ba)));
 BA.debugLineNum = 259;BA.debugLine="rc = l.Tag";
Debug.ShouldStop(4);
_rc = (b4a.DailyNutrition.table._rowcol)(_l.getTag());Debug.locals.put("rc", _rc);
 BA.debugLineNum = 260;BA.debugLine="SelectRow(rc.Row)";
Debug.ShouldStop(8);
_selectrow(_rc.Row);
 BA.debugLineNum = 261;BA.debugLine="If SubExists(Callback, Event & \"_CellClick\") Then";
Debug.ShouldStop(16);
if (__c.SubExists(ba,_callback,_event+"_CellClick")) { 
 BA.debugLineNum = 262;BA.debugLine="CallSub3(Callback, Event & \"_CellClick\", rc.Col,";
Debug.ShouldStop(32);
__c.CallSubNew3(ba,_callback,_event+"_CellClick",(Object)(_rc.Col),(Object)(_rc.Row));
 };
 BA.debugLineNum = 264;BA.debugLine="End Sub";
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
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private StringUtils1 As StringUtils";
_stringutils1 = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 4;BA.debugLine="Private SV As ScrollView";
_sv = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 5;BA.debugLine="Private Header As Panel";
_header = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 6;BA.debugLine="Private Callback As Object";
_callback = new Object();
 //BA.debugLineNum = 7;BA.debugLine="Private Event As String";
_event = "";
 //BA.debugLineNum = 8;BA.debugLine="Private SelectedRow As Int";
_selectedrow = 0;
 //BA.debugLineNum = 9;BA.debugLine="Private Data As List";
_data = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="Private LabelsCache As List";
_labelscache = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Private minVisibleRow, maxVisibleRow As Int";
_minvisiblerow = 0;
_maxvisiblerow = 0;
 //BA.debugLineNum = 12;BA.debugLine="Private visible As Boolean";
_visible = false;
 //BA.debugLineNum = 13;BA.debugLine="Private visibleRows As Map";
_visiblerows = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 14;BA.debugLine="Private NumberOfColumns, ColumnWidth As Int";
_numberofcolumns = 0;
_columnwidth = 0;
 //BA.debugLineNum = 15;BA.debugLine="Public RowHeight, HeaderColor, TableColor, FontCo";
_rowheight = 0;
_headercolor = 0;
_tablecolor = 0;
_fontcolor = 0;
_headerfontcolor = 0;
 //BA.debugLineNum = 16;BA.debugLine="Public FontSize As Float";
_fontsize = 0f;
 //BA.debugLineNum = 17;BA.debugLine="Type RowCol (Row As Int, Col As Int)";
;
 //BA.debugLineNum = 18;BA.debugLine="Public Alignment As Int";
_alignment = 0;
 //BA.debugLineNum = 19;BA.debugLine="Public SelectedDrawable(), Drawable1(), Drawable2";
_selecteddrawable = new Object[(int) (0)];
{
int d0 = _selecteddrawable.length;
for (int i0 = 0;i0 < d0;i0++) {
_selecteddrawable[i0] = new Object();
}
}
;
_drawable1 = new Object[(int) (0)];
{
int d0 = _drawable1.length;
for (int i0 = 0;i0 < d0;i0++) {
_drawable1[i0] = new Object();
}
}
;
_drawable2 = new Object[(int) (0)];
{
int d0 = _drawable2.length;
for (int i0 = 0;i0 < d0;i0++) {
_drawable2[i0] = new Object();
}
}
;
 //BA.debugLineNum = 21;BA.debugLine="HeaderColor = Colors.Gray";
_headercolor = __c.Colors.Gray;
 //BA.debugLineNum = 22;BA.debugLine="RowHeight = 30dip";
_rowheight = __c.DipToCurrent((int) (30));
 //BA.debugLineNum = 23;BA.debugLine="TableColor = Colors.LightGray";
_tablecolor = __c.Colors.LightGray;
 //BA.debugLineNum = 24;BA.debugLine="FontColor = Colors.Black";
_fontcolor = __c.Colors.Black;
 //BA.debugLineNum = 25;BA.debugLine="HeaderFontColor = Colors.White";
_headerfontcolor = __c.Colors.White;
 //BA.debugLineNum = 26;BA.debugLine="FontSize = 14";
_fontsize = (float) (14);
 //BA.debugLineNum = 27;BA.debugLine="Alignment = Gravity.CENTER 'change to Gravity.LEF";
_alignment = __c.Gravity.CENTER;
 //BA.debugLineNum = 28;BA.debugLine="Private PanelC As Panel";
_panelc = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public String  _clearall() throws Exception{
try {
		Debug.PushSubsStack("ClearAll (table) ","table",1,ba,this,40);
 BA.debugLineNum = 40;BA.debugLine="Public Sub ClearAll";
Debug.ShouldStop(128);
 BA.debugLineNum = 41;BA.debugLine="innerClearAll(NumberOfColumns)";
Debug.ShouldStop(256);
_innerclearall(_numberofcolumns);
 BA.debugLineNum = 42;BA.debugLine="End Sub";
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
public anywheresoftware.b4a.objects.LabelWrapper[]  _createnewlabels() throws Exception{
try {
		Debug.PushSubsStack("CreateNewLabels (table) ","table",1,ba,this,221);
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
int _i = 0;
b4a.DailyNutrition.table._rowcol _rc = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
 BA.debugLineNum = 221;BA.debugLine="Private Sub CreateNewLabels As Label()";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 222;BA.debugLine="Dim lbls(NumberOfColumns) As Label";
Debug.ShouldStop(536870912);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[_numberofcolumns];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 223;BA.debugLine="For I = 0 To NumberOfColumns - 1";
Debug.ShouldStop(1073741824);
{
final int step191 = 1;
final int limit191 = (int) (_numberofcolumns-1);
for (_i = (int) (0); (step191 > 0 && _i <= limit191) || (step191 < 0 && _i >= limit191); _i = ((int)(0 + _i + step191))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 224;BA.debugLine="Dim rc As RowCol";
Debug.ShouldStop(-2147483648);
_rc = new b4a.DailyNutrition.table._rowcol();Debug.locals.put("rc", _rc);
 BA.debugLineNum = 225;BA.debugLine="rc.Col = I";
Debug.ShouldStop(1);
_rc.Col = _i;Debug.locals.put("rc", _rc);
 BA.debugLineNum = 226;BA.debugLine="Dim l As Label";
Debug.ShouldStop(2);
_l = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("l", _l);
 BA.debugLineNum = 227;BA.debugLine="l.Initialize(\"cell\")";
Debug.ShouldStop(4);
_l.Initialize(ba,"cell");
 BA.debugLineNum = 228;BA.debugLine="l.Gravity = Alignment";
Debug.ShouldStop(8);
_l.setGravity(_alignment);
 BA.debugLineNum = 229;BA.debugLine="l.TextSize = FontSize";
Debug.ShouldStop(16);
_l.setTextSize(_fontsize);
 BA.debugLineNum = 230;BA.debugLine="l.TextColor = FontColor";
Debug.ShouldStop(32);
_l.setTextColor(_fontcolor);
 BA.debugLineNum = 231;BA.debugLine="l.Tag = rc";
Debug.ShouldStop(64);
_l.setTag((Object)(_rc));
 BA.debugLineNum = 232;BA.debugLine="lbls(I) = l";
Debug.ShouldStop(128);
_lbls[_i] = _l;Debug.locals.put("lbls", _lbls);
 }
}Debug.locals.put("I", _i);
;
 BA.debugLineNum = 234;BA.debugLine="Return lbls";
Debug.ShouldStop(512);
if (true) return _lbls;
 BA.debugLineNum = 235;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public anywheresoftware.b4a.objects.LabelWrapper[]  _getlabels(int _row) throws Exception{
try {
		Debug.PushSubsStack("GetLabels (table) ","table",1,ba,this,204);
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
int _i = 0;
b4a.DailyNutrition.table._rowcol _rc = null;
Debug.locals.put("Row", _row);
 BA.debugLineNum = 204;BA.debugLine="Private Sub GetLabels(Row As Int) As Label()";
Debug.ShouldStop(2048);
 BA.debugLineNum = 205;BA.debugLine="Dim lbls() As Label";
Debug.ShouldStop(4096);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[(int) (0)];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 206;BA.debugLine="If LabelsCache.Size > 0 Then";
Debug.ShouldStop(8192);
if (_labelscache.getSize()>0) { 
 BA.debugLineNum = 208;BA.debugLine="lbls = LabelsCache.Get(LabelsCache.Size - 1)";
Debug.ShouldStop(32768);
_lbls = (anywheresoftware.b4a.objects.LabelWrapper[])(_labelscache.Get((int) (_labelscache.getSize()-1)));Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 209;BA.debugLine="LabelsCache.RemoveAt(LabelsCache.Size - 1)";
Debug.ShouldStop(65536);
_labelscache.RemoveAt((int) (_labelscache.getSize()-1));
 }else {
 BA.debugLineNum = 211;BA.debugLine="lbls = CreateNewLabels";
Debug.ShouldStop(262144);
_lbls = _createnewlabels();Debug.locals.put("lbls", _lbls);
 };
 BA.debugLineNum = 213;BA.debugLine="For I = 0 To lbls.Length - 1";
Debug.ShouldStop(1048576);
{
final int step182 = 1;
final int limit182 = (int) (_lbls.length-1);
for (_i = (int) (0); (step182 > 0 && _i <= limit182) || (step182 < 0 && _i >= limit182); _i = ((int)(0 + _i + step182))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 214;BA.debugLine="Dim rc As RowCol";
Debug.ShouldStop(2097152);
_rc = new b4a.DailyNutrition.table._rowcol();Debug.locals.put("rc", _rc);
 BA.debugLineNum = 215;BA.debugLine="rc = lbls(I).Tag";
Debug.ShouldStop(4194304);
_rc = (b4a.DailyNutrition.table._rowcol)(_lbls[_i].getTag());Debug.locals.put("rc", _rc);
 BA.debugLineNum = 216;BA.debugLine="rc.Row = Row";
Debug.ShouldStop(8388608);
_rc.Row = _row;Debug.locals.put("rc", _rc);
 }
}Debug.locals.put("I", _i);
;
 BA.debugLineNum = 218;BA.debugLine="Return lbls";
Debug.ShouldStop(33554432);
if (true) return _lbls;
 BA.debugLineNum = 219;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _getvalue(int _col,int _row) throws Exception{
try {
		Debug.PushSubsStack("GetValue (table) ","table",1,ba,this,276);
String[] _values = null;
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 276;BA.debugLine="Public Sub GetValue(Col As Int, Row As Int)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 277;BA.debugLine="Dim values() As String";
Debug.ShouldStop(1048576);
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");Debug.locals.put("values", _values);
 BA.debugLineNum = 278;BA.debugLine="values = Data.Get(Row)";
Debug.ShouldStop(2097152);
_values = (String[])(_data.Get(_row));Debug.locals.put("values", _values);
 BA.debugLineNum = 279;BA.debugLine="Return values(Col)";
Debug.ShouldStop(4194304);
if (true) return _values[_col];
 BA.debugLineNum = 280;BA.debugLine="End Sub";
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
public String  _header_click() throws Exception{
try {
		Debug.PushSubsStack("Header_Click (table) ","table",1,ba,this,266);
anywheresoftware.b4a.objects.LabelWrapper _l = null;
int _col = 0;
 BA.debugLineNum = 266;BA.debugLine="Private Sub Header_Click";
Debug.ShouldStop(512);
 BA.debugLineNum = 267;BA.debugLine="Dim l As Label";
Debug.ShouldStop(1024);
_l = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("l", _l);
 BA.debugLineNum = 268;BA.debugLine="Dim col As Int";
Debug.ShouldStop(2048);
_col = 0;Debug.locals.put("col", _col);
 BA.debugLineNum = 269;BA.debugLine="l = Sender";
Debug.ShouldStop(4096);
_l.setObject((android.widget.TextView)(__c.Sender(ba)));
 BA.debugLineNum = 270;BA.debugLine="col = l.Tag";
Debug.ShouldStop(8192);
_col = (int)(BA.ObjectToNumber(_l.getTag()));Debug.locals.put("col", _col);
 BA.debugLineNum = 271;BA.debugLine="If SubExists(Callback, Event & \"_HeaderClick\") Th";
Debug.ShouldStop(16384);
if (__c.SubExists(ba,_callback,_event+"_HeaderClick")) { 
 BA.debugLineNum = 272;BA.debugLine="CallSub2(Callback, Event & \"_HeaderClick\", col)";
Debug.ShouldStop(32768);
__c.CallSubNew2(ba,_callback,_event+"_HeaderClick",(Object)(_col));
 };
 BA.debugLineNum = 274;BA.debugLine="End Sub";
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
public String  _hiderow(int _row) throws Exception{
try {
		Debug.PushSubsStack("HideRow (table) ","table",1,ba,this,189);
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
int _i = 0;
Debug.locals.put("Row", _row);
 BA.debugLineNum = 189;BA.debugLine="Private Sub HideRow (Row As Int)";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 191;BA.debugLine="Dim lbls() As Label";
Debug.ShouldStop(1073741824);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[(int) (0)];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 192;BA.debugLine="lbls = visibleRows.Get(Row)";
Debug.ShouldStop(-2147483648);
_lbls = (anywheresoftware.b4a.objects.LabelWrapper[])(_visiblerows.Get((Object)(_row)));Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 193;BA.debugLine="If lbls = Null Then";
Debug.ShouldStop(1);
if (_lbls== null) { 
 BA.debugLineNum = 194;BA.debugLine="Log(\"HideRow: (null) \" & Row)";
Debug.ShouldStop(2);
__c.Log("HideRow: (null) "+BA.NumberToString(_row));
 BA.debugLineNum = 195;BA.debugLine="Return";
Debug.ShouldStop(4);
if (true) return "";
 };
 BA.debugLineNum = 197;BA.debugLine="For I = 0 To lbls.Length - 1";
Debug.ShouldStop(16);
{
final int step168 = 1;
final int limit168 = (int) (_lbls.length-1);
for (_i = (int) (0); (step168 > 0 && _i <= limit168) || (step168 < 0 && _i >= limit168); _i = ((int)(0 + _i + step168))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 198;BA.debugLine="lbls(I).RemoveView";
Debug.ShouldStop(32);
_lbls[_i].RemoveView();
 }
}Debug.locals.put("I", _i);
;
 BA.debugLineNum = 200;BA.debugLine="visibleRows.Remove(Row)";
Debug.ShouldStop(128);
_visiblerows.Remove((Object)(_row));
 BA.debugLineNum = 201;BA.debugLine="LabelsCache.Add(lbls)";
Debug.ShouldStop(256);
_labelscache.Add((Object)(_lbls));
 BA.debugLineNum = 202;BA.debugLine="End Sub";
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
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callbackmodule,String _eventname,int _vnumberofcolumns) throws Exception{
innerInitialize(_ba);
try {
		Debug.PushSubsStack("Initialize (table) ","table",1,ba,this,31);
Debug.locals.put("ba", _ba);
Debug.locals.put("CallbackModule", _callbackmodule);
Debug.locals.put("EventName", _eventname);
Debug.locals.put("vNumberOfColumns", _vnumberofcolumns);
 BA.debugLineNum = 31;BA.debugLine="Public Sub Initialize (CallbackModule As Object, E";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 32;BA.debugLine="SV.Initialize2(0, \"SV\")";
Debug.ShouldStop(-2147483648);
_sv.Initialize2(ba,(int) (0),"SV");
 BA.debugLineNum = 33;BA.debugLine="SV.Panel.Color = TableColor";
Debug.ShouldStop(1);
_sv.getPanel().setColor(_tablecolor);
 BA.debugLineNum = 34;BA.debugLine="Callback = CallbackModule";
Debug.ShouldStop(2);
_callback = _callbackmodule;
 BA.debugLineNum = 35;BA.debugLine="Event = EventName";
Debug.ShouldStop(4);
_event = _eventname;
 BA.debugLineNum = 36;BA.debugLine="innerClearAll(vNumberOfColumns)";
Debug.ShouldStop(8);
_innerclearall(_vnumberofcolumns);
 BA.debugLineNum = 37;BA.debugLine="End Sub";
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
public String  _innerclearall(int _vnumberofcolumns) throws Exception{
try {
		Debug.PushSubsStack("innerClearAll (table) ","table",1,ba,this,65);
int _i = 0;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd1 = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd2 = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd3 = null;
Debug.locals.put("vNumberOfColumns", _vnumberofcolumns);
 BA.debugLineNum = 65;BA.debugLine="Private Sub innerClearAll(vNumberOfColumns As Int)";
Debug.ShouldStop(1);
 BA.debugLineNum = 66;BA.debugLine="For i = SV.Panel.NumberOfViews -1 To 0 Step -1";
Debug.ShouldStop(2);
{
final int step55 = (int) (-1);
final int limit55 = (int) (0);
for (_i = (int) (_sv.getPanel().getNumberOfViews()-1); (step55 > 0 && _i <= limit55) || (step55 < 0 && _i >= limit55); _i = ((int)(0 + _i + step55))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 67;BA.debugLine="SV.Panel.RemoveViewAt(i)";
Debug.ShouldStop(4);
_sv.getPanel().RemoveViewAt(_i);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 69;BA.debugLine="NumberOfColumns = vNumberOfColumns";
Debug.ShouldStop(16);
_numberofcolumns = _vnumberofcolumns;
 BA.debugLineNum = 70;BA.debugLine="Dim Drawable1(NumberOfColumns) As Object";
Debug.ShouldStop(32);
_drawable1 = new Object[_numberofcolumns];
{
int d0 = _drawable1.length;
for (int i0 = 0;i0 < d0;i0++) {
_drawable1[i0] = new Object();
}
}
;
 BA.debugLineNum = 71;BA.debugLine="Dim Drawable2(NumberOfColumns) As Object";
Debug.ShouldStop(64);
_drawable2 = new Object[_numberofcolumns];
{
int d0 = _drawable2.length;
for (int i0 = 0;i0 < d0;i0++) {
_drawable2[i0] = new Object();
}
}
;
 BA.debugLineNum = 72;BA.debugLine="Dim SelectedDrawable(NumberOfColumns) As Object";
Debug.ShouldStop(128);
_selecteddrawable = new Object[_numberofcolumns];
{
int d0 = _selecteddrawable.length;
for (int i0 = 0;i0 < d0;i0++) {
_selecteddrawable[i0] = new Object();
}
}
;
 BA.debugLineNum = 73;BA.debugLine="For i = 0 To NumberOfColumns - 1";
Debug.ShouldStop(256);
{
final int step62 = 1;
final int limit62 = (int) (_numberofcolumns-1);
for (_i = (int) (0); (step62 > 0 && _i <= limit62) || (step62 < 0 && _i >= limit62); _i = ((int)(0 + _i + step62))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 74;BA.debugLine="Dim cd1, cd2, cd3 As ColorDrawable";
Debug.ShouldStop(512);
_cd1 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();Debug.locals.put("cd1", _cd1);
_cd2 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();Debug.locals.put("cd2", _cd2);
_cd3 = new anywheresoftware.b4a.objects.drawable.ColorDrawable();Debug.locals.put("cd3", _cd3);
 BA.debugLineNum = 75;BA.debugLine="cd1.Initialize(Colors.White, 0)";
Debug.ShouldStop(1024);
_cd1.Initialize(__c.Colors.White,(int) (0));
 BA.debugLineNum = 76;BA.debugLine="cd2.Initialize(0xFF98F5FF, 0)";
Debug.ShouldStop(2048);
_cd2.Initialize((int) (0xff98f5ff),(int) (0));
 BA.debugLineNum = 77;BA.debugLine="cd3.Initialize(0xFF007FFF, 0)";
Debug.ShouldStop(4096);
_cd3.Initialize((int) (0xff007fff),(int) (0));
 BA.debugLineNum = 78;BA.debugLine="Drawable1(i) = cd1";
Debug.ShouldStop(8192);
_drawable1[_i] = (Object)(_cd1.getObject());
 BA.debugLineNum = 79;BA.debugLine="Drawable2(i) = cd2";
Debug.ShouldStop(16384);
_drawable2[_i] = (Object)(_cd2.getObject());
 BA.debugLineNum = 80;BA.debugLine="SelectedDrawable(i) = cd3";
Debug.ShouldStop(32768);
_selecteddrawable[_i] = (Object)(_cd3.getObject());
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 82;BA.debugLine="SV.Panel.Height = 0";
Debug.ShouldStop(131072);
_sv.getPanel().setHeight((int) (0));
 BA.debugLineNum = 83;BA.debugLine="SelectedRow = -1";
Debug.ShouldStop(262144);
_selectedrow = (int) (-1);
 BA.debugLineNum = 84;BA.debugLine="minVisibleRow = -1";
Debug.ShouldStop(524288);
_minvisiblerow = (int) (-1);
 BA.debugLineNum = 85;BA.debugLine="maxVisibleRow = 0";
Debug.ShouldStop(1048576);
_maxvisiblerow = (int) (0);
 BA.debugLineNum = 86;BA.debugLine="Data.Initialize";
Debug.ShouldStop(2097152);
_data.Initialize();
 BA.debugLineNum = 87;BA.debugLine="LabelsCache.Initialize";
Debug.ShouldStop(4194304);
_labelscache.Initialize();
 BA.debugLineNum = 88;BA.debugLine="visibleRows.Initialize";
Debug.ShouldStop(8388608);
_visiblerows.Initialize();
 BA.debugLineNum = 89;BA.debugLine="SV.ScrollPosition = 0";
Debug.ShouldStop(16777216);
_sv.setScrollPosition((int) (0));
 BA.debugLineNum = 90;BA.debugLine="DoEvents";
Debug.ShouldStop(33554432);
__c.DoEvents();
 BA.debugLineNum = 91;BA.debugLine="SV.ScrollPosition = 0";
Debug.ShouldStop(67108864);
_sv.setScrollPosition((int) (0));
 BA.debugLineNum = 92;BA.debugLine="For i = 1 To 80 'fill the cache to avoid delay on";
Debug.ShouldStop(134217728);
{
final int step81 = 1;
final int limit81 = (int) (80);
for (_i = (int) (1); (step81 > 0 && _i <= limit81) || (step81 < 0 && _i >= limit81); _i = ((int)(0 + _i + step81))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 93;BA.debugLine="LabelsCache.Add(CreateNewLabels)";
Debug.ShouldStop(268435456);
_labelscache.Add((Object)(_createnewlabels()));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 95;BA.debugLine="If visible Then";
Debug.ShouldStop(1073741824);
if (_visible) { 
 BA.debugLineNum = 96;BA.debugLine="SV_ScrollChanged(0)";
Debug.ShouldStop(-2147483648);
_sv_scrollchanged((int) (0));
 };
 BA.debugLineNum = 98;BA.debugLine="End Sub";
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
public boolean  _isrowvisible(int _row) throws Exception{
try {
		Debug.PushSubsStack("IsRowVisible (table) ","table",1,ba,this,184);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 184;BA.debugLine="Private Sub IsRowVisible(Row As Int) As Boolean";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 185;BA.debugLine="Return Row < (SV.ScrollPosition + SV.Height) / (R";
Debug.ShouldStop(16777216);
if (true) return _row<(_sv.getScrollPosition()+_sv.getHeight())/(double)(_rowheight+1) && _row>_sv.getScrollPosition()/(double)_rowheight;
 BA.debugLineNum = 187;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _jumptorow(int _row) throws Exception{
try {
		Debug.PushSubsStack("JumpToRow (table) ","table",1,ba,this,314);
Debug.locals.put("Row", _row);
 BA.debugLineNum = 314;BA.debugLine="Public Sub JumpToRow(Row As Int)";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 315;BA.debugLine="SV.ScrollPosition = Row * RowHeight";
Debug.ShouldStop(67108864);
_sv.setScrollPosition((int) (_row*_rowheight));
 BA.debugLineNum = 316;BA.debugLine="End Sub";
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
public String  _loadtablefromcsv(String _dir,String _filename,boolean _headersexist) throws Exception{
try {
		Debug.PushSubsStack("LoadTableFromCSV (table) ","table",1,ba,this,320);
anywheresoftware.b4a.objects.collections.List _list1 = null;
String[] _h = null;
anywheresoftware.b4a.objects.collections.List _headers = null;
int _i = 0;
String[] _firstrow = null;
String[] _row = null;
Debug.locals.put("Dir", _dir);
Debug.locals.put("Filename", _filename);
Debug.locals.put("HeadersExist", _headersexist);
 BA.debugLineNum = 320;BA.debugLine="Public Sub LoadTableFromCSV(Dir As String, Filenam";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 322;BA.debugLine="Dim List1 As List";
Debug.ShouldStop(2);
_list1 = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("List1", _list1);
 BA.debugLineNum = 323;BA.debugLine="Dim h() As String";
Debug.ShouldStop(4);
_h = new String[(int) (0)];
java.util.Arrays.fill(_h,"");Debug.locals.put("h", _h);
 BA.debugLineNum = 324;BA.debugLine="If HeadersExist Then";
Debug.ShouldStop(8);
if (_headersexist) { 
 BA.debugLineNum = 325;BA.debugLine="Dim headers As List";
Debug.ShouldStop(16);
_headers = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("headers", _headers);
 BA.debugLineNum = 326;BA.debugLine="List1 = StringUtils1.LoadCSV2(Dir, Filename, \",\"";
Debug.ShouldStop(32);
_list1 = _stringutils1.LoadCSV2(_dir,_filename,BA.ObjectToChar(","),_headers);Debug.locals.put("List1", _list1);
 BA.debugLineNum = 327;BA.debugLine="Dim h(headers.Size) As String";
Debug.ShouldStop(64);
_h = new String[_headers.getSize()];
java.util.Arrays.fill(_h,"");Debug.locals.put("h", _h);
 BA.debugLineNum = 328;BA.debugLine="For i = 0 To headers.Size - 1";
Debug.ShouldStop(128);
{
final int step282 = 1;
final int limit282 = (int) (_headers.getSize()-1);
for (_i = (int) (0); (step282 > 0 && _i <= limit282) || (step282 < 0 && _i >= limit282); _i = ((int)(0 + _i + step282))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 329;BA.debugLine="h(i) = headers.Get(i)";
Debug.ShouldStop(256);
_h[_i] = BA.ObjectToString(_headers.Get(_i));Debug.locals.put("h", _h);
 }
}Debug.locals.put("i", _i);
;
 }else {
 BA.debugLineNum = 332;BA.debugLine="List1 = StringUtils1.LoadCSV(Dir, Filename, \",\")";
Debug.ShouldStop(2048);
_list1 = _stringutils1.LoadCSV(_dir,_filename,BA.ObjectToChar(","));Debug.locals.put("List1", _list1);
 BA.debugLineNum = 333;BA.debugLine="Dim firstRow() As String";
Debug.ShouldStop(4096);
_firstrow = new String[(int) (0)];
java.util.Arrays.fill(_firstrow,"");Debug.locals.put("firstRow", _firstrow);
 BA.debugLineNum = 334;BA.debugLine="firstRow = List1.Get(0)";
Debug.ShouldStop(8192);
_firstrow = (String[])(_list1.Get((int) (0)));Debug.locals.put("firstRow", _firstrow);
 BA.debugLineNum = 335;BA.debugLine="Dim h(firstRow.Length)";
Debug.ShouldStop(16384);
_h = new String[_firstrow.length];
java.util.Arrays.fill(_h,"");Debug.locals.put("h", _h);
 BA.debugLineNum = 336;BA.debugLine="For i = 0 To firstRow.Length - 1";
Debug.ShouldStop(32768);
{
final int step290 = 1;
final int limit290 = (int) (_firstrow.length-1);
for (_i = (int) (0); (step290 > 0 && _i <= limit290) || (step290 < 0 && _i >= limit290); _i = ((int)(0 + _i + step290))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 337;BA.debugLine="h(i) = \"Col\" & (i + 1)";
Debug.ShouldStop(65536);
_h[_i] = "Col"+BA.NumberToString((_i+1));Debug.locals.put("h", _h);
 }
}Debug.locals.put("i", _i);
;
 };
 BA.debugLineNum = 340;BA.debugLine="innerClearAll(h.Length)";
Debug.ShouldStop(524288);
_innerclearall(_h.length);
 BA.debugLineNum = 341;BA.debugLine="ColumnWidth = SV.Width / NumberOfColumns";
Debug.ShouldStop(1048576);
_columnwidth = (int) (_sv.getWidth()/(double)_numberofcolumns);
 BA.debugLineNum = 342;BA.debugLine="SetHeader(h)";
Debug.ShouldStop(2097152);
_setheader(_h);
 BA.debugLineNum = 343;BA.debugLine="For i = 0 To List1.Size - 1";
Debug.ShouldStop(4194304);
{
final int step297 = 1;
final int limit297 = (int) (_list1.getSize()-1);
for (_i = (int) (0); (step297 > 0 && _i <= limit297) || (step297 < 0 && _i >= limit297); _i = ((int)(0 + _i + step297))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 344;BA.debugLine="Dim row() As String";
Debug.ShouldStop(8388608);
_row = new String[(int) (0)];
java.util.Arrays.fill(_row,"");Debug.locals.put("row", _row);
 BA.debugLineNum = 345;BA.debugLine="row = List1.Get(i)";
Debug.ShouldStop(16777216);
_row = (String[])(_list1.Get(_i));Debug.locals.put("row", _row);
 BA.debugLineNum = 346;BA.debugLine="AddRow(row)";
Debug.ShouldStop(33554432);
_addrow(_row);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 348;BA.debugLine="End Sub";
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
public String  _savetabletocsv(String _dir,String _filename) throws Exception{
try {
		Debug.PushSubsStack("SaveTableToCSV (table) ","table",1,ba,this,351);
String[] _headers = null;
int _i = 0;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
Debug.locals.put("Dir", _dir);
Debug.locals.put("Filename", _filename);
 BA.debugLineNum = 351;BA.debugLine="Public Sub SaveTableToCSV(Dir As String, Filename";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 352;BA.debugLine="Dim headers(NumberOfColumns) As String";
Debug.ShouldStop(-2147483648);
_headers = new String[_numberofcolumns];
java.util.Arrays.fill(_headers,"");Debug.locals.put("headers", _headers);
 BA.debugLineNum = 353;BA.debugLine="For i = 0 To headers.Length - 1";
Debug.ShouldStop(1);
{
final int step305 = 1;
final int limit305 = (int) (_headers.length-1);
for (_i = (int) (0); (step305 > 0 && _i <= limit305) || (step305 < 0 && _i >= limit305); _i = ((int)(0 + _i + step305))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 354;BA.debugLine="Dim l As Label";
Debug.ShouldStop(2);
_l = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("l", _l);
 BA.debugLineNum = 355;BA.debugLine="l = Header.GetView(i)";
Debug.ShouldStop(4);
_l.setObject((android.widget.TextView)(_header.GetView(_i).getObject()));
 BA.debugLineNum = 356;BA.debugLine="headers(i) = l.Text";
Debug.ShouldStop(8);
_headers[_i] = _l.getText();Debug.locals.put("headers", _headers);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 358;BA.debugLine="StringUtils1.SaveCSV2(Dir, Filename, \",\", Data, h";
Debug.ShouldStop(32);
_stringutils1.SaveCSV2(_dir,_filename,BA.ObjectToChar(","),_data,anywheresoftware.b4a.keywords.Common.ArrayToList(_headers));
 BA.debugLineNum = 359;BA.debugLine="End Sub";
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
public String  _selectrow(int _row) throws Exception{
try {
		Debug.PushSubsStack("SelectRow (table) ","table",1,ba,this,293);
int _prev = 0;
int _col = 0;
Debug.locals.put("Row", _row);
 BA.debugLineNum = 293;BA.debugLine="Private Sub SelectRow(Row As Int)";
Debug.ShouldStop(16);
 BA.debugLineNum = 294;BA.debugLine="Dim prev As Int";
Debug.ShouldStop(32);
_prev = 0;Debug.locals.put("prev", _prev);
 BA.debugLineNum = 295;BA.debugLine="prev = SelectedRow";
Debug.ShouldStop(64);
_prev = _selectedrow;Debug.locals.put("prev", _prev);
 BA.debugLineNum = 296;BA.debugLine="SelectedRow = Row";
Debug.ShouldStop(128);
_selectedrow = _row;
 BA.debugLineNum = 298;BA.debugLine="If prev > -1 Then";
Debug.ShouldStop(512);
if (_prev>-1) { 
 BA.debugLineNum = 299;BA.debugLine="If visibleRows.ContainsKey(prev) Then";
Debug.ShouldStop(1024);
if (_visiblerows.ContainsKey((Object)(_prev))) { 
 BA.debugLineNum = 300;BA.debugLine="HideRow(prev)";
Debug.ShouldStop(2048);
_hiderow(_prev);
 BA.debugLineNum = 301;BA.debugLine="ShowRow(prev)";
Debug.ShouldStop(4096);
_showrow(_prev);
 };
 };
 BA.debugLineNum = 304;BA.debugLine="SelectedRow = Row";
Debug.ShouldStop(32768);
_selectedrow = _row;
 BA.debugLineNum = 305;BA.debugLine="For col = 0 To NumberOfColumns - 1";
Debug.ShouldStop(65536);
{
final int step265 = 1;
final int limit265 = (int) (_numberofcolumns-1);
for (_col = (int) (0); (step265 > 0 && _col <= limit265) || (step265 < 0 && _col >= limit265); _col = ((int)(0 + _col + step265))) {
Debug.locals.put("col", _col);
 BA.debugLineNum = 306;BA.debugLine="If visibleRows.ContainsKey(SelectedRow) Then";
Debug.ShouldStop(131072);
if (_visiblerows.ContainsKey((Object)(_selectedrow))) { 
 BA.debugLineNum = 307;BA.debugLine="HideRow(SelectedRow)";
Debug.ShouldStop(262144);
_hiderow(_selectedrow);
 BA.debugLineNum = 308;BA.debugLine="ShowRow(SelectedRow)";
Debug.ShouldStop(524288);
_showrow(_selectedrow);
 };
 }
}Debug.locals.put("col", _col);
;
 BA.debugLineNum = 311;BA.debugLine="End Sub";
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
public String  _setcolumnswidths(int[] _widths) throws Exception{
try {
		Debug.PushSubsStack("SetColumnsWidths (table) ","table",1,ba,this,46);
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
int _i = 0;
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
int _lbl = 0;
Debug.locals.put("Widths", _widths);
 BA.debugLineNum = 46;BA.debugLine="Public Sub SetColumnsWidths(Widths() As Int)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 47;BA.debugLine="Dim v As View";
Debug.ShouldStop(16384);
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();Debug.locals.put("v", _v);
 BA.debugLineNum = 48;BA.debugLine="For i = 0 To Widths.Length - 1";
Debug.ShouldStop(32768);
{
final int step39 = 1;
final int limit39 = (int) (_widths.length-1);
for (_i = (int) (0); (step39 > 0 && _i <= limit39) || (step39 < 0 && _i >= limit39); _i = ((int)(0 + _i + step39))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 49;BA.debugLine="v = Header.GetView(i)";
Debug.ShouldStop(65536);
_v = _header.GetView(_i);Debug.locals.put("v", _v);
 BA.debugLineNum = 50;BA.debugLine="v.Width = Widths(i) - 1dip";
Debug.ShouldStop(131072);
_v.setWidth((int) (_widths[_i]-__c.DipToCurrent((int) (1))));
 BA.debugLineNum = 51;BA.debugLine="If i > 0 Then";
Debug.ShouldStop(262144);
if (_i>0) { 
 BA.debugLineNum = 52;BA.debugLine="v.Left = Header.GetView(i-1).Left + Widths(i-1)";
Debug.ShouldStop(524288);
_v.setLeft((int) (_header.GetView((int) (_i-1)).getLeft()+_widths[(int) (_i-1)]+__c.DipToCurrent((int) (1))));
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 55;BA.debugLine="Dim lbls() As Label";
Debug.ShouldStop(4194304);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[(int) (0)];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 56;BA.debugLine="For i = 0 To visibleRows.Size - 1";
Debug.ShouldStop(8388608);
{
final int step47 = 1;
final int limit47 = (int) (_visiblerows.getSize()-1);
for (_i = (int) (0); (step47 > 0 && _i <= limit47) || (step47 < 0 && _i >= limit47); _i = ((int)(0 + _i + step47))) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 57;BA.debugLine="lbls = visibleRows.GetValueAt(i)";
Debug.ShouldStop(16777216);
_lbls = (anywheresoftware.b4a.objects.LabelWrapper[])(_visiblerows.GetValueAt(_i));Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 58;BA.debugLine="For lbl = 0 To lbls.Length - 1";
Debug.ShouldStop(33554432);
{
final int step49 = 1;
final int limit49 = (int) (_lbls.length-1);
for (_lbl = (int) (0); (step49 > 0 && _lbl <= limit49) || (step49 < 0 && _lbl >= limit49); _lbl = ((int)(0 + _lbl + step49))) {
Debug.locals.put("lbl", _lbl);
 BA.debugLineNum = 59;BA.debugLine="lbls(lbl).SetLayout(Header.GetView(lbl).Left, l";
Debug.ShouldStop(67108864);
_lbls[_lbl].SetLayout(_header.GetView(_lbl).getLeft(),_lbls[_lbl].getTop(),_header.GetView(_lbl).getWidth(),_rowheight);
 }
}Debug.locals.put("lbl", _lbl);
;
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 63;BA.debugLine="End Sub";
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
public String  _setheader(String[] _values) throws Exception{
try {
		Debug.PushSubsStack("SetHeader (table) ","table",1,ba,this,238);
int _i = 0;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
Debug.locals.put("Values", _values);
 BA.debugLineNum = 238;BA.debugLine="Public Sub SetHeader(Values() As String)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 239;BA.debugLine="For I = Header.NumberOfViews - 1 To 0 Step -1";
Debug.ShouldStop(16384);
{
final int step205 = (int) (-1);
final int limit205 = (int) (0);
for (_i = (int) (_header.getNumberOfViews()-1); (step205 > 0 && _i <= limit205) || (step205 < 0 && _i >= limit205); _i = ((int)(0 + _i + step205))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 240;BA.debugLine="Header.RemoveViewAt(I)";
Debug.ShouldStop(32768);
_header.RemoveViewAt(_i);
 }
}Debug.locals.put("I", _i);
;
 BA.debugLineNum = 242;BA.debugLine="For I = 0 To NumberOfColumns - 1";
Debug.ShouldStop(131072);
{
final int step208 = 1;
final int limit208 = (int) (_numberofcolumns-1);
for (_i = (int) (0); (step208 > 0 && _i <= limit208) || (step208 < 0 && _i >= limit208); _i = ((int)(0 + _i + step208))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 243;BA.debugLine="Dim l As Label";
Debug.ShouldStop(262144);
_l = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("l", _l);
 BA.debugLineNum = 244;BA.debugLine="l.Initialize(\"header\")";
Debug.ShouldStop(524288);
_l.Initialize(ba,"header");
 BA.debugLineNum = 245;BA.debugLine="l.Gravity = Gravity.CENTER";
Debug.ShouldStop(1048576);
_l.setGravity(__c.Gravity.CENTER);
 BA.debugLineNum = 246;BA.debugLine="l.TextSize = FontSize";
Debug.ShouldStop(2097152);
_l.setTextSize(_fontsize);
 BA.debugLineNum = 247;BA.debugLine="l.Color = HeaderColor";
Debug.ShouldStop(4194304);
_l.setColor(_headercolor);
 BA.debugLineNum = 248;BA.debugLine="l.TextColor = HeaderFontColor";
Debug.ShouldStop(8388608);
_l.setTextColor(_headerfontcolor);
 BA.debugLineNum = 249;BA.debugLine="l.Text = Values(I)";
Debug.ShouldStop(16777216);
_l.setText((Object)(_values[_i]));
 BA.debugLineNum = 250;BA.debugLine="l.Tag = I";
Debug.ShouldStop(33554432);
_l.setTag((Object)(_i));
 BA.debugLineNum = 251;BA.debugLine="Header.AddView(l, ColumnWidth * I, 0, ColumnWidt";
Debug.ShouldStop(67108864);
_header.AddView((android.view.View)(_l.getObject()),(int) (_columnwidth*_i),(int) (0),(int) (_columnwidth-__c.DipToCurrent((int) (1))),_rowheight);
 }
}Debug.locals.put("I", _i);
;
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
public String  _setvalue(int _col,int _row,String _value) throws Exception{
try {
		Debug.PushSubsStack("SetValue (table) ","table",1,ba,this,282);
String[] _values = null;
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
Debug.locals.put("Col", _col);
Debug.locals.put("Row", _row);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 282;BA.debugLine="Public Sub SetValue(Col As Int, Row As Int, Value";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 283;BA.debugLine="Dim values() As String";
Debug.ShouldStop(67108864);
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");Debug.locals.put("values", _values);
 BA.debugLineNum = 284;BA.debugLine="values = Data.Get(Row)";
Debug.ShouldStop(134217728);
_values = (String[])(_data.Get(_row));Debug.locals.put("values", _values);
 BA.debugLineNum = 285;BA.debugLine="values(Col) = Value";
Debug.ShouldStop(268435456);
_values[_col] = _value;Debug.locals.put("values", _values);
 BA.debugLineNum = 286;BA.debugLine="If visibleRows.ContainsKey(Row) Then";
Debug.ShouldStop(536870912);
if (_visiblerows.ContainsKey((Object)(_row))) { 
 BA.debugLineNum = 287;BA.debugLine="Dim lbls() As Label";
Debug.ShouldStop(1073741824);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[(int) (0)];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 288;BA.debugLine="lbls = visibleRows.Get(Row)";
Debug.ShouldStop(-2147483648);
_lbls = (anywheresoftware.b4a.objects.LabelWrapper[])(_visiblerows.Get((Object)(_row)));Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 289;BA.debugLine="lbls(Col).Text = Value";
Debug.ShouldStop(1);
_lbls[_col].setText((Object)(_value));
 };
 BA.debugLineNum = 291;BA.debugLine="End Sub";
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
public String  _showrow(int _row) throws Exception{
try {
		Debug.PushSubsStack("ShowRow (table) ","table",1,ba,this,160);
anywheresoftware.b4a.objects.LabelWrapper[] _lbls = null;
String[] _values = null;
Object[] _rowcolor = null;
int _i = 0;
Debug.locals.put("row", _row);
 BA.debugLineNum = 160;BA.debugLine="Private Sub ShowRow(row As Int)";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 161;BA.debugLine="If visibleRows.ContainsKey(row) Then Return";
Debug.ShouldStop(1);
if (_visiblerows.ContainsKey((Object)(_row))) { 
if (true) return "";};
 BA.debugLineNum = 163;BA.debugLine="Dim lbls() As Label";
Debug.ShouldStop(4);
_lbls = new anywheresoftware.b4a.objects.LabelWrapper[(int) (0)];
{
int d0 = _lbls.length;
for (int i0 = 0;i0 < d0;i0++) {
_lbls[i0] = new anywheresoftware.b4a.objects.LabelWrapper();
}
}
;Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 164;BA.debugLine="Dim values() As String";
Debug.ShouldStop(8);
_values = new String[(int) (0)];
java.util.Arrays.fill(_values,"");Debug.locals.put("values", _values);
 BA.debugLineNum = 165;BA.debugLine="lbls = GetLabels(row)";
Debug.ShouldStop(16);
_lbls = _getlabels(_row);Debug.locals.put("lbls", _lbls);
 BA.debugLineNum = 166;BA.debugLine="values = Data.Get(row)";
Debug.ShouldStop(32);
_values = (String[])(_data.Get(_row));Debug.locals.put("values", _values);
 BA.debugLineNum = 167;BA.debugLine="visibleRows.Put(row, lbls)";
Debug.ShouldStop(64);
_visiblerows.Put((Object)(_row),(Object)(_lbls));
 BA.debugLineNum = 168;BA.debugLine="Dim rowColor() As Object";
Debug.ShouldStop(128);
_rowcolor = new Object[(int) (0)];
{
int d0 = _rowcolor.length;
for (int i0 = 0;i0 < d0;i0++) {
_rowcolor[i0] = new Object();
}
}
;Debug.locals.put("rowColor", _rowcolor);
 BA.debugLineNum = 169;BA.debugLine="If row = SelectedRow Then";
Debug.ShouldStop(256);
if (_row==_selectedrow) { 
 BA.debugLineNum = 170;BA.debugLine="rowColor = SelectedDrawable";
Debug.ShouldStop(512);
_rowcolor = _selecteddrawable;Debug.locals.put("rowColor", _rowcolor);
 }else 
{ BA.debugLineNum = 171;BA.debugLine="Else If row Mod 2 = 0 Then";
Debug.ShouldStop(1024);
if (_row%2==0) { 
 BA.debugLineNum = 172;BA.debugLine="rowColor = Drawable1";
Debug.ShouldStop(2048);
_rowcolor = _drawable1;Debug.locals.put("rowColor", _rowcolor);
 }else {
 BA.debugLineNum = 174;BA.debugLine="rowColor = Drawable2";
Debug.ShouldStop(8192);
_rowcolor = _drawable2;Debug.locals.put("rowColor", _rowcolor);
 }};
 BA.debugLineNum = 176;BA.debugLine="For I = 0 To lbls.Length - 1";
Debug.ShouldStop(32768);
{
final int step152 = 1;
final int limit152 = (int) (_lbls.length-1);
for (_i = (int) (0); (step152 > 0 && _i <= limit152) || (step152 < 0 && _i >= limit152); _i = ((int)(0 + _i + step152))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 177;BA.debugLine="SV.Panel.AddView(lbls(I), Header.GetView(I).Left";
Debug.ShouldStop(65536);
_sv.getPanel().AddView((android.view.View)(_lbls[_i].getObject()),_header.GetView(_i).getLeft(),(int) (_row*_rowheight),_header.GetView(_i).getWidth(),(int) (_rowheight-__c.DipToCurrent((int) (1))));
 BA.debugLineNum = 179;BA.debugLine="lbls(I).Text = values(I)";
Debug.ShouldStop(262144);
_lbls[_i].setText((Object)(_values[_i]));
 BA.debugLineNum = 180;BA.debugLine="lbls(I).Background = rowColor(I)";
Debug.ShouldStop(524288);
_lbls[_i].setBackground((android.graphics.drawable.Drawable)(_rowcolor[_i]));
 }
}Debug.locals.put("I", _i);
;
 BA.debugLineNum = 182;BA.debugLine="End Sub";
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
public String  _sv_scrollchanged(int _position) throws Exception{
try {
		Debug.PushSubsStack("SV_ScrollChanged (table) ","table",1,ba,this,100);
int _currentmin = 0;
int _currentmax = 0;
int _i = 0;
Debug.locals.put("Position", _position);
 BA.debugLineNum = 100;BA.debugLine="Private Sub SV_ScrollChanged(Position As Int)";
Debug.ShouldStop(8);
 BA.debugLineNum = 101;BA.debugLine="Dim currentMin, currentMax As Int";
Debug.ShouldStop(16);
_currentmin = 0;Debug.locals.put("currentMin", _currentmin);
_currentmax = 0;Debug.locals.put("currentMax", _currentmax);
 BA.debugLineNum = 102;BA.debugLine="currentMin = Max(0, Position / RowHeight - 30)";
Debug.ShouldStop(32);
_currentmin = (int) (__c.Max(0,_position/(double)_rowheight-30));Debug.locals.put("currentMin", _currentmin);
 BA.debugLineNum = 103;BA.debugLine="currentMax = Min(Data.Size - 1, (Position + SV.He";
Debug.ShouldStop(64);
_currentmax = (int) (__c.Min(_data.getSize()-1,(_position+_sv.getHeight())/(double)_rowheight+30));Debug.locals.put("currentMax", _currentmax);
 BA.debugLineNum = 104;BA.debugLine="If minVisibleRow > -1 Then";
Debug.ShouldStop(128);
if (_minvisiblerow>-1) { 
 BA.debugLineNum = 105;BA.debugLine="If minVisibleRow < currentMin Then";
Debug.ShouldStop(256);
if (_minvisiblerow<_currentmin) { 
 BA.debugLineNum = 107;BA.debugLine="For I = minVisibleRow To Min(currentMin - 1, ma";
Debug.ShouldStop(1024);
{
final int step94 = 1;
final int limit94 = (int) (__c.Min(_currentmin-1,_maxvisiblerow));
for (_i = _minvisiblerow; (step94 > 0 && _i <= limit94) || (step94 < 0 && _i >= limit94); _i = ((int)(0 + _i + step94))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 108;BA.debugLine="HideRow(I)";
Debug.ShouldStop(2048);
_hiderow(_i);
 }
}Debug.locals.put("I", _i);
;
 }else 
{ BA.debugLineNum = 110;BA.debugLine="Else If minVisibleRow > currentMin Then";
Debug.ShouldStop(8192);
if (_minvisiblerow>_currentmin) { 
 BA.debugLineNum = 112;BA.debugLine="For I = currentMin To Min(minVisibleRow - 1, cu";
Debug.ShouldStop(32768);
{
final int step98 = 1;
final int limit98 = (int) (__c.Min(_minvisiblerow-1,_currentmax));
for (_i = _currentmin; (step98 > 0 && _i <= limit98) || (step98 < 0 && _i >= limit98); _i = ((int)(0 + _i + step98))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 113;BA.debugLine="ShowRow(I)";
Debug.ShouldStop(65536);
_showrow(_i);
 }
}Debug.locals.put("I", _i);
;
 }};
 BA.debugLineNum = 116;BA.debugLine="If maxVisibleRow > currentMax Then";
Debug.ShouldStop(524288);
if (_maxvisiblerow>_currentmax) { 
 BA.debugLineNum = 118;BA.debugLine="For I = maxVisibleRow To Max(currentMax + 1, mi";
Debug.ShouldStop(2097152);
{
final int step103 = (int) (-1);
final int limit103 = (int) (__c.Max(_currentmax+1,_minvisiblerow));
for (_i = _maxvisiblerow; (step103 > 0 && _i <= limit103) || (step103 < 0 && _i >= limit103); _i = ((int)(0 + _i + step103))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 119;BA.debugLine="HideRow(I)";
Debug.ShouldStop(4194304);
_hiderow(_i);
 }
}Debug.locals.put("I", _i);
;
 }else 
{ BA.debugLineNum = 121;BA.debugLine="Else If maxVisibleRow < currentMax Then";
Debug.ShouldStop(16777216);
if (_maxvisiblerow<_currentmax) { 
 BA.debugLineNum = 123;BA.debugLine="For I = currentMax To Max(maxVisibleRow + 1, cu";
Debug.ShouldStop(67108864);
{
final int step107 = (int) (-1);
final int limit107 = (int) (__c.Max(_maxvisiblerow+1,_currentmin));
for (_i = _currentmax; (step107 > 0 && _i <= limit107) || (step107 < 0 && _i >= limit107); _i = ((int)(0 + _i + step107))) {
Debug.locals.put("I", _i);
 BA.debugLineNum = 124;BA.debugLine="ShowRow(I)";
Debug.ShouldStop(134217728);
_showrow(_i);
 }
}Debug.locals.put("I", _i);
;
 }};
 };
 BA.debugLineNum = 128;BA.debugLine="minVisibleRow = currentMin";
Debug.ShouldStop(-2147483648);
_minvisiblerow = _currentmin;
 BA.debugLineNum = 129;BA.debugLine="maxVisibleRow = currentMax";
Debug.ShouldStop(1);
_maxvisiblerow = _currentmax;
 BA.debugLineNum = 130;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
